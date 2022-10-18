package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// servlet che si occupa di aggiornare prezzi e offerte dei giochi, ruolo affidato a un amministratore registrato nel DB,
// vengono prelevati tutti i campi dalla request attraverso l'apposito name, aggiorna i dati sia in application in tempo
// O(n), O(1) per ogni elemento salvato nella mappa, oltre che nel DB.
// Dopodichè aggiorna il totale carrello per ognuno dei carrelli salvati nel DB oltre che aggiornare il totale del carrel
// admin attualmente in sessione.

@WebServlet("/UpdateGiochiServlet")

public class UpdateGiochiServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //recupero parametri dalla request
        String[] titoli = request.getParameterValues("titolo");
        String[] prezziString = request.getParameterValues("prezzo");
        String[] offerte = request.getParameterValues("offerta");

        //controllo errori
        boolean flag = false;

        //verifica per ogni prezzo prelevato dalla request se è stato fatto un errore d'inserimento
        for(int i = 0; i < prezziString.length; i++){
            if(!controllaPrezzo(prezziString[i]))
                flag=true;
        }

        //restituzione messaggio d'errore
        if(flag || !controllaOfferte(offerte)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/result/admin.jsp");
            request.setAttribute("errore", "errore");
            dispatcher.forward(request, response);
            return;
        }

        //costruzione array di Double per ogni prezzo prelevato dalla request
        Double[] prezzi = new Double[prezziString.length];

        for (int i = 0; i<prezzi.length; i++)
            prezzi[i]= Double.parseDouble(prezziString[i]);

        //recupero mappa dei giochi dal contesto dell'applicazione
        Map<String, Gioco> listaGiochiApplication = (Map<String, Gioco>) getServletContext().getAttribute("listaGiochi");

        //aggiornamento prezzi e offerte, si sfrutta l'ordine di arrivo dei dati dalla request, ogni parametro i-esimo,
        //corrisponde al gioco i-esimo
        for (int i = 0; i < titoli.length; i++){
            Gioco gioco = new Gioco();
            gioco.setTitolo(titoli[i]);
            gioco.setPrezzo(prezzi[i]);

            if(offerte[i].equals("Si"))
                gioco.setOfferta(true);
            else
                gioco.setOfferta(false);

            Gioco giocoApplication = listaGiochiApplication.get(gioco.getTitolo());
            giocoApplication.setOfferta(gioco.isOfferta());
            giocoApplication.setPrezzo(gioco.getPrezzo());
        }

        //aggiornamento dei giochi nel DB iterando sulla mappa dei giochi
        for(Gioco gioco: listaGiochiApplication.values()){
            GiocoDAO giocoDAO = new GiocoDAO();
            giocoDAO.doUpdatePrezzoOfferta(gioco);
        }



        //recupero carrello admin in sessione
        Utente utenteAdmin = (Utente) request.getSession(false).getAttribute("utente");
        Carrello carrelloAdmin = utenteAdmin.getCarrello();

        //aggiornamento totale carrelli in DB
        CarrelloDAO carrelloDAO = new CarrelloDAO();
        List<Integer> listaIdCarrelli = carrelloDAO.doRetriveAll();

        for(Integer id: listaIdCarrelli){

            //recupero lista giochi carrello dato id
            List<String> listaGiochiCarrello = carrelloDAO.doRetrieveTitoliGiochiByIdCarrello(id);

            //ricalcolo totale
            double tot = 0;
            for (Gioco gioco : listaGiochiApplication.values()) {
                if (listaGiochiCarrello.contains(gioco.getTitolo()))
                    tot += gioco.getPrezzo();
            }
            tot = Math.round(tot * 100.0) / 100.0;

            //aggiornamento totale carrelli in DB
            Carrello carrello = new Carrello();
            carrello.setId(id);
            carrello.setTotale(tot);
            carrelloDAO.doUpdatePrezzoCarrello(carrello);

        }


        //aggiornamento carrello admin in sessione
        double totAdmin = 0;

        for(String titolo: carrelloAdmin.getListaTitoliGiochiCarrello()){
            Gioco gioco = listaGiochiApplication.get(titolo);
            totAdmin += gioco.getPrezzo();
        }
        totAdmin = Math.round(totAdmin * 100.0) / 100.0;
        carrelloAdmin.setTotale(totAdmin);

        response.sendRedirect("index.html");

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    public static boolean controllaPrezzo(String prezzo){

        Pattern pattern = Pattern.compile("^[0-9]+[.][0-9]{2}$");
        Matcher matcher = pattern.matcher(prezzo);

        return matcher.find();
    }

    public static boolean controllaOfferte(String[] strings){

        for(int i = 0; i < strings.length; i++){
            if(!(strings[i].equals("Si")) && !(strings[i].equals("No")))
                return false;
        }
        return true;
    }

}
