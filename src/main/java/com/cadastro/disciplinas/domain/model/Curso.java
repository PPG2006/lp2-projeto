package com.cadastro.disciplinas.domain.model;

public class Curso {
    private int codigo;
    private String nome;
    private String descricao;

    public Curso(int codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters
    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }

    // Setters
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
