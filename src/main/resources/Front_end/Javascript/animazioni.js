/*Animazione icona login */

fetch('iconaLogin.html')
    .then(response => response.text())
    .then(data => {
      document.getElementById('icona-login-esterna').innerHTML = data;
    })
    .catch(error => console.error('Errore nel caricamento dell\'icona:', error));


/*pop-up apertura */
/*document.addEventListener("DOMContentLoaded", function() {
  const schermataOscura = document.getElementById("sfondoPopup");
  const pulsanteChiudi = document.getElementById("pulsanteChiudiPopup");

  // 1. Mostra il pop-up all'apertura della pagina
  schermataOscura.style.display = "block";

  // 2. Chiudi il pop-up quando si clicca sulla X
  pulsanteChiudi.addEventListener("click", function() {
    schermataOscura.style.display = "none";
  });

  // 3. Chiudi il pop-up se si clicca fuori dalla finestra bianca (sullo sfondo scuro)
  schermataOscura.addEventListener("click", function(evento) {
    if (evento.target === schermataOscura) {
      schermataOscura.style.display = "none";
    }
  });
});*/

/*login-registrazione*/

