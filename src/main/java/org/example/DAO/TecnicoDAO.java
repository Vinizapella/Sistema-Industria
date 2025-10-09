package org.example.DAO;

import org.example.Conexao;
import org.example.Model.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TecnicoDAO {

    public static boolean existeTecnico(String nome, String especialidade) throws SQLException{
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

    public static void inserir(Tecnico tecnico) throws SQLException{
        String sql = "INSERT INTO Tecnico (nome, especialidade) VALUES(?,?)";
        try(Connection conn = Conexao.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tecnico.getNome());
            pstmt.setString(2, tecnico.getEspecialidade());
            pstmt.executeUpdate();
        }
    }
}
