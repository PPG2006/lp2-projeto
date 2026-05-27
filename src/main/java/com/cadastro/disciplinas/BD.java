package com.cadastro.disciplinas;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cadastro.disciplinas.domain.model.Curso;
import com.cadastro.disciplinas.domain.model.Disciplina;
import com.cadastro.disciplinas.domain.model.Professor;
import com.cadastro.disciplinas.domain.repository.CursoRepository;
import com.cadastro.disciplinas.domain.repository.DisciplinaRepository;
import com.cadastro.disciplinas.domain.repository.ProfessorRepository;

public class BD implements ProfessorRepository, CursoRepository, DisciplinaRepository{
    private String url, usuario, senha;
    private Connection conn;

    public BD(String url, String usuario, String senha){
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

   
   public void ConectaBanco(){

     try{
            Connection conn1 = DriverManager.getConnection(url + "postgres",usuario,senha);

            Statement stmt = conn1.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT 1 FROM pg_database WHERE datname = 'escola'");
            
            if (!rs.next()){

                System.out.println("Banco não existe");
                stmt.executeUpdate("CREATE DATABASE escola");
                conn = DriverManager.getConnection(url + "escola",usuario,senha );
                rodaScript();
                System.out.println("Banco criado!");
             }else{
                conn = DriverManager.getConnection(url + "escola",usuario,senha );
             }
             System.out.println("Conectado!");
            
        }
        catch (Exception e) {
            System.out.println("Erro:");
            System.out.println(e.getMessage());
            System.exit(-1);

        }
   }

   public void rodaScript() {

        try {
            Path caminho = Paths.get("bd_lp2.sql");
            String script = Files.readString(caminho);
            Statement stmt2 = conn.createStatement();
            String[] comandos = script.split(";");
            for(String sql : comandos){
                    if(!sql.trim().isEmpty()){
                        stmt2.execute(sql);
                    }
                }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

   
   
    @Override
   public void salvarProfessor(Professor professor) {
        
        String sql = "INSERT INTO professor (codigo_funcional, nome, data_nascimento) VALUES (?, ?, ?)";
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, professor.getCodigoFuncional());
            pstmt.setString(2, professor.getNome());
            pstmt.setDate(3, Date.valueOf(professor.getDataNascimento())); 
            pstmt.executeUpdate(); 
            System.out.println("Inserido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir professor: " + e.getMessage(), e);
        }
   }
   
   @Override
   public Optional<Professor> buscarPorCodigoProfessor(int codigoFuncional){
        
        String sql = "SELECT * FROM professor WHERE codigo_funcional = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, codigoFuncional);
            try (ResultSet rs = stmt.executeQuery()){
            
                if(rs.next()){
                    int codigo = rs.getInt("codigo_funcional");
                    String nome = rs.getString("nome");
                    LocalDate dataNasc = rs.getDate("data_nascimento").toLocalDate();
                    Professor professor = new Professor(codigo, nome, dataNasc);
                    return Optional.of(professor);
                } else return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor: " + e.getMessage(), e);
        }
    }

   @Override
   public List<Professor> listarTodosProfessores(){
    
        String sql = "SELECT * FROM professor";
        ArrayList<Professor> professores = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int codigoFuncional = rs.getInt("codigo_funcional");
                String nome = rs.getString("nome");
                LocalDate dataNasc = rs.getDate("data_nascimento").toLocalDate();
                Professor professor = new Professor(codigoFuncional, nome, dataNasc);
                professores.add(professor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar professores", e);
        }
        return professores;
   }

   @Override
   public void deletarProfessor(int codigoFuncional){
        String sql = "DELETE FROM professor WHERE codigo_funcional = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigoFuncional);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Nenhum professor encontrado com o código: " + codigoFuncional);
            } else {
                System.out.println("Professor deletado com sucesso!");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar professor com o código: " + codigoFuncional, e);
        }
   }

   @Override
   public void salvarCurso(Curso curso) {
        
        String sql = "INSERT INTO curso (codigo, nome, descricao) VALUES (?, ?, ?)";
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, curso.getCodigo());
            pstmt.setString(2, curso.getNome());
            pstmt.setString(3, curso.getDescricao()); 
            pstmt.executeUpdate(); 
            System.out.println("Inserido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir curso: " + e.getMessage(), e);
        }
   }
   
   @Override
   public Optional<Curso> buscarPorCodigoCurso(int codigo){
        
        String sql = "SELECT * FROM curso WHERE codigo = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()){
            
                if(rs.next()){
                    int cd = rs.getInt("codigo");
                    String nome = rs.getString("nome");
                    String descricao = rs.getString("descricao");
                    Curso curso = new Curso(cd, nome, descricao);
                    return Optional.of(curso);
                } else return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar curso: " + e.getMessage(), e);
        }
    }

   @Override
   public List<Curso> listarTodosCursos(){
    
        String sql = "SELECT * FROM curso";
        ArrayList<Curso> cursos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                Curso curso = new Curso(codigo, nome, descricao);
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cursos", e);
        }
        return cursos;
   }

   @Override
   public void deletarCurso(int codigo){
        String sql = "DELETE FROM curso WHERE codigo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Nenhum curso encontrado com o código: " + codigo);
            } else {
                System.out.println("Curso deletado com sucesso!");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar curso com o código: " + codigo, e);
        }
   }

      @Override
   public void salvarDisciplina(Disciplina disciplina) {
        
        String sql = "INSERT INTO disciplina (numero, nome, data_inicio, data_encerramento, codigo_professor, codigo_curso) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, disciplina.getNumero());
            pstmt.setString(2, disciplina.getNome());
            pstmt.setDate(3, Date.valueOf(disciplina.getDataInicio()));
            pstmt.setDate(4, Date.valueOf(disciplina.getDataEncerramento())); 
            pstmt.setInt(5, disciplina.getProfessor().getCodigoFuncional());
            pstmt.setInt(6, disciplina.getCurso().getCodigo());  

            pstmt.executeUpdate(); 
            System.out.println("Inserido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir disciplina: " + e.getMessage(), e);
        }
   }
   
   @Override
   public Optional<Disciplina> buscarPorNumeroDisciplina(int numero){
    
        String sql = "SELECT * FROM disciplina WHERE numero = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, numero);
            try (ResultSet rs = stmt.executeQuery()){
            
                if(rs.next()){
                    int nro = rs.getInt("numero");
                    String nome = rs.getString("nome");
                    LocalDate dataInicio = rs.getDate("data_inicio").toLocalDate();
                    LocalDate dataEncerramento = rs.getDate("data_encerramento").toLocalDate();
                    Professor professor = new Professor(null, null, null);
                    professor.setCodigoFuncional(rs.getInt("codigo_professor"));
                    Curso curso = new Curso(null, null, null);
                    curso.setCodigo(rs.getInt("codigo_curso"));
                    
                    Disciplina disciplina = new Disciplina (nro, nome, dataInicio, dataEncerramento, professor, curso);
                    return Optional.of(disciplina);
                } else return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar disciplina: " + e.getMessage(), e);
        }
    }

   @Override
   public List<Disciplina> listarTodasDisciplinas(){
    
        String sql = "SELECT * FROM disciplina";
        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int nro = rs.getInt("numero");
                String nome = rs.getString("nome");
                LocalDate dataInicio = rs.getDate("data_inicio").toLocalDate();
                LocalDate dataEncerramento = rs.getDate("data_encerramento").toLocalDate();

                Disciplina disciplina = new Disciplina(null, null, null, null, null, null);
                Professor codigoProfessor = new Professor(null, null, null);
                codigoProfessor.setCodigoFuncional(rs.getInt("codigo_professor"));
                disciplina.setProfessor(codigoProfessor);

                Curso codigoCurso = new Curso(null, null, null);
                codigoCurso.setCodigo(rs.getInt("codigo_curso"));
                disciplina.setCurso(codigoCurso);
                    
                disciplina = new Disciplina (nro, nome, dataInicio, dataEncerramento, codigoProfessor, codigoCurso);
                disciplinas.add(disciplina);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar disciplinas", e);
        }
        return disciplinas;
   }

   @Override
   public void deletarDisciplina(int numero){
        String sql = "DELETE FROM disciplina WHERE numero = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numero);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Nenhuma disciplina encontrada com o número: " + numero);
            } else {
                System.out.println("Disciplina deletada com sucesso!");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar disciplina com o número: " + numero, e);
        }
   }
}
