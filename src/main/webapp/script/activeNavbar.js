//funzione chiamata nel momento in cui la pagina è stata caricata, si occupa di prelevare dall'oggetto window il pathname
//della pagina in cui si trova l'utente, in base a dove si trova setta la classe active ad un elemento specifico della navbar1
//e della navbar2
function activeNavBarOnLoad(){
    let x = window.location.pathname;

    if(x === "/Game2Store_war_exploded/index.html" || x === "/Game2Store_war_exploded/" ||
        x === "/Game2Store-1.0-SNAPSHOT/index.html" || x === "/Game2Store-1.0-SNAPSHOT/" ) {
        document.getElementById("home").classList.add("active");
        document.getElementById("scopri").classList.add("active2");     //si rimuove con remove()
    }
    else if(x === "/Game2Store_war_exploded/sezioni/esplora.jsp" || x === "/Game2Store-1.0-SNAPSHOT/sezioni/esplora.jsp"){
        document.getElementById("home").classList.add("active");
        document.getElementById("esplora").classList.add("active2");
    }
    else if(x === "/Game2Store_war_exploded/sezioni/notizie.jsp" || x === "/Game2Store-1.0-SNAPSHOT/sezioni/notizie.jsp") {
        document.getElementById("home").classList.add("active");
        document.getElementById("notizie").classList.add("active2");
    }
    else if(x === "/Game2Store_war_exploded/accesso/login.jsp" || x === "/Game2Store_war_exploded/login-servlet" ||
        x === "/Game2Store-1.0-SNAPSHOT/accesso/login.jsp" || x === "/Game2Store-1.0-SNAPSHOT/login-servlet"){
        document.getElementById("accedi").classList.add("active");
    }
    else if(x === "/Game2Store_war_exploded/MostraCarrelloServlet" || x === "/Game2Store-1.0-SNAPSHOT/MostraCarrelloServlet"){
        document.getElementById("carrello").classList.add("active2");
        document.getElementById("home").classList.add("active");
    }
    else if(x === "/Game2Store_war_exploded/libreria-servlet" || x === "/Game2Store_war_exploded/EffettuaOrdineServlet" ||
        x === "/Game2Store-1.0-SNAPSHOT/libreria-servlet" || x === "/Game2Store-1.0-SNAPSHOT/EffettuaOrdineServlet"){
        document.getElementById("libreria").classList.add("active2");
        document.getElementById("home").classList.add("active");
    }
    else if(x === "/Game2Store_war_exploded/LoadPageUserServlet" || x === "/Game2Store_war_exploded/CartaDiCreditoServlet" || x === "/Game2Store_war_exploded/UpdateFieldUserServlet"
    || x === "/Game2Store_war_exploded/RimuoviCartaCreditoServlet" || x === "/Game2Store-1.0-SNAPSHOT/LoadPageUserServlet" || x === "/Game2Store-1.0-SNAPSHOT/CartaDiCreditoServlet"
        || x === "/Game2Store-1.0-SNAPSHOT/UpdateFieldUserServlet" || x === "/Game2Store-1.0-SNAPSHOT/RimuoviCartaCreditoServlet"){
        document.getElementById("profilo").classList.add("active");
    }
    else if(x === "/Game2Store_war_exploded/LoadPageProdottoServlet" || x === "/Game2Store-1.0-SNAPSHOT/LoadPageProdottoServlet"){
        document.getElementById("home").classList.add("active");
    }
    else if(x === "/Game2Store_war_exploded/accesso/registrati.jsp" || x === "/Game2Store_war_exploded/register-servlet" ||
            x === "/Game2Store-1.0-SNAPSHOT/accesso/registrati.jsp" || x === "/Game2Store-1.0-SNAPSHOT/register-servlet"){
        document.getElementById("accedi").classList.add("active");
    }
    else if(x === "/Game2Store_war_exploded/sezioni/info.jsp" || x === "/Game2Store-1.0-SNAPSHOT/sezioni/info.jsp"){
        document.getElementById("info").classList.add("active");
    }
    else if(x === "/Game2Store_war_exploded/sezioni/assistenza.jsp" || x === "/Game2Store-1.0-SNAPSHOT/sezioni/assistenza.jsp"){
        document.getElementById("assistenza").classList.add("active");
    }
}