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

    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;

    public class LoginFragment extends Fragment {
        private EditText input_email, input_senha;
        private Button botaoLogin, botaoCadastrar;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_login, container, false);

            input_email = view.findViewById(R.id.input_email);
            input_senha = view.findViewById(R.id.input_senha);
            botaoLogin = view.findViewById(R.id.botao_login);
            botaoCadastrar = view.findViewById(R.id.botao_cadastrar);

            botaoLogin.setOnClickListener(v -> {
                String email = input_email.getText().toString().trim();
                String senha = input_senha.getText().toString().trim();

                if (email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    UsuarioDAO usuarioDAO = Database.getInstance(getActivity()).usuarioDAO();
                    Usuario usuario = usuarioDAO.autenticar(email, senha);

                    getActivity().runOnUiThread(() -> {
                        if (usuario != null) {
                            Toast.makeText(getActivity(), "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                            abrirHomeFragment();
                        } else {
                            Toast.makeText(getActivity(), "Credenciais inválidas!", Toast.LENGTH_SHORT).show();
                        }
                    });
                });

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
