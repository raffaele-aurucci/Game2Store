<div id="sticky">
    <div id="content_btn2">
        <a href="javascript:void(0)">&#5121</a>
    </div>

    <nav class="bar2">
        <ul>
            <li>
                <a id="scopri" href="${pageContext.request.contextPath}/index.html">Scopri</a>
            </li>
            <li>
                <a id="esplora" href="${pageContext.request.contextPath}/sezioni/esplora.jsp">Esplora</a>
            </li>
            <li>
                <a id="notizie" href="${pageContext.request.contextPath}/sezioni/notizie.jsp">Notizie</a>
            </li>

            <% if (utente != null) { %>
            <li class="right">
                <a id="libreria" href="${pageContext.request.contextPath}/libreria-servlet">Libreria</a>
            </li>
            <li class="right">
                <a id="carrello" href="${pageContext.request.contextPath}/MostraCarrelloServlet">Carrello</a>
            </li>
            <% } %>

            <li>

                <input id="searchBar" type="text" name="search" placeholder="Cerca" onkeyup="resulting()">
                <div id="containerResult">

                </div>

            </li>
        </ul>

    </nav>
</div>

<script>

        //funzione che al click della freccia in giù (img) attiverà la funzione slideToggle() sulla navbar2 con una velocità
        //di 200ms e con relativa funzione eseguita al termine dello slide (cambia il riempimento della freccia in giù)
        $(document).ready(function (){
            $("#content_btn2 a").click(function (){
                $("nav.bar2").slideToggle(200, swap);
            });
        })

        function swap(){
            if($(".bar2").css("display") === "none"){
                $("#content_btn2 a").html("&#5121");
                $("#content_btn2 a").css({"padding-top":"0px"});
            }
            else{
                $("#content_btn2 a").html("&#9660");
                $("#content_btn2 a").css({"padding-top":"3px"});
            }
        }


        //funzione ajax attivata quando si verifica l'evento onkeyup() nella barra di ricerca
        function resulting(){

            let xhttp = new XMLHttpRequest();

            xhttp.onreadystatechange = function (){

                if(xhttp.readyState === 4 && xhttp.status === 200){

                    var obj = this.responseText;

                    //parsa la stringa json di titoli presi dalla response in oggetto Javascript
                    var x = JSON.parse(obj);

                    //prende il div che ospiterà una serie di <a> con all'interno i titoli recuperati dall'oggetto x
                    let list = document.getElementById("containerResult")

                    //rimuove eventuali figli del contenitore per inserire i nuovi risultati
                    while(list.hasChildNodes()){
                        list.removeChild(list.firstChild);
                    }

                    //creazione dei link contenente i titoli, messi in append al div contenitore
                    for(var i=0; i<x["lista"].length; i++){
                        const a = document.createElement("a");
                        var path = "${pageContext.request.contextPath}/LoadPageProdottoServlet?titolo=" + x["lista"][i];
                        a.setAttribute("href", path);
                        a.innerHTML = x["lista"][i];
                        a.classList.add("resultSearch");
                        document.getElementById("containerResult").appendChild(a);
                    }
                }
            }

            //preleva l'input dell'utente
            var text = document.getElementById("searchBar").value;
            xhttp.open("get", "${pageContext.request.contextPath}/resultSearchBar?testo=" + text, true);
            xhttp.send();
        }

</script>