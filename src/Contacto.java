public class Contacto {
    private String nombre;
    private String apellido;
    private String telefono;

    public Contacto(String nombre, String apellido, String telefono) {
        this.nombre = (nombre != null) ? nombre.trim() : "";
        this.apellido = (apellido != null) ? apellido.trim() : "";
        this.telefono = (telefono != null) ? telefono.trim() : "";
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", nombre, apellido, telefono);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Contacto) {
            Contacto other = (Contacto) obj;
            return this.nombre.equalsIgnoreCase(other.nombre) && this.apellido.equalsIgnoreCase(other.apellido);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (nombre.toLowerCase() + apellido.toLowerCase()).hashCode();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

