public class Agenda {
    private Contacto[] contactos;
    private int tamano;
    private int cantidadDeContactos;

    // Constructor por defecto con tamaño de 10
    public Agenda() {
        this(10);
    }

    // Constructor con tamaño específico
    public Agenda(int tamano) {
        this.tamano = tamano;
        this.contactos = new Contacto[tamano];
        this.cantidadDeContactos = 0;
    }

    // Añadir un contacto a la agenda
    public void anadirContacto(Contacto c) {
        if (agendaLlena()) {
            System.out.println("La agenda está llena");
            return;
        }
        if (existeContacto(c)) {
            System.out.println("El contacto ya existe en la agenda");
            return;
        }
        contactos[cantidadDeContactos] = c;
        cantidadDeContactos++;
        System.out.println("Contacto agregado: " + c.getNombre() + " " + c.getApellido());
    }

    // Verificar si un contacto ya existe en la agenda
    public boolean existeContacto(Contacto c) {
        for (int i = 0; i < cantidadDeContactos; i++) {
            if (contactos[i].equals(c)) {
                return true;
            }
        }
        return false;
    }

    // Listar todos los contactos de la agenda
    public void listarContactos() {
        if (cantidadDeContactos == 0) {
            System.out.println("La agenda está vacía.");
            return;
        }
        ordenarContactos();
        for (int i = 0; i < cantidadDeContactos; i++) {
            System.out.println(contactos[i]);
        }
    }

    // Ordenar los contactos alfabéticamente por nombre
    public void ordenarContactos() {
        for (int i = 0; i < cantidadDeContactos - 1; i++) {
            for (int j = i + 1; j < cantidadDeContactos; j++) {
                if (contactos[i].getNombre().compareToIgnoreCase(contactos[j].getNombre()) > 0) {
                    Contacto temporal = contactos[i];
                    contactos[i] = contactos[j];
                    contactos[j] = temporal;
                }
            }
        }
    }

    // Buscar un contacto por nombre y apellido
    public void buscaContacto(String nombre, String apellido) {
        for (int i = 0; i < cantidadDeContactos; i++) {
            if (contactos[i].getNombre().equalsIgnoreCase(nombre) && contactos[i].getApellido().equalsIgnoreCase(apellido)) {
                System.out.println("Contacto encontrado:");
                System.out.println(contactos[i]);
                return;
            }
        }
        System.out.println("Contacto no encontrado.");
    }

    // Eliminar un contacto de la agenda
    public void eliminarContacto(Contacto contacto) {
        for (int i = 0; i < cantidadDeContactos; i++) {
            if (contactos[i].getNombre().equalsIgnoreCase(contacto.getNombre()) &&
                    contactos[i].getApellido().equalsIgnoreCase(contacto.getApellido())) {
                for (int j = i; j < cantidadDeContactos - 1; j++) {
                    contactos[j] = contactos[j + 1];
                }
                contactos[cantidadDeContactos - 1] = null;
                cantidadDeContactos--;
                System.out.println("Contacto eliminado exitosamente.");
                return;
            }
        }
        System.out.println("Contacto no encontrado, no se puede eliminar.");
    }

    // Modificar el teléfono de un contacto
    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        for (int i = 0; i < cantidadDeContactos; i++) {
            if (contactos[i].getNombre().equalsIgnoreCase(nombre) &&
                    contactos[i].getApellido().equalsIgnoreCase(apellido)) {
                contactos[i].setTelefono(nuevoTelefono);
                System.out.println("Teléfono modificado exitosamente.");
                return;
            }
        }
        System.out.println("Contacto no encontrado, no se puede modificar.");
    }

    // Verificar si la agenda está llena
    public boolean agendaLlena() {
        return cantidadDeContactos >= tamano;
    }

    // Obtener la cantidad de espacios libres en la agenda
    public int espacioLibres() {
        return tamano - cantidadDeContactos;
    }

    // Verificar si hay contactos en la agenda
    public boolean tieneContactos() {
        return cantidadDeContactos > 0;
    }

    // Obtener todos los contactos
    public Contacto[] getContactos() {
        return contactos;
    }

    // Obtener la cantidad de contactos en la agenda
    public int getCantidadDeContactos() {
        return cantidadDeContactos;
    }
}
