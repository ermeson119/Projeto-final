package com.example.projetofinal.fragmentos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.projetofinal.model.conexao.Database;
import com.example.projetofinal.model.entity.Usuario;
import com.example.projetofinal.model.dao.UsuarioDAO;
import com.example.projetofinal.R;
import java.util.concurrent.Executors;

public class CadastroFragment extends Fragment {
    private EditText input_nome, input_email, input_senha;
    private Button botaoCadastrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro, container, false);

        input_nome = view.findViewById(R.id.input_nome);
        input_email = view.findViewById(R.id.input_email);
        input_senha = view.findViewById(R.id.input_senha);
        botaoCadastrar = view.findViewById(R.id.botao_cadastrar);

        botaoCadastrar.setOnClickListener(v -> {
            String nome = input_nome.getText().toString().trim();
            String email = input_email.getText().toString().trim();
            String senha = input_senha.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Executando a inserção em uma thread separada
            Executors.newSingleThreadExecutor().execute(() -> {
                UsuarioDAO usuarioDAO = Database.getInstance(getActivity()).usuarioDAO();
                usuarioDAO.inserirUsuario(new Usuario(nome, email, senha));

                // Atualizar a UI na thread principal
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getActivity(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                    // Retorna para a tela de login
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new LoginFragment());
                    transaction.commit();
                });
            });
        });

        return view;
    }
}
