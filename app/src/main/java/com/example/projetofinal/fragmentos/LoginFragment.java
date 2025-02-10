package com.example.projetofinal.fragmentos;

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

public class LoginFragment extends Fragment {
    private EditText input_email, input_senha;
    private Button botaoLogin, botaoCadastrar;
    private Database db;
    private UsuarioDAO usuarioDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        input_email = view.findViewById(R.id.input_email);
        input_senha = view.findViewById(R.id.input_senha);
        botaoLogin = view.findViewById(R.id.botao_login);
        botaoCadastrar = view.findViewById(R.id.botao_cadastrar);

        // Inicializa o banco diretamente sem usar threads
        db = Room.databaseBuilder(getActivity().getApplicationContext(), Database.class, "projeto_final.db")
                .allowMainThreadQueries() // Permite rodar na UI Thread
                .build();
        usuarioDAO = db.usuarioDAO();

        botaoLogin.setOnClickListener(v -> {
            String email = input_email.getText().toString().trim();
            String senha = input_senha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = usuarioDAO.autenticar(email, senha);

            if (usuario != null) {
                Toast.makeText(getActivity(), "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                abrirHomeFragment();
            } else {
                Toast.makeText(getActivity(), "Credenciais inválidas!", Toast.LENGTH_SHORT).show();
            }
        });

        botaoCadastrar.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new CadastroFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private void abrirHomeFragment() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
