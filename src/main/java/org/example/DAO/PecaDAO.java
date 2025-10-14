package org.example.DAO;

import org.example.Conexao;
import org.example.Model.Peca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {

    public boolean existePeca(String nome) throws SQLException{
        String sql = "SELECT COUNT(*) FROM Peca WHERE nome = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()){
                    return rs.getInt(1)>0;
                }
            }
        }
        return false;
    }

    public void inserir(Peca peca) throws SQLException{
        String sql = "INSERT INTO Peca (nome, estoque) VALUES (?, ?)";
        try (Connection conn = Conexao.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, peca.getNome());
            pstmt.setDouble(2, peca.getEstoque());
            pstmt.executeUpdate();
        }
    }

    public List<Peca> listarTodas() throws SQLException {
        List<Peca> pecas = new ArrayList<>();
        String sql = "SELECT * FROM Peca";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double estoque = rs.getDouble("estoque");

                pecas.add(new Peca(id, nome, estoque));
            }
        }
        return pecas;
    }

    public void atualizarEstoque(int idPeca, double novoEstoque) throws SQLException{
        String sql = "UPDATE Peca SET estoque = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setDouble(1, novoEstoque);
            pstmt.setInt(2, idPeca);
            pstmt.executeUpdate();
        }
    }

}
