//funzione eseguita sempre nel momento in cui ci si trova sulla home page, si occupa di modificare le immagini presenti
//al centro della pagina, mediante una funzione che viene eseguita ogni 5000ms

var counter = 0;

setInterval(myFunction, 5000);

function myFunction(){
    if(counter==0) {
        document.getElementById("content").src = "./images/GodOFWar/screen.jpg";
        //modifica i colori dei cerchietti presenti sotto alle immagini, indicano quale immagine è stata raggiunta
        document.getElementById("img3").style.backgroundColor = "#d1d3d1";
        document.getElementById("img2").style.backgroundColor = "#d1d3d1";
        document.getElementById("img1").style.backgroundColor = "#2388cc";
        counter++;
    }
    else if(counter==1){
        document.getElementById("content").src = "./images/F12022/screen.jpg";
        document.getElementById("img3").style.backgroundColor = "#d1d3d1";
        document.getElementById("img1").style.backgroundColor = "#d1d3d1";
        document.getElementById("img2").style.backgroundColor = "#2388cc";
        counter++;
    }
    else if(counter==2){
        document.getElementById("content").src = "./images/FIFA22/screen.jpg";
        document.getElementById("img3").style.backgroundColor = "#2388cc";
        document.getElementById("img1").style.backgroundColor = "#d1d3d1";
        document.getElementById("img2").style.backgroundColor = "#d1d3d1";
        counter=0;
    }
}


//funzione che viene eseguita al click dei cerchietti da parte dell'utente, cambia l'immagine presentata al centro della
//home page
$(document).ready(function() {

    $("input[type=radio]").click(function(event) {

        //permette di ottenere l'id del cerchietto che ha scatenato l'evento click()
        let radioID = event.target.id;

        if(radioID === "img1"){
            counter=0;
            $("#content").attr("src", "./images/GodOFWar/screen.jpg");
            $("#img1").css("background-color", "#2388cc");
            $("#img2").css("background-color", "#d1d3d1");
            $("#img3").css("background-color", "#d1d3d1");
        }
        else if(radioID === "img2"){
            counter=1;
            $("#content").attr("src", "./images/F12022/screen.jpg");
            $("#img1").css("background-color", "#d1d3d1");
            $("#img2").css("background-color", "#2388cc");
            $("#img3").css("background-color", "#d1d3d1");
        }
        else if(radioID === "img3"){
            counter=2;
            $("#content").attr("src", "./images/FIFA22/screen.jpg");
            $("#img1").css("background-color", "#d1d3d1");
            $("#img2").css("background-color", "#d1d3d1");
            $("#img3").css("background-color", "#2388cc");
        }
    });
});