package com.cadastro.disciplinas;

import com.cadastro.disciplinas.domain.model.Curso;
import com.cadastro.disciplinas.domain.model.Disciplina;
import com.cadastro.disciplinas.domain.model.Professor;
import com.cadastro.disciplinas.domain.repository.ITodosRepository;
import com.cadastro.disciplinas.service.CursoService;
import com.cadastro.disciplinas.service.DisciplinaService;
import com.cadastro.disciplinas.service.ProfessorService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UI {

    private final ITodosRepository repositorio;
    private final ProfessorService professorService;
    private final CursoService cursoService;
    private final DisciplinaService disciplinaService;
    private final Scanner scanner;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
        "dd/MM/yyyy"
    );

    public UI(ITodosRepository repositorio) {
        this.repositorio = repositorio;
        this.professorService = new ProfessorService(repositorio);
        this.cursoService = new CursoService(repositorio);
        this.disciplinaService = new DisciplinaService(repositorio);
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao;
        do {
            System.out.println("\n=== Sistema de Cadastro de Disciplinas ===");
            System.out.println("1. Gerenciar Professores");
            System.out.println("2. Gerenciar Cursos");
            System.out.println("3. Gerenciar Disciplinas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> menuProfessores();
                case 2 -> menuCursos();
                case 3 -> menuDisciplinas();
                case 0 -> {
                    System.out.println("Saindo...");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuProfessores() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Professores ---");
            System.out.println("1. Novo Professor");
            System.out.println("2. Listar Professores");
            System.out.println("3. Atualizar Professor");
            System.out.println("4. Remover Professor");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> {
                    try {
                        System.out.print("Código Funcional: ");
                        int codigo = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Data de Nascimento (dd/MM/yyyy): ");
                        LocalDate dataNascimento = LocalDate.parse(
                            scanner.nextLine(),
                            formatter
                        );
                        Professor professor = new Professor(
                            codigo,
                            nome,
                            dataNascimento
                        );
                        professorService.cadastrarProfessor(professor);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case 2 -> {
                    List<Professor> lista =
                        repositorio.listarTodosProfessores();
                    System.out.println("\n--- Lista de Professores ---");
                    for (Professor p : lista) {
                        System.out.printf(
                            "Cód: %d | Nome: %s | Nasc: %s\n",
                            p.getCodigoFuncional(),
                            p.getNome(),
                            p.getDataNascimento().format(formatter)
                        );
                    }
                }
                case 3 -> {
                    try {
                        System.out.print(
                            "Código Funcional do Professor a atualizar: "
                        );
                        int codigo = Integer.parseInt(scanner.nextLine());
                        Optional<Professor> pOpt =
                            repositorio.buscarPorCodigoProfessor(codigo);
                        if (pOpt.isEmpty()) {
                            System.out.println("Professor não encontrado.");
                            break;
                        }
                        System.out.print("Novo Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print(
                            "Nova Data de Nascimento (dd/MM/yyyy): "
                        );
                        LocalDate dataNascimento = LocalDate.parse(
                            scanner.nextLine(),
                            formatter
                        );
                        Professor p = pOpt.get();
                        p.setNome(nome);
                        p.setDataNascimento(dataNascimento);
                        professorService.atualizarProfessor(p);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.print(
                        "Código Funcional do Professor a remover: "
                    );
                    int codigo = Integer.parseInt(scanner.nextLine());
                    repositorio.deletarProfessor(codigo);
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuCursos() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Cursos ---");
            System.out.println("1. Novo Curso");
            System.out.println("2. Listar Cursos");
            System.out.println("3. Atualizar Curso");
            System.out.println("4. Remover Curso");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> {
                    try {
                        System.out.print("Código: ");
                        int codigo = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Descrição: ");
                        String descricao = scanner.nextLine();
                        Curso curso = new Curso(codigo, nome, descricao);
                        cursoService.cadastrarCurso(curso);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case 2 -> {
                    List<Curso> lista = repositorio.listarTodosCursos();
                    System.out.println("\n--- Lista de Cursos ---");
                    for (Curso c : lista) {
                        System.out.printf(
                            "Cód: %d | Nome: %s | Descrição: %s\n",
                            c.getCodigo(),
                            c.getNome(),
                            c.getDescricao()
                        );
                    }
                }
                case 3 -> {
                    try {
                        System.out.print("Código do Curso a atualizar: ");
                        int codigo = Integer.parseInt(scanner.nextLine());
                        Optional<Curso> cOpt = repositorio.buscarPorCodigoCurso(
                            codigo
                        );
                        if (cOpt.isEmpty()) {
                            System.out.println("Curso não encontrado.");
                            break;
                        }
                        System.out.print("Novo Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Nova Descrição: ");
                        String descricao = scanner.nextLine();
                        Curso c = cOpt.get();
                        c.setNome(nome);
                        c.setDescricao(descricao);
                        cursoService.atualizarCurso(c);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.print("Código do Curso a remover: ");
                    int codigo = Integer.parseInt(scanner.nextLine());
                    repositorio.deletarCurso(codigo);
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void menuDisciplinas() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Disciplinas ---");
            System.out.println("1. Nova Disciplina");
            System.out.println("2. Listar Disciplinas");
            System.out.println("3. Atualizar Disciplina");
            System.out.println("4. Remover Disciplina");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> {
                    try {
                        System.out.print("Número: ");
                        int numero = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Data de Início (dd/MM/yyyy): ");
                        LocalDate dataInicio = LocalDate.parse(
                            scanner.nextLine(),
                            formatter
                        );
                        System.out.print("Data de Encerramento (dd/MM/yyyy): ");
                        LocalDate dataEncerramento = LocalDate.parse(
                            scanner.nextLine(),
                            formatter
                        );

                        System.out.print("Código Funcional do Professor: ");
                        int codigoProfessor = Integer.parseInt(
                            scanner.nextLine()
                        );
                        Professor professor = repositorio
                            .buscarPorCodigoProfessor(codigoProfessor)
                            .orElseThrow(() ->
                                new RuntimeException(
                                    "Professor não encontrado!"
                                )
                            );

                        System.out.print("Código do Curso: ");
                        int codigoCurso = Integer.parseInt(scanner.nextLine());
                        Curso curso = repositorio
                            .buscarPorCodigoCurso(codigoCurso)
                            .orElseThrow(() ->
                                new RuntimeException("Curso não encontrado!")
                            );

                        Disciplina disciplina = new Disciplina(
                            numero,
                            nome,
                            dataInicio,
                            dataEncerramento,
                            professor,
                            curso
                        );
                        disciplinaService.registrarDisciplina(disciplina);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case 2 -> {
                    List<Disciplina> lista =
                        repositorio.listarTodasDisciplinas();
                    System.out.println("\n--- Lista de Disciplinas ---");
                    for (Disciplina d : lista) {
                        System.out.printf(
                            "Num: %d | Nome: %s | Início: %s | Enc: %s | Prof Cód: %d | Curso Cód: %d\n",
                            d.getNumero(),
                            d.getNome(),
                            d.getDataInicio().format(formatter),
                            d.getDataEncerramento().format(formatter),
                            d.getProfessor().getCodigoFuncional(),
                            d.getCurso().getCodigo()
                        );
                    }
                }
                case 3 -> {
                    try {
                        System.out.print("Número da Disciplina a atualizar: ");
                        int numero = Integer.parseInt(scanner.nextLine());
                        Optional<Disciplina> dOpt =
                            repositorio.buscarPorNumeroDisciplina(numero);
                        if (dOpt.isEmpty()) {
                            System.out.println("Disciplina não encontrada.");
                            break;
                        }
                        System.out.print("Novo Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Nova Data de Início (dd/MM/yyyy): ");
                        LocalDate dataInicio = LocalDate.parse(
                            scanner.nextLine(),
                            formatter
                        );
                        System.out.print(
                            "Nova Data de Encerramento (dd/MM/yyyy): "
                        );
                        LocalDate dataEncerramento = LocalDate.parse(
                            scanner.nextLine(),
                            formatter
                        );

                        System.out.print(
                            "Novo Código Funcional do Professor: "
                        );
                        int codigoProfessor = Integer.parseInt(
                            scanner.nextLine()
                        );
                        Professor professor = repositorio
                            .buscarPorCodigoProfessor(codigoProfessor)
                            .orElseThrow(() ->
                                new RuntimeException(
                                    "Professor não encontrado!"
                                )
                            );

                        System.out.print("Novo Código do Curso: ");
                        int codigoCurso = Integer.parseInt(scanner.nextLine());
                        Curso curso = repositorio
                            .buscarPorCodigoCurso(codigoCurso)
                            .orElseThrow(() ->
                                new RuntimeException("Curso não encontrado!")
                            );

                        Disciplina d = dOpt.get();
                        d.setNome(nome);
                        d.setDataInicio(dataInicio);
                        d.setDataEncerramento(dataEncerramento);
                        d.setProfessor(professor);
                        d.setCurso(curso);
                        disciplinaService.atualizarDisciplina(d);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.print("Número da Disciplina a remover: ");
                    int numero = Integer.parseInt(scanner.nextLine());
                    repositorio.deletarDisciplina(numero);
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
