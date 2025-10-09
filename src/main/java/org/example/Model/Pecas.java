package org.example.Model;

public class Pecas {
    private String nome;
    private double estoque;

    public Pecas(String nome, double estoque) {
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
}
