package com.cadastro.disciplinas;

import com.cadastro.disciplinas.domain.repository.TodosRepository;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/";
        String usuario = "postgres";
        String senha = "postgres";

        BD banco = new BD(url, usuario, senha);

        UI ui = new UI((TodosRepository) banco);
        ui.iniciar();

    }

}
