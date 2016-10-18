package com.example.alex.raihere;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alex.raihere.Base.Viajes;
import com.example.alex.raihere.Web_Service.Peticiones;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;


public class detalle_viaje extends AppCompatActivity {
    private static final String TAG="detalle_viaje";
    private EditText edtdescripcciondetalle;
    private TextView txtnombredetalle,txthoradetalle,txtfechadetalle,txtiniciodetalle,txtdestinodetalle,txtescalasdetalle,txtcostodetalle,txtdisponible,txtmarcadetalle,txtmodelodetalle,txtañodetalle;
    private ImageView imgDetalle,imgalcohol,imgcigarro,imgpet,imgsuitcase;
    private ProgressView progresdetalle;
    private Button btnInscribirse,btnperfil;
    private RelativeLayout relative;
    private LinearLayout linearLayout;
    private SharedPreferences shared;
    private Viajes viaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        referencias();
        shared= PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();
        String id= intent.getStringExtra("ID"), iduser=shared.getString("IDUSUARIO","");
        final Peticiones peticiones = new Peticiones();
        peticiones.detalleViaje(getApplicationContext(),id,iduser);
        Handler handerl = new Handler();
        handerl.postDelayed(new Runnable() {
            @Override
            public void run() {
                relative.setVisibility(View.GONE);
                viaje=peticiones.saberDetalle(peticiones.respuesta);
                agregarDetalle(viaje);

            }
        },1500);
    }

    private void referencias(){
        edtdescripcciondetalle=(EditText)findViewById(R.id.edtdescripcciondetalle);
        txtnombredetalle=(TextView)findViewById(R.id.txtnombredetalle);
        linearLayout=(LinearLayout)findViewById(R.id.linerocultar);
        txthoradetalle=(TextView)findViewById(R.id.txthoradetalle);
        txtfechadetalle=(TextView)findViewById(R.id.txtfechadetalle);
        txtiniciodetalle=(TextView)findViewById(R.id.txtiniciodetalle);
        txtdestinodetalle=(TextView)findViewById(R.id.txtdestinodetalle);
        txtescalasdetalle=(TextView)findViewById(R.id.txtescalasdetalle);
        txtcostodetalle=(TextView)findViewById(R.id.txtcostodetalle);
        txtdisponible=(TextView)findViewById(R.id.txtdisponible);
        txtmarcadetalle=(TextView)findViewById(R.id.txtmarcadetalle);
        txtmodelodetalle=(TextView)findViewById(R.id.txtmodelodetalle);
        txtañodetalle=(TextView)findViewById(R.id.txtañodetalle);
        imgDetalle=(ImageView)findViewById(R.id.imgDetalle);
        imgalcohol=(ImageView)findViewById(R.id.imgalcohol);
        imgcigarro=(ImageView)findViewById(R.id.imgcigarro);
        imgpet=(ImageView)findViewById(R.id.imgpet);
        imgsuitcase=(ImageView)findViewById(R.id.imgsuitcase);
        progresdetalle=(ProgressView)findViewById(R.id.progresdetalle);
        btnInscribirse=(Button)findViewById(R.id.btnInscribirse);
        btnperfil=(Button)findViewById(R.id.btnPerfil   );
        relative=(RelativeLayout)findViewById(R.id.relative);



    }

    private void agregarDetalle(Viajes viaje){
        int c,a,m,p;
        String foto;
        edtdescripcciondetalle.setText(viaje.getDescripcion());
        edtdescripcciondetalle.setEnabled(false);
        txtnombredetalle.setText(viaje.getNombre());
        txthoradetalle.setText(viaje.getHora());
        txtfechadetalle.setText(viaje.getFecha());
        txtiniciodetalle.setText(viaje.getInicio());
        txtdestinodetalle.setText(viaje.getDestino());
        txtescalasdetalle.setText(viaje.getEscalas());
        txtcostodetalle.setText("$"+viaje.getCosto()+ " pesos");
        txtdisponible.setText("Lugares disponibles: "+String.valueOf(viaje.getCantidad()));
        txtmarcadetalle.setText(viaje.getMarca());
        txtmodelodetalle.setText(viaje.getModelo());
        txtañodetalle.setText(viaje.getAnio());
        foto=viaje.getFoto();
        c= Integer.parseInt(viaje.getCigarro());
        a=Integer.parseInt(viaje.getAlcohol());
        m=Integer.parseInt(viaje.getMaleta());
        p=Integer.parseInt(viaje.getMascota()) ;
        if(c==1);
            imgcigarro.setVisibility(View.VISIBLE);
        if(a==1)
            imgalcohol.setVisibility(View.VISIBLE);
        if(m==1)
            imgsuitcase.setVisibility(View.VISIBLE);
        if(p==1)
            imgpet.setVisibility(View.VISIBLE);
        if(!foto.equals(""))
            Picasso.with(getApplicationContext())
                    .load(foto)
                    .placeholder(R.drawable.email)
                    .error(R.drawable.perfil)
                    .into(imgDetalle);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("IDVIAJE",viaje.getId().toString());
        editor.commit();
        if(viaje.getRegistro().equals("1"))
            btnInscribirse.setText("Cancelar suscripccion");
        if(txtdisponible.getText().equals("Lugares disponibles: 0") && !btnInscribirse.getText().equals("Cancelar suscripccion"))
            btnInscribirse.setEnabled(false);


    }

    public void inscribirse(View v){
        Peticiones peticion = new Peticiones();
        if(!btnInscribirse.getText().toString().equals("Cancelar suscripccion")) {
            String id, idviaje;
            id = shared.getString("IDUSUARIO", "");
            idviaje = shared.getString("IDVIAJE", "");
            peticion.inscribirse(getApplicationContext(), id, idviaje, btnInscribirse);
        }
        else{
            String id, idviaje;
            id = shared.getString("IDUSUARIO", "");
            idviaje = shared.getString("IDVIAJE", "");
            peticion.borrar(getApplicationContext(), id, idviaje, btnInscribirse);

        }
    }

    public void verPerfil(View v) {
        Intent intent = new Intent(getApplicationContext(),portadora.class);
        SharedPreferences.Editor edit = shared.edit();
        edit.putString("TIPOPERFIL","1");
        edit.putString("TIPOCOMENTARIO","1");//1 ES COMENTARIO HACIA CHOFER
        edit.putString("IDUSUARIOP",viaje.getIdusuario());
        edit.putString("NOMBREP",viaje.getNombre());
        edit.putString("TELEFONOP",viaje.getTelefono());
        edit.putString("MARCAP",viaje.getMarca());
        edit.putString("MODELOP",viaje.getModelo());
        edit.putString("AÑOP",viaje.getAnio());
        edit.putString("IMAGENP",viaje.getImagen());
        edit.commit();
        startActivity(intent);
    }
}
