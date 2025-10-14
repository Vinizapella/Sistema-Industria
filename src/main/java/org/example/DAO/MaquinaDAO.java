package org.example.DAO;

import org.example.Conexao;
import org.example.Model.Maquina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaquinaDAO {

    public boolean existeMaquina(String nome, String setor) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Maquina WHERE nome = ? AND setor = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, setor);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void inserir(Maquina maquina) throws SQLException {
        String sql = "INSERT INTO Maquina (nome, setor, status) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maquina.getNome());
            pstmt.setString(2, maquina.getSetor());
            pstmt.setString(3, maquina.getStatus());
            pstmt.executeUpdate();
        }
    }
}
