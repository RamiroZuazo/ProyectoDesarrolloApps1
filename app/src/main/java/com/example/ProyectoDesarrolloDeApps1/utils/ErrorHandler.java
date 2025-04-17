package com.example.ProyectoDesarrolloDeApps1.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ProyectoDesarrolloDeApps1.ErrorFragment;
import com.example.ProyectoDesarrolloDeApps1.R;

public class ErrorHandler {
    
    public static void showError(FragmentManager fragmentManager, String errorMessage, View.OnClickListener retryListener) {
        ErrorFragment errorFragment = new ErrorFragment();
        Bundle args = new Bundle();
        args.putString("error_message", errorMessage);
        errorFragment.setArguments(args);
        errorFragment.setRetryListener(retryListener);
        
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, errorFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void showToastError(Context context, String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
    }
} 