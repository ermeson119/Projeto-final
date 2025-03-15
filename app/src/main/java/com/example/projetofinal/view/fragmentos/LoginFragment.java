package com.example.projetofinal.view.fragmentos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private ProgressBar progressBar;
    private Database db;
    private UsuarioDAO usuarioDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_login, container, false);

        input_email = view.findViewById(R.id.input_email);
        input_senha = view.findViewById(R.id.input_senha);
        botaoLogin = view.findViewById(R.id.botao_login);
        botaoCadastrar = view.findViewById(R.id.botao_cadastrar);
        progressBar = view.findViewById(R.id.progressBar);

        db = Room.databaseBuilder(getActivity().getApplicationContext(), Database.class, "projeto_final.db")
                .allowMainThreadQueries()
                .build();
        usuarioDAO = db.usuarioDAO();

        botaoLogin.setOnClickListener(v -> {
            String email = input_email.getText().toString().trim();
            String senha = input_senha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            botaoLogin.setEnabled(false);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Usuario usuario = usuarioDAO.autenticar(email, senha);

                progressBar.setVisibility(View.GONE);
                botaoLogin.setEnabled(true);

                if (usuario != null) {
                    Toast.makeText(getActivity(), "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                    usuarioDAO.deslogarTodosUsuarios();
                    usuarioDAO.setUsuarioLogado(email);

                    abrirHomeFragment();
                } else {
                    Toast.makeText(getActivity(), "Credenciais inválidas!", Toast.LENGTH_SHORT).show();
                }
            }, 3000);
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