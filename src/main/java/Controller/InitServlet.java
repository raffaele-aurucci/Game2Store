package Controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import Model.Gioco;
import Model.GiocoDAO;

import java.util.Map;

// prima servlet istanziata al momento dell'avvio dell'applicazione, si occupa di andare a prelevare
// tutti i giochi dal DB per poterli caricare nel contesto dell'applicazione

@WebServlet(urlPatterns = "/myInit", loadOnStartup = 0)

public class InitServlet extends HttpServlet {

    public void init() {

        GiocoDAO giocoDAO = new GiocoDAO();
        Map<String, Gioco> giochi = giocoDAO.doRetrieveAll();

        giochi.forEach((titolo, gioco) -> {gioco.setPathImages(giocoDAO.doRetrieveImagesByTitolo(titolo));});

        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("listaGiochi", giochi);
    }
}
