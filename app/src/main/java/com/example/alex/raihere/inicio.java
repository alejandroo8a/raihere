package com.example.alex.raihere;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.alex.raihere.mis_datos;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class inicio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static String TAG="inicio";
    String imagen="";
    private Bitmap loadedImage;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Intent intent=getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtNombreUsuario = (TextView) headerLayout.findViewById(R.id.txtNombreUsuario);
        TextView txtCorreo = (TextView) headerLayout.findViewById(R.id.txtCorreo);
        String id=intent.getStringExtra("ID"), imagen=intent.getStringExtra("IMAGEN");
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("MOSTRAR","1");
        editor.putString("IDUSUARIO",id);
        editor.putString("IMAGEN",imagen);
        editor.putString("NOMBRE", intent.getStringExtra("NOMBRE"));
        editor.putString("TIPOPERFIL","0");
        editor.commit();
        CircularImageView imageProfesor = (CircularImageView) headerLayout.findViewById(R.id.perfil);
        txtNombreUsuario.setText(intent.getStringExtra("NOMBRE"));
        txtCorreo.setText(intent.getStringExtra("EMAIL"));
        if(!imagen.equals(""))
            Picasso.with(getApplicationContext())
                    .load(imagen)
                    .placeholder(R.drawable.email)
                    .error(R.drawable.perfil)
                    .into(imageProfesor);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main_content, new principal());
        tx.commit();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


//SE USA PARA CAPTURAR EL BOTON DE RETROSESO
    @Override
    public void onBackPressed() {
        String saber=sharedPreferences.getString("MOSTRAR","1");
        if(saber.equals("0")){
            Fragment fragment= new buscar();
            FragmentManager fragmentManager= getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content,fragment).commit();
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("MOSTRAR","0");
            editor.commit();

        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Cerrar sesión")
                    .setMessage("¿Seguro que deseas cerrar sesión?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    }).create().show();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment =null;
        if (id == R.id.buscar) {
            fragment = new buscar();
        }else if( id==R.id.hoy){
            fragment= new principal();
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("MOSTRAR","1");
            editor.commit();
        }
        else if( id==R.id.perfil){
            fragment= new perfil();
        }
        else if( id==R.id.ofrecer_viajes){
            fragment= new generar_viaje();
        }
        else if( id==R.id.viajes){
            fragment= new mis_viaje();
        }
        else if( id==R.id.datos){
            fragment= new mis_datos();
        }
        if(R.id.logout!=id ) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_content, fragment).commit();
        }
        else
            finish();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }


}
