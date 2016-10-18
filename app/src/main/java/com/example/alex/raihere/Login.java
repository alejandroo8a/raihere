package com.example.alex.raihere;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.raihere.Web_Service.Peticiones;

import java.text.SimpleDateFormat;
import java.util.Date;

import is.arontibo.library.ElasticDownloadView;

public class Login extends AppCompatActivity {
    final static String TAG = "login";
    private Button btnLogin, btnEnviar, btnCancelar;
    private EditText user, pass, passForget;
    private TextView registrar, olvidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        user = (EditText) findViewById(R.id.edtUser2);
        pass = (EditText) findViewById(R.id.edtPass2);
        registrar = (TextView) findViewById(R.id.txtRegistro);
        olvidar = (TextView) findViewById(R.id.txtOlvido);

        //BOTON PARA ENTRAR A LA PANTALLA PRINCIPAL
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().equals("") || pass.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Ingresa tu usuario o contraseña", Toast.LENGTH_SHORT).show();
                else {
                    Peticiones peticiones = new Peticiones();
                    peticiones.Login(getApplicationContext(), user.getText().toString(), pass.getText().toString());

                }

            }
        });
        //BOTON QUE TE ENVIA AL REGISTRO DE LA APLICACION
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), registro.class);
                startActivity(intent);
            }
        });
        //BOTON QUE TE ENVIA LA CONTRASEÑA AL CORREO
        olvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View vieww = inflater.inflate(R.layout.olvidar_pass, null);
                final AlertDialog dialogo = new AlertDialog.Builder(Login.this).create();
                TextView myMsg = new TextView(getApplicationContext());
                myMsg.setText("Recuperar contraseña");
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.WHITE);
                myMsg.setBackgroundColor(Color.parseColor("#1976D2"));
                dialogo.setCustomTitle(myMsg);
                dialogo.setView(vieww);
                //ALERT
                btnEnviar = (Button) vieww.findViewById(R.id.btnEnviar);
                btnCancelar = (Button) vieww.findViewById(R.id.btnCancelar);
                passForget = (EditText) vieww.findViewById(R.id.edtpassForget);
                btnEnviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String correo = passForget.getText().toString();
                        Peticiones peticiones = new Peticiones();
                        peticiones.recuperarPass(getApplicationContext(), correo);
                    }
                });
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogo.dismiss();
                    }
                });
                dialogo.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}




