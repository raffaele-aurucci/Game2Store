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

// servlet che si occupa di verificare l'accesso dell'utente che ha sottomesso il form di login, verifica la correttezza
// dei campi inseriti prelevandoli dalla request, se esiste un utente salvato nel DB che ha un email e una password che
// corrisponde ai campi inseriti dall'utente, recupera tutti i campi dell'utente e lo inserisce in sessione.
// In caso di errori setta un attributo nella request che comunica all'utente quali errori si sono verificati, tale erro
// re verrà prelevato dalla JSP che lo inserirà nella response.

@WebServlet("/login-servlet")

public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UtenteDAO utenteDAO = new UtenteDAO();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.equals("") || password.equals("")) {
            request.setAttribute("Errore", "ErroreCampi");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/accesso/login.jsp");
            dispatcher.forward(request, response);
        }
        else {

                Boolean passwordBoolean = RegisterServlet.controlloPassword(password);
                Boolean emailBoolean = RegisterServlet.controlloEmail(email);

                if(!(emailBoolean && passwordBoolean)){
                    String errore="<b>I seguenti campi non sono validi:<br></b>";
                    if(!emailBoolean) errore +="<b>•email</b> (almeno 5 caratteri, deve essere valida, esempio: a@a.a)<br>";
                    if(!passwordBoolean) errore += "<b>•password</b> (almeno 8 caratteri, deve avere almeno un numero, una lettera maiuscola e minuscola, un carattere speciale, no spazi)<br>";
                    request.setAttribute("Errore", errore);
                    RequestDispatcher dispatcher=request.getRequestDispatcher("/accesso/login.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                Utente utente = utenteDAO.doRetrieveByEmailPassword(email, password);

                if (utente == null) {
                    request.setAttribute("Errore", "ErroreUtente");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/accesso/login.jsp");
                    dispatcher.forward(request, response);
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("utente", utente);
                    response.sendRedirect("index.html");
                }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

