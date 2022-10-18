<%@ page import="Model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <title>Esplora</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/activeNavbar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generic.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar1.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar2.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/esplora.css" type="text/css">
</head>
    <body onload="activeNavBarOnLoad()" style="min-height: 100vh; display: flex; flex-direction: column;">

        <% Utente utente = (Utente) session.getAttribute("utente");%>

            <header>
                <%@include file="/navbar/navbarPrincipale.jsp"%>
            </header>

            <br>

                <%@include file="/navbar/navbarSecondaria.jsp"%>

            <br>

            <div style="flex-grow: 1;">

                    <h1>Esplora i nostri contenuti</h1>

                    <section>
                        <div class="row">
                            <div class="column">
                                <input type="image" onclick="recuperaGiochiGenere('azione')" src="${pageContext.request.contextPath}/images/EldenRing/copertina.jpg">
                                <p>Azione</p>
                            </div>
                            <div class="column">
                                <input type="image" onclick="recuperaGiochiGenere('avventura')" src="${pageContext.request.contextPath}/images/Minecraft/copertina.jpg">
                                <p>Avventura</p>
                            </div>
                            <div class="column">
                                <input type="image" onclick="recuperaGiochiGenere('strategia')" src="${pageContext.request.contextPath}/images/Pac-Man/copertina.jpg">
                                <p>Strategia</p>
                            </div>
                            <div class="column">
                                <input type="image" onclick="recuperaGiochiGenere('corse')" src="${pageContext.request.contextPath}/images/NeedForSpeed/copertina.jpg">
                                <p>Corse</p>
                            </div>
                            <div class="column">
                                <input type="image" onclick="recuperaGiochiGenere('sport')" src="${pageContext.request.contextPath}/images/FIFA22/copertina.jpg">
                                <p>Sport</p>
                            </div>
                        </div>

                        <br>
                        <table>
                            <tr>
                                <td id="10">
                                    Sotto i 10€
                                </td>
                                <td id="20">
                                    Sotto i 20€
                                </td>
                                <td id="20-50">
                                    Tra 20€ e 50€
                                </td>
                                <td id="50">
                                    Più di 50€
                                </td>
                            </tr>
                        </table>

                    </section>


                <br>

                <div class="centralCopertine fade">

                </div>

            </div>

            <br>

            <footer>
                <%@include file="/navbar/footer.jsp"%>
            </footer>

            <script>

                    //funzione chiamata ad ogni click di una delle img che fa riferimento al genere dei giochi
                    function recuperaGiochiGenere(genere){

                        //imposta il display a none al contenitore dei giochi
                        var divCentralCopertine = $(".centralCopertine").first();
                        divCentralCopertine.css("display", "none");

                        //funzione ajax scorciatoia per ricevere (get) dati al server
                        $.get('${pageContext.request.contextPath}/MostraGenereServlet', {"genere":genere},  //url, dati da inviare al server

                            function (data){    //funzione di callback chiamata in caso di successo

                                //rimozione childrens (giochi) dal divCentral
                                divCentralCopertine.empty();
                                divCentralCopertine.css("display", "block");

                                let html = '';

                                //itera su ogni oggetto dell'array data
                                $.each(data, function(i, obj) {

                                    html += '<div class="containerCopertine">' +
                                                '<p class="titoloGioco">' + obj["titolo"] + '</p>' +
                                                <!--form che invia richiesta alla servlet di mostrare la pagina prodotto-->
                                                '<form action="${pageContext.request.contextPath}/LoadPageProdottoServlet" method="post">' +
                                                    '<input type="hidden" name="titolo" value="' + obj["titolo"] + '">' +
                                                    '<input type="image" class="copertine" src="${pageContext.request.contextPath}/' + obj["path"] + '">' +
                                                '</form>' +
                                                '<p class="titoloGioco">' + obj["prezzo"] + '€' + '</p>' +
                                            '</div>'
                                });

                                divCentralCopertine.html(html);

                            }).fail(function() {              //in caso di fallimento della richiesta
                                alert("Request failed.");
                            });

                    };


                    //aggiunge un addEventListener click ai td che definiscono cosa accade al click
                    $("td").click(function (event){

                        var tdID = event.target.id; //permette di capire quale dei td ha generato l'evento, id sarà il prezzo del filtro

                        //imposta il display a none al contenitore dei giochi
                        var divCentralCopertine = $(".centralCopertine").first();
                        divCentralCopertine.css("display", "none");

                        //funzione ajax scorciatoia per ricevere (get) dati al server
                        $.get('${pageContext.request.contextPath}/MostraPrezziGiochiServlet', {"prezzo":tdID},  //url, dati da inviare al server

                            function (data){    //funzione di callback chiamata in caso di successo

                                //rimozione childrens (giochi) dal divCentral
                                divCentralCopertine.empty();
                                divCentralCopertine.css("display", "block");

                                let html = '';

                                //itera su ogni oggetto dell'array data
                                $.each(data, function(i, obj) {

                                    html += '<div class="containerCopertine">' +
                                        '<p class="titoloGioco">' + obj["titolo"] + '</p>' +
                                        <!--form che invia richiesta alla servlet di mostrare la pagina prodotto-->
                                        '<form action="${pageContext.request.contextPath}/LoadPageProdottoServlet" method="post">' +
                                        '<input type="hidden" name="titolo" value="' + obj["titolo"] + '">' +
                                        '<input type="image" class="copertine" src="${pageContext.request.contextPath}/' + obj["path"] + '">' +
                                        '</form>' +
                                        '<p class="titoloGioco">' + obj["prezzo"] + '€' + '</p>' +
                                        '</div>'
                                });

                                divCentralCopertine.html(html);

                            }).fail(function() {              //in caso di fallimento della richiesta
                            alert("Request failed.");
                            });
                    });

            </script>

    </body>
</html>
