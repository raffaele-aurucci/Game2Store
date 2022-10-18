<%@ page import="Model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registrazione</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generic.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar1.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/activeNavbar.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/controlloRegistrazione.js"></script>
</head>
<body onload="activeNavBarOnLoad()" style="min-height: 100vh; display: flex; flex-direction: column;">

    <% Utente utente = null;%>

    <header>
        <%@include file="/navbar/navbarPrincipale.jsp"%>
    </header>

    <% String errore = (String) request.getAttribute("Errore"); %>

    <div style="flex-grow: 1">

        <h1> Inserisci tutti i campi richiesti </h1>

                <section class="container">

                        <form name="form" action="${pageContext.request.contextPath}/register-servlet" onsubmit="return validateFormRegistrati()" method="post">

                            <div class="row">
                                <div class="label">
                                    <label for="nazione">Inserisci paese: </label>
                                </div>
                                <div class="input">
                                    <select name="nazione" id="nazione" required autofocus>
                                        <option value="Austria">Austria</option>
                                        <option value="Cina">Cina</option>
                                        <option value="Francia">Francia</option>
                                        <option value="Germania">Germania</option>
                                        <option value="Italia" selected>Italia</option>
                                        <option value="Portogallo">Portogallo</option>
                                        <option value="Svizzera">Svizzera</option>
                                        <option value="Spagna">Spagna</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row">
                                <div class="label">
                                    <label for="nome">Inserisci nome: </label>
                                </div>
                                <div class="input">
                                    <input type="text" name="nome" id="nome" placeholder="Mario">
                                </div>
                            </div>

                            <div class="row">
                                <div class="label">
                                    <label for="cognome">Inserisci cognome: </label>
                                </div>
                                <div class="input">
                                    <input type="text" name="cognome" id="cognome" placeholder="Rossi">
                                </div>
                            </div>

                            <div class="row">
                                <div class="label">
                                    <label for="date">Inserisci data di nascita: </label>
                                </div>
                                <div class="input">
                                    <input type="date" name="date" id="date" min="1940-01-01" max="2019-12-31">
                                </div>
                            </div>

                            <div class="row">
                                <div class="label">
                                    <label for="username">Inserisci username: </label>
                                </div>
                                <div class="input">
                                    <input type="text" name="username" id="username" placeholder="Mario_14">
                                </div>
                            </div>

                            <div class="row">
                                <div class="label">
                                    <label for="email">Inserisci email: </label>
                                </div>
                                <div class="input">
                                    <input type="email" name="email" id="email" placeholder="mariorossi14@gmail.com">
                                </div>
                            </div>

                            <div class="row">
                                <div class="label">
                                    <label for="password">Inserisci password: </label>
                                </div>
                                <div class="input">
                                    <input type="password" name="password" id="password" placeholder="1234Rossi!">
                                </div>
                            </div>

                            <div class="row">
                                <div class="label">
                                    <label for="admin">Inserisci password admin (opzionale): </label>
                                </div>
                                <div class="input">
                                    <input type="password" name="admin" id="admin" placeholder="password admin" >
                                </div>
                            </div>

                            <br>

                            <div style="text-align: center;">
                                <input type="checkbox" name="showpwd" id="showpwd" onclick="showPasswordAdmin()">
                                <p style="display: inline;">Mostra password</p>
                            </div>

                            <br>

                            <div style="text-align: center;">
                                <p style="display: inline; font-weight: bold">Ho letto e accetto tutti i <a href="https://www.epicgames.com/site/it/tos?lang=it">termini di servizio:</a></p>
                                <input type="checkbox" name="termini" id="termini" required>
                            </div>

                            <br>
                            <input type="submit" value="Continua">
                    </form>
                </section>

                <p id="errori"></p>

                <% if(errore!=null && errore.equalsIgnoreCase("ErroreCampi")){ %>
                <p class="errorServlet"> <b>Non hai inserito tutti i campi richiesti! </b></p>
                    <%}else if(errore!=null && errore.equalsIgnoreCase("ErroreUtenteEsistente")){ %>
                            <p class="errorServlet"> <b>Attenzione, utente già registrato in precedenza! </b></p>
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