package com.example.alex.raihere.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alex.raihere.Base.Viajes;
import com.example.alex.raihere.R;

import java.util.ArrayList;

/**
 * Created by waldo on 28/08/16.
 */
public class adapter_viajes extends BaseAdapter{
    ArrayList<Viajes> lista;
    Context context;
    String TAG="adapter_viajes";
    private static LayoutInflater inflater=null;

    public adapter_viajes(Context contexto, ArrayList<Viajes> listaviajes){
        lista=listaviajes;
        context=contexto;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowview;
        rowview=inflater.inflate(R.layout.item_mis_viajes, null);
        holder.txtnombreviajes=(TextView) rowview.findViewById(R.id.txtnombreviajes);
        holder.txtdestinoviajes=(TextView)rowview.findViewById(R.id.txtdestinoviajes);
        holder.txtfechaviajes=(TextView)rowview.findViewById(R.id.txtfechaviajes);
        holder.txtcostoviajes=(TextView)rowview.findViewById(R.id.txtcostoviajes);

        holder.txtnombreviajes.setText("Chofer: "+lista.get(position).getNombre());
        holder.txtdestinoviajes.setText("Destino: "+lista.get(position).getDestino());
        holder.txtfechaviajes.setText("Fecha: "+lista.get(position).getFecha());
        holder.txtcostoviajes.setText("$ "+lista.get(position).getCosto()+" pesos");
        return rowview;
    }

    public class Holder
    {
        TextView txtdestinoviajes;
        TextView txtnombreviajes;
        TextView txtfechaviajes;
        TextView txtcostoviajes;
    }
}
