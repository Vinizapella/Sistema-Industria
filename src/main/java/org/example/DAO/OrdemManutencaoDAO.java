package org.example.DAO;

import org.example.Conexao;
import org.example.Model.OrdemManutencao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemManutencaoDAO {

    public int inserir(OrdemManutencao ordem) throws SQLException {
        String sql = "INSERT INTO OrdemManutencao (idMaquina, idTecnico, dataSolicitacao, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, ordem.getIdMaquina());
            pstmt.setInt(2, ordem.getIdTecnico());
            pstmt.setDate(3, Date.valueOf(ordem.getDataSolicitacao()));
            pstmt.setString(4, ordem.getStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }
        return -1;
    }

    public List<OrdemManutencao> listarPendentes() throws SQLException{
        List<OrdemManutencao> ordens = new ArrayList<>();
        String sql = "SELECT * FROM OrdemManutencao WHERE status = 'PENDENTE'";
        try (Connection conn = Conexao.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()){

            while (rs.next()){
                ordens.add(new OrdemManutencao(
                        rs.getInt("id"),
                        rs.getInt("idMaquina"),
                        rs.getInt("idTecnico"),
                        rs.getDate("dataSolicitacao").toLocalDate(),
                        rs.getString("status")
                ));
            }

        }
        return ordens;
    }

    public void atualizarStatus(int idOrdem, String novoStatus) throws SQLException{
        String sql = "UPDATE OrdemManutecao SET status = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, novoStatus);
            pstmt.setInt(2, idOrdem);
            pstmt.executeUpdate();
        }
    }
}
