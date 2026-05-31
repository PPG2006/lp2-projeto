package com.cadastro.disciplinas;

import com.cadastro.disciplinas.domain.repository.ITodosRepository;
import com.cadastro.disciplinas.infrastructure.database.BD;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/";
        String usuario = "teles";
        String senha = "pg";

        BD banco = new BD(url, usuario, senha);

        UI ui = new UI((ITodosRepository) banco);
        ui.iniciar();
    }
}
