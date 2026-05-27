package com.cadastro.disciplinas.domain.model;

import java.time.LocalDate;

public class Disciplina {
    private int numero;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataEncerramento;
    private Professor professor;
    private Curso curso;

    public Disciplina(int numero, String nome, LocalDate dataInicio, LocalDate dataEncerramento, Professor professor, Curso curso) {
        this.numero = numero;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataEncerramento = dataEncerramento;
        this.professor = professor;
        this.curso = curso;
    }

    /**
     * R4 - Ao registrar uma disciplina é necessário validar a data de início do curso 
     * para que não seja possível informar uma data anterior à data corrente.
     */
    public void validarDataInicio(LocalDate dataCorrente) {
        if (this.dataInicio != null && this.dataInicio.isBefore(dataCorrente)) {
            throw new IllegalArgumentException("A data de início da disciplina não pode ser anterior à data corrente.");
        }
    }

    // Getters
    public int getNumero() { return numero; }
    public String getNome() { return nome; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataEncerramento() { return dataEncerramento; }
    public Professor getProfessor() { return professor; }
    public Curso getCurso() { return curso; }

    // Setters
    public void setNumero(int numero) { this.numero = numero; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public void setDataEncerramento(LocalDate dataEncerramento) { this.dataEncerramento = dataEncerramento; }
    public void setProfessor(Professor professor) { this.professor = professor; }
    public void setCurso(Curso curso) { this.curso = curso; }
}
