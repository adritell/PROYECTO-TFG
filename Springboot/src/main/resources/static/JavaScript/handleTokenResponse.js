/*
// Realizar la solicitud de registro al servidor
fetch('/api/v1/auth/signin', {
    method: 'POST',
    body: JSON.stringify(userData), // userData es la información del usuario para registrarse
    headers: {
        'Content-Type': 'application/json'
    }
})
.then(response => response.json())
.then(data => {
    // Manejar la respuesta del servidor
    handleTokenResponse(data); // Llamar a la función para manejar el token JWT
})
.catch(error => {
    console.error('Error during signup:', error);
});

// Función para manejar la respuesta del servidor y almacenar el token JWT
function handleTokenResponse(response) {
    const token = response.token;
    // Almacenar el token JWT en sessionStorage o localStorage
    sessionStorage.setItem('jwtToken', token);
    // Redireccionar a otra página, actualizar la interfaz de usuario, etc.
    // Por ejemplo:
    window.location.href = '/ruta/a/la/pagina/principal';
}
*/