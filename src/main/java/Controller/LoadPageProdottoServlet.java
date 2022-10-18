package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// servlet che si occupa di recuperare il titolo del gioco di cui si vuole mostrare la pagina del prodotto, si aggiunge
// come attributo della request il titolo recuperato, in maniera tale che la JSP possa costruire la relativa pagina.

@WebServlet("/LoadPageProdottoServlet")

public class LoadPageProdottoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String titolo = request.getParameter("titolo");
        request.setAttribute("titolo", titolo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/sezioni/pageGioco.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
