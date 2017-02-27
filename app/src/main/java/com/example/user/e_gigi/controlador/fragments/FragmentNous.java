package com.example.user.e_gigi.controlador.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.e_gigi.R;

/**
 * Created by User on 15/02/2017.
 */

public class FragmentNous extends Fragment {

    public FragmentNous() {
        // Required empty public constructor
    }

    public static FragmentNous newInstance() {
        FragmentNous fragment = new FragmentNous();
        fragment.setRetainInstance(true);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.content_layout, container, false);

        return v;
    }
}// END
