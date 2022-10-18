package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;


// servlet che si occupa di effettuare l'ordine per l'utente in sessione, recupera tutti gli articoli dal carrello, dopo
// diché salva l'ordine nel DB, aggiunge i giochi acquistati nella tabella (AcquistoGiocoOrdine), oltre che ad aggiornare
// la libreria dell'utente in sessione, svuota il carrello

@WebServlet("/EffettuaOrdineServlet")

public class EffettuaOrdineServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //recupero numeroCarta dalla request
        String numeroCarta = request.getParameter("numeroCarta");

        //recupero carrello e utente dalla sessione
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        Carrello carrello = utente.getCarrello();

        //istanzia oggetto ordine
        Ordine ordine = new Ordine();

        ordine.setTotaleOrdine(carrello.getTotale());
        GregorianCalendar calendar = new GregorianCalendar();
        ordine.setDataAcquisto(new Date(calendar.getTimeInMillis()));


        //salvataggio ordine dell'utente in sessione nel DB
        OrdineDAO ordineDAO = new OrdineDAO();
        ordineDAO.doSave(ordine, utente.getEmail(), numeroCarta);


        //recupero lista giochi dal carrello e giochi dall'application
        List<String> listaTitoliCarrello = carrello.getListaTitoliGiochiCarrello();
        Map<String, Gioco> listaGiochiApplication = (Map<String, Gioco>) getServletContext().getAttribute("listaGiochi");

        //incremento contatoreRilevanze
        GiocoDAO giocoDAO = new GiocoDAO();

        for(String titolo: listaTitoliCarrello){
            Gioco gioco = listaGiochiApplication.get(titolo);
            ordineDAO.doSaveAcquistoGiocoOrdine(ordine, gioco);
            giocoDAO.doIncrementRilevanza(gioco);
        }

        //svuoto il carrello dal DB (tabella RegistrareGiocoCarrello) e aggiorna totale a 0
        CarrelloDAO carrelloDAO = new CarrelloDAO();
        carrelloDAO.doEmptyCarrello(carrello);
        carrello.setTotale(0.0);
        carrelloDAO.doUpdatePrezzoCarrello(carrello);

        //svuoto carrello in sessione
        carrello.setListaTitoliGiochiCarrello(new ArrayList<>());

        //salvataggio libreria utente in sessione
        utente.setLibreria(ordineDAO.doRetrieveLibreriaUtenteByEmail(utente.getEmail()));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/result/libreria.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
