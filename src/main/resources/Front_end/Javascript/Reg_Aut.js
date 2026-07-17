document.getElementById('loginForm').addEventListener('submit', async (e) => {
            e.preventDefault();

            
            const payload = {

                /*id inpit*/
                username: document.getElementById('usernameLogin').value,
                password: document.getElementById('password').value
            };

            try {
                // Chiamata esplicita all'endpoint di login di Spring Boot sulla porta 8081
                const response = await fetch('http://localhost:8081/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                        /*manca token*/
                    },
                    body: JSON.stringify(payload)
                });

                const messageElement = document.getElementById('message');
                
                if (response.ok) {
                    // LEGGI IL TOKEN COME TESTO SEMPLICE (Risolve l'errore "Unexpected token 'e'")
                    const token = await response.text(); 
                    
                    // Salviamo il Token JWT nel sessionStorage
                    sessionStorage.setItem('token', token);

                    messageElement.style.color = 'green';
                    messageElement.innerText = "Accesso effettuato con successo!";
                    
                    // Reindirizziamo l'utente alla dashboard protetta
                    setTimeout(() => {
                        window.location.href = '../html/home.html'; 
                    }, 1500);

                } else {
                    // Se le credenziali sono errate
                    const errorMsg = await response.text(); 
                    messageElement.style.color = 'red';
                    messageElement.innerText = "Errore di accesso: " + (errorMsg || "credenziali non valide.");
                }
            } catch (error) {
                console.error("Errore durante la connessione:", error);
                const messageElement = document.getElementById('message');
                messageElement.style.color = 'red';
                messageElement.innerText = "Errore di connessione al server.";
            }
        });

        document.getElementById('registerForm').addEventListener('submit', async (e) => {
            e.preventDefault();

            // 1. Recuperiamo i dati inseriti nel form mappandoli ESATTAMENTE come UserDto in Java
            const payload = {
                nome: document.getElementById('nome').value,
                cognome: document.getElementById('cognome').value,
                cognome: document.getElementById('indirizzo').value,
                cognome: document.getElementById('cf').value,
                username: document.getElementById('username').value,
                password: document.getElementById('password').value
                passwordControllo: document.getElementById('passwordControllo').value
            };

            try {
                // 2. Chiamata esplicita a Spring Boot sulla porta 8081
                const response = await fetch('http://localhost:8081/auth/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                        /*manca sessionStorage */
                    },
                    body: JSON.stringify(payload) 
                });

                const messageElement = document.getElementById('message');
                
                if (response.ok) {
                    messageElement.style.color = 'green';
                    messageElement.innerText = "Registrazione avvenuta con successo! Ora fai login...";
                    
                    setTimeout(() => {
                        window.location.href = '../html/Reg_Aut.html';
                    }, 2000);
                } else {
                    // Legge la risposta in caso di errore (es. se falliscono le validazioni o se l'utente esiste già)
                    const errorMsg = await response.text(); 
                    messageElement.style.color = 'red';
                    messageElement.innerText = "Errore: " + errorMsg;
                }
            } catch (error) {
                console.error("Errore durante la connessione:", error);
                const messageElement = document.getElementById('message');
                messageElement.style.color = 'red';
                messageElement.innerText = "Errore di connessione: verifica che Spring Boot sia avviato.";
            }
        });