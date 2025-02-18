document.addEventListener('DOMContentLoaded', () => {
    // Verificar si el token existe en el localStorage
    const token = localStorage.getItem('jwt');
    if (!token) {
        // Si no hay token, redirigir al login
        window.location.href = '/index.html';  // Página de login
        return;
    }

    // Verificar el rol en el token (decodificarlo)
    const payload = JSON.parse(atob(token.split('.')[1]));
    const role = payload.role;

    // Comprobar si el rol coincide con la página
    if (role === 'ADMIN' && window.location.pathname !== '/admin.html') {
        window.location.href = '/admin.html';
    } else if (role === 'CLIENT' && window.location.pathname !== '/client.html') {
        window.location.href = '/client.html';
    }
});