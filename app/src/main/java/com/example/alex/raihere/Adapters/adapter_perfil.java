package com.example.alex.raihere.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by waldo on 28/08/16.
 */
public class adapter_perfil extends BaseAdapter {
    private static final String TAG= "adapter_principal";
    private static LayoutInflater inflater=null;


    public adapter_perfil(Context context) {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder{

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
