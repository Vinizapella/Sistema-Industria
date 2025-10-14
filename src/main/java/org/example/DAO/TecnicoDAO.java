package org.example.DAO;

import org.example.Conexao;
import org.example.Model.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO {

    public boolean existeTecnico(String nome, String especialidade) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Tecnico WHERE nome = ? AND especialidade = ?";
        try(Connection conn = Conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, nome);
            pstmt.setString(2, especialidade);

            try(ResultSet rs = pstmt.executeQuery()){
                if (rs.next()){
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void inserir(Tecnico tecnico) throws SQLException{
        String sql = "INSERT INTO Tecnico (nome, especialidade) VALUES(?,?)";
        try(Connection conn = Conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tecnico.getNome());
            pstmt.setString(2, tecnico.getEspecialidade());
            pstmt.executeUpdate();
        }
    }

    public List<Tecnico> listarTodos() throws SQLException {
        List<Tecnico> tecnicos = new ArrayList<>();
        String sql = "SELECT * FROM Tecnico";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String especialidade = rs.getString("especialidade");
                tecnicos.add(new Tecnico(id, nome, especialidade));
            }
        }
        return tecnicos;
    }
}