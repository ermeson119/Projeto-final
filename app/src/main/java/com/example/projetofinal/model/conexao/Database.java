package com.example.projetofinal.model.conexao;

import androidx.room.RoomDatabase;

import com.example.projetofinal.model.dao.HistoricoDAO;
import com.example.projetofinal.model.dao.StatusJogadaDAO;
import com.example.projetofinal.model.dao.UsuarioDAO;
import com.example.projetofinal.model.entity.Historico;
import com.example.projetofinal.model.entity.StatusJogada;
import com.example.projetofinal.model.entity.Usuario;

@androidx.room.Database(entities = {Usuario.class, Historico.class, StatusJogada.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract UsuarioDAO usuarioDAO();
    public abstract HistoricoDAO historicoDAO();
    public abstract StatusJogadaDAO statusJogadaDAO();

}
