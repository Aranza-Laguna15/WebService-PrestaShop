package com.example.user.e_gigi.controlador.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.user.e_gigi.R;
import com.example.user.e_gigi.modelo.Products;
import com.example.user.e_gigi.tools.Constantes;
import com.example.user.e_gigi.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class DetProduct extends Fragment {
    private static final String TAG = DetProduct.class.getSimpleName();

 private ImageView cabecera;
    private TextView titulo;
    private TextView descripcion;
    private TextView fecha;
    private TextView categoria;
    private TextView precio;
    private String extra;
    private Gson gson = new Gson();


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
        View view=inflater.inflate(R.layout.fragment_det_product, container, false);

        cabecera=(ImageView)view.findViewById(R.id.product);
        titulo=(TextView)view.findViewById(R.id.det_product_name);
        descripcion=(TextView)view.findViewById(R.id.product_desc);
        fecha=(TextView)view.findViewById(R.id.det_date_product);
        categoria=(TextView)view.findViewById(R.id.det_categoria);
        precio=(TextView)view.findViewById(R.id.det_precio);

        extra = getArguments().getString(Constantes.EXTRA_ID);

        cargarDatos();

        return view;
    }

    public void cargarDatos(){
        String newURL= Constantes.GET_BY_ID+"?id_product="+ extra;

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        null,
                        new Response.Listener<JSONObject>(){
                            public void onResponse(JSONObject response){
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener(){
                            public void onErrorResponse(VolleyError error){
                                Log.d(TAG, "Error Volley: "+error.getLocalizedMessage());
                            }
                        }
                )
        );
    }

    private void procesarRespuesta(JSONObject response){
        try{
            String mensaje= response.getString("estado");

            switch (mensaje){
                case "1":
                    JSONObject object = response.getJSONObject("producto");
                    Products products = gson.fromJson(object.toString(),Products.class);

                 /*   switch (products.getCategoria()){
                        case "":
                            break;
                    }*/
                    titulo.setText(products.getTitulo());
                    descripcion.setText(products.getDescripcion());
                    fecha.setText(products.getFecha());
                    precio.setText(products.getPrecio());
                    categoria.setText(products.getCategoria());
                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;

                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje3,
                            Toast.LENGTH_LONG).show();
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
