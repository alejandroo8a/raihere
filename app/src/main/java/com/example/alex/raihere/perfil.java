package com.example.alex.raihere;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.raihere.Web_Service.Peticiones;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link perfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class perfil extends Fragment {
    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";
    private static final String TAG="perfil";
    private OnFragmentInteractionListener mListener;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView txtnombre, txttelefono, txtmodelo, txtmarca, txtaño;
    private SharedPreferences sharedPreferences;
    private ImageView foto,s1,s2,s3,s4,s5;



    public perfil() {
        // Required empty public constructor
    }

    public static perfil newInstance(String param1, String param2) {
        perfil fragment = new perfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_perfil, container, false);
        tabLayout=(TabLayout)view.findViewById(R.id.tabs2);
        viewPager=(ViewPager)view.findViewById(R.id.viewpager2);
        txtnombre=(TextView)view.findViewById(R.id.txtnombreperfil);
        txttelefono=(TextView)view.findViewById(R.id.txtnumero);
        txtmarca=(TextView)view.findViewById(R.id.txtmarca);
        txtmodelo=(TextView)view.findViewById(R.id.txtmodelo);
        txtaño=(TextView)view.findViewById(R.id.txtaño);
        foto=(ImageView)view.findViewById(R.id.perfilperfil);
        s1=(ImageView)view.findViewById(R.id.imgstar1);
        s2=(ImageView)view.findViewById(R.id.imgstar2);
        s3=(ImageView)view.findViewById(R.id.imgstar3);
        s4=(ImageView)view.findViewById(R.id.imgstar4);
        s5=(ImageView)view.findViewById(R.id.imgstar5);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Peticiones peticiones= new Peticiones();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        String tipo=sharedPreferences.getString("TIPOPERFIL","");
        Log.d(TAG, "perfil "+tipo);
        if(tipo.equals("0")) {
            Log.d(TAG, "emtre ");
            String id = sharedPreferences.getString("IDUSUARIO", "1");
            peticiones.promedio(getContext(), id, s1, s2, s3, s4, s5);
            txtnombre.setText(sharedPreferences.getString("NOMBRE", "Sin nombre"));
            txttelefono.setText(sharedPreferences.getString("TELEFONO", "Sin numero"));
            txtmarca.setText(sharedPreferences.getString("MARCA", ""));
            txtmodelo.setText(sharedPreferences.getString("MODELO", ""));
            txtaño.setText(sharedPreferences.getString("AÑO", ""));
            cargarImagen(sharedPreferences.getString("IMAGEN", ""));
        }
        else{
            Log.d(TAG, "no entre ");
            String id = sharedPreferences.getString("IDUSUARIOP", "");
            peticiones.promedio(getContext(), id, s1, s2, s3, s4, s5);
            txtnombre.setText(sharedPreferences.getString("NOMBREP", "Sin nombre"));
            txttelefono.setText(sharedPreferences.getString("TELEFONOP", "Sin numero"));
            txtmarca.setText(sharedPreferences.getString("MARCAP", ""));
            txtmodelo.setText(sharedPreferences.getString("MODELOP", ""));
            txtaño.setText(sharedPreferences.getString("AÑOP", ""));
            cargarImagen(sharedPreferences.getString("IMAGENP", ""));

        }
        setupViewPager(viewPager);
        // after you set the adapter you have to check if view is laid out, i did a custom method for it
        if (ViewCompat.isLaidOut(tabLayout)) {
            setViewPagerListener();
        } else {
            tabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    setViewPagerListener();
                    tabLayout.removeOnLayoutChangeListener(this);
                }
            });
        }
    }

    private void setViewPagerListener() {
        tabLayout.setupWithViewPager(viewPager);
        // use class TabLayout.ViewPagerOnTabSelectedListener
        // note that it's a class not an interface as OnTabSelectedListener, so you can't implement it in your activity/fragment
        // methods are optional, so if you don't use them, you can not override them (e.g. onTabUnselected)
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        perfil.ViewPagerAdapter viewPagerAdapter = new perfil.ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new comentarios_chofer(), "Chofer");
        viewPagerAdapter.addFragment(new comentarios_pasajero(), "Pasajero");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            fragmentTitles.add(name);
        }
    }

    private void cargarImagen(String imagen){
        if(!imagen.equals(""))
            Picasso.with(getContext())
                    .load(imagen)
                    .placeholder(R.drawable.email)
                    .error(R.drawable.perfil)
                    .into(foto);
    }

}
