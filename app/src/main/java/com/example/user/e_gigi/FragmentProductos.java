package com.example.user.e_gigi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentProductos extends Fragment {
    CardView cardView;
    ImageView imageView;

   public FragmentProductos() {
        // Required empty public constructor
    }


public static FragmentProductos newInstance(){
    FragmentProductos fragmentProductos = new FragmentProductos();
    fragmentProductos.setRetainInstance(true);
    return fragmentProductos;
}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_content_product, container, false);
        imageView=(ImageView)view.findViewById(R.id.product);
/*        view.findViewById(R.id.shared_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });*/
        return view;
    }



    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        cardView=(CardView)view.findViewById(R.id.cv);
    }


}
