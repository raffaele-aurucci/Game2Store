package Controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Model.Carrello;
import Model.CarrelloDAO;
import Model.Utente;

import java.io.IOException;
import java.util.List;

// servlet che si occupa di effettuare il logout dell'utente, ossia di rimuovere l'utente dalla sessione in corso, prima
// di rimuoverlo si occupa di salvare il carrello dell'utente nel DB in modo tale che al prossimo accesso venga ripreso
// il carrello precedentemente salvato.

@WebServlet("/logout-servlet")

public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //recupero sessione dalla richiesta corrente, l'attributo false indica che se non esiste non la crea e restituisce false
        HttpSession session = request.getSession(false);

        //salvataggio carrello e lista titoli giochi nel DB
        Utente utente = (Utente) session.getAttribute("utente");
        CarrelloDAO carrelloDAO = new CarrelloDAO();
        Carrello carrello = utente.getCarrello();

        //update totale carrello nel DB
        carrelloDAO.doUpdatePrezzoCarrello(carrello);

        //recupero lista titoli di giochi dal carrello attualmente salvato nel DB e dal carrello attualmente presente in sessione
        List<String> listaTitoliDB = carrelloDAO.doRetrieveTitoliGiochiByIdCarrello(carrello.getId());
        List<String> listaTitoliCarrelloSession = carrello.getListaTitoliGiochiCarrello();

        //se un titolo è nel DB ma non è più in sessione perché eliminato dall'utente allora lo elimino dal DB
        for(String titolo: listaTitoliDB){
            if(listaTitoliCarrelloSession.contains(titolo) == false)
                carrelloDAO.doDeleteRegistrareGiocoCarrello(carrello.getId(), titolo);
        }

        //se un titolo è nella sessione ma non nel DB allora deve essere salvato nel carrello del DB
        for(String titolo: listaTitoliCarrelloSession){
            if(listaTitoliDB.contains(titolo) == false)
                carrelloDAO.doSaveRegistraGiocoCarrello(carrello.getId(), titolo);
        }

        session.removeAttribute("utente");
        response.sendRedirect("index.html");
    }
}
