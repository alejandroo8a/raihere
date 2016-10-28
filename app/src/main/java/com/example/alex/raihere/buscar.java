package com.example.alex.raihere;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import com.rey.material.widget.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link buscar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class buscar extends Fragment {

    private OnFragmentInteractionListener mListener;
    TextView txtFecha;
    EditText edtorigen, edtdestino;
    FloatingActionButton btnbuscar;
    SharedPreferences sharedPreferences;

    public buscar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_buscar, container, false);
        txtFecha = (TextView) view.findViewById(R.id.txtfecha);
        btnbuscar=(FloatingActionButton)view.findViewById(R.id.fab);
        edtorigen=(EditText)view.findViewById(R.id.edtOrigen);
        edtdestino=(EditText)view.findViewById(R.id.edtDestino);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = simpleDateFormat.format(date);
        txtFecha.setText(fecha);
        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFecha();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtdestino.getText().toString().equals("")&&!edtorigen.getText().toString().equals(""))
                    buscar();
                else
                    Snackbar.make(view,"Llene todos los campos",Snackbar.LENGTH_SHORT).setAction("Atencion",null).show();
            }
        });
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
    private void dialogFecha(){
        final Calendar c = Calendar.getInstance();
        Integer mYear = c.get(Calendar.YEAR);
        Integer mMonth = c.get(Calendar.MONTH);
        Integer mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String fechaFormateada=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        try {
                            Date fecha = simpleDateFormat.parse(fechaFormateada);
                            fechaFormateada = simpleDateFormat.format(fecha);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        txtFecha.setText(fechaFormateada);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void buscar(){
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MOSTRAR","0");
        editor.putString("ORIGEN",edtorigen.getText().toString());
        editor.putString("DESTINO",edtdestino.getText().toString());
        editor.putString("FECHA",txtFecha.getText().toString());
        editor.commit();
        Fragment fragment= new principal();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, fragment).commit();

    }
}
