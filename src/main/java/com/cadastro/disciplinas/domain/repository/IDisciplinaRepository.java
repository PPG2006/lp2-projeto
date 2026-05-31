package com.cadastro.disciplinas.domain.repository;

import com.cadastro.disciplinas.domain.model.Disciplina;
import java.util.List;
import java.util.Optional;

public interface IDisciplinaRepository {
    void salvarDisciplina(Disciplina disciplina);
    void atualizarDisciplina(Disciplina disciplina);
    Optional<Disciplina> buscarPorNumeroDisciplina(int numero);
    List<Disciplina> listarTodasDisciplinas();
    void deletarDisciplina(int numero);
}
