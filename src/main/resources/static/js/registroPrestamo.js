document.addEventListener("DOMContentLoaded", () => {

    const radiosLibro = document.querySelectorAll(".radioLibro");

    radiosLibro.forEach(radio => {

        radio.addEventListener("change", () => {

            document
                    .getElementById("formSeleccionLibro")
                    .submit();

        });

    });

    const radiosEjemplar = document.querySelectorAll(".radioEjemplar");

    radiosEjemplar.forEach(radio => {

        radio.addEventListener("change", () => {

            document.getElementById("formSeleccionEjemplar").submit();

        });

    });

    const radiosUsuario =
            document.querySelectorAll(".radioUsuario");

    radiosUsuario.forEach(radio => {

        radio.addEventListener("change", () => {

            document
                    .getElementById("formSeleccionUsuario")
                    .submit();

        });

    });
});