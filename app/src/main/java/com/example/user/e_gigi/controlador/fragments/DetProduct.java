package com.example.user.e_gigi.controlador.fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.e_gigi.R;
import com.example.user.e_gigi.modelo.Products;
import com.example.user.e_gigi.tools.Constantes;
import com.example.user.e_gigi.web.SQLiteDB;

/**
 * Created by Aranza on 09/02/2017.
 * Clase Detalle del Producto
 */

public class DetProduct extends Fragment {
    private static final String TAG = DetProduct.class.getSimpleName();

 private ImageView cabecera;
    private TextView txt_titulo;
    private TextView txt_descripcion;
    private TextView txt_fecha;
    private TextView txt_categoria;
    private TextView txt_precio;
    private TextView txt_stock;
    private String extra;
    SQLiteDB sqLiteDB;

    public DetProduct() {
        // Required empty public constructor
    }

    public static DetProduct createInstance(String idProduct) {
        DetProduct detProduct = new DetProduct();
        Bundle bundle= new Bundle();
        bundle.putString(Constantes.EXTRA_ID,idProduct);
        detProduct.setArguments(bundle);
        return detProduct;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_detail_products, container, false);

        cabecera=(ImageView)view.findViewById(R.id.product);
        txt_titulo=(TextView)view.findViewById(R.id.det_product_name);
        txt_descripcion=(TextView)view.findViewById(R.id.descripcion);
        txt_fecha=(TextView)view.findViewById(R.id.det_date_product);
        txt_categoria=(TextView)view.findViewById(R.id.det_categoria);
        txt_precio=(TextView)view.findViewById(R.id.det_precio);
        txt_stock= (TextView)view.findViewById(R.id.det_stock);

       extra = getArguments().getString(Constantes.EXTRA_ID);

        sqLiteDB = new SQLiteDB(getActivity());

        cargarSQL();

        return view;
    }

    private void showProduct(Products products){
        txt_titulo.setText(products.getTitulo());
        txt_descripcion.setText(products.getDescripcion());
        txt_fecha.setText(products.getFecha());
        txt_precio.setText("$"+products.getPrecio());
        txt_categoria.setText(products.getCategoria());
        txt_stock.setText(products.getStock());
    }

    private void cargarSQL(){
        try{
            new ProductsLoadTask().execute();

        }catch (Exception e){
            Log.e("ERROR SQL: ",e.getLocalizedMessage());
        }
    }

    private class ProductsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            Log.e("ID",extra);
            return sqLiteDB.getProductById(extra);
        }
        protected void onPostExecute(Cursor cursor){
            if(cursor != null && cursor.moveToLast()){
                showProduct(new Products(cursor));
            }else{
                Toast.makeText(getActivity(),"Error al cargar DetalleProductos",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
