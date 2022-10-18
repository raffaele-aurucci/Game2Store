package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Model.Utente;
import Model.UtenteDAO;

import java.io.IOException;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// servlet che si occupa di prelevare i dati provenienti dalla request, dopo che l'utente ha fatto il submit del form,
// di verificare che siano stati inseriti tutti i campi, effettuare controlli sulla correttezza dei campi inseriti, in
// caso di errore setta degli attributi nella request che facciano capire alla JSP che ci sono stati degli errori
// facendo il forward alla stessa. Se tutti i campi sono stati inseriti correttamente e inoltre non vi è un altro
// utente con lo stesso username/email dell'utente che vuole registrarsi, allora crea un nuovo utente, lo salva nel
// DB e lo inserisce in sessione.

@WebServlet("/register-servlet")

public class RegisterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String passwordAdmin = request.getParameter("admin");
        Date data;

        try {
            String[] dataDiNascita = request.getParameter("date").split("-");
            GregorianCalendar dataNascita = new GregorianCalendar(Integer.parseInt(dataDiNascita[0]), Integer.parseInt(dataDiNascita[1])-1, Integer.parseInt(dataDiNascita[2]));
            data = new Date(dataNascita.getTimeInMillis());
        } catch (Exception e) {
            data = null;
        }


        String nazione = request.getParameter("nazione");

        if (username.equals("") || password.equals("") || email.equals("") || nome.equals("") || cognome.equals("") || nazione.equals("")
            || username == null || password == null|| email == null || nome == null || cognome == null || data == null || nazione == null) {
                request.setAttribute("Errore", "ErroreCampi");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/accesso/registrati.jsp");
                dispatcher.forward(request, response);
        }
        else {

                Boolean nomeBoolean = RegisterServlet.controlloNome(nome);
                Boolean cognomeBoolean = RegisterServlet.controlloCognome(cognome);
                Boolean usernameBoolean = RegisterServlet.controlloUsername(username);
                Boolean passwordBoolean = RegisterServlet.controlloPassword(password);
                Boolean emailBoolean = RegisterServlet.controlloEmail(email);
                Boolean pwdAdmin = passwordAdmin.equals("Game2Store");
                Boolean nazioneBoolean = RegisterServlet.controlloNazione(nazione);

                //flag verifica errori
                boolean flag = false;

                //stringa errore
                String stringa = "<b>I seguenti campi non sono validi:<br></b>";

                if (!(nomeBoolean && cognomeBoolean && usernameBoolean && passwordBoolean && emailBoolean && nazioneBoolean)){
                    if (!nomeBoolean) stringa += "<b>•nome</b> (almeno 3 caratteri, la prima lettera maiuscola, solo lettere ammesse)<br>";
                    if (!cognomeBoolean) stringa += "<b>•cognome</b> (almeno 3 caratteri, la prima lettera maiuscola, solo lettere ammesse)<br>";
                    if (!usernameBoolean) stringa += "<b>•username</b> (almeno 8 caratteri, non più di 10, può avere 0 o più '_' all'inizio, seguito da lettere e numeri)<br>";
                    if (!passwordBoolean) stringa += "<b>•password</b> (almeno 8 caratteri, deve avere almeno un numero, una lettera maiuscola e minuscola, un carattere speciale, no spazi)<br>";
                    if (!emailBoolean) stringa += "<b>•email</b> (almeno 5 caratteri, deve essere valida, esempio: a@a.a)<br>";
                    if (!nazioneBoolean) stringa += "<b>•nazione</b> (almeno 1 carattere, solo lettere ammesse)<br>";
                    flag = true;
                }

                if(!pwdAdmin && !passwordAdmin.equals("")){
                    stringa+= "<b>•password admin</b> (non corretta)<br>";
                    flag = true;
                }

                if(flag){
                    request.setAttribute("Errore", stringa);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/accesso/registrati.jsp");
                    dispatcher.forward(request, response);
                    return;
                }


                UtenteDAO utenteDAO = new UtenteDAO();
                Utente utente = utenteDAO.doRetrieveByEmailOrUsername(email, username);

                if(utente==null) {
                        Utente utente1 = new Utente();
                        utente1.setAdmin(pwdAdmin);
                        utente1.setEmail(email);
                        utente1.setUsername(username);
                        utente1.setCognome(cognome);
                        utente1.setNome(nome);
                        utente1.setDataDiNascita(data);
                        utente1.setPassword(password);
                        utente1.setNazione(nazione);

                        utenteDAO.doSaveUtente(utente1);

                        HttpSession session = request.getSession();
                        session.setAttribute("utente", utente1);

                        //comunica al browser di effettuare una nuova richiesta per ricaricare la home page
                        response.sendRedirect("index.html");
                }
                else {
                        request.setAttribute("Errore", "ErroreUtenteEsistente");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/accesso/registrati.jsp");
                        dispatcher.forward(request, response);
                }
        }

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public static Boolean controlloNome(String nome){

        if(nome.length() < 3)
            return false;

        Pattern pattern = Pattern.compile("^[A-Z]{1}[a-z]+$");       //oggetto che astrae il concetto di pattern data una regExp

        Matcher matcher = pattern.matcher(nome);                    //crea un engine che esegue operazioni di corrispondenza su una sequenza di caratteri interpretando un pattern

        return matcher.find();                                      //Il metodo find() chiamato sul matcher esegue la scansione della sequenza di input alla ricerca della sottosequenza
                                                                    //che corrisponde al pattern.
    }

    public static Boolean controlloCognome(String cognome) {

        if(cognome.length() < 3)
            return false;

        Pattern pattern = Pattern.compile("^^[A-Z]{1}[a-z]+$");

        Matcher matcher = pattern.matcher(cognome);

        return matcher.find();
    }

    public static Boolean controlloUsername(String username) {

        if(username.length() < 8 || username.length() >10)
            return false;

        Pattern pattern = Pattern.compile("^[_]*[A-Za-z]{1}[A-Za-z0-9_-]+$");

        Matcher matcher = pattern.matcher(username);

        return matcher.find();
    }

    public static Boolean controlloPassword(String password){

        if(password.length() < 8)
            return false;

        Pattern pattern1 = Pattern.compile("[0-9]+");
        Pattern pattern2 = Pattern.compile("[A-Z]+");
        Pattern pattern3 = Pattern.compile("[a-z]+");
        Pattern pattern4 = Pattern.compile("[\\W]+");
        Pattern pattern5 = Pattern.compile("^[^ ]+$");

        Matcher matcher1 = pattern1.matcher(password);
        Matcher matcher2 = pattern2.matcher(password);
        Matcher matcher3 = pattern3.matcher(password);
        Matcher matcher4 = pattern4.matcher(password);
        Matcher matcher5 = pattern5.matcher(password);

        return  matcher1.find() && matcher2.find() && matcher3.find() && matcher4.find() && matcher5.find();
    }

    public static Boolean controlloEmail(String email){

        if(email.length() < 5)
            return false;

        Pattern pattern = Pattern.compile("^[A-z0-9\\._-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}$");

        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }

    public static Boolean controlloNazione(String nazione){

        if(nazione.length() < 1)
            return false;

        Pattern pattern = Pattern.compile("^[A-Za-z]+$");

        Matcher matcher = pattern.matcher(nazione);

        return matcher.find();
    }

}


