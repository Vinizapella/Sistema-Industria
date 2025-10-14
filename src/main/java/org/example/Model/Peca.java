package org.example.Model;

public class Peca {
    private int id;
    private String nome;
    private double estoque;

    public Peca(String nome, double estoque) {
        this.nome = nome;
        this.estoque = estoque;
    }

    public Peca(int id, String nome, double estoque) {
        this.id = id;
        this.nome = nome;
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getEstoque() {
        return estoque;
    }

    public void setEstoque(double estoque) {
        this.estoque = estoque;
    }

    public int getId() {
        return id;
    }
}
