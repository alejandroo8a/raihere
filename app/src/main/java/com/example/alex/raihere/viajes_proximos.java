package com.example.alex.raihere;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.alex.raihere.Adapters.adapter_viajes;
import com.example.alex.raihere.Base.Viajes;
import com.example.alex.raihere.Web_Service.Peticiones;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link viajes_proximos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class viajes_proximos extends Fragment {
    private static String TAG = "viajes_proximos";

    private OnFragmentInteractionListener mListener;
    private TextView txtnohay;
    private GridView gridView;
    private ArrayList<Viajes> viajes;
    private adapter_viajes adaptador;
    private ProgressView carga;
    private SharedPreferences sp;


    public viajes_proximos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_viajes_proximos, container, false);
        Log.d(TAG, "EStoy en viajes proximos: ");
        sp= PreferenceManager.getDefaultSharedPreferences(getContext());
        txtnohay=(TextView)view.findViewById(R.id.txtnohayproximos);
        gridView=(GridView)view.findViewById(R.id.gridviewproximos);
        carga=(ProgressView)view.findViewById(R.id.progresproximos);
        final Peticiones peticion= new Peticiones();
        Handler handler = new Handler();

        peticion.misviajes(getContext(),1,sp.getString("IDUSUARIO",""));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                carga.setVisibility(View.INVISIBLE);
                String res=peticion.respuesta;
                viajes=new ArrayList<>();
                viajes=peticion.saberviaje(res);
                Log.d(TAG, "viajes "+viajes);
                if(viajes==null||viajes.equals(null))
                    txtnohay.setVisibility(View.VISIBLE);
                else {
                    adaptador = new adapter_viajes(getContext(), viajes);
                    gridView.setAdapter(adaptador);
                }
            }
        }, 2000);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int seleccion    , long l) {
                Intent intent = new Intent(getContext(), detalle_viaje.class);
                intent.putExtra("ID",viajes.get(seleccion).getId());
                startActivity(intent);
            }
        });
    }
}
