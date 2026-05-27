package com.cadastro.disciplinas.domain.model;

import java.time.LocalDate;

public class Professor {
    private int codigoFuncional;
    private String nome;
    private LocalDate dataNascimento;

    public Professor(int codigoFuncional, String nome, LocalDate dataNascimento) {
        this.codigoFuncional = codigoFuncional;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    // Getters
    public int getCodigoFuncional() { return codigoFuncional; }
    public String getNome() { return nome; }
    public LocalDate getDataNascimento() { return dataNascimento; }

    // Setters
    public void setCodigoFuncional(int codigoFuncional) { this.codigoFuncional = codigoFuncional; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
}
