package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.GiocoDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

// servlet che si occupa di soddisfare la richiesta Ajax, in particolare di restituire nella response una stringa JSON
// composta da una serie di titoli che soddisfano i criteri di ricerca richiesti dall'utente, quindi recupera i titoli
// dal DB, crea un JSONArray di titoli e lo inserisce in un JSONObject, dopodiché lo codifica in stringa e lo inserisce
// nella response, definendo come header (content type) il tipo di dato, ossia "application/json".

@WebServlet("/resultSearchBar")

public class ResultSearchBarServlet extends HttpServlet {

    synchronized public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String text = request.getParameter("testo");

        GiocoDAO giocoDAO = new GiocoDAO();
        List<String> listaTitoliGiochi = giocoDAO.doSearchByCharacter(text);

        //crea un JSONArray vuoto quando si cliccano tasti come "DEL" o altri
        if(text.equals("")) {
            JSONArray jarray = new JSONArray();

            JSONObject obj = new JSONObject();
            obj.put("lista", jarray);

            StringWriter out = new StringWriter();
            obj.writeJSONString(out);

            String jsonText = out.toString();
            response.setContentType("application/json");
            response.getWriter().print(jsonText);

            return;
        }

        //crea un JSONArray di titoli
        JSONArray jarray = new JSONArray();

        //inserisce i titoli trovati nel jsonArray
        for(String s1: listaTitoliGiochi){
            jarray.add(s1);
        }

        //salva in un jsonObject il jsonArray
        JSONObject obj = new JSONObject();
        obj.put("lista", jarray);

        //definisce un flusso di caratteri che può essere utilizzato per costruire una stringa
        StringWriter out = new StringWriter();

        //converte l'oggetto json in testo, scrivendo tale flusso nell'oggetto StringWriter
        obj.writeJSONString(out);

        //scrive nella response la stringa JSON costruita precedentemente
        String jsonText = out.toString();
        response.setContentType("application/json");
        response.getWriter().print(jsonText);
    }
}
