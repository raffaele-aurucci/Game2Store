package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.CartaDiCredito;
import Model.CartaDiCreditoDAO;
import Model.Utente;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// servlet che si occupa di recuperare i dati della carta di credito dalla request e di salvare quest'ultma all'interno
// del DB per l'utente in sessione

@WebServlet("/CartaDiCreditoServlet")

public class CartaDiCreditoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //recupero dati carta di credito dalla request
        String numeroCartaDiCredito = request.getParameter("numeroCarta");
        String indirizzo = request.getParameter("indirizzo");
        Integer cap;

        try{
            cap = Integer.parseInt(request.getParameter("cap"));
        }
        catch (NumberFormatException numberFormatException){
            cap = null;
        }

        String citta = request.getParameter("citta");
        String circuito = request.getParameter("circuito");
        String dataScadenza = request.getParameter("dataScadenza");

        //recupero utente dalla session
        Utente utente = (Utente) request.getSession().getAttribute("utente");

        //attributo che permette all'admin di accedere all'area profilo
        if(utente.getAdmin()) request.setAttribute("profilo", "si");

        //controllo campi vuoti
        if(numeroCartaDiCredito == null || indirizzo == null || cap == null || citta == null || circuito == null || dataScadenza == null ||
           numeroCartaDiCredito.equals("") || indirizzo.equals("") || citta.equals("") || circuito.equals("") || dataScadenza.equals("")){
            request.setAttribute("ErroreCarta", "ErroreCampiCarta");
            RequestDispatcher dispatcher = request.getRequestDispatcher("LoadPageUserServlet");
            dispatcher.forward(request, response);
            return;
        }

        //controllo data scadenza valida
        String meseScadenza = "", annoScadenza = "";

        int i;
        for(i=0; i<dataScadenza.length(); i++){
            if(dataScadenza.charAt(i)!='-')
                annoScadenza+=dataScadenza.charAt(i);
            else break;
        }
        for(int j=i+1; j<dataScadenza.length(); j++){
            meseScadenza+=dataScadenza.charAt(j);
        }

        GregorianCalendar gregorianCalendar = new GregorianCalendar(Integer.parseInt(annoScadenza), (Integer.parseInt(meseScadenza))-1, 1);

        //controllo correttezza campi
        Boolean numeroCartaBoolean = controlloNumeroCarta(numeroCartaDiCredito);
        Boolean indirizzoBoolean = controlloIndirizzo(indirizzo);
        Boolean capBoolean = controlloCap(cap);
        Boolean cittaBoolean = controlloCitta(citta);
        Boolean dataScadenzaBoolean = gregorianCalendar.after(new GregorianCalendar());

        if(!(numeroCartaBoolean && indirizzoBoolean && capBoolean && cittaBoolean && dataScadenzaBoolean)){
            String stringa = "<b>I seguenti campi non sono validi:<br></b>";
            if(!numeroCartaBoolean) stringa += "<b>•Numero carta: </b> (esattamente 19 caratteri, del tipo 1234-1234-1234-1234)<br>";
            if(!indirizzoBoolean) stringa += "<b>•Indirizzo: </b> (almeno 5 caratteri max 30, può contenere solo caratteri alfanumerici e spazi)<br>";
            if(!capBoolean) stringa += "<b>•Cap: </b> (esattamente 5 caratteri, tutte cifre)<br>";
            if(!cittaBoolean) stringa += "<b>•Città: </b> (almeno 4 caratteri max 20, può contenere solo lettere e spazi)<br>";
            if(!dataScadenzaBoolean) stringa += "<b>•Data scadenza: </b> (carta inserita ha data di scadenza non più valida)<br>";
            request.setAttribute("ErroreCarta", stringa);
            RequestDispatcher dispatcher = request.getRequestDispatcher("LoadPageUserServlet");
            dispatcher.forward(request, response);
            return;
        }


        //verifica esistenza carta di credito già inserita
        CartaDiCreditoDAO cartaDiCreditoDAO = new CartaDiCreditoDAO();
        List<CartaDiCredito> cartaDiCreditoList = cartaDiCreditoDAO.doRetrieveCartaDiCreditoByEmailUtente(utente.getEmail());

        if(cartaDiCreditoList.size() == 3){
            request.setAttribute("ErroreCarta", "NumeroMaxCarte");
            RequestDispatcher dispatcher = request.getRequestDispatcher("LoadPageUserServlet");
            dispatcher.forward(request, response);
            return;
        }

        boolean flag = false;

        for(CartaDiCredito cartaDiCredito: cartaDiCreditoList) {
            if(cartaDiCredito.getNumeroCarta().equals(numeroCartaDiCredito)){
                flag = true;
                break;
            }
        }

        if(!flag){
            CartaDiCredito cartaDiCredito = new CartaDiCredito();
            cartaDiCredito.setCircuito(circuito);
            cartaDiCredito.setNumeroCarta(numeroCartaDiCredito);
            cartaDiCredito.setCap(cap);
            cartaDiCredito.setIndirizzo(indirizzo);
            cartaDiCredito.setCitta(citta);
            cartaDiCredito.setDataScadenza(dataScadenza);
            cartaDiCreditoDAO.doSave(utente, cartaDiCredito);

            RequestDispatcher dispatcher = request.getRequestDispatcher("LoadPageUserServlet");
            dispatcher.forward(request, response);
            return;
        }

        if(flag){
            request.setAttribute("ErroreCarta", "ErroreCartaEsistente");
            RequestDispatcher dispatcher = request.getRequestDispatcher("LoadPageUserServlet");
            dispatcher.forward(request, response);
            return;
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }


    private Boolean controlloNumeroCarta(String numeroCarta) {
        if(numeroCarta.length() != 19)
            return false;

        Pattern pattern = Pattern.compile("^[0-9]{4}[-][0-9]{4}[-][0-9]{4}[-][0-9]{4}$");

        Matcher matcher = pattern.matcher(numeroCarta);

        return matcher.find();
    }

    private Boolean controlloCap(Integer cap) {
        if(cap.toString().length() != 5)
            return false;

        Pattern pattern = Pattern.compile("^[0-9]{5}$");

        Matcher matcher = pattern.matcher(cap.toString());

        return matcher.find();
    }

    private Boolean controlloIndirizzo(String indirizzo) {
        if(indirizzo.length() < 5 || indirizzo.length() > 30)
            return false;

        Pattern pattern = Pattern.compile("^[A-Za-z\\s]+[\\s][0-9]{1,3}$");

        Matcher matcher = pattern.matcher(indirizzo);

        return matcher.find();
    }

    private Boolean controlloCitta(String citta) {
        if(citta.length() < 4 || citta.length() > 20)
            return false;

        Pattern pattern = Pattern.compile("^[A-Za-z\\s]+$");

        Matcher matcher = pattern.matcher(citta);

        return matcher.find();
    }
}
