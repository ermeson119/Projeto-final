package com.example.projetofinal.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetofinal.R;
import com.example.projetofinal.model.entity.Lixo;

import java.util.ArrayList;

public class TabelaAdapeter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Lixo> intensPriodica;

    public TabelaAdapeter(Context context, ArrayList<Lixo> intensPriodica) {
        inflater = LayoutInflater.from(context);
        this.intensPriodica = intensPriodica;
    }

    @Override
    public int getCount() {
        return intensPriodica.size();
    }

    @Override
    public Lixo getItem(int position) {
        return intensPriodica.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lixo, parent, false);
        }

        Lixo elemento = getItem(position);


        ImageView imagemElemento = convertView.findViewById(R.id.elemento_imagem);
        imagemElemento.setImageResource(elemento.getImagemLixo());

        TextView nomeElemento = convertView.findViewById(R.id.textViewDescricao);
        nomeElemento.setText(elemento.getDescricao());

        return convertView;
    }

}