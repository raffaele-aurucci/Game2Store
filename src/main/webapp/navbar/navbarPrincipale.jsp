<div id="content_btn">
    <a id="threeBar" style="float: left; padding-left: 5px" href="javascript:void(0)" onclick="swapNavBar()">&#9776</a>
        <a id="logo" style="float: right; padding: 0" href="${pageContext.request.contextPath}/index.html">
            <img src="${pageContext.request.contextPath}/images/icon/Logo.png" style="width: 120px; padding: 0">
        </a>
</div>

<nav class="bar1">
    <ul>
        <li class="img">
            <a style="padding: 0; font-size: 14px;" href="${pageContext.request.contextPath}/index.html">
                <img src="${pageContext.request.contextPath}/images/icon/Logo.png" style="width: 160px;">
            </a>
        </li>

        <li>
            <a id="home" href="${pageContext.request.contextPath}/index.html">NEGOZIO</a>
        </li>
        <li>
            <a id="info" href="${pageContext.request.contextPath}/sezioni/info.jsp">INFO</a>
        </li>
        <li>
            <a id="assistenza" href="${pageContext.request.contextPath}/sezioni/assistenza.jsp">ASSISTENZA</a>
        </li>

        <% if (utente == null) { %>
        <li class="right">
            <a id="accedi" href="${pageContext.request.contextPath}/accesso/login.jsp"> ACCEDI </a>
        </li>
        <% } else { %>
        <li class="right">
            <a href="${pageContext.request.contextPath}/logout-servlet"> ESCI </a>
        </li>
        <li class="right">
            <a id="profilo" href="${pageContext.request.contextPath}/LoadPageUserServlet"><%= utente.getUsername()%>
            </a>
        </li>
        <% } %>

        <li class="right img">
            <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/images/icon/icon.png" style="width: 20px;"></a>
        </li>
    </ul>
</nav>

<script>

    //funzione chiamata ogni volta che si clicca sul threeBar, che diventa visibile quando viene raggiunto un width di 700px (css display block)
    function swapNavBar(){

        if(document.getElementsByClassName("bar1")[0].style.display === "none"){
            document.getElementsByClassName("bar1")[0].style.display = "block"
            document.querySelector("#threeBar").innerHTML = "&#9587"
            document.querySelector("#threeBar").style.fontSize = "18px"
            document.querySelector("#threeBar").style.paddingTop = "3px"
            document.querySelector("#threeBar").style.paddingLeft = "8px"
        }
        else{
            document.getElementsByClassName("bar1")[0].style.display = "none"
            document.querySelector("#threeBar").innerHTML = "&#9776"
            document.querySelector("#threeBar").style.fontSize = "24px"
            document.querySelector("#threeBar").style.paddingTop = "0px"
            document.querySelector("#threeBar").style.paddingLeft = "5px"
        }
    }

</script>
