package Controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.Gioco;
import Model.GiocoDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

// servlet che soddisfa la richiesta ajax di recuperare tutti i giochi di un dato prezzo specificato nella request, e di
// creare un array JSON di giochi con le informazioni necessarie (titolo, prezzo, path copertina)

@WebServlet("/MostraPrezziGiochiServlet")

public class MostraPrezziGiochiServlet extends HttpServlet {

    synchronized public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //recupero prezzo dalla request
        String prezzoString = request.getParameter("prezzo");
        GiocoDAO giocoDAO = new GiocoDAO();

        //definizione lista di titoli dei giochi con prezzo definito nella request
        List<String> listaTitoliGiochiPrezzo = null;

        switch (prezzoString){
            case "10":      listaTitoliGiochiPrezzo = giocoDAO.doRetrieveByPrezzoMinore(10);
                break;
            case "20":      listaTitoliGiochiPrezzo = giocoDAO.doRetrieveByPrezzoMinore(20);
                break;
            case "20-50":   listaTitoliGiochiPrezzo = giocoDAO.doRetrieveByPrezzoCompreso(20, 50);
                break;
            case "50":      listaTitoliGiochiPrezzo = giocoDAO.doRetrieveByPrezzoMaggiore(50);
                break;
        }

        //recupero mappa dei giochi dall'application
        Map<String, Gioco> listaGiochiApplication = (Map<String, Gioco>) request.getServletContext().getAttribute("listaGiochi");

        //costruzione JSON array
        JSONArray arrayDiGiochi = new JSONArray();

        for(String stringa: listaTitoliGiochiPrezzo){
            Gioco gioco = listaGiochiApplication.get(stringa);

            JSONObject obj = new JSONObject();
            obj.put("titolo", gioco.getTitolo());
            obj.put("prezzo", gioco.getPrezzo());

            for(String path: gioco.getPathImages()){
                if(path.contains("copertina"))
                    obj.put("path", path);
            }

            arrayDiGiochi.add(obj);
        }

        //codifica in stringa del JSON array
        StringWriter stringWriter = new StringWriter();
        arrayDiGiochi.writeJSONString(stringWriter);

        String jsonString = stringWriter.toString();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //scrittura della stringa JSON nella response
        response.getWriter().print(jsonString);

    }

    synchronized public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        doGet(request, response);
    }
}
