package com.example.user.e_gigi.controlador;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.e_gigi.R;
import com.example.user.e_gigi.controlador.activity.ProductsActivity;
import com.example.user.e_gigi.modelo.Products;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.user.e_gigi.web.SQLiteDB.DATABASE_NAME;

/**
 * Created by Aranza on 27/02/2017.
 * Clase donde se enlistan los productos en el RecyclerView y se obtiene el ID para el Detalle
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>
        implements ItemClickListener {

    private Cursor items;
    private Context context;

    public ProductsAdapter(Cursor items, Context context){
        this.context=context;
        this.items=items;
    }
//Intercambia el cursor actual por uno nuevo.
    public Cursor swapCursor(final Cursor cursor){
      if(items==cursor){
          return null;
      }
        Cursor oldCursor = items;
        this.items = cursor;
        if(cursor!= null){
            this.notifyDataSetChanged();
        }
          return oldCursor;
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.getCount();
        return 0;
    }
    /*
        Permite limpiar todos los elementos del recycler
         */
    public void clear(){
        context.deleteDatabase(DATABASE_NAME);
        notifyDataSetChanged();
    }

    public Cursor getItem(int position){
        if (items != null && !items.isClosed()){
            items.moveToPosition(position);
        }
        return items;
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_products,parent,false);
        return new ProductsViewHolder(v, this);
    }

    @Override //Obtener los datos de la base y mostrarlos en el Holder
    public void onBindViewHolder(ProductsViewHolder holder, final int i){

        items.moveToPosition(i);
        String titulo, descripcion, fecha, imagen;
        titulo=items.getString(2);
        descripcion=items.getString(2);
        fecha=items.getString(4);
        imagen=items.getString(8);

        Picasso.with(context)
                .load("http://"+imagen)
                .error(R.drawable.ic_broken_image)
                .tag(context)
                .into(holder.imageView);

        holder.titulo.setText(titulo);
        holder.descripcion.setText(descripcion);
        holder.fecha.setText(fecha);

    }

    public void onItemClick(View view, int position) {
        ProductsActivity.launch(
                (Activity) context, String.valueOf(position+1));
    }
//Habilitar los textView y el ClickListener en la lista para pasar el ID
    public class ProductsViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        public TextView titulo;
        public TextView descripcion;
        public TextView fecha;
        public ImageView imageView;

        public ItemClickListener listener;

        public ProductsViewHolder(View v, ItemClickListener listener){
            super(v);
            titulo=(TextView)v.findViewById(R.id.det_product_name);
            descripcion=(TextView)v.findViewById(R.id.product_desc);
            fecha=(TextView)v.findViewById(R.id.det_date_product);
            imageView=(ImageView)v.findViewById(R.id.detail_photo);

            this.listener=listener;
            v.setOnClickListener(this);
        }
        public void onClick(View v){
            listener.onItemClick(v,getAdapterPosition());
        }
    }
}// End ProductsAdapter

interface ItemClickListener {
    void onItemClick(View view, int position);
}
