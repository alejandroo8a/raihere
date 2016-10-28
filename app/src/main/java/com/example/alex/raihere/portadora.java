package com.example.alex.raihere;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alex.raihere.Web_Service.Peticiones;
import com.rey.material.widget.FloatingActionButton;

import net.gotev.uploadservice.Logger;

public class portadora extends AppCompatActivity {
    private static final String TAG = "portadora";
    private FloatingActionButton btncomentar;
    private Button btnEnviar,btnCancelar;
    private EditText comentar;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portadora);
        btncomentar=(FloatingActionButton)findViewById(R.id.publicar);
        btncomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comentar(view);
            }
        });
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    private void comentar(View v){
        final String tipo=sharedPreferences.getString("TIPOCOMENTARIO","");
        Log.d(TAG, "Tipo de comentario "+tipo);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vieww = inflater.inflate(R.layout.comentario, null);
        final AlertDialog dialog = new AlertDialog.Builder(portadora.this).create();
        TextView myMsg = new TextView(getApplicationContext());
        myMsg.setText("Escibir comentario");
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(20);
        myMsg.setTextColor(Color.WHITE);
        myMsg.setBackgroundColor(Color.parseColor("#1976D2"));
        dialog.setCustomTitle(myMsg);
        dialog.setView(vieww);
        btnEnviar = (Button) vieww.findViewById(R.id.btnEnviar2);
        btnCancelar = (Button) vieww.findViewById(R.id.btnCancelar2);
        comentar = (EditText) vieww.findViewById(R.id.edtcomentario);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario = comentar.getText().toString();
                Peticiones peticiones = new Peticiones();
                if(tipo.equals("1")) {
                    Log.d(TAG, "ENTRE A COMENTARIO HACIA CHOFER");
                    peticiones.comentar_chofer(getApplicationContext(),sharedPreferences.getString("IDUSUARIO","").toString(),sharedPreferences.getString("IDVIAJE","").toString(),comentario);
                    dialog.dismiss();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("TIPOPERFIL","0");
                    editor.commit();
                }
                else{
                    Log.d(TAG, "ENTRE A COMENTARIO HACIA PASAJERO");
                    peticiones.comentar_pasajero(getApplicationContext(),sharedPreferences.getString("IDUSUARIOP","").toString(),sharedPreferences.getString("IDVIAJE","").toString(),comentario);
                    dialog.dismiss();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("TIPOPERFIL","0");
                    editor.commit();

                }

            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
