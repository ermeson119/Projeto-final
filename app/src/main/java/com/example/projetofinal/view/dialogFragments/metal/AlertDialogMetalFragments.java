package com.example.projetofinal.view.dialogFragments.metal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.projetofinal.R;
import com.example.projetofinal.model.entity.Lixo;

public class AlertDialogMetalFragments extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_metal, null);
        TextView textViewDescricao = view.findViewById(R.id.textViewDescricao);


        Button buttonFechar = view.findViewById(R.id.buttonFechar);
        Lixo lixo = new Lixo("Metal tal tal tal tal e importante");

        textViewDescricao.setText(lixo.getDescricao());

        buttonFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
