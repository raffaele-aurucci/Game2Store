package Model;

import java.sql.*;
import java.util.*;

public class CarrelloDAO{

    //inserimento di un nuovo carrello nel DB
    public void doCreateCarrelloUtente (String emailUtente, Carrello carrello){
        try (Connection con = ConnectionDB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Carrello (totaleCarrello, emailUtente) VALUES(?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, carrello.getTotale());
            ps.setString(2, emailUtente);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            carrello.setId(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //recupero di un carrello di un determinato utente dal DB
    public Carrello doRetrieveCarrelloUtente (String emailUtente) {
        Carrello carrello = null;

        try (Connection connection = ConnectionDB.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, totaleCarrello FROM Carrello WHERE emailUtente='" + emailUtente + "'");
            carrello = new Carrello();

            if (resultSet.next()) {
                carrello.setId(resultSet.getInt("id"));
                carrello.setTotale(resultSet.getDouble("totaleCarrello"));
                carrello.setListaTitoliGiochiCarrello(doRetrieveTitoliGiochiByIdCarrello(carrello.getId()));
                return carrello;
            }

        } catch (SQLException e)  {
            throw new RuntimeException();
        }

        return null;
    }


    //recupero della lista dei giochi presenti nel carrello di un determinato utente
    public List<String> doRetrieveTitoliGiochiByIdCarrello(int id){

        List<String> listaTitoliGiochi = new ArrayList<>();

        try (Connection connection = ConnectionDB.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT titoloGioco FROM RegistrareGiocoCarrello  WHERE idCarrello=" + id + "");

            while (resultSet.next())
                listaTitoliGiochi.add(resultSet.getString("titoloGioco"));

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return listaTitoliGiochi;
    }


    //salvataggio di un gioco nel carrello di un determinato utente
    public void doSaveRegistraGiocoCarrello(int id, String titolo){
        try (Connection con = ConnectionDB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO RegistrareGiocoCarrello (idCarrello, titoloGIoco) VALUES(?,?)");
            ps.setInt(1, id);
            ps.setString(2, titolo);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //cancellazione di un gioco dal carrello di un determinato utente
    public void doDeleteRegistrareGiocoCarrello(int id, String titolo){
        try (Connection con = ConnectionDB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM RegistrareGiocoCarrello WHERE idCarrello=? AND titoloGioco=?");
            ps.setInt(1, id);
            ps.setString(2, titolo);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //update del prezzo totale dei prodotti presenti in carrello
    public void doUpdatePrezzoCarrello(Carrello carrello){
        try (Connection connection = ConnectionDB.getConnection()) {

            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Carrello SET totaleCarrello=" + carrello.getTotale() + " WHERE id=" + carrello.getId() + " ");

        } catch (SQLException e) {
           e.printStackTrace();
        }
    }


    //recupero lista di id di carrelli necessari ad aggiornare il totale quando l'admin aggiorna i prezzi dei giochi
    public List<Integer> doRetriveAll(){

        List<Integer> listaId = new ArrayList<>();

        try (Connection connection = ConnectionDB.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM Carrello");

            while (resultSet.next()){
                Integer id = resultSet.getInt("id");
                listaId.add(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaId;
    }


    //svuota il carrello dell'utente in sessione dal DB
    public void doEmptyCarrello(Carrello carrello){
        try (Connection connection = ConnectionDB.getConnection()) {

            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM RegistrareGiocoCarrello WHERE idCarrello=" + carrello.getId() + "");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
