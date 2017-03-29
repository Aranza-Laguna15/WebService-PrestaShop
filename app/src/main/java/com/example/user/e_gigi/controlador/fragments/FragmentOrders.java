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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.user.e_gigi.R;
import com.example.user.e_gigi.controlador.OrdersAdapter;
import com.example.user.e_gigi.modelo.Orders;
import com.example.user.e_gigi.tools.Constantes;
import com.example.user.e_gigi.web.SQLiteDB;
import com.example.user.e_gigi.web.SQLiteOrders;
import com.example.user.e_gigi.web.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aranza on 17/03/2017.
 */
public class FragmentOrders extends Fragment {

    private static final String TAG = FragmentOrders.class.getSimpleName();
    private OrdersAdapter adapter;
    private RecyclerView lista;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;
    SQLiteOrders sqLiteDB;
    Cursor cursor;

    public FragmentOrders() {
        // Required empty public constructor
    }

    public static FragmentOrders newInstance(){
        FragmentOrders fragmentOrders = new FragmentOrders();
        fragmentOrders.setRetainInstance(true);
        return fragmentOrders;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_orders,container,false);

        lista=(RecyclerView)v.findViewById(R.id.recycler_orders);
        lista.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshOrders);
        refreshLayout.setColorSchemeResources(
                R.color.md_pink_400,
                R.color.md_pink_600,
                R.color.md_pink_800,
                R.color.md_pink_900
        );

        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener(){
                    public void onRefresh(){
                        new AsyncRefresh().execute();
                    }
                }
        );

        sqLiteDB = new SQLiteOrders(getActivity());

        cargarOrders();
        adapter = new OrdersAdapter(cursor,getActivity());
        lista.setAdapter(adapter);
        lista.setLayoutManager(layoutManager);

        return v;
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

    private void cargarOrders(){
        new OrdersLoadTask().execute();
    }

    private class OrdersLoadTask extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... params) {
            return sqLiteDB.getAllOrders();
        }

        protected void onPostExecute(Cursor cursor){
            if(cursor != null && cursor.getCount() > 0){
               adapter.swapCursor(cursor);
            }else{
                if(conexionInternet()){
                    descargarPedidos();
                    Toast.makeText(getActivity(),"Descargando Pedidos",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"No hay conexión a internet",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
  public void descargarPedidos(){
      VolleySingleton.getInstance(getActivity()).addToRequestQueue(
              new JsonObjectRequest(
                      Request.Method.GET,
                      Constantes.GET_PEDIDOS,
                      null,
                      new Response.Listener<JSONObject>(){
                          public void onResponse(JSONObject response){
                              procesarRespuesta(response);
                              cargarOrders();
                          }
                      },
                      new Response.ErrorListener() {
                          @Override
                          public void onErrorResponse(VolleyError error) {
                              Log.e(TAG, "Error Volley" + error.toString());
                              Toast.makeText(getActivity(),"El servidor ha tardado demasiado tiempo en responder",Toast.LENGTH_SHORT).show();
                              cargarOrders();
                          }
                      }
              )
      );
  }
    public void procesarRespuesta(JSONObject response){
        try{
            String estado = response.getString("estado");
            String reference, total, date, customer, country, state,id, pay;

            switch (estado){
                case "1":
                    JSONArray order = response.getJSONArray("pedidos");
                    for (int i=0;i<order.length();i++){
                        JSONObject js=order.getJSONObject(i);
                        Orders orders = new Orders(
                              id= js.getString("id_order"),
                              reference=js.getString("reference"),
                              total=js.getString("total"),
                              pay =js.getString("payment"),
                              date=js.getString("date"),
                              customer=js.getString("customer"),
                              state=js.getString("state"),
                              country=js.getString("country")
                        );
                        chargeOrders(orders);
                    }
                    break;
                case "2":
                    String error= response.getString("mensaje");
                    Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                    break;
            }

        }catch (JSONException e){
            Log.e(TAG+" Respuesta", e.getMessage());
        }
    }

    private void chargeOrders(Orders orders){
        sqLiteDB = new SQLiteOrders(getActivity());
        try{
            sqLiteDB.getWritableDatabase();
            sqLiteDB.saveOrders(orders);
        }catch (SQLiteException e){
            Log.e("ERROR INSERTAR PEDIDOS", e.getLocalizedMessage());
        }
    }

    private class AsyncRefresh extends AsyncTask<String, String, String>{
        static  final int Duracion = 3*1000;

        @Override
        protected String doInBackground(String... params) {
            try{
                Thread.sleep(Duracion);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String res) {
            if (conexionInternet()){
                adapter.clear();
                descargarPedidos();
                refreshLayout.setRefreshing(false);
                cargarOrders();
                Toast.makeText(getActivity(),"Actualización completa",Toast.LENGTH_SHORT).show();
            }else{
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(),"Actualización fallida",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
