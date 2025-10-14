package org.example.DAO;

import org.example.Conexao;
import org.example.Model.OrdemPeca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdemPecaDAO {

    public void inserir(OrdemPeca ordemPeca) throws SQLException{
        String sql = "INSERT INTO OrdemPeca (idOrdem, idPeca, quantidade) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, ordemPeca.getIdOrdem());
            pstmt.setInt(2, ordemPeca.getIdPeca());
            pstmt.setDouble(3, ordemPeca.getQuantidade());
            pstmt.executeUpdate();
        }
    }

    public List<OrdemPeca> listarPecasPorOrdem(int idOrdem) throws SQLException{
        List<OrdemPeca> pecasDaOrdem = new ArrayList<>();
        String sql = "SELECT * FROM OrdemPeca WHERE idOrdem = ?";
        try (Connection conn= Conexao.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, idOrdem);

            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    pecasDaOrdem.add(new OrdemPeca(
                            rs.getInt("idOrdem"),
                            rs.getInt("idPeca"),
                            rs.getDouble("quantidade")
                    ));
                }

            }
        }
        return pecasDaOrdem;
    }
}
