package org.example.Model;

public class Tecnico {
    private int id;
    private String nome;
    private String especialidade;

    // Construtor para inserir (sem id)
    public Tecnico(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public Tecnico(int id, String nome, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return "Tecnico [ID=" + id + ", Nome=" + nome + ", Especialidade=" + especialidade + "]";
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}