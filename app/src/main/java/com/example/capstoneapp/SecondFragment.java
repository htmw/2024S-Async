package com.example.capstoneapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.capstoneapp.databinding.FragmentSecondBinding;

import java.io.IOException;
import java.io.InputStream;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    //private ImageView imageTaken;
    //private ImageViewModel viewModel;
    private TextView textView;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        textView = binding.textviewSecond;
        //imageTaken = binding.imageView2;
        //viewModel = new ViewModelProvider(requireActivity()).get(ImageViewModel.class);
        //imageTaken.setImageBitmap(viewModel.getImageView());
        String str = "";
        try {
            InputStream inputStream = getContext().getAssets().open("howtoguide.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);

            str = new String(buffer);
        }catch (IOException e){
            e.printStackTrace();
        }

        textView.setText(str);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}