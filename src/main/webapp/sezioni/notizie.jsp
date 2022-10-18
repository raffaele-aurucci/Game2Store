<%@ page import="Model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <title>Notizie</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/activeNavbar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generic.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar1.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar2.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">

    <style>

        section {
            margin-left: 214px;
            margin-right: 214px;
            margin-top: 100px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 25px;
        }

        th ,td{
            padding: 8px;
            text-align: center;
            border-bottom: 1px solid #B7B7B7;
            border-top: 1px solid #B7B7B7;
        }

        th {
            color: white;
            font-size: 16px;
        }

        td {
            color: white;
        }

        td.notizia{
            text-align: left;
        }

        img.copertine{
            max-width: 180px;
            max-height: 180px;
            width: 100%;
        }

        @media screen and (max-width: 700px){

            h1 {
                font-size: 18px;
            }

            img.copertine{
                width: 100px;
                height: 100px;
            }
        }

        @media screen and (max-width: 1150px){

            section{
                margin-right: 0px;
                margin-left: 0px;
            }
        }

        @media screen and (max-width: 1400px) and (min-width: 1151px){

            section{
                margin-right: 114px;
                margin-left: 114px;
            }
        }

    </style>
</head>

<body onload="activeNavBarOnLoad()" style="min-height: 100vh; display: flex; flex-direction: column;">

<% Utente utente = (Utente) session.getAttribute("utente");%>

<header>
    <%@include file="/navbar/navbarPrincipale.jsp"%>
</header>

<br>

    <%@include file="/navbar/navbarSecondaria.jsp"%>

    <br>

    <div style="flex-grow: 1">

        <h1> Notizie del giorno </h1>

        <section>

            <table>
                <tr>
                    <td>
                        <img class="copertine" src="${pageContext.request.contextPath}/images/EldenRing/copertina.jpg">
                    </td>
                    <td class="notizia">
                        Elden ring mania! Il gioco del momento è il più giocato del 2022 ad oggi.
                    </td>
                <tr>
                    <td>
                        <img class="copertine" src="${pageContext.request.contextPath}/images/Tetris/copertina.jpg">
                    </td>
                    <td class="notizia">
                        Quanti tasselli di tetris servono per circumnavigare il globo? Clicca e lo scoprirai.
                    </td>
                <tr>
                    <td>
                        <img class="copertine" src="${pageContext.request.contextPath}/images/Pac-Man/copertina.jpg">
                    </td>
                    <td class="notizia">
                        Bambino dice di sognare i fantasmi del famoso gioco Pac-man che lo rincorrono durante la notte e distrugge il suo pc.
                    </td>
                <tr>
                    <td>
                        <img class="copertine" src="${pageContext.request.contextPath}/images/Minecraft/copertina.jpg">
                    </td>
                    <td class="notizia">
                        Minecraft continua a stupire, artista Finlandese riproduce in scala 1:1 la Gioconda.
                    </td>
                <tr>
                    <td>
                        <img class="copertine" src="${pageContext.request.contextPath}/images/NeedForSpeed/copertina.jpg">
                    </td>
                    <td class="notizia">
                        Arrestato a Roma, diceva di voler giocare a Need for Speed per le strade della città eterna.
                    </td>
                </tr>
            </table>
        </section>

    </div>

    <br>

    <footer>
        <%@include file="/navbar/footer.jsp"%>
    </footer>

</body>
</html>
