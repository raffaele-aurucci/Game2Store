<%@ page import="Model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Accedi</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generic.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar1.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
    <script defer src="${pageContext.request.contextPath}/script/activeNavbar.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/controlloLogin.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/controlloRegistrazione.js"></script>
</head>

<!--dichiara body flessibile, il modo in cui gli elementi saranno impilati è in colonna, la lunghezza minima del body è pari al 100% della viewport-->
<body onload="activeNavBarOnLoad()" style="min-height: 100vh; display: flex; flex-direction: column;">

    <% Utente utente = null;%>

    <header>
        <%@include file="/navbar/navbarPrincipale.jsp"%>
    </header>

    <% String errore = (String) request.getAttribute("Errore"); %>

    <!--div flessibile che specifica di aumentare ad 1 la grandezza rispetto agli altri elementi flessibili-->
    <div style="flex-grow: 1;">

        <h1> Effettua l'accesso con l'account Game2Store</h1>

                <section class="container">

                    <form action="${pageContext.request.contextPath}/login-servlet" method="post" id="myForm">

                        <div class="row">
                            <div class="label">
                                <label for="email">Inserisci email: </label>
                            </div>

                            <div class="input">
                                <input type="email" name="email" id="email" placeholder="Inserisci email" required autofocus>
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="password">Inserisci password: </label>
                            </div>

                            <div class="input">
                                <input type="password" name="password" id="password" placeholder="Inserisci password" required>
                            </div>
                        </div>

                        <br>

                        <div style="text-align: center;">
                            <input type="checkbox" name="showpwd" id="showpwd" onclick="showPassword()">
                            <p style="display: inline;">Mostra password</p>
                        </div>


                        <br>
                        <input type="submit" value="Accedi ora">

                    </form>

                        <br>
                        <p style="font-weight: bold"> Non possiedi un account Game2Store? <a href="${pageContext.request.contextPath}/accesso/registrati.jsp"> Iscriviti </a> </p>

                </section>

        <p id="errori"></p>

        <% if(errore!=null && errore.equalsIgnoreCase("ErroreUtente")){ %>
        <p class="errorServlet"> <b>Nessun utente corrisponde alle credenziali inserite!</b> </p>
            <%}else if(errore!=null && errore.equalsIgnoreCase("ErroreCampi")){ %>
        <p class="errorServlet"> <b>Non hai inserito tutti i campi obbligatori! </b></p>
                    <% }
                else if(errore!=null){ %>
                <p class="errorServlet"><%=errore%></p>
                <%}%>

    </div>

    <br>

    <footer>
        <%@include file="/navbar/footer.jsp"%>
    </footer>

</body>
</html>