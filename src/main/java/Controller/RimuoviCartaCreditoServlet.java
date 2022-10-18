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

// servlet che si occupa di rimuovere una carta di credito dal DB dato il numero di carta inviato dalla request e indiriz
// zo email prelevato dall'utente in sessione

@WebServlet("/RimuoviCartaCreditoServlet")

public class RimuoviCartaCreditoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String numeroCarta = request.getParameter("numeroCarta");
        Utente utente = (Utente) request.getSession().getAttribute("utente");

        CartaDiCredito cartaDiCredito = new CartaDiCredito();
        cartaDiCredito.setNumeroCarta(numeroCarta);
        CartaDiCreditoDAO cartaDiCreditoDAO = new CartaDiCreditoDAO();
        cartaDiCreditoDAO.doDelete(utente, cartaDiCredito);

        RequestDispatcher dispatcher = request.getRequestDispatcher("LoadPageUserServlet");
        request.setAttribute("profilo", "si");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{
        doGet(request, response);
    }
}
