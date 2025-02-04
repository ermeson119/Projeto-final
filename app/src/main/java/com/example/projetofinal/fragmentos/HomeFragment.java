package com.example.projetofinal.fragmentos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.projetofinal.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView mensagemBoasVindas = view.findViewById(R.id.mensagem_boas_vindas);
        mensagemBoasVindas.setText("Bem-vindo ao app!");

        return view;
    }
}