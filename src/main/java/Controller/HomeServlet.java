package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Model.*;

import java.io.IOException;
import java.util.List;

// servlet chiamata ogni volta che viene avviata l'applicazione, successiva all'istanziazione della InitServlet,
// si occupa di recuperare dal DB i titoli dei giochi in offerta, titoli di ultima uscita e titoli rilevanti.
// Dopodiché si occupa di controllare se vi è una sessione attiva, dal momento che l'utente registrato/loggato
// sarà salvato in sessione e reindirizzato alla home dopo aver fatto l'accesso, di conseguenza va a caricare
// nella stessa sessione il rispettivo carrello.

@WebServlet("/index.html")

public class HomeServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        GiocoDAO giocoDAO = new GiocoDAO();
        List<String> listaGiochiOfferta = giocoDAO.doRetrieveByOfferta();
        List<String> listaGiochiDataUscita = giocoDAO.doRetrieveByDataUscitaLastYear();
        List<String> listaGiochiRilevanti = giocoDAO.doRetrieveByRilevanza();

        request.setAttribute("listaGiochiOfferta", listaGiochiOfferta);
        request.setAttribute("listaGiochiDataUscita", listaGiochiDataUscita);
        request.setAttribute("listaGiochiRilevanti", listaGiochiRilevanti);


        //recupero di una sessione legata alla richiesta corrente
        HttpSession session = request.getSession(false);

        Utente utente = null;

        //se è presente una sessione legata alla richiesta corrente recupera l'utente
        if(session!=null) utente = (Utente) session.getAttribute("utente");

        //se vi è l'utente precedentemente salvato in sessione e non ha un carrello
        if(utente!=null && utente.getCarrello()==null){

            //carica il carrello dell'utente che ha richiesto login/registrazione in sessione
            //come suo attributo diretto, se non ha un carrello lo crea.
            CarrelloDAO carrelloDAO = new CarrelloDAO();
            Carrello carrello = carrelloDAO.doRetrieveCarrelloUtente(utente.getEmail());

            if(carrello == null){
                carrello = new Carrello();
                carrello.setTotale(0);
                carrelloDAO.doCreateCarrelloUtente(utente.getEmail(), carrello);
                carrello.setListaTitoliGiochiCarrello(carrelloDAO.doRetrieveTitoliGiochiByIdCarrello(carrello.getId()));
            }

            //salva il carrello dell'utente in sessione
            utente.setCarrello(carrello);

            //carica la libreria dell'utente dal DB
            OrdineDAO ordineDAO = new OrdineDAO();
            List<String> libreria = ordineDAO.doRetrieveLibreriaUtenteByEmail(utente.getEmail());

            //salvo la libreria dell'utente in sessione
            utente.setLibreria(libreria);
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/result/home.jsp");
        dispatcher.forward(request, response);
    }
}
