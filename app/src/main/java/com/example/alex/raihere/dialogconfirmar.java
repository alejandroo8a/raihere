package com.example.alex.raihere;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.alex.raihere.Web_Service.Peticiones;

/**
 * Created by alejandro on 7/10/16.
 */

public class dialogconfirmar extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Desea eliminar a este pasajero de su viaje?")
        .setTitle("Aviso")
        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String iduser=getArguments().getString("IDUSER"), idviaje=getArguments().getString("VIAJE");
                Peticiones peticiones = new Peticiones();
                peticiones.eliminar_usuario(getActivity(),iduser,idviaje);
            }
        })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        return builder.create();
    }

}
