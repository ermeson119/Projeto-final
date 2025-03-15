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

    @Query("UPDATE usuarios SET logado = 0")
    void deslogarTodosUsuarios();

    @Query("UPDATE usuarios SET logado = 1 WHERE email = :email")
    void setUsuarioLogado(String email);

    @Query("SELECT * FROM usuarios WHERE logado = 1 LIMIT 1")
    Usuario getUsuarioLogado();

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    Usuario buscarUsuarioPorEmail(String email);

}