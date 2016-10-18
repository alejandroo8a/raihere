package com.example.alex.raihere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alex.raihere.Web_Service.Peticiones;

public class registro extends AppCompatActivity {

    EditText edtnombre, edtemail, edtcontraseña, edtcontraseña2;
    Button btnguardar;
    String email, pass, nombre, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edtnombre = (EditText) findViewById(R.id.edtNombre);
        edtemail = (EditText) findViewById(R.id.edtEmail);
        edtcontraseña = (EditText) findViewById(R.id.edtContraseña);
        edtcontraseña2 = (EditText) findViewById(R.id.edtContraseña2);
        btnguardar = (Button) findViewById(R.id.btnguardar);

    }

    public void entrar(View v) {
        pass=edtcontraseña.getText().toString();
        pass2=edtcontraseña2.getText().toString();
        email=edtemail.getText().toString();
        nombre=edtnombre.getText().toString();
        if (nombre.equals("") || email.equals("") ||  pass.equals("") || pass2.equals(""))
            Toast.makeText(getApplicationContext(), "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();
        else if (pass.equals(pass2))
            enviarServidor();
        else
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
    }

    private void enviarServidor() {
        Peticiones peticiones = new Peticiones();
        peticiones.Registro(getApplicationContext(), nombre, email,  pass);


    }
    @Override
    protected void onStop(){
        super.onStop();
        finish();

    }
}
