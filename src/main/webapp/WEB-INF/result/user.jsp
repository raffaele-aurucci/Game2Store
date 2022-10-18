<%@ page import="Model.Utente" %>
<%@ page import="Model.CartaDiCredito" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Ordine" %>
<%@ page import="Model.Gioco" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Area utente</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/controlloRegistrazione.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/activeNavbar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generic.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar1.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" type="text/css">

    <style>

        table {
            border-collapse: collapse;
            table-layout: fixed;
            width: 100%;
            margin-top: 25px;
        }

        th ,td{
            padding: 8px;
            text-align: center;
            border-bottom: 1px solid #B7B7B7;
        }

        th {
            color: white;
            font-size: 16px;
        }

        td {
            color: white;
        }

        img.icone {
            width: 100%;
        }

        #erroriCarta, .errorServletCarta {
            color: orange;
        }

        button{
            margin-top: 0;
            font-size: 14px;
        }

        @media screen and (max-width: 1160px){
            .nascondi{
                display: none;
            }
        }

        @media screen and (max-width: 450px){
            button {
                padding: 8px 10px;
            }
        }

    </style>
</head>

<body onload="activeNavBarOnLoad()" style="min-height: 100vh; display: flex; flex-direction: column;">

    <%  Utente utente = (Utente) session.getAttribute("utente");
        List<CartaDiCredito> listaCarteDiCredito = (List<CartaDiCredito>) request.getAttribute("listaCarteDiCredito");
        List<Ordine> listaOrdini = (List<Ordine>) request.getAttribute("listaOrdini");
        String s = "";
    %>

    <header>
        <%@include file="/navbar/navbarPrincipale.jsp"%>
    </header>

        <% String errore = (String) request.getAttribute("Errore");
           String erroreCarta = (String) request.getAttribute("ErroreCarta");
        %>

        <br>

        <div style="flex-grow: 1">

            <h1> Bentornato <%=utente.getUsername()%>! </h1>

            <% if(listaOrdini.size()>0) { %>
            <section class="container" style="margin-top: 100px">
                <h2 style="font-size: 18px"> Lista ordini effettuati </h2>
                <table>
                    <tr>
                        <th>
                            Data acquisto
                        </th>
                        <th>
                            Totale ordine
                        </th>
                        <th>
                            Titoli e prezzi
                        </th>
                    </tr>
                    <% for(Ordine ordine: listaOrdini){
                        %>
                            <tr>
                                <td>
                                    <%=ordine.getDataAcquisto()%>
                                </td>
                                <td>
                                    <%=ordine.getTotaleOrdine() + "€"%>
                                </td>
                                <td>
                                    <% for(Gioco gioco: ordine.getListaGiochiOrdinati()) {
                                            s += "<br>" + gioco.getTitolo() + "<br>" + gioco.getPrezzo() + "€ <br><br>";
                                    } %>
                                    <%=s%>
                                    <% s = ""; %>
                                </td>
                            </tr>
                    <% } %>
                </table>
            </section>
            <% } %>

            <!--Modifica campi utente-->
                <section class="container" style="margin-top: 50px">

                    <h2 style="font-size: 18px"> Modifica i campi che preferisci </h2>

                    <form name="form" action="${pageContext.request.contextPath}/UpdateFieldUserServlet" method="post" id="myForm">

                        <div class="row">
                            <div class="label">
                                <label for="nazione">Modifica nazione: </label>
                            </div>
                            <div class="input">
                                <input type="text" name="nazione" id="nazione" value="<%=utente.getNazione()%>">
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="nome">Modifica nome: </label>
                            </div>
                            <div class="input">
                                <input type="text" name="nome" id="nome" value="<%=utente.getNome()%>">
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="cognome">Modifica cognome: </label>
                            </div>
                            <div class="input">
                                <input type="text" name="cognome" id="cognome" value="<%=utente.getCognome()%>">
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="username">Modifica username: </label>
                            </div>
                            <div class="input">
                                <input type="text" name="username" id="username" value="<%=utente.getUsername()%>">
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="email">Modifica email: </label>
                            </div>
                            <div class="input">
                                <input type="email" name="email" id="email" value="<%=utente.getEmail()%>">
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="passwordVecchia">Inserisci password: </label>
                            </div>
                            <div class="input">
                                <input type="password" name="passwordVecchia" id="passwordVecchia" placeholder="Inserisci password per confermare">
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="password">Nuova password: (opzionale)</label>
                            </div>
                            <div class="input">
                                <input type="password" name="password" id="password" placeholder="Inserisci nuova password">
                            </div>
                        </div>

                        <br>

                        <div style="text-align: center;">
                            <input type="checkbox" name="showpwd" id="showpwd" onclick="showPassword2()">
                            <p style="display: inline;">Mostra password</p>
                        </div>

                        <br>

                        <br>
                        <input type="submit" value="Conferma modifiche">
                    </form>
                </section>

                <p id="errori"></p>
                <% if(errore!=null && errore.equalsIgnoreCase("ErroreCampi")){ %>
                <p class="errorServlet"> <b>Non sono ammessi campi vuoti!</b> </p>
                <%}else if(errore!=null && errore.equalsIgnoreCase("ErroreUtenteEsistente")){ %>
                <p class="errorServlet"> <b>Attenzione, email e/o username già esistenti! </b></p>
                <% }
                else if(errore!=null){ %>
                <p class="errorServlet"><%=errore%></p>
                <%}%>

            <!--Carte di credito-->
                <section class="container" style="margin-top: 50px">

                    <h2 style="font-size: 18px"> Gestisci carte di credito </h2>

                    <% if(listaCarteDiCredito.size()>0) { %>
                    <table class="cartaUtente">
                        <tr>
                            <th>
                                Circuito
                            </th>
                            <th >
                                Numero carta
                            </th>
                            <th class="nascondi">
                                Scadenza
                            </th>
                            <th class="nascondi">
                                Indirizzo
                            </th>
                            <th class="nascondi">
                                Città
                            </th>
                            <th>

                            </th>
                        </tr>
                    <%for(CartaDiCredito cartaDiCredito: listaCarteDiCredito) {%>
                        <tr>
                            <td>
                                <img class="icone" <%if(cartaDiCredito.getCircuito().equals("Visa")) { %>
                                        src="${pageContext.request.contextPath}/images/icon/visa.jpg"
                                <% } else if(cartaDiCredito.getCircuito().equals("Mastercard")) {
                                        %>
                                        src="${pageContext.request.contextPath}/images/icon/mastercard.jpg"
                                <% } else if(cartaDiCredito.getCircuito().equals("American Express")) {
                                        %>
                                        src="${pageContext.request.contextPath}/images/icon/americanExpress.jpg"
                                <% } %> >
                            </td>
                            <td>
                                <%="**** " + cartaDiCredito.getNumeroCarta().charAt(15) + cartaDiCredito.getNumeroCarta().charAt(16) + cartaDiCredito.getNumeroCarta().charAt(17) + cartaDiCredito.getNumeroCarta().charAt(18)%>
                            </td>
                            <td class="nascondi">
                                <%=cartaDiCredito.getDataScadenza()%>
                            </td>
                            <td class="nascondi">
                                <%=cartaDiCredito.getIndirizzo()%>
                            </td>
                            <td class="nascondi">
                                <%=cartaDiCredito.getCitta()%>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/RimuoviCartaCreditoServlet" method="post">
                                    <input type="hidden" name="numeroCarta" value="<%=cartaDiCredito.getNumeroCarta()%>">
                                    <button type="submit" class="rimuovi">Rimuovi</button>
                                </form>
                            </td>
                        <tr>
                    <% } %>
                    </table>
                    <% } %>

                    <br>

                    <h2 style="font-size: 18px"> Aggiungi una nuova carta di credito </h2>

                    <form name="form" action="${pageContext.request.contextPath}/CartaDiCreditoServlet" method="post" id="myForm2">

                        <div class="row">
                            <div class="label">
                                <label for="numeroCarta">Inserisci numero carta: </label>
                            </div>
                            <div class="input">
                                <input type="text" name="numeroCarta" id="numeroCarta" placeholder="1234-1234-1234-1234">
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="circuito">Inserisci il circuito: </label>
                            </div>
                            <div class="input">
                                <select name="circuito" id="circuito">
                                    <option value="Visa" selected>Visa</option>
                                    <option value="Mastercard">Mastercard</option>
                                    <option value="American Express">American Express</option>
                                </select>
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="data">Inserisci data scadenza: </label>
                            </div>
                            <div class="input">
                                <input type="month" name="dataScadenza" id="data" min="2022-06" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="indirizzo">Inserisci indirizzo: </label>
                            </div>
                            <div class="input">
                                <input type="text" name="indirizzo" id="indirizzo" placeholder="Via Roma 30">
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="cap">Inserisci CAP: </label>
                            </div>
                            <div class="input">
                                <input type="text" name="cap" id="cap" placeholder="12345">
                            </div>
                        </div>

                        <div class="row">
                            <div class="label">
                                <label for="citta">Inserisci città: </label>
                            </div>
                            <div class="input">
                                <input type="text" name="citta" id="citta" placeholder="Roma">
                            </div>
                        </div>

                        <br>
                        <input type="submit" value="Inserisci carta" onclick="return controlliCarta()">
                    </form>
                </section>

                <p id="erroriCarta"></p>
                <% if(erroreCarta!=null && erroreCarta.equalsIgnoreCase("ErroreCampiCarta")){ %>
                <p class="errorServletCarta"> <b>Non sono ammessi campi vuoti! </b></p>
                <% } else if(erroreCarta!=null && erroreCarta.equalsIgnoreCase("ErroreCartaEsistente")){ %>
                <p class="errorServletCarta"> <b>Attenzione, carta inserita già esistente!</b> </p>
                <% } else if(erroreCarta!=null && erroreCarta.equalsIgnoreCase("NumeroMaxCarte")){ %>
                <p class="errorServletCarta"> <b>Attenzione, raggiunto numero massimo di carte inseribili!</b> </p>
                <% } else if(erroreCarta!=null){ %>
                <p class="errorServletCarta"><%=erroreCarta%></p>
                <% } %>

    </div>

    <br>

    <footer>
        <%@include file="/navbar/footer.jsp"%>
    </footer>

<script>

    //controlli form di modifica dei campi dell'utente
    $(document).ready(function (){
        $("#myForm").submit(function (){

            let nazione = $("#nazione").val();
            let nome = $("#nome").val()
            let cognome = $("#cognome").val()
            let username = $("#username").val()
            let email = $("#email").val();
            let passwordVecchia = $("#passwordVecchia").val();
            let passwordNuova = $("#password").val();

            if(email === "" || nazione === "" || nome==="" || cognome=== "" || username==="" || passwordVecchia === ""){
                alert("Attenzione, non sono ammessi campi vuoti, eccetto nuova password");
                return false;
            }

            let nazioneBoolean = controlloNazione(nazione);
            let nomeBoolean = controlloNome(nome);
            let cognomeBoolean = controlloCognome(cognome);
            let usernameBoolean = controlloUsername(username);
            let emailBoolean = controlloEmail(email);
            let passwordBooleanV = controlloPassword(passwordVecchia);
            let passwordBooleanN = (controlloPassword(passwordNuova) || passwordNuova === "");

            if (nazioneBoolean && nomeBoolean && cognomeBoolean && usernameBoolean && emailBoolean && passwordBooleanV && passwordBooleanN){
                return true;
            }
            else {
                let stringa = "<b>I seguenti campi non sono validi:</b><br>";
                if (nazioneBoolean === false) stringa+= "<b>•nazione</b> (almeno 1 caratteri, solo lettere ammesse)<br>";
                if (nomeBoolean === false) stringa+= "<b>•nome</b> (almeno 3 caratteri, solo lettere ammesse)<br>";
                if (cognomeBoolean === false) stringa+= "<b>•cognome</b> (almeno 3 caratteri, solo lettere ammesse)<br>";
                if (usernameBoolean === false) stringa += "<b>•username</b> (almeno 8 caratteri, non più di 10, può avere 0 o più '_' all'inizio, seguito da lettere e numeri)<br>";
                if (emailBoolean === false) stringa += "<b>•email</b> (almeno 5 caratteri, deve essere valida, esempio: a@a.a)<br>";
                if (passwordBooleanV === false) stringa += "<b>•password attuale</b> (almeno 8 caratteri, deve avere almeno un numero, una lettera maiuscola e minuscola, un carattere speciale, no spazi)<br>";
                if (passwordBooleanN === false) stringa += "<b>•password nuova</b> (almeno 8 caratteri, deve avere almeno un numero, una lettera maiuscola e minuscola, un carattere speciale, no spazi)<br>";
                $("p#errori").html(stringa);
                if($("p.errorServlet")!=null) $("p.errorServlet").css({"display":"none"});
                return false;
            }
        });
    });


    //funzione di controllo dell'inserimento della carta di credito da parte dell'utente
    function controlliCarta(){

        let numeroCarta = document.forms["myForm2"]["numeroCarta"].value;
        let indirizzo = document.forms["myForm2"]["indirizzo"].value;
        let cap = document.forms["myForm2"]["cap"].value;
        let citta = document.forms["myForm2"]["citta"].value;
        let dataScadenza = document.forms["myForm2"]["data"].value;

        if(numeroCarta === "" || indirizzo === "" || cap === "" || citta === "" || dataScadenza === ""){
            alert("Attenzione, non sono ammessi campi vuoti")
            return false;
        }

        let numeroCartaBoolean = controlloNumeroCarta(numeroCarta);
        let indirizzoBoolean = controlloIndirizzo(indirizzo);
        let capBoolean = controlloCap(cap);
        let cittaBoolean = controlloCitta(citta);
        let dataScadenzaBoolean = controlloDataScadenza(dataScadenza);

        if(numeroCartaBoolean && indirizzoBoolean && capBoolean && cittaBoolean && dataScadenzaBoolean)
            return true;
        else {
            let stringa = "<b>I seguenti campi non sono validi:<br></b>";
            if(!numeroCartaBoolean) stringa += "<b>•Numero carta</b> (esattamente 19 caratteri, del tipo 1234-1234-1234-1234)<br>";
            if(!indirizzoBoolean) stringa += "<b>•Indirizzo</b> (almeno 5 caratteri, max 30, può contenere solo caratteri alfanumerici e spazi)<br>";
            if(!capBoolean) stringa += "<b>•Cap</b> (esattamente 5 caratteri, tutte cifre)<br>";
            if(!cittaBoolean) stringa += "<b>•Città</b> (almeno 4 caratteri, max 20, può contenere solo lettere e spazi)<br>";
            if(!dataScadenzaBoolean) stringa += "<b>•Data scadenza: </b> (carta inserita ha data di scadenza non più valida)<br>";

            let pErroriCarta = document.getElementById("erroriCarta");
            pErroriCarta.innerHTML = stringa;
            if($("p.errorServletCarta")!=null) $("p.errorServletCarta").css({"display":"none"});
            return false;
        }

    }

    function controlloNumeroCarta(numeroCarta){
        if(numeroCarta.length !== 19)
            return false;

        let pattern = /^[0-9]{4}[-][0-9]{4}[-][0-9]{4}[-][0-9]{4}$/;

        return pattern.test(numeroCarta);
    }

    function controlloIndirizzo(indirizzo){
        if(indirizzo.length < 5 || indirizzo.length >30)
            return false;

        /*accetta indirizzi composti da più parole separati da spazi, poi necessariamente un solo spazio, seguito dal numero civico da max 3 cifre*/
        let pattern = /^[A-Za-z\s]+[\s][0-9]{1,3}$/;

        return pattern.test(indirizzo);
    }

    function controlloCap(cap){
        if(cap.length !== 5)
            return false;

        let pattern = /^[0-9]{5}$/;
        return pattern.test(cap);
    }

    function controlloCitta(citta){
        if(citta.length < 4 || citta.length > 20)
            return false;

        let pattern = /^[A-Za-z\s]+$/;

        return pattern.test(citta);
    }

    function controlloDataScadenza(dataScadenza) {
        let dataAttuale = new Date();

        let dataScadenza2 = new Date(dataScadenza + "-01"); //per semplicità le carte di credito scadono il primo giorno del mese

        return (dataScadenza2 > dataAttuale)
    }
</script>

</body>
</html>