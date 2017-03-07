package com.example.user.e_gigi.controlador.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentProductos extends Fragment {

    private static final String TAG = FragmentProductos.class.getSimpleName();
    private ProductsAdapter adapter;
    private RecyclerView lista;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;
    private Spinner spinner;

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
        spinner=(Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter spiner_adapter=ArrayAdapter.createFromResource(getActivity(), R.array.categorias, android.R.layout.simple_spinner_item);
        spiner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spiner_adapter);

        lista=(RecyclerView) view.findViewById(R.id.reciclador);

        lista.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

        refreshLayout.setColorSchemeResources(
                R.color.md_blue_200,
                R.color.md_blue_300,
                R.color.md_blue_400,
                R.color.md_blue_500
        );
        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new AsyncRefresh().execute();
                    }
                }
        );
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
            String titulo, descripcion,fecha,categoria,precio,stock,idProduct;
            List<Products> data=new ArrayList<>();
            switch (estado){
                case "1":
                    JSONArray mensaje = response.getJSONArray("productos");
                   for(int i=0;i<mensaje.length();i++){
                    JSONObject js=mensaje.getJSONObject(i);
                       Products products = new Products(
                         idProduct=js.getString("id_product"),
                         titulo=js.getString("name"),
                         descripcion=js.getString("name"),
                         fecha=js.getString("date"),
                         categoria=js.getString("category"),
                         precio=js.getString("price"),
                         stock=js.getString("stock")
                       );
                       data.add(products);
                       adapter= new ProductsAdapter(data,getActivity());
                       lista.setAdapter(adapter);
                       lista.setLayoutManager(layoutManager);
                }
                    break;
                case "2":
                    String mensaje2= response.getString("mensaje");
                    Toast.makeText(getActivity(),mensaje2,Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (JSONException e) {
            Log.d(TAG,e.getMessage());
        }
    }//END procesarRespuesta

    private class AsyncRefresh extends AsyncTask<String, String, String>{
        static final int DURACION = 3 * 1000; // 3 segundos de carga

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String res){
            adapter.clear();
            cargarAdaptador();
            refreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(),"Lista actualizada",Toast.LENGTH_SHORT).show();
        }
    }

    } //END

/*
Products[] productos = gson.fromJson(mensaje.toString(),Products[].class);
                    Log.e("POPO2: ", mensaje.toString());
                    adapter=new ProductsAdapter(Arrays.asList(productos),getActivity());
                    Log.e("POPO3: ", Arrays.asList(productos).toString());
                    lista.setAdapter(adapter);
int DURACION = 3 * 1000; // 3 segundos de carga
try {
        Thread.sleep(DURACION);
        adapter.clear();
        cargarAdaptador();
        refreshLayout.setRefreshing(false);
        } catch (InterruptedException e) {
        e.printStackTrace();
        */