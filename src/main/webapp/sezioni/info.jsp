<%@ page import="Model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <title>Info</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/script/activeNavbar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/generic.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar1.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar2.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
    <style>

        section {
            margin-right: 214px;
            margin-left: 214px;
            text-align: center;
            margin-top: 150px;
        }

        img.logoCentrale {
            margin-top: 50px;
            max-width: 500px;
            max-height: 300px;
            width: 100%;
        }

        h2{
            font-size: 19px;
        }

        @media screen and (max-width: 700px){

            h1 {
                font-size: 18px;
            }

            h2{
                font-size: 16px !important;
            }

            img.logoCentrale{
                max-width: 300px;
                max-height: 200px;
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

    <div style="flex-grow: 1">

        <section>

            <h1> La nostra storia, autentica passione. </h1>
        <h2>
            Game2Store nasce per condividere l'amore per il mondo videoludico, permettendo a milioni di persone in tutto
            il mondo di scoprire l'infinito universo dei videogiochi.
            Tutto nasce nel più recente Aprile 2022 dall'idea di due giovani promettenti italiani, che hanno fatto della
            loro passione un lavoro.
            Ci auguriamo che l'esperienza sul nostro store possa essere del vostro miglior gradimento,
            una buona permanenza a tutti su Game2Store!
        </h2>

            <img class="logoCentrale" src="${pageContext.request.contextPath}/images/icon/Logo.png">
        </section>
    </div>

    <br>

    <footer>
        <%@include file="/navbar/footer.jsp"%>
    </footer>

</body>
</html>