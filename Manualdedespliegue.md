### Manual de despliegue
A continuación, se detalle el proceso, paso a paso, de cómo se despliega una aplicación Java Spring Boot MVC en el entorno de explotación Google Cloud.

**Paso 1: Dockerización de la aplicación Spring Boot (contenedores)**

El primer paso para que una aplicación sea “nube-nativa” es empaquetarla en un contenedor. Esto garantiza que la aplicación se ejecute exactamente igual en tu ordenador que en los servidores de Google Cloud.

Para ello, es necesario crear un archivo llamado Dockerfile (sin extensión) en la raíz de tu proyecto. El siguiente archivo contiene las instrucciones para construir la imagen de tu sistema. Se recomienda una configuración de multi-stage build para que la imagen final sea ligera y segura:

![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.001.png)

A continuación, se detalla el funcionamiento de cada instrucción:

- FROM maven:3.9.6-eclipse-temurin-21-alpine AS build: define la imagen base inicial que contiene Maven y el JDK 21. La etiqueta “AS build” nombra esta etapa para poder referenciarla más adelante.
- WORKDIR /app: establece el directorio de trabajo dentro del contenedor donde se ejecutarán los comandos.
- COPY pom.xml . Y COPY src ./src: copia los archivos de configuración de dependencias y el código fuente desde el equipo local al contenedor.
- RUN mvn clean package –DskipTests: ejecuta la compilación de maven. Genera el archivo comprimido .jar en la carpeta target. Se omiten los test para optimizar el tiempo de despliegue en la nube.
- FROM eclipse-temurin:21-jdk-alpine: inicia una segunda etapa con una iamgen mucho más pequeña que solo contiene el entorno de ejecución, no el compilador de ni Maven.
- COPY –from=build /app/target/\*.jar app.jar: esta es la línea clave. Copia el archivo .jar construido en la etapa “build” y lo trae a la nueva imagen limpia.
- EXPOSE 8080: informa que el contenedor escuchará en el puerto 8080, que es el estándar de Spring Boot y el esperado por Cloud Run.
- ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]: define el comando definitivo que se ejecutará al iniciar el contenedor.

El concepto de Multi-stage build consiste en dividir el proceso de despliegue en dos fases:

- **Etapa de construcción:** se usan las herramientas pesadas para crear el archivo ejecutable.
- **Etapa de ejecución:** se desecha el anterior y solo se conserva el ejecutable sobre una basae mínima.
- **Ventajas:** reduce el tamaño final de la imagen, el despliegue es más rápido en Google Cloud y otorga una mayor seguridad.

**Paso 2: Creación de la base de datos en GoogleCloud**

Nos dirigimos a Cloud SQL y creamos una nueva instancia en el apartado de comenzar.

![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.002.png)

Seleccionamos MySQL y rellenamos los campos:

- Id de instancia va a ser el identificador de la instancia (útil en un futuro) y no podrá contener símbolos ni letras minúsculas. 
- Contraseña para acceder a la hora de conectarse desde la configuración de la aplicación (guardar en un archivo de texto para usar después). Se recomienda hacer poner una contraseña complicada por seguridad.
- Elegir la zona en la que va a estar alojado el servidor de SQL. En este caso, se recomienda lo más cercano a tu país, siendo cualquier opción de Europa válida para España.

Una vez finalicemos dicho formulario, creamos la instancia. A continuación, vamos al apartado de instancias y seleccionamos la recién creada.

![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.003.png)

Se nos abrirá el panel de configuración y gestión de la instancia donde se encuentra el apartado de bases de datos donde crearemos la necesaria para la aplicación

![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.004.png)

Encontraremos varias bases creadas por defecto y la opción que crear una nueva.

![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.005.png)

Introducimos el nombre de la base el cual será necesario en el archivo de configuración de la aplicación para realizar la conexión

![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.006.png)

**Paso 3: Gestión de repositorio y configuración de perfiles (Spring Profiles)**

Para que el despliegue sea existoso, la aplicación debe ser capaz de distinguir cuándo se está ejecutando en el ordenador del desarrollador y cuándo está en los servidores de Google Cloud. Para ello, se utiliza la potencia de los Spring Profiles.

Dentro de la carpeta src/main/resources, junto con el archivo application.properties, se debe crear un archivo específico para la nube. Este archivo contendrá las credenciales de la base de datos Cloud SQL y los ajustes de Hibernate para producción.

![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.007.png)

Una vez configurado el archivo, se procede a subir el código al repositorio remoto. Es fundamental asegurar que la rama principal (master o main) esté limpia y contenga el Dockerfile creado en el paso anterior. Realizamos un commit y un push en la rama master. Dicho push va es estar sujeto a un trigger creado en el google cloud.

Desde el apartado de repositorios en Cloud Build debemos realizar la conexión con nuestro repositorio. Se puede escoger entre conectar la cuenta completa o escoger únicamente el respositorio del proyecto.

![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.008.png)

En el panel lateral que aparece se selecciona el repositorio que estamos usando online.

![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.009.png)

Acto seguido, seleccionamos el proyecto concreto que queremos subir o, como segunda opción, también podemos escoger todo y después definir cual subir.\
![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.010.png)

Como se observa en la imagen superior, se puede crear un activador durante la conexión del repositorio GitHub, pero en este caso, se va a realizar desde el apartado de activadores donde se presenta mejor el formulario.

![](/docs/images/Aspose.Words.a2e1f265-3ec1-4afe-b25c-a727930b7a81.011.png)En dicho formulario estableceremos la rama en la que queremos situar el activador o “trigger”. Este trigger se dispara cuando se realiza un “push” desde tu IDE al repositorio en GitHub haciendo que el desarrollo y el despliegue sea seguro.

**Paso 4: configuración y despliegue en Google Cloud Run.**

Una vez que el código está en GitHub y el Dockerfile está listo, el siguiente paso es configurar Cloud Run. Este servicio está encargado de recibir la imagen de Docker, ejecutarla y asignarle una dirección URL pública.

Para que el despliegue sea automático, primero debemos configurar el trigger entre Google y GitHub:

- En la consola de GCP, accedemos a Cloud Build > Activadores
- Creamos un nuevo activador vinculado a nuestro repositorio de GitHub
- Seleccionamos el evento de ejecución: “Empujar a una rama” y elegimos master.
- En “Configuración”, seleccionamos Dockerfile, lo que indica a Google que debe usar las instrucciones que escribimos en el paso 1 para construir la imagen.

Con el activador listo, procedemos a configurar el servicio que mantendrá viva la aplicación:

- Vamos a Cloud Run y seleccionamos “Crear servicio”.
- Elegimos la opción “implementar continuamente desde un repositorio” para que cada cambio en el código actualice la web automáticamente.
- Configuración del contenedor en el puerto 8080.
- Establecemos la variable de entorno “SPRING\_PROFILES*\_*ACTIVE” con valor “prod”

