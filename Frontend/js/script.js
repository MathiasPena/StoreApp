document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://127.0.0.1:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (response.ok) {
            alert('OK');
        } else {
            console.error('Error en el login:', await response.text());
        }
    } catch (error) {
        console.error('Error:', error);
    }
});

document.getElementById('registerBtn').addEventListener('click', () => {
    // Lógica para registro
    console.log('Redirigir a página de registro');
});