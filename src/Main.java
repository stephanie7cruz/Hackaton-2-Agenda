import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Agenda agendaTelefonica;
    private static final Scanner entradaUsuario;

    public Main() {
    }

    public static void main(String[] args) {
        inicializarAgenda();

        int seleccion;
        do {
            mostrarMenu();
            seleccion = entradaUsuario.nextInt();
            entradaUsuario.nextLine();  // Limpiar buffer
            ejecutarAccion(seleccion);
        } while (seleccion != 8);

        entradaUsuario.close();
    }

    private static void inicializarAgenda() {
        System.out.print("¿Cuál es el tamaño máximo de la agenda? (Presiona Enter para usar el tamaño por defecto de 10): ");
        String input = entradaUsuario.nextLine();
        agendaTelefonica = input.isEmpty() ? new Agenda() : new Agenda(Integer.parseInt(input));
    }

    private static void mostrarMenu() {
        System.out.println("\n*** Menú de Gestión de Contactos ***");
        System.out.println("1. Añadir un Contacto");
        System.out.println("2. Buscar un Contacto");
        System.out.println("3. Listar todos los Contactos");
        System.out.println("4. Eliminar un Contacto");
        System.out.println("5. Actualizar Teléfono de Contacto");
        System.out.println("6. Verificar si la agenda está llena");
        System.out.println("7. Consultar espacios disponibles");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void ejecutarAccion(int seleccion) {
        switch (seleccion) {
            case 1:
                agregarContacto();
                break;
            case 2:
                buscarContacto();
                break;
            case 3:
                agendaTelefonica.listarContactos();
                break;
            case 4:
                eliminarContacto();
                break;
            case 5:
                actualizarTelefono();
                break;
            case 6:
                mostrarEstadoAgendaLlena();
                break;
            case 7:
                mostrarEspaciosDisponibles();
                break;
            case 8:
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("Opción no válida, por favor intente de nuevo.");
        }
    }

    private static void agregarContacto() {
        Contacto nuevoContacto = solicitarInformacionContacto(true);
        if (nuevoContacto != null) {
            agendaTelefonica.agregarContacto(nuevoContacto);
        }
    }

    private static void buscarContacto() {
        if (!agendaTelefonica.hayContactos()) {
            System.out.println("La agenda está vacía.");
        } else {
            Contacto c = solicitarInformacionContacto(false);
            if (c != null) {
                agendaTelefonica.buscarContacto(c.getNombre(), c.getApellido());
            }
        }
    }

    private static void eliminarContacto() {
        if (!agendaTelefonica.hayContactos()) {
            System.out.println("La agenda está vacía.");
        } else {
            Contacto c = solicitarInformacionContacto(false);
            if (c != null) {
                agendaTelefonica.eliminarContacto(c);
            }
        }
    }

    private static void actualizarTelefono() {
        if (!agendaTelefonica.hayContactos()) {
            System.out.println("La agenda está vacía.");
        } else {
            Contacto c = solicitarInformacionContacto(false);
            if (c != null) {
                System.out.print("Introduzca el nuevo número de teléfono: ");
                String telefonoNuevo = entradaUsuario.nextLine();
                agendaTelefonica.modificarTelefono(c.getNombre(), c.getApellido(), telefonoNuevo);
            }
        }
    }

    private static void mostrarEstadoAgendaLlena() {
        if (agendaTelefonica.agendaLlena()) {
            System.out.println("La agenda está completamente llena.");
        } else {
            System.out.println("Aún hay espacio disponible en la agenda.");
        }
    }

    private static void mostrarEspaciosDisponibles() {
        System.out.println("Espacios restantes en la agenda: " + agendaTelefonica.espacioLibres());
    }

    private static Contacto solicitarInformacionContacto(boolean incluirTelefono) {
        try {
            System.out.print("Ingrese el nombre del contacto: ");
            String nombre = entradaUsuario.nextLine();
            System.out.print("Ingrese el apellido del contacto: ");
            String apellido = entradaUsuario.nextLine();
            if (!nombre.isEmpty() && !apellido.isEmpty()) {
                String telefono = incluirTelefono ? obtenerTelefono() : "";
                return new Contacto(nombre, apellido, telefono);
            } else {
                System.out.println("El nombre y el apellido no pueden estar vacíos.");
                return null;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Los datos ingresados no son válidos.");
            return null;
        }
    }

    private static String obtenerTelefono() {
        System.out.print("Ingrese el teléfono: ");
        return entradaUsuario.nextLine();
    }

    static {
        entradaUsuario = new Scanner(System.in);
    }
}
