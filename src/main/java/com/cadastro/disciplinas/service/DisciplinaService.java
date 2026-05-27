package com.cadastro.disciplinas.service;

import com.cadastro.disciplinas.domain.model.Disciplina;
import com.cadastro.disciplinas.domain.repository.DisciplinaRepository;
import java.time.LocalDate;

public class DisciplinaService {
    private final DisciplinaRepository disciplinaRepository;

    // Injeção de dependência via construtor
    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public void registrarDisciplina(Disciplina disciplina) {
        
        if (disciplina.getProfessor() == null) throw new IllegalArgumentException("Professor não pode ser nulo.");
        if (disciplina.getCurso() == null) throw new IllegalArgumentException("Curso não pode ser nulo.");
        if (disciplina.getNumero() <= 0) throw new IllegalArgumentException("Número deve ser maior que zero.");
        if (disciplina.getNome() == null) throw new IllegalArgumentException("Nome não deve ser nulo.");
        if (disciplina.getDataInicio() == null) throw new IllegalArgumentException("Data de início não deve ser nula.");
        
        // R4: Validando a data de início através da lógica de negócio na própria entidade
        disciplina.validarDataInicio(LocalDate.now());
        if (disciplina.getDataEncerramento() == null) throw new IllegalArgumentException("Data de encerramento não deve ser nula.");
        if (disciplina.getDataInicio().isAfter(disciplina.getDataEncerramento())) if (disciplina.getDataEncerramento() == null) throw new IllegalArgumentException("Data de encerramento não deve ser antes de data de início.");
        
        disciplinaRepository.salvarDisciplina(disciplina);
    }

    public void atualizarDisciplina(Disciplina disciplina) {
        if (disciplina.getProfessor() == null) throw new IllegalArgumentException("Professor não pode ser nulo.");
        if (disciplina.getCurso() == null) throw new IllegalArgumentException("Curso não pode ser nulo.");
        if (disciplina.getNumero() <= 0) throw new IllegalArgumentException("Número deve ser maior que zero.");
        if (disciplina.getNome() == null) throw new IllegalArgumentException("Nome não deve ser nulo.");
        if (disciplina.getDataInicio() == null) throw new IllegalArgumentException("Data de início não deve ser nula.");
        
        // Pode ser necessário ignorar a validação de data de início para edições antigas, 
        // mas manteremos como regra de negócio para simplificação
        disciplina.validarDataInicio(LocalDate.now());
        if (disciplina.getDataEncerramento() == null) throw new IllegalArgumentException("Data de encerramento não deve ser nula.");
        if (disciplina.getDataInicio().isAfter(disciplina.getDataEncerramento())) throw new IllegalArgumentException("Data de encerramento não deve ser antes de data de início.");
        
        disciplinaRepository.atualizarDisciplina(disciplina);
    }
}
