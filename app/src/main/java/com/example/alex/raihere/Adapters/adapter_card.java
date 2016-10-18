package com.example.alex.raihere.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.raihere.Base.Viajes;
import com.example.alex.raihere.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alejandro on 26/09/16.
 */

public class adapter_card extends RecyclerView.Adapter<adapter_card.viajeViewHolder> {
    private ArrayList<Viajes> items;
    private String TAG ="muestra";


    public static class viajeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView fechacard,nombrecard,destinocard,costocard,idcard;
        private CardView cardview;

        public viajeViewHolder(View v) {
            super(v);
            cardview= (CardView)itemView.findViewById(R.id.cardview);
            fechacard = (TextView) v.findViewById(R.id.fechacard);
            destinocard = (TextView) v.findViewById(R.id.destinocard);
            costocard = (TextView) v.findViewById(R.id.costocard);
            idcard = (TextView) v.findViewById(R.id.idcard);
            nombrecard = (TextView) v.findViewById(R.id.nombrecard);
        }
    }

    public adapter_card(ArrayList<Viajes> items, Context context) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public viajeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        return new viajeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final viajeViewHolder viewHolder, final int i) {
        viewHolder.fechacard.setText(items.get(i).getFecha());
        viewHolder.destinocard.setText("El destino del viaje fue: "+items.get(i).getDestino());
        viewHolder.costocard.setText("El costo fue: "+items.get(i).getCosto()+" pesos.");
        viewHolder.nombrecard.setText("El chofer fue: "+items.get(i).getNombre());
        viewHolder.idcard.setText("Número de viaje: "+items.get(i).getId());
        viewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}