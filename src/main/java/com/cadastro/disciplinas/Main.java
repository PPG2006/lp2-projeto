package com.cadastro.disciplinas;

import java.time.LocalDate;

import com.cadastro.disciplinas.domain.model.Disciplina;
import com.cadastro.disciplinas.domain.model.Professor;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/"; 
        String usuario = "postgres";
        String senha = "postgres";

        BD banco = new BD(url, usuario,senha);
        banco.ConectaBanco();


    }

}
