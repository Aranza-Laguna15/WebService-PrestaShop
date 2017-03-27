package com.example.user.e_gigi.controlador.fragments;

import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.user.e_gigi.web.SQLiteDB;
import com.example.user.e_gigi.web.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aranza on 07/02/2017.
 * Clase Fragment del Producto, conexion a la BD y WebService
 */
public class FragmentProductos extends Fragment {

    private static final String TAG = FragmentProductos.class.getSimpleName();
    private ProductsAdapter adapter;
    private RecyclerView lista;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;
    SQLiteDB sqLiteDB;
    Cursor cursor;

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

        //RecyclerView
        lista=(RecyclerView) view.findViewById(R.id.reciclador);
        lista.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());

        //SwipeRefreshLayout
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        refreshLayout.setColorSchemeResources(
                R.color.md_blue_400,
                R.color.md_blue_600,
                R.color.md_blue_800,
                R.color.md_blue_900
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

        //SQLiteDB
        sqLiteDB = new SQLiteDB(getActivity());

        //Carga la lista de la BD
        cargarSQL();
        adapter = new ProductsAdapter(cursor,getActivity());
        lista.setAdapter(adapter);
        lista.setLayoutManager(layoutManager);

         return view;
    }
    //Verifica que haya conexion a internet
    public boolean conexionInternet(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void cargarSQL(){
        new ProductsLoadTask().execute();
    }

    //Metodo AsyncTask para obtener todos los productos de la BD
    private class ProductsLoadTask extends AsyncTask<Void, Void, Cursor>{

    @Override
    protected Cursor doInBackground(Void... params) {
        return sqLiteDB.getAllProducts();
    }

    protected void onPostExecute(Cursor cursor){
        if(cursor != null && cursor.getCount() > 0){
            adapter.swapCursor(cursor);
        }else{
            if(conexionInternet()){
                descargarProductos();
                Toast.makeText(getActivity(),"Descargando Productos",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),"No hay conexión a internet",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
    //Realiza la peticion en Volley para conectarse con el WebService
    public void descargarProductos(){
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        Constantes.GET_PRODUCTOS,
                        null,
                        new Response.Listener<JSONObject>(){
                            public void onResponse(JSONObject response){
                                procesarRespuesta(response);
                                cargarSQL();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "Error Volley: " + error.toString());
                                Toast.makeText(getActivity(),"El servidor ha tardado demasiado tiempo en responder",Toast.LENGTH_SHORT).show();
                                cargarSQL();
                            }
                        }
                )
        );
    }
    //Procesa los datos obtenidos del WebService y los manda al metodo chargeProducts() para cargarlos a la BD
    public void procesarRespuesta(JSONObject response){
        try{
            String estado = response.getString("estado");
            String titulo, descripcion,fecha,categoria,precio,stock,idProduct,imagen;

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
                         stock=js.getString("stock"),
                         imagen=js.getString("link_image")
                       );
                      chargeProducts(products);
                }
                    break;
                case "2":
                    String mensaje2= response.getString("mensaje");
                    Toast.makeText(getActivity(),mensaje2,Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (JSONException e) {
            Log.e(TAG,e.getLocalizedMessage());
        }
    }//END procesarRespuesta

    //Carga los productos a la BD
    private void chargeProducts(Products data) {
        sqLiteDB=new SQLiteDB(getActivity());
         try{
            sqLiteDB.getWritableDatabase();
            sqLiteDB.saveProducts(data);
        }catch (SQLiteException e){
            Log.e("ERROR AL INSERTAR: ",e.getLocalizedMessage());
        }
    }

//Metodo AsyncTask para el SwipeRefreshLayout
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
            if(conexionInternet()){
                adapter.clear();
                descargarProductos();
                refreshLayout.setRefreshing(false);
                cargarSQL();
                Toast.makeText(getActivity(),"Actualización completa",Toast.LENGTH_SHORT).show();
            }else{
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(),"Actualización fallida",Toast.LENGTH_SHORT).show();
            }
        }
    }
} //END
