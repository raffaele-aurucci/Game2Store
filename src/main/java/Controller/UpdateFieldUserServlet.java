package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.Utente;
import Model.UtenteDAO;

import java.io.IOException;

// servlet che si occupa di aggiornare i campi che caratterizzano l'utente, viene data la possibilità di modificare ogni
// genere di campo, compresa email, username e password (a patto che email e/o username non siano già salvati nel DB come
// campi appartenenti a un altro utente). Vengono effettuati controlli sulla correttezza dei campi inseriti dall'utente.


@WebServlet("/UpdateFieldUserServlet")

public class UpdateFieldUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String passwordAttuale = request.getParameter("passwordVecchia");
        String passwordNuova = request.getParameter("password");
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String nazione = request.getParameter("nazione");

        Utente utenteSession =  (Utente) request.getSession(false).getAttribute("utente");
        String emailUtenteSession = utenteSession.getEmail();
        String usernameUtenteSession = utenteSession.getUsername();
        String passwordUtenteSession = utenteSession.getPassword();

        //attributo che permette all'admin di accedere all'area profilo
        if(utenteSession.getAdmin()) request.setAttribute("profilo", "si");

        //verifica campi vuoti
        if (username.equals("") || passwordAttuale.equals("") || email.equals("") || nome.equals("") || cognome.equals("") || nazione.equals("")
                || username == null || passwordAttuale == null || email == null || nome == null || cognome == null || nazione == null) {
            request.setAttribute("Errore", "ErroreCampi");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/result/user.jsp");
            dispatcher.forward(request, response);
            return;
        }

        //controllo correttezza campi inseriti
        Boolean nomeBoolean = RegisterServlet.controlloNome(nome);
        Boolean cognomeBoolean = RegisterServlet.controlloCognome(cognome);
        Boolean usernameBoolean = RegisterServlet.controlloUsername(username);
        Boolean passwordNuovaBoolean = RegisterServlet.controlloPassword(passwordNuova);
        Boolean emailBoolean = RegisterServlet.controlloEmail(email);
        Boolean nazioneBoolean = RegisterServlet.controlloNazione(nazione);

        //verifica se passwordVecchia è uguale alla password attualmente salvata nella sessione
        Boolean coincidePwd = passwordAttuale.equals(passwordUtenteSession);


        //verifica errori
        Boolean flag = false;

        //stringa errore
        String stringa = "<b>I seguenti campi non sono validi:<br></b>";

        if (!(nomeBoolean && cognomeBoolean && usernameBoolean && emailBoolean && nazioneBoolean)){
            if (!nomeBoolean) stringa += "<b>•nome</b> (almeno 3 caratteri, solo lettere ammesse)<br>";
            if (!cognomeBoolean) stringa += "<b>•cognome</b> (almeno 3 caratteri, solo lettere ammesse)<br>";
            if (!usernameBoolean) stringa += "<b>•username</b> (almeno 8 caratteri, non più di 10, può avere 0 o più '_' all'inizio, seguito da lettere e numeri)<br>";
            if (!emailBoolean) stringa += "<b>•email</b> (almeno 5 caratteri, deve essere valida, esempio: a@a.a)<br>";
            if (!nazioneBoolean) stringa += "<b>•nazione</b> (almeno 1 carattere, solo lettere ammesse)<br>";
            flag = true;
        }

        if(!coincidePwd){
            stringa+= "<b>•password attuale </b> (non coincide)<br>";
            flag = true;
        }

        if(!passwordNuova.equals("")){
            if (!passwordNuovaBoolean) {
                stringa += "<b>•password nuova</b> (almeno 8 caratteri, deve avere almeno un numero, una lettera maiuscola e minuscola, un carattere speciale, no spazi)<br>";
                flag = true;
            }
        }

        if(flag){
            request.setAttribute("Errore", stringa);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/result/user.jsp");
            dispatcher.forward(request, response);
            return;
        }


        Utente utente = null;
        UtenteDAO utenteDAO = new UtenteDAO();

        //verifica se email e username inseriti nelle modifiche sono già esistenti, in tal caso non esegue la query
        if(!(usernameUtenteSession.equals(username))){
            utente = utenteDAO.doRetrieveByEmailOrUsername("", username);
        }
        if(!(emailUtenteSession.equals(email))){
            utente = utenteDAO.doRetrieveByEmailOrUsername(email, "");
        }

        if(utente == null){

            //creazione utente
            Utente utente1 = new Utente();
            utente1.setEmail(email);
            utente1.setUsername(username);
            utente1.setCognome(cognome);
            utente1.setNome(nome);

            if(passwordNuova.equals("")){
                utente1.setPassword(passwordAttuale);
            }
            else{
                utente1.setPassword(passwordNuova);
            }
            utente1.setNazione(nazione);
            utente1.setDataDiNascita(utenteSession.getDataDiNascita());
            utente1.setAdmin(utenteSession.getAdmin());
            utente1.setCarrello(utenteSession.getCarrello());
            utente1.setLibreria(utenteSession.getLibreria());

            //update campi inserirti dall'utente nel DB
            utenteDAO.doUpdateByEmailUsername(emailUtenteSession, usernameUtenteSession, utente1);

            //rimuove l'utente attualmente in sessione e lo rimpiazza con i dati dell'utente aggiornato
            request.getSession(false).removeAttribute("utente");
            request.getSession(false).setAttribute("utente", utente1);
        }
        else{
            //utente gia registrato
            request.setAttribute("Errore", "ErroreUtenteEsistente");
            RequestDispatcher dispatcher = request.getRequestDispatcher("LoadPageUserServlet");
            dispatcher.forward(request, response);
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("LoadPageUserServlet");
        dispatcher.forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
