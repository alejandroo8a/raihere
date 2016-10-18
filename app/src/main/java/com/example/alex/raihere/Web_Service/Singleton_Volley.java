package com.example.alex.raihere.Web_Service;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by waldo on 23/08/16.
 */
public class Singleton_Volley extends Application{
    public static final String TAG = "Singleton_Volley";


    // Atributos
    private static Singleton_Volley singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private Singleton_Volley(Context context) {
        Singleton_Volley.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized Singleton_Volley getInstance(Context context) {
        if (singleton == null) {
            singleton = new Singleton_Volley(context);
        }
        return singleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public  void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }
}
