<%@ page import="Model.Utente" %>
<%@ page import="Model.Gioco" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin area</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/activeNavbar.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/controlloRegistrazione.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generic.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar1.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css" type="text/css">
</head>
<body onload="activeNavBarOnLoad()" style="min-height: 100vh; display: flex; flex-direction: column;">

    <%  Utente utente = (Utente) session.getAttribute("utente");
        Map<String, Gioco> listaGiochi =  (Map<String, Gioco>) application.getAttribute("listaGiochi");
        String errore = (String) request.getAttribute("errore");
    %>

    <header>
        <%@include file="/navbar/navbarPrincipale.jsp"%>
    </header>

    <div style="flex-grow: 1">

        <h1>Bentornato <%=utente.getUsername()%>, modifica prezzi e offerte.</h1>

                <table>
                    <tr>
                        <th>
                            Copertina
                        </th>
                        <th>
                            Titolo del Gioco
                        </th>
                        <th>
                            Prezzo (€)
                        </th>
                        <th>
                            Offerta
                        </th>
                    </tr>


                    <% for(Gioco gioco: listaGiochi.values()){ %>
                        <tr class="trGiochi">
                        <%for(String path: gioco.getPathImages()) {
                            if(path.contains("copertina")) { %>
                                <td>
                                    <img class="copertine" src="${pageContext.request.contextPath}/<%=path%>">
                                </td>
                             <% }
                             }%>
                            <td>
                                <input type="text" name="titolo" id="titolo" value="<%=gioco.getTitolo()%>" readonly form="form">
                            </td>
                            <td>
                                <input class="prezzo" type="text" name="prezzo" id="<%=gioco.getTitolo()%>" value="<%=gioco.getPrezzo()%>" form="form">
                            </td>
                            <td>
                                <select form="form" name="offerta" id="offerta">
                                    <option value="Si" <% if(gioco.isOfferta()){ %>
                                            selected
                                            <% } %>>Si</option>
                                    <option value="No" <% if(!gioco.isOfferta()){ %>
                                            selected
                                            <% } %>>No</option>
                                </select>
                            </td>
                    </tr>
                    <% } %>
                </table>

                <form action="${pageContext.request.contextPath}/UpdateGiochiServlet" method="post" id="form" onsubmit="return controlloPrezzi()">
                    <input style="margin-top: 20px" type="submit" value="Conferma modifiche">
                </form>

                <p id="errori"></p>

                <% if(errore!=null) { %>
                <p class="errorServlet"> <b>Errore, prezzi ed/od offerte inserite non validi! </b></p>
                <% } %>

                <br>

                <center>
                    <a href="${pageContext.request.contextPath}/LoadPageUserServlet?profilo=si">
                    Accedi alla tua area profilo riservata
                    </a>
                </center>
    </div>

    <br>

    <footer>
        <%@include file="/navbar/footer.jsp"%>
    </footer>

    <script>

        //funzione che controlla la correttezza dei prezzi inseriti dall'utente
        function controlloPrezzi(){

            //restituisce una collezione di input-text
            let prezzi = document.getElementsByClassName("prezzo");

            let stringa = "<b>I seguenti campi non sono validi (inserire solo numeri, seguito da '.' e parte decimale):</b><br>";

            let flag = false;

            //costruisce una stringa di errore con i titoli ai quali sono stati fatti degli errori di battitura
            for(let i = 0; i<prezzi.length; i++){
                if(patternPrezzi(prezzi[i].value) == false){
                    stringa+= "<b>•" + prezzi[i].getAttribute("id") + "</b> non ha un prezzo valido <br>";
                    flag = true;
                }
            }

            if(flag === true){
                document.getElementById("errori").innerHTML = stringa;
                return false;
            }
            else
                return true;
        }

        function patternPrezzi(prezzo){
            const pattern = /^[0-9]+[.][0-9]{2}$/i;    //verifica che ci siano solo numeri e in mezzo vi sia il ( . ) seguito da parte decimale

            if(pattern.test(prezzo))
                return true;
            return false;
        }
    </script>

</body>
</html>