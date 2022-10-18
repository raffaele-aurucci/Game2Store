<div id="footer">

    <p id="containerImg">
        <img id="FB" src="${pageContext.request.contextPath}/images/icon/iconFB1.png">
        <img id="Insta" src="${pageContext.request.contextPath}/images/icon/iconINSTA1.png">
        <img id="YT" src="${pageContext.request.contextPath}/images/icon/iconYT1.png">
    </p>

    <p id="diritti">
        &#169 2022 &#8482Game2Store, Inc. Tutti i diritti riservati. &#8482Game2Store, il logo &#8482Game2Store, Fortnite, il logo Fortnite, Unreal, Unreal Engine, il logo Unreal Engine, Unreal Tournament e il logo Unreal Tournament sono marchi o marchi registrati di &#8482 Game2Store, Inc. negli Stati Uniti e in altri paesi. Altri marchi o nomi di prodotti sono marchi dei rispettivi proprietari. Transazioni esterne agli USA tramite &#8482Game2Store International, S.r.l.
    </p>


    <p id="logoB">
        <img src="${pageContext.request.contextPath}/images/icon/Logo2.png">
    </p>

</div>

<script>

    $(document).ready( function (){
        $("#FB").hover(
            function (){
                $("#FB").attr("src", "${pageContext.request.contextPath}/images/icon/iconFB2.png");
            },
            function (){
                $("#FB").attr("src", "${pageContext.request.contextPath}/images/icon/iconFB1.png");
            }
        );

        $("#Insta").hover(
            function (){
                $("#Insta").attr("src", "${pageContext.request.contextPath}/images/icon/iconINSTA2.png");
            },
            function (){
                $("#Insta").attr("src", "${pageContext.request.contextPath}/images/icon/iconINSTA1.png");
            }
        );

        $("#YT").hover(
            function (){
                $("#YT").attr("src", "${pageContext.request.contextPath}/images/icon/iconYT2.png");
            },
            function (){
                $("#YT").attr("src", "${pageContext.request.contextPath}/images/icon/iconYT1.png");
            }
        );

        $("#logoB img").hover(
            function (){
                $("#logoB img").attr("src", "${pageContext.request.contextPath}/images/icon/Logo2B.png");
            },
            function (){
                $("#logoB img").attr("src", "${pageContext.request.contextPath}/images/icon/Logo2.png");
            }
        );
    });

</script>