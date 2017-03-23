package com.example.user.e_gigi.controlador;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.e_gigi.R;

import static com.example.user.e_gigi.web.SQLiteDB.DATABASE_NAME;
import static java.security.AccessController.getContext;

/**
 * Created by Aranza on 23/03/2017.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>{

    private Cursor items;
    private Context context;

    public OrdersAdapter(Cursor cursor, Context context){
        this.items=cursor;
        this.context=context;
    }

    public Cursor swapCursor(final Cursor cursor){
        if(items==cursor){
            return null;
        }
        Cursor oldCursor = cursor;
        this.items = cursor;
        if(cursor!= null){
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }
    /*
      Permite limpiar todos los elementos del recycler
       */
    public void clear(){
        context.deleteDatabase(DATABASE_NAME);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(items != null)
            return items.getCount();
        return 0;
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_orders, parent, false);
        return new OrdersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrdersViewHolder holder, final int position) {
        items.moveToPosition(position);
        String reference, total, date, customer, country, state;
        reference=items.getString(2);
        total=items.getString(3);
        total = total.substring(0,5);
        date=items.getString(5);
        customer=items.getString(6);
        country=items.getString(8);
        state=items.getString(7);

        Log.e("ITEMS", "reference: "+reference+" total:"+total+" date:"+date+" customer:"+customer+" country:"+country+" state:"+state);

        switch (state){
            case "Canceled":
                holder.cover.setBackgroundColor(context.getResources().getColor(R.color.md_red_500));
                break;
            case "Awaiting check payment":
                holder.cover.setBackgroundColor(context.getResources().getColor(R.color.md_blue_900));
                break;
            case "Awaiting bank wire payment":
                holder.cover.setBackgroundColor(context.getResources().getColor(R.color.md_blue_500));
                break;
            case "Payment accepted":
                holder.cover.setBackgroundColor(context.getResources().getColor(R.color.md_green_500));
                break;
            case "Delivered":
                holder.cover.setBackgroundColor(context.getResources().getColor(R.color.md_green_900));
                break;
            case "Payment error":
                holder.cover.setBackgroundColor(context.getResources().getColor(R.color.md_red_900));
                break;
            case "Shipped":
                holder.cover.setBackgroundColor(context.getResources().getColor(R.color.md_light_green_400));
                break;
           default:
               holder.cover.setBackgroundColor(context.getResources().getColor(R.color.md_yellow_700));
               break;

        }
        holder.reference.setText(reference);
        holder.date.setText(date);
        holder.total.setText("$"+total);
        holder.customer.setText(customer);
        holder.country.setText(country);

    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder{
        public TextView reference;
        public TextView total;
        public TextView date;
        public TextView customer;
        public TextView country;
        public ImageView cover;

        public OrdersViewHolder(View v) {
            super(v);
            reference=(TextView)v.findViewById(R.id.reference);
            total=(TextView)v.findViewById(R.id.total);
            date=(TextView)v.findViewById(R.id.date);
            customer=(TextView)v.findViewById(R.id.customer);
            country=(TextView)v.findViewById(R.id.country);
            cover = (ImageView)v.findViewById(R.id.cover);
        }
    }
}
