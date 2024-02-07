package com.example.capstoneapp;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

public class ImageViewModel extends ViewModel {
    private Bitmap bitmap;
    public Bitmap getImageView(){
        return bitmap;
    }

    public void setImageView(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
