document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('error-message');

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
            const data = await response.json();
            // Guardar el token en el localStorage
            localStorage.setItem('jwt', data.token);

            // Obtener el rol del token (decodificarlo)
            const payload = JSON.parse(atob(data.token.split('.')[1]));
            const role = payload.role; // Asegúrate de que el rol esté presente en el payload

            // Redirigir al usuario según el rol
            if (role === 'ADMIN') {
                window.location.href = '/admin.html';  // Redirige a la página de admin
            } else if (role === 'CLIENT') {
                window.location.href = '/client.html';  // Redirige a la página de cliente
            }
        } else {
            const errorText = await response.json();
            errorMessage.textContent = errorText.error || 'Usuario o contraseña incorrectos';
            errorMessage.style.display = 'block';
        }
    } catch (error) {
        console.error('Error:', error);
        errorMessage.textContent = 'Error de conexión con el servidor';
        errorMessage.style.display = 'block';
    }
});

document.getElementById('registerBtn').addEventListener('click', () => {
    // Lógica para registro
    console.log('Redirigir a página de registro');
});