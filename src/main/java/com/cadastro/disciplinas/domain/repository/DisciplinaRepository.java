package com.cadastro.disciplinas.domain.repository;

import com.cadastro.disciplinas.domain.model.Disciplina;
import java.util.List;
import java.util.Optional;

public interface DisciplinaRepository {
    void salvarDisciplina(Disciplina disciplina);
    Optional<Disciplina> buscarPorNumeroDisciplina(int numero);
    List<Disciplina> listarTodasDisciplinas();
    void deletarDisciplina(int numero);
}
