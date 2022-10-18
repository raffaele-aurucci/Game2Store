package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

    //salvataggio dell'ordine dell'utente nel DB
    public void doSave (Ordine ordine, String emailUtente, String numeroCarta){

        try (Connection con = ConnectionDB.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Ordine (totaleOrdine, emailUtente, numeroCarta, dataAcquisto) VALUES(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setDouble(1, ordine.getTotaleOrdine());
            ps.setString(2, emailUtente);
            ps.setString(3, numeroCarta);
            ps.setDate(4, ordine.getDataAcquisto());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            ordine.setId(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //salvataggio giochi acquistati relativi a un ordine effettuato dall'utente
    public void doSaveAcquistoGiocoOrdine (Ordine ordine, Gioco gioco){

        try (Connection con = ConnectionDB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO AcquistoGiocoOrdine (idOrdine, titoloGioco, prezzoAcquisto) VALUES(?,?,?)");
            ps.setInt(1, ordine.getId());
            ps.setString(2, gioco.getTitolo());
            ps.setDouble(3, gioco.getPrezzo());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //recupero la libreria dei giochi acquistati dall'utente
    public List<String> doRetrieveLibreriaUtenteByEmail(String emailUtente){

        List<String> libreria = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT titoloGioco FROM Ordine INNER JOIN AcquistoGiocoOrdine ON Ordine.id=AcquistoGiocoOrdine.idOrdine WHERE emailUtente=?");
            preparedStatement.setString(1, emailUtente);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String titoloGioco = resultSet.getString("titoloGioco");
                libreria.add(titoloGioco);
            }

        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return libreria;
    }


    //recupera i titoli dei giochi acquistati in un determinato ordine dato il suo id
    public List<Gioco> doRetrieveTitoliGiochiByIdOrdine(Integer idOrdine){

        List<Gioco> list = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT titoloGioco, prezzoAcquisto FROM AcquistoGiocoOrdine WHERE idOrdine=?");
            preparedStatement.setInt(1, idOrdine);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Gioco gioco = new Gioco();
                String titoloGioco = resultSet.getString("titoloGioco");
                gioco.setTitolo(titoloGioco);
                Double prezzo = resultSet.getDouble("prezzoAcquisto");
                gioco.setPrezzo(prezzo);
                list.add(gioco);
            }

        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return list;
    }


    //recupera una lista di ordini effettuati dall'utente
    public List<Ordine> doRetrieveOrdineByEmail(String emailUtente){

        List<Ordine> listaOrdini = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Ordine WHERE emailUtente=?");
            preparedStatement.setString(1, emailUtente);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Ordine ordine = new Ordine();
                ordine.setId(resultSet.getInt("id"));
                ordine.setTotaleOrdine(resultSet.getDouble("totaleOrdine"));
                ordine.setDataAcquisto(resultSet.getDate("dataAcquisto"));
                listaOrdini.add(ordine);
            }

        } catch (SQLException sql){
            throw new RuntimeException();
        }

        return listaOrdini;
    }
}
