document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');

    errorMessage.textContent = '';
    successMessage.textContent = '';

    try {
        const response = await fetch('http://127.0.0.1:8080/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
                email: email,
                password: password
            })
        });

        if (response.ok) {
            successMessage.textContent = 'Usuario registrado con éxito.';
            successMessage.style.display = 'block';
            setTimeout(() => {
                window.location.href = 'index.html';
            }, 2000);
        } else {
            const errorText = await response.text();
            errorMessage.textContent = errorText;
            errorMessage.style.display = 'block';
        }
    } catch (error) {
        errorMessage.textContent = 'Error en el servidor.';
        errorMessage.style.display = 'block';
    }
});

document.getElementById('loginBtn').addEventListener('click', () => {
    window.location.href = 'index.html';
});
