package com.example.projetofinal.model.entity;

public class Lixo {

    private String descricao;
    private int imagemLixo;


    public Lixo(String descricao) {
        this.descricao = descricao;
    }

    public Lixo(String descricao, int imagemLixo) {
        this.descricao = descricao;
        this.imagemLixo = imagemLixo;
    }

    public int getImagemLixo() {
        return imagemLixo;
    }

    public void setImagemLixo(int imagemLixo) {
        this.imagemLixo = imagemLixo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
