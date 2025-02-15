package com.example.projetofinal.view.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projetofinal.R;
import com.example.projetofinal.model.entity.DadosLixo;
import com.example.projetofinal.model.entity.Lixo;
import com.example.projetofinal.view.adpter.TabelaAdapeter;
import com.example.projetofinal.view.dialogFragments.metal.AlertDialogMetalFragments;
import com.example.projetofinal.view.dialogFragments.organico.AlertDialogOrganicoFragments;
import com.example.projetofinal.view.dialogFragments.papel.AlertDialogPapelFragments;
import com.example.projetofinal.view.dialogFragments.plastico.AlertDialogPlasticoFragments;
import com.example.projetofinal.view.dialogFragments.vidro.AlertDialogVidroFragments;


import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ListView listViewLixo;
    private Button btnJogar;
    private TabelaAdapeter adapter;
    private ArrayList<Lixo> listaLixo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnJogar = view.findViewById(R.id.buttonJogar);

        listViewLixo = view.findViewById(R.id.listViewLixo);
        criarAdapter();
        listViewLixo.setAdapter(adapter);
        listViewLixo.setOnItemClickListener(this::onItemClick);

        btnJogar.setOnClickListener(v -> abrirJogarPerguntaFragment());

        return view;
    }

    private void criarAdapter() {
        listaLixo = DadosLixo.obterAdapter();
        adapter = new TabelaAdapeter(requireContext(), listaLixo);
        listViewLixo.setAdapter(adapter);
    }

    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Lixo lixoSelecionado = (Lixo) parent.getItemAtPosition(position);
        abrirDialogFragment(lixoSelecionado.getDescricao());
    }

    private void abrirDialogFragment(String tipoLixo) {
        DialogFragment dialogFragment = null;

        switch (tipoLixo.toLowerCase()) {
            case "plastico":
                dialogFragment = new AlertDialogPlasticoFragments();
                break;
            case "papel":
                dialogFragment = new AlertDialogPapelFragments();
                break;
            case "vidro":
                dialogFragment = new AlertDialogVidroFragments();
                break;
            case "organico":
                dialogFragment = new AlertDialogOrganicoFragments();
                break;
            case "metal":
                dialogFragment = new AlertDialogMetalFragments();
                break;
        }

        if (dialogFragment != null) {
            dialogFragment.show(getParentFragmentManager(), "dialog_lixo");
        }
    }

    private void abrirJogarPerguntaFragment() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new JogarPerguntaFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
