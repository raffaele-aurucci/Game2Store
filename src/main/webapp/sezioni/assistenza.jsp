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
        }

        h2{
            font-size: 19px;
        }

        table {
            margin-top: 80px;
            border-radius: 1em;
            border-collapse: collapse;
            table-layout: fixed;
            width: 49%;
            background-color: #3E3E3E;
        }

        td {
            padding: 8px;
            text-align: center;
            color: white;
        }

        img.icone {
            max-width: 200px;
            max-height: 200px;
            width: 100%;
        }

        @media screen and (max-width: 700px){

            h1 {
                font-size: 18px;
            }

            h2{
                font-size: 16px;
            }

            table{
                float: none;
                width: 100%;
                margin: 50px 0 0 0;
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
            <h1> Contattaci se hai bisogno di assistenza </h1>
            <h2> il nostro personale sarà a tua completa disposizione </h2>

                <table style="float: left">
                    <tr>
                        <td>
                            <img class="icone" src="${pageContext.request.contextPath}/images/icon/profilo1.jpg">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b> Nome: </b> Peppe Matriosca
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b> Email: </b> peppematri11@libero.com
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b> Numero: </b> 321 4467222
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b> Facebook: </b> Peppe Matriosca
                        </td>
                    </tr>
                </table>

                <table style="float: right">
                    <tr>
                        <td>
                            <img class="icone" src="${pageContext.request.contextPath}/images/icon/profilo2.jpg">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b> Nome: </b> Pedra Pedrita
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b> Email: </b> pedritas101@tiscali.com
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b> Numero: </b> 333 1123232
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b> Facebook: </b> Pedrina Pedrita
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
