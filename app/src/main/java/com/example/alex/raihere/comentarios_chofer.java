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
 * {@link comentarios_chofer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class comentarios_chofer extends Fragment {
    private static String TAG = "comentarios_chofer";
    ListView lista;
    ArrayAdapter<comentarios> arrayAdapter;
    SharedPreferences sharedPreferences;

    private OnFragmentInteractionListener mListener;

    public comentarios_chofer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment probando git
        View view =inflater.inflate(R.layout.fragment_comentarios_chofer, container, false);
        final Peticiones peticiones = new Peticiones();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        String tipo=sharedPreferences.getString("TIPOPERFIL","0"),id="";
        if(tipo=="0")
            id=sharedPreferences.getString("IDUSUARIO","");
        else
            id=sharedPreferences.getString("IDUSUARIOP","");
        Log.d(TAG, "ID "+id);
        peticiones.comentarios2(getContext(),id);
        final TextView txtno=(TextView)view.findViewById(R.id.txtnohaychofer);
        lista=(ListView)view.findViewById(R.id.lista_chofer);
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {;
                final List<comentarios> comen=peticiones.sabercomentario(peticiones.respuesta2);
                Log.d(TAG, "choferron "+peticiones.respuesta2);
                if(peticiones.respuesta2==null||peticiones.respuesta2.equals("\tfalse"))
                    txtno.setVisibility(View.VISIBLE);
                else {
                    arrayAdapter = new adapter_comentarios(getContext(), comen);
                    lista.setAdapter(arrayAdapter);
                }
            }
        },1000);
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
