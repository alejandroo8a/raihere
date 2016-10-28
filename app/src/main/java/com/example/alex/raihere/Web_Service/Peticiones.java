package com.example.alex.raihere.Web_Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.alex.raihere.Base.Usuario;
import com.example.alex.raihere.Base.Viajes;
import com.example.alex.raihere.Base.comentarios;
import com.example.alex.raihere.R;
import com.example.alex.raihere.inicio;
import com.example.alex.raihere.mis_datos;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.BinaryUploadRequest;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by waldo on 24/08/16.
 */
public class Peticiones {
    //SEGUIR CON LA PARTE DE VIAJES PROXIMOS, LA DE VIAJES ACTUALES YA QUEDO SOLO FALTA GUARDAR EL ARRAY
    private static String TAG = "peticiones";

    final String URL = "http://www.yomplii.com/gomovil/Raihere.php";
    final String URL2 = "http://www.yomplii.com/gomovil/Foto.php";
    Usuario usuario;
    public String respuesta, respuesta2;


    public Peticiones() {

    }

    public void Login(final Context context, final String user, final String pass) {
        StringRequest loginRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "respuesta: " + response);
                        boolean i = saberLogin(response);
                        if (i) {
                            Intent intent = new Intent(context, inicio.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("EMAIL", usuario.getEmail());
                            intent.putExtra("ID", usuario.getId());
                            intent.putExtra("NOMBRE", usuario.getNombre());
                            intent.putExtra("IMAGEN", usuario.getImagen());
                            context.startActivity(intent);
                        } else
                            Toast.makeText(context, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "fallo " + volleyError.toString());
                        Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tipo", "login");
                params.put("correo", user);
                params.put("password", pass);
                return params;

            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(loginRequest);
    }

    public void misviajes(final Context context, Integer id, final String idu) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (id == 1) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            date = calendar.getTime();
        }
        final String fecha = simpleDateFormat.format(date);
        StringRequest loginRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta = response;
                        Log.d(TAG, "respuesta viajes " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "fallo " + volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tipo", "viajes");
                params.put("id",idu);
                params.put("fecha", fecha);
                return params;

            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(loginRequest);

    }

    public void misviajes_activo(final Context context,  final String idu) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String fecha = simpleDateFormat.format(date);
        StringRequest loginRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta = response;
                        Log.d(TAG, "mis viajes2 " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "fallo " + volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tipo", "viajes_activos");
                params.put("id",idu);
                params.put("fecha", fecha);
                return params;

            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(loginRequest);

    }

    public void detalleViaje(Context context,final String id, final String idUsuario){
        StringRequest request= new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta=response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected  Map<String, String>getParams(){
                Map<String, String> params= new HashMap<>();
                params.put("tipo", "detalle");
                params.put("id",id);
                params.put("idUsuario",idUsuario);
                return params;
            }

        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);
    }

    public void recuperarPass(final Context context, final String correo) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "response" + response);
                        if (response.equals("\ttrue"))
                            Toast.makeText(context, "Contraseña cambiada, revisa tu correo electrónico", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", "forget");
                params.put("correo", correo);
                return params;
            }
        };

        Singleton_Volley.getInstance(context).addToRequestQueue(request);
    }

    public void Registro(final Context context, final String nombre, final String email, final String pass) {
        StringRequest registro = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "exito " + response);
                        if (response.equals("\tfalse"))
                            Toast.makeText(context, "¡El usuario ya existe!", Toast.LENGTH_SHORT).show();
                        else {
                            Intent intent = new Intent(context, inicio.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("EMAIL", email);
                            intent.putExtra("ID", response);
                            intent.putExtra("NOMBRE", nombre);
                            intent.putExtra("IMAGEN", "");
                            context.startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "fallo " + volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", "registro");
                params.put("nombre", nombre);
                params.put("email", email);
                params.put("pass", pass);
                return params;
            }

        };
        Singleton_Volley.getInstance(context).addToRequestQueue(registro);


    }

    public void altaViaje(final Context context, final String id, final String descripccion, final String inicio, final String destino, final String fecha, final String costo, final String cantidad, final String escalas, final String hora,final String  fumar, final String  alcohol, final String  mascotas, final String  maletas) {
        Log.d(TAG, "altaViaje: ");
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta=response;
                        Log.d(TAG, "respuesteishon "+response);
                        Toast.makeText(context, "Viaje dado de alta correctamente.", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        respuesta=error.toString();
                        Toast.makeText(context, "Error al dar de alta viaje. Intentalo de nuevo", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo","altaviaje");
                params.put("id",id);
                params.put("descripccion",descripccion);
                params.put("inicio",inicio);
                params.put("destino",destino);
                params.put("fecha",fecha);
                params.put("costo",costo);
                params.put("cantidad",cantidad);
                params.put("escalas",escalas);
                params.put("hora",hora);
                params.put("fumar",fumar);
                params.put("alcohol", alcohol);
                params.put("mascotas",mascotas);
                params.put("maleta",maletas);
                return  params;
            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);
    }

    public void misDatos(final Context context, final String id, final EditText edtmarca, final EditText edtmodelo, final EditText edtaño, final ImageView carro, final RadioButton rdsi, final RadioButton rdno
    , final Button btnagregarfoto, final TextView txtv, final TextView txtm, final TextView txtmo, final TextView txta, final Button btnGuardar, final Button btnGuardar2, final ProgressView carga){
        Log.d(TAG, "misDatos: "+id);
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                        mis_datos m= new mis_datos();
                        if(!response.equals("\tfalse")) {
                            saberMisDatos(response, edtmarca, edtmodelo, edtaño, carro, context);
                            rdsi.setChecked(true);
                            edtmarca.setVisibility(View.VISIBLE);
                            edtmodelo.setVisibility(View.VISIBLE);
                            edtaño.setVisibility(View.VISIBLE);
                            carro.setVisibility(View.VISIBLE);
                            btnagregarfoto.setVisibility(View.VISIBLE);
                            txtv.setVisibility(View.VISIBLE);
                            txtm.setVisibility(View.VISIBLE);
                            txtmo.setVisibility(View.VISIBLE);
                            txta.setVisibility(View.VISIBLE);
                            btnGuardar.setVisibility(View.VISIBLE);
                            btnGuardar2.setVisibility(View.INVISIBLE);
                        }else {
                            rdno.setChecked(true);
                            edtmarca.setVisibility(View.INVISIBLE);
                            edtmodelo.setVisibility(View.INVISIBLE);
                            edtaño.setVisibility(View.INVISIBLE);
                            carro.setVisibility(View.INVISIBLE);
                            btnagregarfoto.setVisibility(View.INVISIBLE);
                            txtv.setVisibility(View.INVISIBLE);
                            txtm.setVisibility(View.INVISIBLE);
                            txtmo.setVisibility(View.INVISIBLE);
                            txta.setVisibility(View.INVISIBLE);
                            btnGuardar.setVisibility(View.INVISIBLE);
                            btnGuardar2.setVisibility(View.VISIBLE);
                        }
                        carga.setVisibility(View.INVISIBLE);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo","misdatos");
                params.put("ID",id);
                return  params;
            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);
    }

    public void promedio(final Context context, final String id, final ImageView s1,final ImageView s2,final ImageView s3,final ImageView s4,final ImageView s5){
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "PERFIL "+response);
                        if(!response.equals("\tnull")) {
                            String num=response.substring(2);
                            num=num.substring(0,4);
                            double prom = Double.parseDouble(num);
                            if (prom >= 4.5) {
                                s1.setVisibility(View.VISIBLE);
                                s2.setVisibility(View.VISIBLE);
                                s3.setVisibility(View.VISIBLE);
                                s4.setVisibility(View.VISIBLE);
                                s5.setVisibility(View.VISIBLE);
                            } else if (prom >= 3.5) {
                                s1.setVisibility(View.VISIBLE);
                                s2.setVisibility(View.VISIBLE);
                                s3.setVisibility(View.VISIBLE);
                                s4.setVisibility(View.VISIBLE);
                            } else if (prom >= 2.5) {
                                s1.setVisibility(View.VISIBLE);
                                s2.setVisibility(View.VISIBLE);
                                s3.setVisibility(View.VISIBLE);
                            } else if (prom >= 1.5) {
                                s1.setVisibility(View.VISIBLE);
                                s2.setVisibility(View.VISIBLE);
                            } else {
                                s1.setVisibility(View.VISIBLE);
                            }
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", "promedio");
                params.put("id", id);
                return params;
            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);

    }

    public void comentarios(final Context context,  final String id){
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta=response;
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", "pasajero");
                params.put("id", id);
                return params;
            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);

    }

    public void comentarios2(final Context context, final String id){
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta2=response;
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", "chofer");
                params.put("id", id);
                return params;
            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);

    }

    public void inscribirse(final Context context, final String id, final String idviaje, final com.rey.material.widget.Button btn){
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "AGREGADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                        btn.setText("Cancelar suscripccion");
                        Log.d(TAG, "SUSCRIPCCION "+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", "inscribirse");
                params.put("id", id);
                params.put("idviaje",idviaje);
                return params;
            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);
    }

    public void borrar(final Context context, final String id, final String idviaje, final com.rey.material.widget.Button btn){
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "BORRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "RESUESTA "+response);
                        btn.setText("Inscribirse");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", "borrar");
                params.put("id", id);
                params.put("idviaje",idviaje);
                Log.d(TAG, "id e id viaje "+id+"-"+idviaje);
                return params;
            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);
    }

    public void buscar(final Context context,  final String id, final String inicio, final String destino, final String fecha) {
        StringRequest loginRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta = response;
                        Log.d(TAG, "respuesta viajes " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "fallo " + volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tipo", "buscar");
                params.put("id", id);
                params.put("inicio", inicio);
                params.put("destino",destino);
                params.put("fecha",fecha);
                return params;

            }
        };

        Singleton_Volley.getInstance(context).addToRequestQueue(loginRequest);
    }

    public void chofer_pasajero(final Context context, final String id, final String tipo) {

        StringRequest loginRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta = response;
                        Log.d(TAG, "mis viajes " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "fallo " + volleyError.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tipo", tipo);
                params.put("id",id);
                return params;

            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(loginRequest);

    }

    public void comentar_chofer(final Context context, final String id, final String idviaje, final String comentario) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String fecha = format.format(date);
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta = response;
                        Log.d(TAG, "COMENTARIOS DEL CHOFER " + response);
                        Toast.makeText(context, "Comentario agregado con éxito.", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "fallo " + volleyError.toString());
                        Toast.makeText(context, "Error al agrgar comentario.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tipo", "comentario_chofer");
                params.put("id",id);
                params.put("idviaje",idviaje);
                params.put("comentario",comentario);
                params.put("fecha",fecha);

                return params;

            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);

    }

    public void comentar_pasajero(final Context context, final String id, final String idviaje, final String comentario) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String fecha = format.format(date);
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta = response;
                        Log.d(TAG, "COMENTARIOS DEL PASAJERO " + response);
                        Toast.makeText(context, "Comentario agregado con éxito.", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "fallo " + volleyError.toString());
                        Toast.makeText(context, "Error al agrgar comentario.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tipo", "comentario_pasajero");
                params.put("id",id);
                params.put("idviaje",idviaje);
                params.put("comentario",comentario);
                params.put("fecha",fecha);

                return params;

            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);

    }

    public void detalle_activo(final Context context,  final String id){
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta=response;
                        Log.d(TAG, "onResponse: "+response);
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", "detalle_activo");
                params.put("idviaje", id);
                return params;
            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);

    }

    public void detalleViajesActivo(Context context,final String id, final String idUsuario){
        StringRequest request= new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuesta=response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected  Map<String, String>getParams(){
                Map<String, String> params= new HashMap<>();
                params.put("tipo", "detalle_viajes_activos");
                params.put("id",id);
                params.put("idUsuario",idUsuario);
                return params;
            }

        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);
    }

    public void eliminar_usuario(final Context context, final String id, final String idviaje) {
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                        if(response.equals("\ttrue"))
                            Toast.makeText(context,"Usuario eliminado",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", "quitar_usuario");
                params.put("id",id);
                params.put("idviaje", idviaje);
                return params;
            }
        };

        Singleton_Volley.getInstance(context).addToRequestQueue(request);
    }

    public void terminar_viaje(final Context context, final String idviaje) {
        StringRequest request = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                        if(response.equals("\ttrue"))
                            Toast.makeText(context,"Viaje dado de baja",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", "terminar_viaje");
                params.put("idviaje", idviaje);
                return params;
            }
        };

        Singleton_Volley.getInstance(context).addToRequestQueue(request);
    }


    //-----------------------------------------------------------------------------------------------------------------


    //Pone en los objetos correspondientes los json que recibo
    public void subirMisDatos(final Context context, final String id, final String nombre, final String marca, final String modelo, final String año, final String tel){
        StringRequest request= new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "subir viaje "+ response);
                        if(response.equals("\ttrue"))
                            Toast.makeText(context, "Informacion actualizada con éxito", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Nombre actualizado con éxito", Toast.LENGTH_SHORT).show();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipo","misdatossubir");
                params.put("id",id);
                params.put("nombre",nombre);
                params.put("marca",marca);
                params.put("modelo",modelo);
                params.put("anio",año);
                params.put("tel",tel);
                return  params;
            }
        };
        Singleton_Volley.getInstance(context).addToRequestQueue(request);
    }

    private void saberMisDatos(String recibido, EditText marca, EditText modelo, EditText año, ImageView carro,Context context){
        String foto="";
        try {
            JSONObject jsonobjeto = new JSONObject(recibido);
            marca.setText(jsonobjeto.getString("Marca"));
            modelo.setText(jsonobjeto.getString("Modelo"));
            año.setText(jsonobjeto.getString("Anio"));
            foto=jsonobjeto.getString("Foto");
            cargarImagen(foto,carro, context);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private boolean saberLogin(String recibido) {
        String result = "";
        JSONObject jsonobject = null;
        try {
            JSONObject jsonObjecto = new JSONObject(recibido);
            result = jsonObjecto.getString("status");
            if (result.equals("true")) {
                usuario = new Usuario();
                jsonobject = jsonObjecto.getJSONObject("usuario");
                usuario.setId(jsonobject.getString("ID"));
                usuario.setEmail(jsonobject.getString("Email"));
                usuario.setNombre(jsonobject.getString("Nombre"));
                usuario.setImagen(jsonobject.getString("Imagen"));
            }
        } catch (JSONException e) {
            Log.d(TAG, "error " + e);
            e.printStackTrace();
        }

        if (result.equals("true"))
            return true;
        else
            return false;
    }

    public ArrayList<Viajes> saberviaje(String recibido) {
        String result = "";
        JSONArray jsonArray = null;
        Log.d(TAG, "saberviaje:" + recibido);
        if(recibido==null)
            return null;
        else {
            if (!recibido.equals("\tfalse")) {
                ArrayList<Viajes> viajes = new ArrayList<>();
                try {
                    JSONObject jsonObjecto = new JSONObject(recibido);
                    result = jsonObjecto.getString("respuesta");
                    if (result.equals("true")) {
                        jsonArray = jsonObjecto.getJSONArray("Viajes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            Viajes viaje = new Viajes();
                            viaje.setId(json.getString("ID"));
                            viaje.setNombre(json.getString("Nombre"));
                            viaje.setDestino(json.getString("Destino"));
                            viaje.setFecha(json.getString("Fecha"));
                            viaje.setCosto(json.getString("Costo"));
                            viajes.add(viaje);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return viajes;
            }
        }
        return null;

    }

    public ArrayList<Viajes> saberviaje_activo(String recibido) {
        String result = "";
        JSONArray jsonArray = null;
        Log.d(TAG, "saberviaje:" + recibido);
        if(recibido==null)
            return null;
        else {
            if (!recibido.equals("\tfalse")) {
                ArrayList<Viajes> viajes = new ArrayList<>();
                try {
                    JSONObject jsonObjecto = new JSONObject(recibido);
                    result = jsonObjecto.getString("respuesta");
                    if (result.equals("true")) {
                        jsonArray = jsonObjecto.getJSONArray("Viajes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            Viajes viaje = new Viajes();
                            viaje.setId(json.getString("ID"));
                            viaje.setNombre(json.getString("Nombre"));
                            viaje.setDestino(json.getString("Destino"));
                            viaje.setFecha(json.getString("Fecha"));
                            viaje.setCantidad(Integer.parseInt(json.getString("Cantidad")));
                            viajes.add(viaje);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return viajes;
            }
        }
        return null;

    }

    public Viajes saberDetalle(String descifrar){
        Viajes viaje = new Viajes();
        Log.d(TAG, "saberDetalle: "+descifrar);
        if(descifrar!=null) {
            try {
                JSONObject objeto = new JSONObject(descifrar);
                viaje.setId(objeto.getString("ID"));
                viaje.setIdusuario(objeto.getString("IDUsuario"));
                viaje.setNombre(objeto.getString("Nombre"));
                viaje.setTelefono(objeto.getString("Telefono"));
                viaje.setFecha(objeto.getString("Fecha"));
                viaje.setHora(objeto.getString("Hora"));
                viaje.setInicio(objeto.getString("Inicio"));
                viaje.setDestino(objeto.getString("Destino"));
                viaje.setCantidad(objeto.getInt("Cantidad"));
                viaje.setEscalas(objeto.getString("Escalas"));
                viaje.setCosto(objeto.getString("Costo"));
                viaje.setDescripcion(objeto.getString("Descripcion"));
                viaje.setCigarro(objeto.getString("Cigarro"));
                viaje.setMascota(objeto.getString("Mascota"));
                viaje.setAlcohol(objeto.getString("Alcohol"));
                viaje.setMaleta(objeto.getString("Maletas"));
                viaje.setMarca(objeto.getString("Marca"));
                viaje.setModelo(objeto.getString("Modelo"));
                viaje.setAnio(objeto.getString("Anio"));
                viaje.setFoto(objeto.getString("Foto"));
                viaje.setImagen(objeto.getString("imagen"));
                viaje.setRegistro(objeto.getString("Repetir"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return viaje;
        }
        else
            return null;

    }

    public Viajes saberDetalleViajes(String descifrar){
        Viajes viaje = new Viajes();
        Log.d(TAG, "saberDetalle: "+descifrar);
        if(descifrar!=null) {
            try {
                JSONObject objeto = new JSONObject(descifrar);
                viaje.setIdusuario(objeto.getString("IDUsuario"));
                viaje.setNombre(objeto.getString("Nombre"));
                viaje.setTelefono(objeto.getString("Telefono"));
                viaje.setMarca(objeto.getString("Marca"));
                viaje.setModelo(objeto.getString("Modelo"));
                viaje.setAnio(objeto.getString("Anio"));
                viaje.setImagen(objeto.getString("imagen"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return viaje;
        }
        else
            return null;

    }

    public List<comentarios> sabercomentario(String descrifar){
        String result = "";
        JSONArray jsonArray = null;
        List<comentarios> comentarios= new ArrayList<>();
        if(descrifar==null)
            return null;
        else {
            if (!descrifar.equals("\tfalse")) {
                try {
                    JSONArray jsonObjecto = new JSONArray(descrifar);
                        jsonArray = jsonObjecto;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            comentarios comentario = new comentarios();
                            comentario.setComentario(json.getString("Comentario"));
                            comentario.setNombre(json.getString("Nombre"));
                            comentario.setImagen(json.getString("Imagen"));
                            comentario.setFecha(json.getString("Fecha"));
                            comentarios.add(comentario);
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return comentarios;
            }
        }
        return null;
    }

    public void subirFoto(String pat, Context context){
        try {
            UploadNotificationConfig notificationConfig = new UploadNotificationConfig()
                    .setTitle("Subiendo foto")
                    .setInProgressMessage("Uploading at [[UPLOAD_RATE]] ([[PROGRESS]])")
                    .setErrorMessage("Ha ocurrido un error mientras su archivo era subido al servidor")
                    .setCompletedMessage("Archivo exitosamente subido al servidor en [[ELAPSED_TIME]]");
            Log.d(TAG, "subirFoto: "+pat);
            String uploadId =
                    new MultipartUploadRequest(context, URL2)
                            .addFileToUpload(pat,"file")
                            .setNotificationConfig(notificationConfig)
                    .setMaxRetries(3)
                    .setDelegate(new UploadStatusDelegate() {
                                @Override
                                public void onProgress(UploadInfo uploadInfo) {
                                    // your code here
                                }

                                @Override
                                public void onError(UploadInfo uploadInfo, Exception exception) {
                                }

                                @Override
                                public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
                                    Log.d(TAG, "onCompleted: "+serverResponse.getBodyAsString());
                                }

                                @Override
                                public void onCancelled(UploadInfo uploadInfo) {
                                    // your code here
                                }
                            })
                            .startUpload();
        } catch (Exception exc) {
            Log.e("ERROR", exc.getMessage(), exc);
        }
    }

    private void cargarImagen(String imagen,ImageView carro,Context context){
        if(!imagen.equals(""))
            Picasso.with(context)
                    .load(imagen)
                    .placeholder(R.drawable.email)
                    .error(R.drawable.perfil)
                    .into(carro);
    }

    public ArrayList<comentarios> saberDetalleActivo(String descrifar){
        String result = "";
        JSONArray jsonArray = null;
        ArrayList<comentarios> comentarios=new  ArrayList<>();
        if(descrifar==null)
            return null;
        else {
            if (!descrifar.equals("\tfalse")) {
                try {
                    JSONArray jsonObjecto = new JSONArray(descrifar);
                    jsonArray = jsonObjecto;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        comentarios comentario = new comentarios();
                        comentario.setComentario(json.getString("IDUsuario"));
                        comentario.setNombre(json.getString("Nombre"));
                        comentario.setImagen(json.getString("Imagen"));
                        comentarios.add(comentario);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return comentarios;
            }
        }
        return null;
    }
}
