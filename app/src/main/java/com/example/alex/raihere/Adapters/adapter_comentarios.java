package com.example.alex.raihere.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.raihere.Base.comentarios;
import com.example.alex.raihere.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alejandro on 22/09/16.
 */

public class adapter_comentarios extends ArrayAdapter<comentarios>{
    private static String TAG="adapter_cometarios";
    public adapter_comentarios(Context context, List<comentarios> resource) {
        super(context, 0,resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //OBTENER INFLATER
        LayoutInflater inflater =(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        // Lead actual.
        comentarios comentario = getItem(position);
        Log.d(TAG, "getView: "+comentario.getComentario().toString());
        // ¿Ya se infló este view?
        if(!comentario.getComentario().toString().equals("null")||comentario.getComentario().toString()!="null") {
            if (null == convertView) {
                //Si no existe, entonces inflarlo con image_list_view.xml
                convertView = inflater.inflate(R.layout.item_comentarios, parent, false);
                holder = new ViewHolder();
                holder.avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
                holder.name = (TextView) convertView.findViewById(R.id.txtname);
                holder.comentario = (TextView) convertView.findViewById(R.id.txtcomentarios);
                holder.fecha = (TextView) convertView.findViewById(R.id.txtdate);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            // Setup.
            if (!comentario.getImagen().equals(""))
                Picasso.with(getContext())
                        .load(comentario.getImagen())
                        .placeholder(R.drawable.email)
                        .error(R.drawable.perfil)
                        .into(holder.avatar);
            holder.name.setText(comentario.getNombre());
            holder.comentario.setText(comentario.getComentario());
            holder.fecha.setText(comentario.getFecha());

            return convertView;
        }
        return null;


    }

    static class ViewHolder {
        ImageView avatar;
        TextView name;
        TextView comentario;
        TextView fecha;
    }
}
