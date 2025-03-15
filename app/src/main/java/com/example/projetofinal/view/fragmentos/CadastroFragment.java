package com.example.projetofinal.view.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.projetofinal.R;
import com.example.projetofinal.model.conexao.Database;
import com.example.projetofinal.model.dao.UsuarioDAO;
import com.example.projetofinal.model.entity.Usuario;
import com.example.projetofinal.view.fragmentos.LoginFragment;

public class CadastroFragment extends Fragment {
    private EditText input_nome, input_email, input_senha, input_confirmar_senha;
    private Button botaoCadastrar;
    private Database db;
    private UsuarioDAO usuarioDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_cadastro, container, false);

        input_nome = view.findViewById(R.id.input_nome);
        input_email = view.findViewById(R.id.input_email);
        input_senha = view.findViewById(R.id.input_senha);
        input_confirmar_senha = view.findViewById(R.id.input_confirmar_senha);
        botaoCadastrar = view.findViewById(R.id.botao_cadastrar);

        db = Room.databaseBuilder(getActivity().getApplicationContext(), Database.class, "projeto_final.db")
                .allowMainThreadQueries()
                .build();
        usuarioDAO = db.usuarioDAO();

        botaoCadastrar.setOnClickListener(v -> {
            String nome = input_nome.getText().toString().trim();
            String email = input_email.getText().toString().trim();
            String senha = input_senha.getText().toString().trim();
            String confirmarSenha = input_confirmar_senha.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmarSenha)) {
                Toast.makeText(getActivity(), "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuarioExistente = usuarioDAO.buscarUsuarioPorEmail(email);
            if (usuarioExistente != null) {
                Toast.makeText(getActivity(), "Email já cadastrado!", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario novoUsuario = new Usuario(nome, email, senha);
            usuarioDAO.inserirUsuario(novoUsuario);

            Toast.makeText(getActivity(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new LoginFragment());
            transaction.commit();
        });

        return view;
    }
}