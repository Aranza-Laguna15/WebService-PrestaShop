package com.example.user.e_gigi.controlador;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.e_gigi.controlador.activity.ECommerce;
import com.example.user.e_gigi.controlador.fragments.FragmentNous;
import com.example.user.e_gigi.controlador.fragments.FragmentProductos;

/**
 * Created by User on 14/02/2017.
 */

public class TabAdapter extends FragmentPagerAdapter {


   // private final Product[] items;
   final int PAGE_COUNT = 3;
    boolean fragmentTransaction = false;
    private String tabTitles[] =
            new String[] {"E-GiGi", "Productos", "E-Commerce"};


    public TabAdapter(FragmentManager fm /* ,Product[] items*/) {
        super(fm);
    }

    @Override
    public int getCount() {
       // return items.length -1;
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag= FragmentNous.newInstance();
                fragmentTransaction = true;
                break;
            case 1:
                frag= FragmentProductos.newInstance();
                FragmentProductos frP= new FragmentProductos();
//                frP.cargarAdaptador();
                fragmentTransaction = true;
                break;
            case 2:
                frag= ECommerce.newInstance();
                fragmentTransaction = true;
                break;
        }
        return frag;
    }
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }
}
