package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.Carrello;
import Model.Gioco;
import Model.Utente;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

// servlet che soddisfa la richiesta ajax di aggiunta/rimozione di un prodotto al carrello, recupera il titolo del gioco
// che deve essere rimosso/aggiunto dalla request, quindi verifica se il gioco è già presente nel carrello salvato in
// sessione, se è già presente allora il gioco deve essere rimosso dal carrello, se non è presente allora deve essere
// aggiunto al carrello

@WebServlet("/carrello-servlet")

public class CarrelloServlet extends HttpServlet {

    synchronized public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //recupero utente e carrello dalla sessione
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        String titoloGioco = request.getParameter("titolo");
        Carrello carrello = utente.getCarrello();
        List<String> listaTitoliGiochiCarrello = carrello.getListaTitoliGiochiCarrello();

        //recupero mappa dei giochi dal contesto dell'applicazione
        Map<String, Gioco> giochiApplication = (Map<String, Gioco>) getServletContext().getAttribute("listaGiochi");


        Boolean rimuovi = false;

        //se il gioco viene trovato nel carrello, allora dovrà essere rimosso, altrimenti aggiunto
        for(String s: listaTitoliGiochiCarrello){
            if(s.equals(titoloGioco)) {
                rimuovi = true;
                break;
            }
        }

        //recupero dell'oggetto Gioco data la key 'titolo' dall'application
        Gioco giocoApplication = giochiApplication.get(titoloGioco);
        Double tot = carrello.getTotale();

        //rimozione/aggiunta gioco al carrello
        if(rimuovi == true){
            listaTitoliGiochiCarrello.remove(titoloGioco);
            tot -= giocoApplication.getPrezzo();
        }
        else{
            listaTitoliGiochiCarrello.add(titoloGioco);
            tot += giocoApplication.getPrezzo();
        }

        //dato che i numeri non sono sempre rappresentati correttamente per questioni di sicurezza settiamo il totale a 0 se vi è un valore negativo
        if(tot<0)
            carrello.setTotale(0);
        else {
            tot = Math.round(tot*100.0) / 100.0;    //arrotonda a due cifre decimali il valore tot
            carrello.setTotale(tot);
        }


        //utile nel caso si voglia rappresentare a schermo la parte decimale compreso lo 0 -> 9.90 anziché 9.9
        String totStringa = String.format("%.2f", carrello.getTotale());

        String totStringaPunto = "";

        for(int i=0; i<totStringa.length(); i++){
            if(totStringa.charAt(i) != ',')
                totStringaPunto+=totStringa.charAt(i);
            else
                totStringaPunto+= ".";
        }

        //definizione oggetto JSON
        JSONObject obj = new JSONObject();
        obj.put("GiocoRimosso", rimuovi);
        obj.put("totale", totStringaPunto);

        //codifica oggetto JSON in testo
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);

        //scrittura oggetto JSON nella risposta
        String jsonText = out.toString();
        response.setContentType("application/json");
        response.getWriter().print(jsonText);
    }
}
