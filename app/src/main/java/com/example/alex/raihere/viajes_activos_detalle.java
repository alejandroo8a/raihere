package com.example.alex.raihere;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.alex.raihere.Adapters.adapter_activos_detalle;
import com.example.alex.raihere.Base.Viajes;
import com.example.alex.raihere.Base.comentarios;
import com.example.alex.raihere.Web_Service.Peticiones;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

public class viajes_activos_detalle extends AppCompatActivity {
    private static String TAG = "viajes_activos_detalle";
    private ArrayList<comentarios> comentario;
    private GridView gridView;
    private adapter_activos_detalle adapter;
    private SharedPreferences shared;
    private Button btnCancelar;
    private TextView txtnohay,txtlistaUser;
    private ProgressView barra;


    public viajes_activos_detalle() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes_activos_detalle);
        gridView=(GridView)findViewById(R.id.gridviewactivos_detalle );
        btnCancelar=(Button)findViewById(R.id.btncancelarDetalle);
        txtnohay=(TextView)findViewById(R.id.txtnoactivos);
        txtlistaUser=(TextView)findViewById(R.id.txtlistaUser);
        barra=(ProgressView)findViewById(R.id.progressactivo);
        Intent intent = getIntent();
        shared= PreferenceManager.getDefaultSharedPreferences(this);
        final Peticiones peticion= new Peticiones();
        final Intent intent2=getIntent();
        peticion.detalle_activo(getApplicationContext(),intent.getStringExtra("ID"));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                comentario=new ArrayList<>();
                String res=peticion.respuesta;
                comentario=peticion.saberDetalleActivo(res);
                Log.d(TAG, "run: "+res);
                barra.setVisibility(View.INVISIBLE);
                if(res=="\tfalse"||res.equals("\tfalse")||res==null)
                    txtnohay.setVisibility(View.VISIBLE);
                else {
                    txtlistaUser.setVisibility(View.VISIBLE);
                    adapter = new adapter_activos_detalle(getApplicationContext(), comentario);
                    gridView.setAdapter(adapter);
                }
                btnCancelar.setVisibility(View.VISIBLE);

            }
        },2000);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Intent intent = new Intent(getApplicationContext(),portadora.class);
                final Peticiones peticiones = new Peticiones();
                Log.d(TAG, "id usuario: "+comentario.get(i).getComentario());
                Log.d(TAG, "id viaje: "+intent2.getStringExtra("ID"));
                barra.setVisibility(View.VISIBLE);
                final String id=intent2.getStringExtra("ID"),iduser=comentario.get(i).getComentario();
                peticiones.detalleViajesActivo(getApplicationContext(),id,iduser);
                Handler handerl = new Handler();
                handerl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Viajes viaje;
                        viaje=peticiones.saberDetalleViajes(peticiones.respuesta);
                        SharedPreferences.Editor edit = shared.edit();
                        edit.putString("TIPOPERFIL","1");
                        edit.putString("TIPOCOMENTARIO","0");//1 ES COMENTARIO HACIA PASAJERO
                        edit.putString("IDUSUARIOP",iduser);
                        edit.putString("NOMBREP",viaje.getNombre());
                        edit.putString("TELEFONOP",viaje.getTelefono());
                        edit.putString("MARCAP",viaje.getMarca());
                        edit.putString("MODELOP",viaje.getModelo());
                        edit.putString("AÑOP",viaje.getAnio());
                        edit.putString("IMAGENP",viaje.getImagen());
                        edit.putString("IDVIAJE",id);
                        edit.commit();
                        Log.d(TAG, "nombre "+viaje.getNombre());
                        startActivity(intent);
                        barra.setVisibility(View.INVISIBLE);

                    }
                },1500);
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogconfirmar dialogconfirmar= new dialogconfirmar();
                Bundle args = new Bundle();
                args.putString("IDUSER",comentario.get(i).getComentario());
                args.putString("VIAJE",intent2.getStringExtra("ID"));
                dialogconfirmar.setArguments(args);
                dialogconfirmar.show(getFragmentManager(),"dualog");
                return true;
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idviaje=intent2.getStringExtra("ID");
                peticion.terminar_viaje(getApplicationContext(),idviaje);
                finish();
            }
        });
    }
}


