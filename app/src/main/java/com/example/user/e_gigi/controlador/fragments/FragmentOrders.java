package com.example.user.e_gigi.controlador.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.e_gigi.R;

/**
 * Created by Aranza on 17/03/2017.
 */
public class FragmentOrders extends Fragment {


    public FragmentOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    public static Fragment newInstace() {
        FragmentOrders fragmentOrders = new FragmentOrders();
        fragmentOrders.setRetainInstance(true);
        return fragmentOrders;
    }
}
