package Model;

import java.sql.*;
import java.util.*;

public class GiocoDAO {

    //recupero di tutti i giochi dal DB salvandoli in una mappa
    public Map<String, Gioco> doRetrieveAll(){

        Map<String, Gioco> giochi = new Hashtable<>();

        try (Connection conn = ConnectionDB.getConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gioco");

            while(resultSet.next()){
                Gioco gioco = new Gioco();
                gioco.setTitolo(resultSet.getString("titolo"));
                gioco.setGenere(resultSet.getString("genere"));
                gioco.setOfferta(resultSet.getBoolean("offerta"));
                gioco.setPrezzo(resultSet.getDouble("prezzo"));
                gioco.setDataUscita(resultSet.getDate("dataUscita"));
                gioco.setContatoreRilevanze(resultSet.getInt("contatoreRilevanze"));
                gioco.setDescrizione(resultSet.getString("descrizione"));
                giochi.put(gioco.getTitolo(), gioco);
            }

        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return giochi;
    }


    //recupero della lista di path d'immagini di un determinato gioco dal DB
    public List<String> doRetrieveImagesByTitolo(String titolo) {

        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT pathImages FROM ImagesGioco where titoloGioco=?");
            preparedStatement.setString(1, titolo);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                list.add(resultSet.getString("pathImages"));
            }

        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return list;
    }


    //recupero della lista dei titoli dei giochi in offerta dal DB
    public List<String> doRetrieveByOfferta(){

        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT titolo FROM Gioco WHERE offerta=1;");

            while(resultSet.next()){
                list.add(resultSet.getString("titolo"));
            }
        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return list;
    }


    //recupero della lista dei titoli dei giochi di ultima uscita (anno corrente - 1) dal DB
    public List<String> doRetrieveByDataUscitaLastYear(){
        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gioco WHERE YEAR(dataUscita)>=YEAR(CURDATE())-1;");

            while(resultSet.next()){
                list.add(resultSet.getString("titolo"));
            }
        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return list;
    }


    //recupero della lista dei titoli dei giochi più rilevanti (più acquistati) dal DB
    public List<String> doRetrieveByRilevanza(){
        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Gioco WHERE contatoreRilevanze>=5 ORDER BY contatoreRilevanze DESC");

            while(resultSet.next()){
                list.add(resultSet.getString("titolo"));
            }
        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return list;
    }


    //recupero della lista dei titoli dei giochi dal DB che presentano nel titoli i caratteri passati in input
    public List<String> doSearchByCharacter(String name) {
        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT titolo FROM Gioco WHERE titolo LIKE '%" + name + "%' ORDER BY contatoreRilevanze DESC");

            while(resultSet.next()){
                list.add(resultSet.getString("titolo"));
            }
        } catch (SQLException sql) {
            throw new RuntimeException();
        }

        return list;
    }


    //update prezzo e offerta di un gioco presente nel DB
    public void doUpdatePrezzoOfferta (Gioco gioco){

        try (Connection connection = ConnectionDB.getConnection()) {

            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Gioco SET prezzo=" + gioco.getPrezzo() + " ,offerta=" + gioco.isOfferta() + " WHERE titolo='" + gioco.getTitolo() + "'");


        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }


    //restituisce una lista di titoli di giochi con genere dato in input
    public List<String> doRetrieveByGenere (String genere){

        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT titolo FROM Gioco WHERE genere='" + genere + "'" + "ORDER BY titolo");
            while(resultSet.next()){
                String titolo = resultSet.getString("titolo");
                list.add(titolo);
            }
        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return list;
    }


    //recupero lista di titoli dei giochi con prezzo minore di quello in input
    public List<String> doRetrieveByPrezzoMinore (double prezzo){

        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT titolo, prezzo FROM Gioco WHERE prezzo<" + prezzo + " ORDER BY prezzo");
            while(resultSet.next()){
                String titolo = resultSet.getString("titolo");
                list.add(titolo);
            }
        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return list;
    }


    // recupero lista di titoli dei giochi con prezzo maggiore di quello in input
    public List<String> doRetrieveByPrezzoMaggiore (double prezzo){

        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT titolo, prezzo FROM Gioco WHERE prezzo>" + prezzo + " ORDER BY prezzo");
            while(resultSet.next()){
                String titolo = resultSet.getString("titolo");
                list.add(titolo);
            }
        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return list;
    }


    // recupero lista di titoli dei giochi con prezzo compreso tra prezzo1 e prezzo2 presi in input
    public List<String> doRetrieveByPrezzoCompreso (double prezzo1, double prezzo2){

        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT titolo, prezzo FROM Gioco WHERE prezzo>=" + prezzo1 + " AND prezzo<=" + prezzo2 + " ORDER BY prezzo");
            while(resultSet.next()){
                String titolo = resultSet.getString("titolo");
                list.add(titolo);
            }
        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return list;
    }

    public void doIncrementRilevanza (Gioco gioco)
    {
        try (Connection connection = ConnectionDB.getConnection()) {
            int count = gioco.getContatoreRilevanze();
            count++;
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Gioco SET contatoreRilevanze=" + count + " WHERE titolo='"+ gioco.getTitolo() + "'");


        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
