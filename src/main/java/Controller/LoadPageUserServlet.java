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

// servlet che si occupa di effettuare il forward alla pagina profilo dell'amministratore/utente, dopo aver chiamato
// CartaDiCreditoServlet, recupera l'utente dalla sessione ed eventuali errori dalla servlet precedente, dopodiché
// verifica se l'attributo admin è settato a true o false, a seconda di tale valore effettua il forward alla
// JSP corretta.

@WebServlet("/LoadPageUserServlet")

public class LoadPageUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Utente utente = (Utente) request.getSession().getAttribute("utente");

        //recupero lista carte di credito dell'utente in sessione
        CartaDiCreditoDAO cartaDiCreditoDAO = new CartaDiCreditoDAO();
        List<CartaDiCredito> cartaDiCreditoList = cartaDiCreditoDAO.doRetrieveCartaDiCreditoByEmailUtente(utente.getEmail());

        request.setAttribute("listaCarteDiCredito", cartaDiCreditoList);
        String address, richiestaUtenteProfilo = null;

        //campo inviato dalla request dell'admin in admin.jsp o carrello.jsp per accedere alla pagina profilo oppure settato da
        //CartaDiCreditoServlet o da UpdateFieldUserServlet come attributo
        richiestaUtenteProfilo = request.getParameter("profilo");

        if(richiestaUtenteProfilo == null) richiestaUtenteProfilo = (String) request.getAttribute("profilo");

        if(utente.getAdmin() && richiestaUtenteProfilo == null)
            address = "/WEB-INF/result/admin.jsp";
        else if(utente.getAdmin() && richiestaUtenteProfilo != null)
            address = "/WEB-INF/result/user.jsp";
        else
            address = "/WEB-INF/result/user.jsp";


        //recupero ordini effettuati dall'utente
        OrdineDAO ordineDAO = new OrdineDAO();
        List<Ordine> listaOrdini = ordineDAO.doRetrieveOrdineByEmail(utente.getEmail());

        for(Ordine ordine: listaOrdini)
            ordine.setListaGiochiOrdinati(ordineDAO.doRetrieveTitoliGiochiByIdOrdine(ordine.getId()));

        request.setAttribute("listaOrdini", listaOrdini);

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}
