package com.example.projetofinal.model.conexao;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.projetofinal.model.dao.UsuarioDAO;
import com.example.projetofinal.model.entity.Usuario;

@androidx.room.Database(entities = {Usuario.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract UsuarioDAO usuarioDAO();

}
