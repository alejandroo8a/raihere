package com.example.alex.raihere;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.raihere.Adapters.adapter_comentarios;
import com.example.alex.raihere.Base.comentarios;
import com.example.alex.raihere.Web_Service.Peticiones;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link comentarios_pasajero.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class comentarios_pasajero extends Fragment {
    private static final String TAG = "comentarios_pasajero";
    ListView lista;
    ArrayAdapter<comentarios> arrayAdapter;
    SharedPreferences sharedPreferences;

    private OnFragmentInteractionListener mListener;

    public comentarios_pasajero() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comentarios_pasajero, container, false);
        final Peticiones peticiones = new Peticiones();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        String tipo=sharedPreferences.getString("TIPOPERFIL","0"),id="";
        if(tipo=="0")
            id=sharedPreferences.getString("IDUSUARIO","");
        else
            id=sharedPreferences.getString("IDUSUARIOP","");
        Log.d(TAG, "ID "+id);
        peticiones.comentarios(getContext(),id);
        final TextView txtno=(TextView)view.findViewById(R.id.txtnohaypasajero);
        lista=(ListView)view.findViewById(R.id.lista_pasajero);
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {;
                final List<comentarios> comen=peticiones.sabercomentario(peticiones.respuesta);
                Log.d(TAG, "pasajerron "+peticiones.respuesta);
                if(peticiones.respuesta==null||peticiones.respuesta=="\tfalse"||peticiones.respuesta.equals("\tfalse"))
                    txtno.setVisibility(View.VISIBLE);
                else{
                    arrayAdapter=new adapter_comentarios(getContext(),comen);
                    lista.setAdapter(arrayAdapter);
                }
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("TIPOPERFIL","0");
                edit.commit();

            }
        },2000);
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
}
