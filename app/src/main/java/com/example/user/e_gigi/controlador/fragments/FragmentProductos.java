package com.example.user.e_gigi.controlador.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.user.e_gigi.R;
import com.example.user.e_gigi.controlador.ProductsAdapter;
import com.example.user.e_gigi.modelo.Products;
import com.example.user.e_gigi.tools.Constantes;
import com.example.user.e_gigi.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FragmentProductos extends Fragment {
    CardView cardView;

    private static final String TAG = FragmentProductos.class.getSimpleName();
    private ProductsAdapter adapter;
    private RecyclerView lista;
    private RecyclerView.LayoutManager layoutManager;
    private Gson gson = new Gson();

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
        lista=(RecyclerView) view.findViewById(R.id.reciclador);
        lista.setHasFixedSize(true);
        cargarAdaptador();

        return view;
    }

    public void cargarAdaptador(){
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        Constantes.GET_PRODUCTOS,
                        null,
                        new Response.Listener<JSONObject>(){
                            public void onResponse(JSONObject response){
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.toString());
                            }
                        }
                )
        );
    }
    public void procesarRespuesta(JSONObject response){
        try{
            String estado = response.getString("estado");
            switch (estado){
                case "1":
                    JSONArray mensaje = response.getJSONArray("productos");
                    Products[] productos = gson.fromJson(mensaje.toString(),Products[].class);
                    adapter=new ProductsAdapter(Arrays.asList(productos),getActivity());
                    lista.setAdapter(adapter);
                    break;
                case "2":
                    String mensaje2= response.getString("mensaje");
                    Toast.makeText(getActivity(),mensaje2,Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (JSONException e) {
            Log.d(TAG,e.getMessage());
        }
    }



    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        cardView=(CardView)view.findViewById(R.id.cv);
    }


}
