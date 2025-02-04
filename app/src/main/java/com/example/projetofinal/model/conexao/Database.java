package com.example.projetofinal.model.conexao;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.projetofinal.model.dao.UsuarioDAO;
import com.example.projetofinal.model.entity.Usuario;

@androidx.room.Database(entities = {Usuario.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract UsuarioDAO usuarioDAO();

    private static volatile Database instancia; // volatile assegura que todas as threads vejam as atualizações corretas da variável.

    public static Database getInstance(Context contexto) {
        if (instancia == null) {
            synchronized (Database.class) {
                if (instancia == null) {
                    instancia = Room.databaseBuilder(contexto.getApplicationContext(),
                                    Database.class, "projeto_final.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instancia;
    }
}
