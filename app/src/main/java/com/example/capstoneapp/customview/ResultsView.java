package com.example.capstoneapp.customview;


import com.example.capstoneapp.tflite.Classifier.Recognition;

import java.util.List;

public interface ResultsView {
    public void setResults(final List<Recognition> results);
}