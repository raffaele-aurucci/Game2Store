package Model;

import java.sql.*;

public class UtenteDAO {

    //salvataggio di un utente nel DB
    public void doSaveUtente(Utente utente) {

        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Utente (email, password, username, nome, cognome, ddn, nazione, ammin) VALUES (?,?,?,?,?,?,?,?);");
            preparedStatement.setString(1, utente.getEmail());
            preparedStatement.setString(2, utente.getPassword());
            preparedStatement.setString(3, utente.getUsername());
            preparedStatement.setString(4, utente.getNome());
            preparedStatement.setString(5, utente.getCognome());
            preparedStatement.setDate(6, utente.getDataDiNascita());
            preparedStatement.setString(7, utente.getNazione());
            preparedStatement.setBoolean(8, utente.getAdmin());

            if (preparedStatement.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //recupero di un utente dal DB che ha email e password che coincidono a quelle passate in input
    public Utente doRetrieveByEmailPassword(String email, String password) {

        Utente utente = null;

        try (Connection connection = ConnectionDB.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Utente WHERE email='" + email + "' AND password='" + password + "'");

            while (resultSet.next()) {
                utente = new Utente();
                utente.setEmail(resultSet.getString("email"));
                utente.setPassword(resultSet.getString("password"));
                utente.setUsername(resultSet.getString("username"));
                utente.setNome(resultSet.getString("nome"));
                utente.setCognome(resultSet.getString("cognome"));
                utente.setAdmin(resultSet.getBoolean("ammin"));
                utente.setDataDiNascita(resultSet.getDate("ddn"));
                utente.setNazione(resultSet.getString("nazione"));
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return utente;
    }


    //recupero di un utente dal DB che ha email o username uguali a quelle passate in input
    public Utente doRetrieveByEmailOrUsername(String email, String username) {

        Utente utente = null;

        try (Connection connection = ConnectionDB.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Utente WHERE email='" + email + "' OR username='" + username + "'");

            while (resultSet.next()) {
                utente = new Utente();
                utente.setEmail(resultSet.getString("email"));
                utente.setPassword(resultSet.getString("password"));
                utente.setUsername(resultSet.getString("username"));
                utente.setNome(resultSet.getString("nome"));
                utente.setCognome(resultSet.getString("cognome"));
                utente.setAdmin(resultSet.getBoolean("ammin"));
                utente.setDataDiNascita(resultSet.getDate("ddn"));
                utente.setNazione(resultSet.getString("nazione"));
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return utente;
    }


    //update dei campi di un utente nel DB che ha email e username uguali a quelle passate in input
    public void doUpdateByEmailUsername (String emailAttuale, String usernameAttuale, Utente utente){

        try (Connection connection = ConnectionDB.getConnection()) {

            Statement statement = connection.createStatement();
            statement.executeUpdate(("UPDATE Utente SET email='" + utente.getEmail() + "' ,username='" + utente.getUsername() + "' ,cognome='" + utente.getCognome() + "' ,nome='" + utente.getNome() + "' ,password='" + utente.getPassword() + "' ,nazione='" + utente.getNazione() + "' WHERE email='" + emailAttuale + "' AND username='" + usernameAttuale + "';"));

        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }


}
