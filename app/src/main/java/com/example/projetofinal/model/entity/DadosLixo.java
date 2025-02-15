package com.example.projetofinal.model.entity;

import com.example.projetofinal.R;

import java.util.ArrayList;

public class DadosLixo {

    public static ArrayList<Lixo> obterAdapter() {
        ArrayList<Lixo> listaElementos = new ArrayList<>();
        listaElementos.add(new Lixo("Plastico", R.drawable.ic_launcher_background));
        listaElementos.add(new Lixo("Papel", R.drawable.ic_launcher_background));
        listaElementos.add(new Lixo("Vidro", R.drawable.ic_launcher_background));
        listaElementos.add(new Lixo("Organico", R.drawable.ic_launcher_background));
        listaElementos.add(new Lixo("Metal", R.drawable.ic_launcher_background));
    ;

        return listaElementos;
    }
}
