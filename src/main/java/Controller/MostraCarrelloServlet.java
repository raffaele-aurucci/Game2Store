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
import java.util.List;

// servlet che si occupa semplicemente di fare il forward verso la JSP carrello dal momento che quest'ultima non può
// essere chiamata direttamente dal browser se non facendo richiesta a una servlet, essendo che la JSP è salvata nella
// cartella WEB-INF.

@WebServlet("/MostraCarrelloServlet")

public class MostraCarrelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente utente = (Utente) request.getSession().getAttribute("utente");

        //recupero lista carte di credito dell'utente in sessione
        CartaDiCreditoDAO cartaDiCreditoDAO = new CartaDiCreditoDAO();
        List<CartaDiCredito> cartaDiCreditoList = cartaDiCreditoDAO.doRetrieveCartaDiCreditoByEmailUtente(utente.getEmail());
        request.setAttribute("listaCarteDiCredito", cartaDiCreditoList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/result/carrello.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
