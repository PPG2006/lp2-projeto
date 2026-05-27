package com.cadastro.disciplinas.domain.repository;

import com.cadastro.disciplinas.domain.model.Professor;
import java.util.List;
import java.util.Optional;

public interface ProfessorRepository {
    void salvarProfessor(Professor professor);
    Optional<Professor> buscarPorCodigoProfessor(int codigoFuncional);
    List<Professor> listarTodosProfessores();
    void deletarProfessor(int codigoFuncional);
}
