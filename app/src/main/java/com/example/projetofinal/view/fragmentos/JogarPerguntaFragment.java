package com.example.projetofinal.view.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.projetofinal.R;
import com.example.projetofinal.model.conexao.Database;
import com.example.projetofinal.model.dao.HistoricoDAO;
import com.example.projetofinal.model.dao.StatusJogadaDAO;
import com.example.projetofinal.model.dao.UsuarioDAO;
import com.example.projetofinal.model.entity.DadosLixo;
import com.example.projetofinal.model.entity.Historico;
import com.example.projetofinal.model.entity.Lixo;
import com.example.projetofinal.model.entity.StatusJogada;
import com.example.projetofinal.model.entity.Usuario;

import java.util.List;

public class JogarPerguntaFragment extends Fragment {

    private LinearLayout perguntasLayout;
    private TextView textViewCoracoes, textViewPontuacao;
    private Button btnFinalizar;
    private List<Lixo> lixosSelecionados;
    private Database db;
    private HistoricoDAO historicoDAO;
    private UsuarioDAO usuarioDAO;
    private StatusJogadaDAO statusJogadaDAO;
    private StatusJogada statusJogada;
    private Usuario usuarioLogado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_jogar_pergunta, container, false);

        perguntasLayout = view.findViewById(R.id.perguntasLayout);
        textViewCoracoes = view.findViewById(R.id.textViewCoracoes);
        textViewPontuacao = view.findViewById(R.id.textViewPontuacao);
        btnFinalizar = view.findViewById(R.id.btnFinalizar);

        db = Room.databaseBuilder(getActivity().getApplicationContext(), Database.class, "projeto_final.db")
                .allowMainThreadQueries()
                .build();
        usuarioDAO = db.usuarioDAO();
        historicoDAO = db.historicoDAO();
        statusJogadaDAO = db.statusJogadaDAO();

        usuarioLogado = usuarioDAO.getUsuarioLogado();

        if (usuarioLogado != null) {
            statusJogadaDAO.finalizarJogada(usuarioLogado.getId());


            statusJogada = new StatusJogada(usuarioLogado.getId(), 3, 0);
            statusJogadaDAO.iniciarJogada(statusJogada);
        } else {
            Toast.makeText(getActivity(), "Erro: Nenhum usuário logado encontrado!", Toast.LENGTH_LONG).show();
        }

        atualizarVida();

        lixosSelecionados = DadosLixo.obterAdapter();
        if (lixosSelecionados.isEmpty()) {
            Toast.makeText(getActivity(), "Erro: Nenhuma pergunta carregada!", Toast.LENGTH_LONG).show();
        } else {
            gerarPerguntas();
        }

        btnFinalizar.setOnClickListener(v -> finalizarJogo());

        return view;
    }

    private void gerarPerguntas() {
        for (Lixo lixo : lixosSelecionados) {
            View perguntaView = getLayoutInflater().inflate(R.layout.item_pergunta, perguntasLayout, false);

            TextView textViewElemento = perguntaView.findViewById(R.id.textViewElemento);
            ImageView imageViewElemento = perguntaView.findViewById(R.id.elemento_imagem);
            CheckBox checkBox1 = perguntaView.findViewById(R.id.checkBox1);
            CheckBox checkBox2 = perguntaView.findViewById(R.id.checkBox2);
            CheckBox checkBox3 = perguntaView.findViewById(R.id.checkBox3);
            CheckBox checkBox4 = perguntaView.findViewById(R.id.checkBox4);

            if (imageViewElemento != null) {
                int imagemResId = lixo.getImagemLixo();
                imageViewElemento.setImageResource(imagemResId);
            }

            textViewElemento.setText("Qual lixeira correta para: " + lixo.getDescricao());

            String[] opcoes = carregarPerguntas(lixo.getDescricao());
            if (opcoes.length == 4) {
                checkBox1.setText(opcoes[0]);
                checkBox2.setText(opcoes[1]);
                checkBox3.setText(opcoes[2]);
                checkBox4.setText(opcoes[3]);

                View.OnClickListener respostaListener = v -> {
                    CheckBox selecionado = (CheckBox) v;
                    boolean acertou = selecionado.getText().toString().equals(opcoes[0]);
                    atualizarStatusJogada(usuarioLogado.getId(), acertou);

                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                };

                checkBox1.setOnClickListener(respostaListener);
                checkBox2.setOnClickListener(respostaListener);
                checkBox3.setOnClickListener(respostaListener);
                checkBox4.setOnClickListener(respostaListener);
            } else {
                Toast.makeText(getActivity(), "Erro ao carregar perguntas para " + lixo.getDescricao(), Toast.LENGTH_SHORT).show();
            }

            perguntasLayout.addView(perguntaView);
        }
    }

    private String[] carregarPerguntas(String tipoLixo) {
        int arrayResId;
        switch (tipoLixo.toLowerCase()) {
            case "plastico":
                arrayResId = R.array.perguntas_plastico;
                break;
            case "papel":
                arrayResId = R.array.perguntas_papel;
                break;
            case "vidro":
                arrayResId = R.array.perguntas_vidro;
                break;
            case "organico":
                arrayResId = R.array.perguntas_organico;
                break;
            case "metal":
                arrayResId = R.array.perguntas_metal;
                break;
            default:
                return new String[]{};
        }
        return getResources().getStringArray(arrayResId);
    }

    private void atualizarStatusJogada(int usuarioId, boolean acertou) {
        statusJogada = statusJogadaDAO.obterJogadaAtual(usuarioId);
        if (statusJogada != null) {
            if (acertou) {
                statusJogada.setPontos(statusJogada.getPontos() + 10);
            } else {
                if (statusJogada.getVidas() > 0) {
                    statusJogada.setVidas(statusJogada.getVidas() - 1);
                }

                if (statusJogada.getVidas() == 0) {
                    finalizarJogo();
                    return;
                }
            }
            statusJogadaDAO.atualizarJogada(statusJogada);
            atualizarVida();
        }
    }

    private void atualizarVida() {
        statusJogada = statusJogadaDAO.obterJogadaAtual(usuarioLogado.getId());
        if (statusJogada != null) {
            textViewCoracoes.setText("❤ ".repeat(statusJogada.getVidas()));
            textViewPontuacao.setText("Pontuação: " + statusJogada.getPontos());
        }
    }

    private void finalizarJogo() {
        statusJogada = statusJogadaDAO.obterJogadaAtual(usuarioLogado.getId());

        if (usuarioLogado != null && statusJogada != null) {
            Historico novoHistorico = new Historico(usuarioLogado.getId(), statusJogada.getPontos());
            historicoDAO.inserirHistorico(novoHistorico);

            statusJogadaDAO.finalizarJogada(usuarioLogado.getId());
        }

        Toast.makeText(getActivity(), "Jogo encerrado! Confira seus resultados.", Toast.LENGTH_LONG).show();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ResultadoFragment())
                .commit();
    }
}