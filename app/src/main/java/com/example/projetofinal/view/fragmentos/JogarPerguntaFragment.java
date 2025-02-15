package com.example.projetofinal.view.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.projetofinal.R;

public class JogarPerguntaFragment  extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_jogar_pergunta, container, false);

        TextView mensagemBoasVindas = view.findViewById(R.id.mensagem_boas_vindas);
        mensagemBoasVindas.setText("Bora jogar!");

        return view;
    }
}
