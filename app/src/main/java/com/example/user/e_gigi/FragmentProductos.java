package com.example.user.e_gigi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        View view= inflater.inflate(R.layout.fragment_productos, container, false);
        imageView=(ImageView)view.findViewById(R.id.product);
        view.findViewById(R.id.go_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               addNextFragment(imageView);
            }
        });
        return view;
    }

    private void addNextFragment( ImageView imageView){
        DetProduct detProduct = DetProduct.newInstance();
        Slide slideT=new Slide(Gravity.RIGHT);
        slideT.setDuration(getResources().getInteger(R.integer.anim_duration_medium));


        detProduct.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));
        detProduct.setAllowEnterTransitionOverlap(true);
        detProduct.setAllowReturnTransitionOverlap(true);
        detProduct.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.transitions));

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_products,detProduct)
                .addToBackStack(null)
                .addSharedElement(imageView, getString(R.string.product))
                .commit();
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        cardView=(CardView)view.findViewById(R.id.cv);
    }


}
