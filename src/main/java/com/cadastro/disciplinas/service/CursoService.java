package com.cadastro.disciplinas.service;

import com.cadastro.disciplinas.domain.model.Curso;
import com.cadastro.disciplinas.domain.repository.CursoRepository;

public class CursoService {
    private final CursoRepository cursoRepository;

    // Injeção de dependência via construtor
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public void cadastrarCurso(Curso curso) {
        
        if (curso == null) throw new IllegalArgumentException("Curso não pode ser nulo.");
        if (curso.getCodigo() <= 0) throw new IllegalArgumentException("Código deve ser maior que zero.");
        if (curso.getNome() == null) throw new IllegalArgumentException("Nome não deve ser nulo.");
        if (curso.getDescricao() == null) throw new IllegalArgumentException("Descrição não deve ser nula.");

        cursoRepository.salvarCurso(curso);
    }

    public void atualizarCurso(Curso curso) {
        if (curso == null) throw new IllegalArgumentException("Curso não pode ser nulo.");
        if (curso.getCodigo() <= 0) throw new IllegalArgumentException("Código deve ser maior que zero.");
        if (curso.getNome() == null) throw new IllegalArgumentException("Nome não deve ser nulo.");
        if (curso.getDescricao() == null) throw new IllegalArgumentException("Descrição não deve ser nula.");

        cursoRepository.atualizarCurso(curso);
    }
}
