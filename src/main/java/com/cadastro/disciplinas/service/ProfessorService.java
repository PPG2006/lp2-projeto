package com.cadastro.disciplinas.service;

import java.time.LocalDate;

import com.cadastro.disciplinas.domain.model.Professor;
import com.cadastro.disciplinas.domain.repository.IProfessorRepository;

public class ProfessorService{
    private final IProfessorRepository professorRepository;

    // Injeção de dependência via construtor
    public ProfessorService(IProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public void cadastrarProfessor(Professor professor) {
        
        LocalDate maioridade = LocalDate.now().minusYears(18);

        if (professor == null) throw new IllegalArgumentException("Professor não pode ser nulo.");
        if (professor.getCodigoFuncional() <= 0) throw new IllegalArgumentException("Código funcional deve ser maior que zero.");
        if (professor.getNome() == null) throw new IllegalArgumentException("Nome não pode ser nulo.");
        if (professor.getDataNascimento().isAfter(maioridade)) throw new IllegalArgumentException("Professor deve ter pelo menos 18 anos.");
        
        professorRepository.salvarProfessor(professor);
    }

    public void atualizarProfessor(Professor professor) {
        LocalDate maioridade = LocalDate.now().minusYears(18);

        if (professor == null) throw new IllegalArgumentException("Professor não pode ser nulo.");
        if (professor.getCodigoFuncional() <= 0) throw new IllegalArgumentException("Código funcional deve ser maior que zero.");
        if (professor.getNome() == null) throw new IllegalArgumentException("Nome não pode ser nulo.");
        if (professor.getDataNascimento().isAfter(maioridade)) throw new IllegalArgumentException("Professor deve ter pelo menos 18 anos.");
        
        professorRepository.atualizarProfessor(professor);
    }
}