document.addEventListener("DOMContentLoaded", function() {
    // Obtenemos las referencias una vez que el HTML está listo
    const rolSelect = document.getElementById("rol");
    const credenciales = document.getElementById("credenciales");

    // Definimos la función dentro para que tenga acceso a las variables
    function mostrarCredenciales() {
        if (rolSelect && credenciales) { // Verificación de seguridad
            if (rolSelect.value === "ROLE_ADMINISTRADOR" || 
                rolSelect.value === "ROLE_BIBLIOTECARIO") {
                
                credenciales.style.display = "block";
            } else {
                credenciales.style.display = "none";
            }
        }
    }

    // Ejecutamos la función al inicio por si ya hay un valor seleccionado (ej. al editar)
    if (rolSelect) {
        mostrarCredenciales();
        
        // Escuchamos el cambio de valor
        rolSelect.addEventListener("change", mostrarCredenciales);
    }
});