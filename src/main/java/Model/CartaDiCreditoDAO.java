package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartaDiCreditoDAO {

    //salva una carta di credito nel DB dato un utente
    public void doSave(Utente utente, CartaDiCredito cartaDiCredito){

        try (Connection con = ConnectionDB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO CartaCredito (numeroCarta, via, cap, citta, emailUtente, circuito, dataScadenza) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, cartaDiCredito.getNumeroCarta());
            ps.setString(2, cartaDiCredito.getIndirizzo());
            ps.setInt(3, cartaDiCredito.getCap());
            ps.setString(4, cartaDiCredito.getCitta());
            ps.setString(5, utente.getEmail());
            ps.setString(6, cartaDiCredito.getCircuito());
            ps.setString(7, cartaDiCredito.getDataScadenza());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //elimina una carta di credito dal DB dato un utente
    public void doDelete(Utente utente, CartaDiCredito cartaDiCredito){

        try (Connection con = ConnectionDB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM CartaCredito WHERE emailUtente=? AND numeroCarta=?");
            ps.setString(1, utente.getEmail());
            ps.setString(2, cartaDiCredito.getNumeroCarta());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //recupera una lista di carte di credito dell'utente dal DB data l'email dell'utente
    public List<CartaDiCredito> doRetrieveCartaDiCreditoByEmailUtente (String emailUtente) {

        List<CartaDiCredito> listaCarteDiCredito = new ArrayList<>();

        try (Connection connection = ConnectionDB.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CartaCredito WHERE emailUtente='" + emailUtente + "'");

            while (resultSet.next()) {
                CartaDiCredito cartaDiCredito= new CartaDiCredito();
                cartaDiCredito.setNumeroCarta(resultSet.getString("numeroCarta"));
                cartaDiCredito.setIndirizzo(resultSet.getString("via"));
                cartaDiCredito.setCap(resultSet.getInt("cap"));
                cartaDiCredito.setCitta(resultSet.getString("citta"));
                cartaDiCredito.setCircuito(resultSet.getString("circuito"));
                cartaDiCredito.setDataScadenza(resultSet.getString("dataScadenza"));
                listaCarteDiCredito.add(cartaDiCredito);
            }

        } catch (SQLException e)  {
            throw new RuntimeException();
        }

        return listaCarteDiCredito;
    }
}
