$(document).ready(function (){
    //aggiunta evento submit al form di login
    $("#myForm").submit(function (){

        let email = $("#email").val();
        let password = $("#password").val();

        if(email === "" || password === ""){
            alert("Attenzione, inserire tutti i campi");
            return false;
        }

        let emailBoolean = controlloEmail(email);
        let passwordBoolean = controlloPassword(password);

        if (emailBoolean && passwordBoolean){
            return true;
        }
        else {
            let stringa = "<b>I seguenti campi non sono validi:</b><br>";
            if (passwordBoolean === false) stringa += "<b>•password</b> (almeno 8 caratteri, deve avere almeno un numero, una lettera maiuscola e minuscola, un carattere speciale, no spazi)<br>";
            if (emailBoolean === false) stringa += "<b>•email</b> (almeno 5 caratteri, deve essere valida, esempio: a@a.a)<br>";
            $("p#errori").html(stringa);
            if($("p.errorServlet") != null) $("p.errorServlet").css({"display":"none"});
            return false;
        }
    });
});
