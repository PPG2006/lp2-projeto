package com.cadastro.disciplinas.domain.repository;

import com.cadastro.disciplinas.domain.model.Curso;
import java.util.List;
import java.util.Optional;

public interface ICursoRepository {
    void salvarCurso(Curso curso);
    void atualizarCurso(Curso curso);
    Optional<Curso> buscarPorCodigoCurso(int codigo);
    List<Curso> listarTodosCursos();
    void deletarCurso(int codigo);
}
