package com.example.alex.raihere;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.alex.raihere.Web_Service.Peticiones;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mis_datos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class mis_datos extends Fragment {
    private static String TAG = "mis_datos";
    private static final int SELECT_PICTURE = 1;
    private OnFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;
    private ImageView imagenPerfil, imgCarro;
    private EditText edtNombre, edtMarca, edtModelo, edtAño,edttel;
    private RadioButton rdsi, rdno;
    private Button btncambiarPerfil, btnAgregarFoto, btnGuardar,btnGuardar2;
    private boolean saberImagen=true;
    private TextView txtv, txtm, txtmo, txta, txttel;
    private ProgressView carga;
    public mis_datos() {
        // Required empty public constructor
    }

//------------------- CHECAR QUE MUESTRE LOS BOTONES CORRESPONDIENTES A LA VISTA!
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mis_datos, container, false);
        imagenPerfil=(ImageView)view.findViewById(R.id.imgperfildatos);
        imgCarro=(ImageView)view.findViewById(R.id.imgMisdatos);
        edtNombre=(EditText)view.findViewById(R.id.edtnombredatos);
        edtMarca=(EditText)view.findViewById(R.id.edtmarca);
        edtModelo=(EditText)view.findViewById(R.id.edtmodelo);
        edtAño=(EditText)view.findViewById(R.id.edtaño);
        rdsi=(RadioButton)view.findViewById(R.id.chksi);
        rdno=(RadioButton)view.findViewById(R.id.chkno);
        btncambiarPerfil=(Button)view.findViewById(R.id.btncambiarfoto);
        btnAgregarFoto=(Button)view.findViewById(R.id.btnagregarfoto);
        txtv=(TextView)view.findViewById(R.id.txtv);
        txtm=(TextView)view.findViewById(R.id.txtm);
        txtmo=(TextView)view.findViewById(R.id.txtmo);
        txta=(TextView)view.findViewById(R.id.txta);
        btnGuardar=(Button)view.findViewById(R.id.btnguardar);
        btnGuardar2=(Button)view.findViewById(R.id.btnguardar2);
        carga=(ProgressView)view.findViewById(R.id.progresDatos);
        txttel=(TextView)view.findViewById(R.id.txttel);
        edttel=(EditText)view.findViewById(R.id.edttel);
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
        String imagen="";

        final Peticiones peticiones = new Peticiones();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        final String id=sharedPreferences.getString("IDUSUARIO","");
        peticiones.misDatos(getContext(),id,edtMarca,edtModelo,edtAño, imgCarro, rdsi,rdno,
                btnAgregarFoto,txtv,txtm,txtmo,txta,btnGuardar,btnGuardar2,carga);
        imagen=sharedPreferences.getString("IMAGEN","0");
        cargarImagen(imagen);
        edtNombre.setText(sharedPreferences.getString("NOMBRE","0"));
        btncambiarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saberImagen=true;
                intentImagen();
            }
        });
        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saberImagen=false;
                intentImagen();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peticiones.subirMisDatos(getContext(), id, edtNombre.getText().toString(),edtMarca.getText().toString(),edtModelo.getText().toString(),edtAño.getText().toString(),edttel.getText().toString());
                crearSharedPreferences();

            }
        });


        btnGuardar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtMarca.setText(null);
                peticiones.subirMisDatos(getContext(), id, edtNombre.getText().toString(),edtMarca.getText().toString(),edtModelo.getText().toString(),edtAño.getText().toString(),edttel.getText().toString());
                crearSharedPreferences();

            }
        });

        rdsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visible();

            }
        });
        rdno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               invisible();
            }
        });
    }

    private void cargarImagen(String imagen){
        if(!imagen.equals(""))
            Picasso.with(getContext())
                    .load(imagen)
                    .placeholder(R.drawable.email)
                    .error(R.drawable.perfil)
                    .into(imagenPerfil);
    }

    private void intentImagen(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecciona foto"),SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImage=imageReturnedIntent.getData();
        if(saberImagen)
            imagenPerfil.setImageURI(selectedImage);
        else
            imgCarro.setImageURI(selectedImage);
        String pat=getPath(selectedImage);
        Peticiones p = new Peticiones();
        p.subirFoto(pat, getContext());
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")

        Cursor cursor =getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void invisible(){
        edtMarca.setVisibility(View.INVISIBLE);
        edtModelo.setVisibility(View.INVISIBLE);
        edtAño.setVisibility(View.INVISIBLE);
        imgCarro.setVisibility(View.INVISIBLE);
        btnAgregarFoto.setVisibility(View.INVISIBLE);
        txtv.setVisibility(View.INVISIBLE);
        txtm.setVisibility(View.INVISIBLE);
        txtmo.setVisibility(View.INVISIBLE);
        txta.setVisibility(View.INVISIBLE);
        btnGuardar.setVisibility(View.INVISIBLE);
        btnGuardar2.setVisibility(View.VISIBLE);
    }

    public void visible(){

        edtMarca.setVisibility(View.VISIBLE);
        edtModelo.setVisibility(View.VISIBLE);
        edtAño.setVisibility(View.VISIBLE);
        imgCarro.setVisibility(View.VISIBLE);
        btnAgregarFoto.setVisibility(View.VISIBLE);
        txtv.setVisibility(View.VISIBLE);
        txtm.setVisibility(View.VISIBLE);
        txtmo.setVisibility(View.VISIBLE);
        txta.setVisibility(View.VISIBLE);
        btnGuardar.setVisibility(View.VISIBLE);
        btnGuardar2.setVisibility(View.INVISIBLE);

    }

    private void crearSharedPreferences(){
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("NOMBRE", edtNombre.getText().toString());
        editor.putString("TELEFONO",edttel.getText().toString());
        editor.putString("MARCA",edtMarca.getText().toString());
        editor.putString("MODELO",edtModelo.getText().toString());
        editor.putString("AÑO",edtAño.getText().toString());
        editor.commit();
    }



}

