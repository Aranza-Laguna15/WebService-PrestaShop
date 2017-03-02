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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetProduct extends Fragment {
    private static final String TAG = DetProduct.class.getSimpleName();

 private ImageView cabecera;
    private TextView txt_titulo;
    private TextView txt_descripcion;
    private TextView txt_fecha;
    private TextView txt_categoria;
    private TextView txt_precio;
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
        txt_titulo=(TextView)view.findViewById(R.id.det_product_name);
        txt_descripcion=(TextView)view.findViewById(R.id.descripcion);
        txt_fecha=(TextView)view.findViewById(R.id.det_date_product);
        txt_categoria=(TextView)view.findViewById(R.id.det_categoria);
        txt_precio=(TextView)view.findViewById(R.id.det_precio);

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
    /*   switch (products.getCategoria()){
                        case "":
                            break;
                    }*/
    private void procesarRespuesta(JSONObject response){
        try {
            String mensaje = response.getString("estado");
            String titulo, descripcion,fecha,categoria,precio,stock,idProduct;
            switch (mensaje) {
                case "1":
                    JSONArray producto = response.getJSONArray("producto");

                        for(int i=0;i<mensaje.length();i++) {
                            JSONObject js = producto.getJSONObject(i);
                            Products products = new Products(
                                    idProduct = js.getString("id_product"),
                                    titulo = js.getString("name"),
                                    descripcion = js.getString("name"),
                                    fecha = js.getString("date"),
                                    categoria = js.getString("category"),
                                    precio = js.getString("price"),
                                    stock = js.getString("stock")
                            );
                            Log.e("POPO1: ",idProduct + " " + products.getTitulo()+ " " + descripcion+ " " + fecha + " " + precio + " " + categoria + " " + stock);


                        txt_titulo.setText(products.getTitulo());
                        txt_descripcion.setText(products.getDescripcion());
                        txt_fecha.setText(products.getFecha());
                        txt_precio.setText(products.getPrecio());
                        txt_categoria.setText(products.getCategoria());
                        }
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
            Toast.makeText(
                    getActivity(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
