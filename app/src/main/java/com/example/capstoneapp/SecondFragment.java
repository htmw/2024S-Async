package com.example.capstoneapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.fragment.NavHostFragment;

import com.example.capstoneapp.databinding.FragmentSecondBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ImageView imageTaken;
    private ImageViewModel viewModel;
    //new changes
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    private ExecutorService cameraExecutor;
    private ProcessCameraProvider cameraProvider;
    private Preview preview;
    private PreviewView cameraPreview;
    private DatagramSocket udpSocket;
    CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        //imageTaken = binding.imageView2;
        //viewModel = new ViewModelProvider(requireActivity()).get(ImageViewModel.class);
        //imageTaken.setImageBitmap(viewModel.getImageView());
        cameraPreview = binding.cameraPreview;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cameraExecutor = Executors.newSingleThreadExecutor();
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_PERMISSIONS
            );
        }
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();

                preview = new Preview.Builder().build();
                preview.setSurfaceProvider(cameraPreview.getSurfaceProvider());



                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle((LifecycleOwner) requireContext(), cameraSelector, preview);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ActivityCompat.getMainExecutor(requireContext()));

        try {
            udpSocket = new DatagramSocket();
            startStreaming();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startStreaming() {
        cameraExecutor.execute(() -> {
            try {
                Size size = new Size(cameraPreview.getWidth(), cameraPreview.getHeight());
                ByteBuffer byteBuffer = ByteBuffer.allocate(size.getWidth() * size.getHeight() * 3);

                // Set up ImageAnalysis use case
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setTargetResolution(size)
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, new ImageAnalysis.Analyzer() {
                    @Override
                    public void analyze(@NonNull ImageProxy image) {
                        try {
                            // Extract image data from the ImageProxy
                            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                            buffer.rewind();
                            byte[] byteArray = new byte[buffer.remaining()];
                            buffer.get(byteArray);

                            // Create UDP packet
                            InetAddress serverAddress = InetAddress.getByName("YOUR_SERVER_IP");
                            int serverPort = 1234;
                            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, serverAddress, serverPort);

                            // Send UDP packet
                            udpSocket.send(packet);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            // Close the ImageProxy to release resources
                            image.close();
                        }
                    }
                });

                // Bind the ImageAnalysis use case to the camera lifecycle
                cameraProvider.bindToLifecycle((LifecycleOwner) requireContext(), cameraSelector, imageAnalysis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private boolean allPermissionsGranted() {
        return ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
        cameraExecutor.shutdown();
    }

}