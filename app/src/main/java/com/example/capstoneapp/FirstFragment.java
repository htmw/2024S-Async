package com.example.capstoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.capstoneapp.databinding.FragmentFirstBinding;
import com.google.firebase.auth.FirebaseAuth;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    // Image view to show the picture taken by the camera
    private ImageView imageTaken;
    private static final int ACTIVITY_REQUEST_CODE = 1000;
    private static final int PERMISSION_REQUEST_CODE = 2000;
    private ImageViewModel viewModel;
    ImageView btnCamera;
    ImageView btnLogOut;
    ImageView btnHowToGuide;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        btnCamera = binding.imageBtnCamera;
        btnLogOut = binding.imageBtnLogOut;
        btnHowToGuide = binding.imageBtnHowToGuide;
        viewModel = new ViewModelProvider(this).get(ImageViewModel.class);

        btnLogOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this.getContext(), LoginActivity.class);
            startActivity(intent);
        });
        btnCamera.setOnClickListener(view -> {
            Intent intent = new Intent(this.getContext(), DetectorActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imageBtnHowToGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}