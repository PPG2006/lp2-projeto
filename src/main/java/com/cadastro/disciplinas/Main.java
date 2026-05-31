package com.cadastro.disciplinas;

import com.cadastro.disciplinas.domain.repository.ITodosRepository;
import com.cadastro.disciplinas.infrastructure.database.BD;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("DB_URL");
        String usuario = dotenv.get("DB_USER");
        String senha = dotenv.get("DB_PASSWORD");

        BD banco = new BD(url, usuario, senha);

        UI ui = new UI((ITodosRepository) banco);
        ui.iniciar();
    }
}
