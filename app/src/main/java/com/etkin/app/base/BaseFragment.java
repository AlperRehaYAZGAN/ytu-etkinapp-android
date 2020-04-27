package com.etkin.app.base;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.etkin.app.MainActivity;


public abstract class BaseFragment extends Fragment {

    public Context mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this.getContext();
    }
}
