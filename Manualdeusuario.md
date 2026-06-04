### Manual de usuario
Guía oficial de la aplicación Gestor de Biblioteca Escolar.

**Inicio**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.001.png)En la ventana de inicio se encuentra una cabecera con los distintos apartados principales de la aplicación: Usuarios, Libros, Préstamos e Iniciar Sesión. A mayores, texto de Gestor de Biblioteca Escolar es un enlace que nos redirige siempre a esta página. 

El contenido principal de la página está compuesto por una API propia que muestra las cifras de los libros activos, los prestamos activos y no devueltos y los usuarios que están asociados a la biblioteca y son activos. Dichas cifras están actualizadas en todo momento. Inmediatamente debajo podemos encontrar la documentación teórica de la aplicación: diagramas de casos de uso, modelo de datos, requisitos mínimos, etc... Por último, en el pie de página podemos encontrar el nombre del autor y tres enlaces diferentes referenciados a: al repositorio en GitHub, la documentación generada por JavaDoc y el pdf de la documentación oficial del TFG.

Para acceder a la zona restringida deberemos iniciar sesión con una cuenta de usuario bibliotecario o administrador, completando el formulario de login con el username y la password. Si se intenta acceder a la zona restringida sin autenticarse, se redirigirá automáticamente al login. En caso de introducir datos incorrectos se mostrará un mensaje de error especificando el motivo.

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.002.png)Este es el formulario de login en el cual se deben introducir las credenciales de usuarios autorizados.

**Usuarios**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.003.png)Este es el mantenimiento de usuarios donde se encuentran y gestionan todos los usuarios del sistema. El contenido principal de la página es una tabla donde se listan los usuarios de forma paginada y de cinco en cinco. Dicho listado se puede modificar y buscar de forma personalizada usando el buscador con filtro que encontramos encima de la tabla. Dicho buscador diferencia DNI y nombre, y filtra por el estado de los usuarios siendo, activos, baja, suspendidos y todos.

Por encima del filtro tenemos un botón en el que pone “Nuevo Usuario”. Es el encargado abrir el formulario de registro de usuario.

**Registro de Usuario**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.004.png)Este es el formulario de registro de usuario en el cual deberemos rellenar los siguientes campos:

- DNI el cual es clave única útil para buscar a un usuario mediante y validado para que cumpla el patrón establecido de DNI español.
- Nombre del usuario.
- Primer apellido del usuario.
- Segundo apellido del usuario.
- Rol para escoger entre alumno, profesor, bibliotecario o administrador.
  - En caso de escoger o bibliotecario o administrador se desplegarán dos apartados más: username y password para establecer unas credenciales de acceso.

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.005.png)

En caso de haber introducido algún dato incorrecto el formulario nos avisará mediante mensajes del error o errores concretos.

**Tabla de usuarios**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.006.png)Cada fila representa a un usuario guardado en la base de datos y cada columna muestra un dato distinto siendo DNI, Nombre, Primer Apellido, Segundo Apellido, Username (en caso de tenerlo), Rol y Estado. La última columna está reservada para las acciones que se pueden realizar sobre el usuario. Dichas acciones son: Editar y Dar de baja las cuales son funcionales para todos los usuarios y consultar que solo está disponible para los usuarios de tipo Profesor y Alumno ya que son los únicos que pueden realizar préstamos.

**Editar usuarios**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.007.png)Este es el formulario de edición de usuario, exactamente igual que el registro de usuarios. En caso de cambiar el rol del usuario o ser originalmente un bibliotecario o un administrador aparecerán los campos de username y password.

**Botón de baja / Rehabilitar**

En caso de querer dar de baja a un usuario por cualquier motivo, se debe pulsar el botón de “Dar de baja”. Este cambiará el estado del usuario y cambiará su texto y funcionalidad ya que aparecerá un botón con el texto “Rehabilitar” y, al ser pulsado, reactivará al usuario en cuestión.

**Consultar usuario**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.008.png)En la página de consulta de usuario podemos encontrar un cuadro con todos los datos del usuario especificados y el estado en el que se encuentra al igual que en la tabla de mantenimiento. Cabe destacar que, en el caso de que el usuario se encuentre suspendido por haber devuelto un préstamo fuera de plazo, aparecerá un botón al lado del estado el cual nos permitirá levantar la suspensión al usuario para que pueda volver a solicitar préstamos.

Debajo del cuadro se encuentra un listado con todos los préstamos realizados por el usuario pudiendo pulsar el botón de consultar y dirigirse a la consulta de préstamo.

**Libros**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.009.png)Esto es el mantenimiento de libros donde se encuentran y gestionan todos los libros y ejemplares guardados en el sistema. El contenido principal de la página es una tabla en la que se listan los libros según el criterio establecido en el buscador con filtro. Igual que en el mantenimiento de usuarios, el buscador es capaz de diferenciar entre ISBN, título y autor aparte de poder filtrar por activos, inactivos o todos. 

**Registrar usuario**

Pulsando el botón superior “Nuevo libro” se accede al formulario de registro de libro.

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.010.png)

En dicho formulario se deben rellenar los campos de forma obligatoria. Todos ellos están validados para no recibir ningún valor incorrecto y, a mayores, el campo de ISBN está validado con el patrón de ISBN 10 e ISBN 13. En caso de haber algún error se mostrará un mensaje explicativo debajo del campo correspondiente o en la parte superior de la tarjeta del formulario.

**Exportar e importar libros**

Volviendo de nuevo al mantenimiento de libros, se puede ver un apartado de exporta e importado de libros el cual exporta todo el listado en csv e importa el mismo tipo de archivo. En caso de haber algún problema durante la importación se mostrará un mensaje de error explicando el motivo y deteniendo la acción.

**Tabla de libros**

Como en todos los mantenimientos, tenemos una tabla en la que cada fila representa un libro y las columnas muestran cada valor siendo en este caso: título, autor, genero, editorial e ISBN. En la columna de acciones se puede dar de baja el libro en caso de no tenerlo en la biblioteca o no tener ejemplares de este. También se puede editar el libro abriendo un formulario idéntico al del registro, pero con los campos completados con los valores actuales del libro. Esta tabla también esta paginada de cinco en cinco libros.

**Editar Libro**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.011.png)Cabe resaltar que todo aquel campo que no se modifique mantendrá el valor antiguo. También se puede decir que el ISBN es un campo identificativo y, en el caso de introducir un ISBN ya existente en el sistema, no se podrá completar la modificación saltando un error en el proceso.

**Consultar libro**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.012.png)En los detalles de un libro podemos encontrar la información en un cuadro y debajo todos los ejemplares del libro. Hay que decir que se encuentran todos los ejemplares pudiendo dar de baja a aquellos que, por perdida, rotura o cualquier otro motivo ya no está disponible en la biblioteca. 

Pulsando el botón de “Registrar ejemplar” se creará un nuevo ejemplar el cual guarda en el campo “Código” el ISBN del libro al que pertenece junto con el id de dicho ejemplar en la base de datos.

Todos aquellos ejemplares que están disponibles se pueden dar de baja o reactivar. Todos aquellos ejemplares ocupados tienen un botón de consulta de préstamo que, al igual que pasaba en detalle de usuario, nos lleva al detalle del préstamo al que pertenece.

**Mantenimiento de préstamos**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.013.png)El mantenimiento de préstamos muestra y gestiona todos los préstamos guardados en el sistema siendo el corazón principal de la operación principal de esta aplicación. En el podemos registrar un nuevo préstamo pulsando el botón azul “Nuevo préstamo”.

**Registro de préstamo**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.014.png)Este es el formulario de registro de préstamo y tiene mucho de qué hablar. Campo a campo estas son las características de un préstamo:

- En el select de usuario se encuentran todos los usuarios disponibles para hacer un préstamo. Para que un usuario pueda realizar un préstamo tiene que cumplir los siguientes requisitos:
  - Estar en estado activo. Si está de baja es porque ya no pertenece a la biblioteca y si está suspendido es por una falta disciplinaria.
  - Tener menos de 5 préstamos activos. Es decir, un usuario no puede tener más de cinco préstamos activos a la vez. Si quiere iniciar un nuevo préstamo en esa situación tiene que finalizar uno antes.
- En el select de ejemplar se encuentran los ejemplares disponibles del sistema. La forma de diferenciarlos es por el ISBN para saber el libro y el id del ejemplar lo que conforma el código identificativo de éste.
- Fecha de inicio y fecha de fin son dos campos fijos y no se pueden editar dado que la lógica de negocio es clara: el préstamo se inicia en el día que se realiza el préstamo y el límite de duración es hasta 5 días hábiles ya que no sería justo contar los fines de semana.

**Tabla de préstamos**

Al igual que en el resto de los mantenimientos de la aplicación, los préstamos se muestran en una tabla filtrada por búsqueda y por estado. La búsqueda puede ser por nombre del usuario o por el código del ejemplar prestado. En la tabla se muestran por columnas los siguientes datos: usuario, libro, ejemplar(código), fecha de inicio, fecha de fin, fecha de devolución y estado. Dicho estado se actualiza cada vez que se entra en el mantenimiento comparando la fecha actual con la fecha de fin para comprobar si está fuera de fecha o no.

**Editar préstamo**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.015.png)Se muestran los valores del préstamo en cada campo y en caso de no modificar un valor se queda como estaba. No se podrán seleccionar usuarios o ejemplares que no reunen las condiciones para realizar el préstamo.

**Consultar préstamo**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.016.png)En la página de consulta de préstamo se muestran los datos del usuario y del ejemplar en dos tarjetas. Más abajo podemos encontrar el método de finalizado de préstamo. Para comprobar la identidad del ejemplar devuelto, el bibliotecario/administrador deberá introducir el código del ejemplar devuelto dentro del campo. Si el código es correcto, se finaliza el préstamo, pero si no lo es no podrá finalizarse hasta que aparezca dicho ejemplar. Durante la finalización del préstamo se comprueban las fechas de fin y de devolución para poder establecer si se ha devuelto a tiempo o no y si el usuario debe ser suspendido o no.

**Perfil autenticado**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.017.png)

Cuando un usuario está autenticado en el sistema tendrá en todo momento este desplegable en la esquina superior derecha de la página. En él podemos encontrar dos opciones: mi perfil, que nos redirige a la página de edición de datos y credenciales y cerrar sesión que simplemente cierra la sesión y redirige al inicio de la aplicación.

**Editar perfil**

![](/docs/images/Aspose.Words.c8c5efbc-24cd-49e8-b24a-f20e28cf4043.018.png)Este es el formulario de edición del perfil autenticado en la aplicación donde puede modificar únicamente su nombre y apellidos y la contraseña para entrar en la aplicación. El cambio de contraseña está sujeto a una medida de seguridad mínima: introducirla dos veces para garantizar que se ha escrito correctamente y el usuario pueda confirmarlo.

