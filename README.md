Proyecto: Sistema de Gestión de Agenda Telefónica
Descripción del Proyecto
Este proyecto implementa un sistema de gestión de una agenda telefónica mediante un menú interactivo en consola. La agenda permite agregar, buscar, listar, modificar y eliminar contactos, siguiendo ciertas restricciones y validaciones.

Definición de un Contacto
Cada contacto en la agenda está definido por:

Nombre
Apellido
Teléfono (No se valida el formato del teléfono)
Un contacto se considera duplicado si tiene el mismo nombre y apellido, sin importar el teléfono y sin distinguir entre mayúsculas y minúsculas.

Características de la Agenda
Se puede crear con un tamaño máximo especificado o con un tamaño por defecto de 10 contactos.
Los nombres y apellidos no pueden estar vacíos.
No se pueden añadir contactos duplicados.
Funcionalidades
añadirContacto(Contacto c):

Añade un contacto a la agenda.
Verifica que no existan contactos duplicados.
No permite agregar contactos si el nombre o apellido están vacíos.
Si la agenda está llena, muestra un mensaje al usuario.
existeContacto(Contacto c):

Comprueba si un contacto ya existe en la agenda.
listarContactos():

Muestra todos los contactos en el formato:
nginx
Copiar
Nombre Apellido - Teléfono
Ordena los contactos alfabéticamente por nombre y apellido.
buscaContacto(String nombre, String apellido):

Permite buscar un contacto por nombre y apellido.
Si el contacto existe, muestra el teléfono.
Si no se encuentra, muestra un mensaje indicando que no existe.
eliminarContacto(Contacto c):

Elimina un contacto de la agenda.
Informa si la eliminación fue exitosa o si el contacto no existe.
modificarTelefono(String nombre, String apellido, String nuevoTelefono):

Permite modificar el teléfono de un contacto existente.
Si el contacto no existe, muestra un mensaje.
agendaLlena():

Indica si la agenda está llena.
espaciosLibres():

Muestra cuántos contactos más se pueden agregar a la agenda según su tamaño máximo.
Cómo Ejecutar el Programa
Compilar el código fuente en Java:

Abre una terminal o consola.
Navega al directorio donde se encuentra el archivo .java.
Ejecuta el comando para compilar:
bash
Copiar
javac Agenda.java
Ejecutar el programa:

Para ejecutar el programa, utiliza el siguiente comando en la terminal:
bash
Copiar
java Agenda
Interacción con el menú:

El programa te presentará un menú interactivo en la consola, donde podrás gestionar los contactos de la agenda.
Tecnologías Utilizadas
Lenguaje de programación: Java
Entrada y salida de datos: Consola
