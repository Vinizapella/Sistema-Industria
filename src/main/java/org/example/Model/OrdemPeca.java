package org.example.Model;

public class OrdemPeca {
    private int idOrdem;
    private int idPeca;
    private double quantidade;
    private String nomePeca;

    public OrdemPeca(int idOrdem, int idPeca, double quantidade) {
        this.idOrdem = idOrdem;
        this.idPeca = idPeca;
        this.quantidade = quantidade;
    }

    public int getIdOrdem() { return idOrdem; }
    public int getIdPeca() { return idPeca; }
    public double getQuantidade() { return quantidade; }
}