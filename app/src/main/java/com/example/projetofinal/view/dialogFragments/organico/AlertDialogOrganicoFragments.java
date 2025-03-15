package com.example.projetofinal.view.dialogFragments.organico;

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

public class AlertDialogOrganicoFragments extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_organico, null);
        TextView textViewDescricao = view.findViewById(R.id.textViewDescricao);


        Button buttonFechar = view.findViewById(R.id.buttonFechar);
        Lixo lixo = new Lixo("Resíduos orgânicos representam cerca de 50% do lixo produzido em casa e, quando enviados para aterros, liberam metano, um gás 25 vezes " +
                "mais potente que o CO₂ no efeito estufa! Mas, ao serem compostados, eles se transformam em adubo natural, enriquecendo o solo e reduzindo a necessidade de fertilizantes químicos.");

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