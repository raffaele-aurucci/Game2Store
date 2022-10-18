<%@ page import="Model.Utente" %>
<%@ page import="Model.Carrello" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Gioco" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/activeNavbar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generic.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar1.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar2.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pageGioco.css" type="text/css">
</head>

<body onload="activeNavBarOnLoad()">

   <% Utente utente = (Utente) session.getAttribute("utente");
       Map<String, Gioco> listaGiochiApplication = (Map<String, Gioco>) application.getAttribute("listaGiochi");
       Gioco gioco = listaGiochiApplication.get(request.getAttribute("titolo"));
       List<String> listaGiochiCarrello = null;
       List<String> libreriaUtente = null;
       if (utente != null){
           listaGiochiCarrello = utente.getCarrello().getListaTitoliGiochiCarrello();
           libreriaUtente = utente.getLibreria();
       }

       String pathScreen1 = null, pathScreen2 = null, pathScreen3 = null, pathScreen4 = null;
       for(String path: gioco.getPathImages()) {
           if (path.contains("screen1")) {
                pathScreen1= path;
           }
           else if(path.contains("screen2")){
                pathScreen2 = path;
           }
           else if(path.contains("screen3")){
               pathScreen3 = path;
           }
           else if(path.contains("screen4")){
               pathScreen4 = path;
           }
       }
    %>

    <header>
        <%@include file="/navbar/navbarPrincipale.jsp"%>
    </header>

    <br>

    <%@include file="/navbar/navbarSecondaria.jsp"%>

    <section>

            <h1><%=gioco.getTitolo()%></h1>

            <div class="container">
                <img id="expandedImg" src="${pageContext.request.contextPath}/<%=pathScreen1%>">
            </div>


            <!-- The four columns -->
            <div class="row">
                <div class="column" style="padding-right: 10px;">
                    <img src="${pageContext.request.contextPath}/<%=pathScreen1%>" alt="screen1" onclick="myFunction(this);">
                </div>
                <div class="column" style="padding-right: 10px">
                    <img src="${pageContext.request.contextPath}/<%=pathScreen2%>" alt="screen2" onclick="myFunction(this);">
                </div>
                <div class="column" style="padding-right: 10px;">
                    <img src="${pageContext.request.contextPath}/<%=pathScreen3%>" alt="screen3" onclick="myFunction(this);">
                </div>
                <div class="column">
                    <img src="${pageContext.request.contextPath}/<%=pathScreen4%>" alt="screen4" onclick="myFunction(this);">
                </div>
            </div>

            <div>
                <p><b>Genere</b>
                    <p><%=gioco.getGenere()%></p>
                </p>
                <br>
                <p><b>Descrizione</b>
                    <p><%=gioco.getDescrizione()%></p>
                </p>
                <br>
                <p><b>Data di uscita</b>
                <p><%=gioco.getDataUscita()%></p>
                </p>
                <br>
                <p><b>Prezzo</b>
                    <p><%=gioco.getPrezzo()%>€</p>
                </p>
            </div>
        <br>

        <% if(utente!=null && !libreriaUtente.contains(gioco.getTitolo())) { %>
            <% if(listaGiochiCarrello.contains(gioco.getTitolo())) { %>
                <button type="submit" id="<%=gioco.getTitolo()%>" class="rimuovi" onclick="rimuoviAggiungiCarrello('<%=gioco.getTitolo()%>');"> Rimuovi dal carrello </button>
            <% }
            else { %>
                <button type="submit" id="<%=gioco.getTitolo()%>" onclick="rimuoviAggiungiCarrello('<%=gioco.getTitolo()%>');"> Aggiungi al carrello </button>
           <% } %>
        <% } else if(utente == null){ %>
            <form action="${pageContext.request.contextPath}/accesso/login.jsp">
                <button type="submit">Aggiungi al carrello</button>
            </form>
        <% } else{ %>
            <p id="libreriaGioco">
                <b>Gioco in libreria </b>&#9745
            </p>
        <% } %>
    </section>

   <br>

   <footer>
       <%@include file="/navbar/footer.jsp"%>
   </footer>


    <script>

        //funzione che modifica l'immagine in primo piano
        function myFunction(imgs) {
            var expandImg = document.getElementById("expandedImg");
            expandImg.src = imgs.src;
        }

        //funzione che aggiunge/rimuove un prodotto al carrello
        function rimuoviAggiungiCarrello(titoloGioco){

            let xhttp = new XMLHttpRequest();

            xhttp.onreadystatechange = function (){

                if(this.readyState === 4 && this.status === 200) {

                    //restituisce il button rimuovi/aggiungi al carrello
                    let button = document.getElementById(titoloGioco);

                    //parsa la stringa JSON presa dalla response in oggetto JS
                    var risposta = JSON.parse(this.responseText);

                    var rimosso = risposta["GiocoRimosso"];

                    if(rimosso === true){
                        button.innerHTML = "Aggiungi al carrello"
                        button.removeAttribute("class");
                    }
                    else{
                        button.innerHTML = "Rimuovi dal carrello"
                        button.setAttribute("class", "rimuovi");
                    }

                }
            }

            xhttp.open("get", "${pageContext.request.contextPath}/carrello-servlet?titolo=" + titoloGioco, true);
            xhttp.send();

        }
    </script>


</body>
</html>
