package com.example.user.e_gigi.controlador;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.e_gigi.R;
import com.example.user.e_gigi.controlador.activity.ProductsActivity;
import com.example.user.e_gigi.modelo.Products;

import java.util.List;

/**
 * Created by User on 27/02/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>
        implements ItemClickListener {

    private List<Products> items;
    private Context context;

    public ProductsAdapter(List<Products> items, Context context){
        this.context=context;
        this.items=items;
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_pedidos,parent,false);
        return new ProductsViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int i) {
        holder.titulo.setText(items.get(i).getTitulo());
        holder.descripcion.setText(items.get(i).getDescripcion());
        holder.fecha.setText(items.get(i).getFecha());
        holder.categoria.setText(items.get(i).getCategoria());

    }

    public void onItemClick(View view, int position) {
        ProductsActivity.launch(
                (Activity) context, items.get(position).getIdProduct());
    }

    public static class ProductsViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        public TextView titulo;
        public TextView descripcion;
        public TextView fecha;
        public TextView categoria;
        public ItemClickListener listener;

        public ProductsViewHolder(View v, ItemClickListener listener){
            super(v);
            titulo=(TextView)v.findViewById(R.id.product_name);
            descripcion=(TextView)v.findViewById(R.id.product_desc);
            fecha=(TextView)v.findViewById(R.id.date_product);
            this.listener=listener;
            v.setOnClickListener(this);
        }
        public void onClick(View v){
            listener.onItemClick(v,getAdapterPosition());
        }
    }
}

interface ItemClickListener {
    void onItemClick(View view, int position);
}
