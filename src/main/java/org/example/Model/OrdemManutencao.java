package org.example.Model;

import java.time.LocalDate;

public class OrdemManutencao {
    private int id;
    private int idMaquina;
    private int idTecnico;
    private LocalDate dataSolicitacao;
    private String status;

    public OrdemManutencao(int idMaquina, int idTecnico, LocalDate dataSolicitacao, String status) {
        this.idMaquina = idMaquina;
        this.idTecnico = idTecnico;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    public OrdemManutencao(int id, int idMaquina, int idTecnico, LocalDate dataSolicitacao, String status) {
        this.id = id;
        this.idMaquina = idMaquina;
        this.idTecnico = idTecnico;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    public int getId() { return id; }
    public int getIdMaquina() { return idMaquina; }
    public int getIdTecnico() { return idTecnico; }
    public LocalDate getDataSolicitacao() { return dataSolicitacao; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Ordem [ID=" + id + ", ID Maquina=" + idMaquina + ", Status=" + status + ", Data=" + dataSolicitacao + "]";
    }
}