//funzione di convalida del form di registrazione
function validateFormRegistrati() {
    let nome = document.forms["form"]["nome"].value;
    let cognome = document.forms["form"]["cognome"].value;
    let username = document.forms["form"]["username"].value;
    let password = document.forms["form"]["password"].value;
    let email = document.forms["form"]["email"].value;

    if (nome === "" || cognome === "" || username === "" || password === "" || email === "") {
        alert("Attenzione, inserire tutti i campi");
        return false;
    }

    let nomeBoolean = controlloNome(nome);
    let cognomeBoolean = controlloCognome(cognome);
    let usernameBoolean = controlloUsername(username);
    let passwordBoolean = controlloPassword(password);
    let emailBoolean = controlloEmail(email);

    if (nomeBoolean && cognomeBoolean && usernameBoolean && passwordBoolean && emailBoolean)
        return true;
    else {
        let stringa = "<b>I seguenti campi non sono validi:<br></b>";
        if (nomeBoolean === false) stringa += "<b>•nome</b> (almeno 3 caratteri, la prima lettera maiuscola, solo lettere ammesse)<br>"
        if (cognomeBoolean === false) stringa += "<b>•cognome</b> (almeno 3 caratteri, la prima lettera maiuscola, solo lettere ammesse)<br>"
        if (usernameBoolean === false) stringa += "<b>•username</b> (almeno 8 caratteri, non più di 10, può avere 0 o più '_' all'inizio, seguito da lettere e numeri)<br>";
        if (passwordBoolean === false) stringa += "<b>•password</b> (almeno 8 caratteri, deve avere almeno un numero, una lettera maiuscola e minuscola, un carattere speciale, no spazi)<br>";
        if (emailBoolean === false) stringa += "<b>•email</b> (almeno 5 caratteri, deve essere valida, esempio: a@a.a)<br>";
        document.getElementById("errori").innerHTML = stringa;
        if(document.querySelector(".errorServlet") != null) document.querySelector(".errorServlet").style.display = "none";
        return false;
    }
}

function controlloNome(nome){
    if(nome.length < 3)
        return false;

    const pattern = /^[A-Z]{1}[a-z]+$/;    //verifica che ci siano solo lettere

    if(pattern.test(nome))
        return true;
    return false;
}

function controlloCognome(cognome){
    if(cognome.length < 3)
        return false;

    const pattern = /^[A-Z]{1}[a-z]+$/;    //verifica che ci siano solo lettere

    if(pattern.test(cognome))
        return true;
    return false;
}

function controlloUsername(username){
    if(username.length < 8 || username.length>10)
        return false;

    //accetta (0 o più) caratteri (_) seguito da (1) lettera poi accetta (lettere, _ -)

    const pattern = /^[_]*[A-Za-z]{1}[A-Za-z0-9_-]+$/;

    if(pattern.test(username))
        return true;
    return false;
}


function controlloPassword(password){
    if(password.length < 8)
        return false;

    //accetta lettere, numeri, caratteri speciali, non accetta spazi

    const pattern1 = /[0-9]+/;		//verifica se ci sono 1 o più numeri
    const pattern2 = /[A-Z]+/;		//verifica se ci sono 1 o più caratteri
    const pattern3 = /[a-z]+/;
    const pattern4 = /[\W]+/;		//test se sono presenti caratteri speciali
    const pattern5 = /^[^ ]+$/;	    //test se lo "" non è presente dall'inizio alla fine della stringa

    if(pattern1.test(password) && pattern2.test(password) && pattern3.test(password) && pattern4.test(password) && pattern5.test(password))
        return true;
    return false;
}

function controlloEmail(email){
    if(email.length < 5)
        return false;

    //accetta (1) o più caratteri/numeri/il punto/il _ /il -) poi (@) seguito (1 o più caratteri/numeri/il punto/il _ /il -) poi (.) e infine (da 2 a 6 caratteri)

    const pattern = /^[A-z0-9\._-]+@[A-z0-9\._-]+\.[A-z]{2,6}$/

    if(pattern.test(email))
        return true;
    return false;
}

function showPassword(){
    let element = document.getElementById("password");

    if(element.type === "password"){
        element.type = "text";
    }
    else{
        element.type = "password";
    }

}

//form modifica campi
function showPassword2(){

    showPassword();

    let element = document.getElementById("passwordVecchia");

    if(element.type === "password"){
        element.type = "text";
    }
    else{
        element.type = "password";
    }
}

function showPasswordAdmin(){

    showPassword();

    let element = document.getElementById("admin");

    if(element.type === "password"){
        element.type = "text";
    }
    else{
        element.type = "password";
    }
}


function controlloNazione(nazione){
    if(nazione.length < 1)
        return false;

    const pattern = /^[A-Z]+$/i;    //verifica che ci siano solo lettere

    if(pattern.test(nazione))
        return true;
    return false;
}

