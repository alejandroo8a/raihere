package com.example.alex.raihere.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.raihere.Base.Viajes;
import com.example.alex.raihere.Base.comentarios;
import com.example.alex.raihere.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alejandro on 4/10/16.
 */

public class adapter_activos_detalle extends BaseAdapter {
    ArrayList<comentarios> lista;
    Context context;
    String TAG="adapter_activos_detalle";
    private static LayoutInflater inflater=null;

    public adapter_activos_detalle(Context contexto, ArrayList<comentarios> listaviajes){
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
        adapter_activos_detalle.Holder holder = new adapter_activos_detalle.Holder();
        View rowview;
        rowview=inflater.inflate(R.layout.item_activos_detalle, null);
        holder.txtnombreactivo=(TextView) rowview.findViewById(R.id.txtnombreactivo);
        holder.imagen=(ImageView) rowview.findViewById(R.id.imgactivodetalle);

        holder.txtnombreactivo.setText(lista.get(position).getNombre());
        if(!lista.get(position).getImagen().equals(""))
            Picasso.with(context)
                    .load(lista.get(position).getImagen())
                    .placeholder(R.drawable.email)
                    .error(R.drawable.perfil)
                    .into(holder.imagen);
        return rowview;
    }

    public class Holder
    {
        TextView txtnombreactivo;
        ImageView imagen;
    }
}
