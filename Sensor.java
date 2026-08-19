public abstract class Sensor {
    // Atributos privados (encapsulamiento)
    private String id;
    private String ubicacion;
    private boolean activo;

    // Constructor
    public Sensor(String id, String ubicacion) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.activo = true;
    }

    // Getters (sin setters para id y ubicacion → inmutables)
    public String getId() {
        return id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public boolean isActivo() {
        return activo;
    }

    // Setter solo para activo
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Métodos abstractos (los implementan las subclases)
    public abstract double tomarLectura();
    public abstract String evaluarEstado();
    public abstract boolean isCritico();
}
