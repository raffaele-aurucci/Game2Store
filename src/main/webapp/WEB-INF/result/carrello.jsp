<%@ page import="Model.Utente" %>
<%@ page import="Model.Carrello" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Gioco" %>
<%@ page import="java.util.Map" %>
<%@ page import="Model.CartaDiCredito" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <title>Carrello</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/activeNavbar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generic.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar1.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar2.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carrello.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
</head>

    <body onload="activeNavBarOnLoad()" style="min-height: 100vh; display: flex; flex-direction: column;">

        <% Utente utente = (Utente) session.getAttribute("utente");
           Carrello carrello = utente.getCarrello();
           List<String> titoliGiochiCarrello = carrello.getListaTitoliGiochiCarrello();
           Map<String, Gioco> listaGiochiApplication = (Map<String, Gioco>) application.getAttribute("listaGiochi");
           List<CartaDiCredito> cartaDiCreditoList = (List<CartaDiCredito>) request.getAttribute("listaCarteDiCredito");
        %>

    <header>
        <%@include file="/navbar/navbarPrincipale.jsp"%>
    </header>

    <br>

    <%@include file="/navbar/navbarSecondaria.jsp"%>

    <br>
    <h1>Il mio carrello</h1>
    <br>
        <div style="flex-grow: 1">
            <section>

                <% if(carrello.getListaTitoliGiochiCarrello().size() > 0) { %>
                    <table>
                        <%for(String titolo: titoliGiochiCarrello){             //itera sui titoli dei giochi della lista dei giochi in offerta recuperata dal DB
                            Gioco gioco = listaGiochiApplication.get(titolo);   //recupero il gioco dalla mappa
                            for(String path: gioco.getPathImages())             //itera sulla lista di path del Gioco (copertine, screen, video...)
                                if(path.contains("copertina")) {                //se la path contiene la sottostringa copertina %>
                        <tr id="<%=gioco.getTitolo()%>">
                            <td>
                                <img class="copertina" src="${pageContext.request.contextPath}/<%=path%>">
                            </td>
                            <td>
                                <p><%=gioco.getTitolo()%></p>
                            </td>
                            <td>
                                <p><%=gioco.getPrezzo() + "€"%></p>
                            </td>
                            <td>
                                <!--button che al click invia richiesta ajax di rimozione di un gioco al carrello mediante il pulsante apposito -->
                                    <button onclick="rimuoviCarrello('<%=gioco.getTitolo()%>');" type="submit" class="rimuovi">Rimuovi</button>
                            </td>
                        <% } %>
                            </tr>
                        <% } %>
                    </table>

                    <% if(carrello.getTotale() != 0) {%>
                        <p id="totale">Totale ordine:
                            <span id="tot">
                                <%String totStringa = String.format("%.2f", carrello.getTotale());
                                    String totStringaPunto = "";

                                    for(int i=0; i<totStringa.length(); i++){
                                        if(totStringa.charAt(i) != ',')
                                            totStringaPunto+=totStringa.charAt(i);
                                        else
                                            totStringaPunto+= ".";
                                    }%>
                                <%=totStringaPunto%>€</span>
                        </p>
                    <% } %>
                <% } %>

                    <p id="vuoto"> <% if(carrello.getTotale() == 0) {%>Non sono presenti articoli nel carrello <% } %> </p>

                    <br>

                    <% if(carrello.getTotale() > 0) { %>
                        <button id="ordine" onclick="openOverlay()">Effettua ordine</button>
                    <% } %>

                    <div id="myNav" class="overlay">
                        <a href="javascript:void(0)" id="closebtn" onclick="closeOverlay()">&times;</a>

                            <% if(cartaDiCreditoList.size()==0) { %>
                                <div id="overlay-content1" class="overlay-content">
                                        <a id="inserisci" href="${pageContext.request.contextPath}/LoadPageUserServlet?profilo=si">Inserisci una carta di credito per effettuare il pagamento
                                        </a>
                                </div>
                            <% } else {
                                    %>
                                    <div id="overlay-content2" class="overlay-content">

                                        <h1>Scegli una carta di credito per effettuare il pagamento</h1>

                                            <% for(CartaDiCredito cartaDiCredito: cartaDiCreditoList) { %>
                                                <table class="carta">
                                                    <form action="${pageContext.request.contextPath}/EffettuaOrdineServlet" method="post">
                                                        <tr class="carta">
                                                            <td class="carta">
                                                                <img class="icone" <%if(cartaDiCredito.getCircuito().equals("Visa")) { %>
                                                                     src="${pageContext.request.contextPath}/images/icon/visa.jpg"
                                                                    <% } else if(cartaDiCredito.getCircuito().equals("Mastercard")) { %>
                                                                     src="${pageContext.request.contextPath}/images/icon/mastercard.jpg"
                                                                    <% } else if(cartaDiCredito.getCircuito().equals("American Express")) { %>
                                                                     src="${pageContext.request.contextPath}/images/icon/americanExpress.jpg"
                                                                    <% } %> >
                                                            </td>
                                                            <td class="carta">
                                                                <%="**** " + cartaDiCredito.getNumeroCarta().charAt(15) + cartaDiCredito.getNumeroCarta().charAt(16) + cartaDiCredito.getNumeroCarta().charAt(17) + cartaDiCredito.getNumeroCarta().charAt(18)%>
                                                                <input type="hidden" name="numeroCarta" value="<%=cartaDiCredito.getNumeroCarta()%>">
                                                            </td>
                                                            <td class="carta">
                                                                <button style="margin-top: 0" type="submit" value="Scegli carta">Scegli carta</button>
                                                            </td>
                                                        </tr>
                                                    </form>
                                                </table>
                                            <% } %>
                                    </div>
                            <% } %>
                    </div>

            </section>
        </div>
    <br>

    <footer>
        <%@include file="/navbar/footer.jsp"%>
    </footer>

<script>

    //funzione che prende in input il titolo del gioco che si vuole rimuovere dal carrello, viene chiamato al click del button
    function rimuoviCarrello(titoloGioco){

        let xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function (){

            if(this.readyState === 4 && this.status === 200) {

                //prende la riga della tabella con id uguale al titolo del gioco
                let tr = document.getElementById(titoloGioco);

                //prende lo span che specifica il totale dei prezzi dei prodotti
                let span = document.getElementById("tot");

                //prende la stringa JSON dalla response e la decodifica in oggetto Javascript
                var risposta = JSON.parse(this.responseText);

                //preleva i campi dall'oggetto risposta
                var rimosso = risposta["GiocoRimosso"];
                var tot = risposta["totale"];

                //prende i paragrafi che definiscono se il carrello è vuoto o sono presenti prodotti
                let pTotale = document.getElementById("totale");
                let pVuoto = document.getElementById("vuoto");

                //prende il button che permette di effettuare l'ordine
                let buttonOrdine = document.getElementById("ordine");

                if(rimosso === true){
                    tr.style.display = "none";
                    if(tot!="0.00"){
                        span.innerHTML = tot + "€";
                        pTotale.style.display = "block";
                        pVuoto.style.display = "none";
                        buttonOrdine.style.display = "block";
                    }
                    else{
                        pTotale.style.display = "none";
                        pVuoto.style.display = "block";
                        pVuoto.innerHTML = "Non sono presenti articoli nel carrello";
                        buttonOrdine.style.display = "none";
                    }
                }
            }
        }

        xhttp.open("get", "${pageContext.request.contextPath}/carrello-servlet?titolo=" + titoloGioco, true);
        xhttp.send();

    }


    //funzioni che aumentano o diminuiscono la width della schermata delle carte di credito
    function openOverlay() {
        document.getElementById("myNav").style.width = "100%";
        if(document.getElementsByClassName("overlay-content")[0] != null)
            document.getElementsByClassName("overlay-content")[0].style.display = "block";
        else if(document.getElementsByClassName("overlay-content")[1] != null)
            document.getElementsByClassName("overlay-content")[1].style.display = "block";
    }

    function closeOverlay() {
        document.getElementById("myNav").style.width = "0%";
        if(document.getElementsByClassName("overlay-content")[0] != null)
            document.getElementsByClassName("overlay-content")[0].style.display = "none";
        else if (document.getElementsByClassName("overlay-content")[1] != null)
            document.getElementsByClassName("overlay-content")[1].style.display = "none";
    }

</script>
</body>
</html>
