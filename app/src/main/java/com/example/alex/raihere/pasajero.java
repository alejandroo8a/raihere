package com.example.alex.raihere;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alex.raihere.Adapters.adapter_card;
import com.example.alex.raihere.Base.Viajes;
import com.example.alex.raihere.Web_Service.Peticiones;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pasajero.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class pasajero extends Fragment {

    private static String TAG = "pasajero";
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private SharedPreferences sharedPreferences;
    private ArrayList<Viajes> viajes;
    private TextView mensaje;
    private ProgressView progressView;


    private OnFragmentInteractionListener mListener;

    public pasajero() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pasajero, container, false);
        //OBTENEMOS EL RECYCLER
        recycler = (RecyclerView) view.findViewById(R.id.pasajero_recycler);
        mensaje=(TextView)view.findViewById(R.id.txtnopasajero);
        progressView=(ProgressView)view.findViewById(R.id.progrespasajero);
        recycler.setHasFixedSize(true);
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
     * <p>
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

        //SE USA UN ADMINISTRADOR PAR EL LINEAR LAYOUT
        lManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(lManager);
        Handler handler = new Handler();
        final Peticiones peticiones = new Peticiones();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        peticiones.chofer_pasajero(getContext(),sharedPreferences.getString("IDUSUARIO","1"),"pasajero_historial");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //SE SETEA EL ADAPTADOR
                progressView.setVisibility(View.INVISIBLE);
                String res=peticiones.respuesta;
                viajes=new ArrayList<>();
                viajes=peticiones.saberviaje(res);
                if(viajes==null||viajes.equals(null))
                    mensaje.setVisibility(View.VISIBLE);
                else{
                    adapter = new adapter_card(viajes,getContext());
                    recycler.setAdapter(adapter);
                }

            }
        },1000);

    }
}
