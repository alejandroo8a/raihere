package com.example.alex.raihere;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.raihere.Web_Service.Peticiones;
import com.rey.material.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link generar_viaje.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class generar_viaje extends Fragment {
    private static String TAG = "generar_viaje";
    private OnFragmentInteractionListener mListener;
    TextView fecha;
    Button btnalta;
    EditText edtReunion, edtDestino,edtHora,edtDescripccion,edtCosto,edtCapacidad,edtEscalas;
    CheckBox chkAlcohol,chkMascota,chkCigarro,chkMaletas;
    SharedPreferences sharedPreferences;

    public generar_viaje() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_generar_viaje, container, false);
        fecha=(TextView)view.findViewById(R.id.txfecha2);
        btnalta=(Button) view.findViewById(R.id.btndar_alta);
        edtReunion=(EditText)view.findViewById(R.id.edtReunion);
        edtDestino=(EditText)view.findViewById(R.id.edtDestino);
        edtHora=(EditText)view.findViewById(R.id.edthora);
        edtDescripccion=(EditText)view.findViewById(R.id.edtDescripccion);
        edtCosto=(EditText)view.findViewById(R.id.edtcosto);
        edtCapacidad=(EditText)view.findViewById(R.id.edtPersonas);
        edtEscalas=(EditText)view.findViewById(R.id.edtEscalas);
        chkAlcohol=(CheckBox)view.findViewById(R.id.chkalcohol);
        chkMascota=(CheckBox)view.findViewById(R.id.chkmascotas);
        chkCigarro=(CheckBox)view.findViewById(R.id.chkfumar);
        chkMaletas=(CheckBox)view.findViewById(R.id.chkmaletas);
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
        btnalta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generarViaje();
            }
        });

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFecha();
            }
        });

    }

    public void generarViaje(){
        Peticiones peticiones= new Peticiones();
        String fumar, mascota, alcohol, maleta,id;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        id=sharedPreferences.getString("IDUSUARIO","0");
        fumar=chkAlcohol.isChecked() ? "1":"0";
        mascota=chkMascota.isChecked() ? "1" : "0";
        alcohol=chkAlcohol.isChecked() ? "1": "0";
        maleta=chkMaletas.isChecked() ? "1": "0";
        Log.d(TAG, "fecha "+fecha.getText().toString());
        if(!edtReunion.getText().toString().equals("") && !edtDestino.getText().toString().equals("") && !edtCosto.getText().toString().equals("")&&!fecha.getText().toString().equals("")&& !edtCapacidad.getText().toString().equals("")) {
            peticiones.altaViaje(getContext(), id, edtDescripccion.getText().toString(), edtReunion.getText().toString(), edtDestino.getText().toString(), fecha.getText().toString(), edtCosto.getText().toString(), edtCapacidad.getText().toString(), edtEscalas.getText().toString(), edtHora.getText().toString(), fumar, alcohol, mascota, maleta);
            cleanScreen();
        }
        else
            Toast.makeText(getContext(), "Complete todos los campos para dar de alta un viaje.", Toast.LENGTH_SHORT).show();
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
                        fecha.setText(fechaFormateada);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void cleanScreen(){
        edtDestino.setText("");
        edtReunion.setText("");
        edtCosto.setText("");
        edtHora.setText("");
        edtCapacidad.setText("");
        edtDescripccion.setText("");
        edtEscalas.setText("");
        chkMaletas.setChecked(false);
        chkAlcohol.setChecked(false);
        chkCigarro.setChecked(false);
        chkMascota.setChecked(false);
        edtReunion.requestFocus();
    }
}
