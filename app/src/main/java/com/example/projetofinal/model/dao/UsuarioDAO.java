package com.example.projetofinal.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projetofinal.model.entity.Usuario;

@Dao
public interface UsuarioDAO {
    @Insert
    void inserirUsuario(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    Usuario autenticar(String email, String senha);
}