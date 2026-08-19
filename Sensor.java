public abstract class Sensor {
    // Atributos privados (encapsulamiento)
    private String id;
    private String ubicacion;
    private boolean activo;

    
    public Sensor(String id, String ubicacion) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.activo = true;
    }

    // Getters sin setters es inmutables
    public String getId() {
        return id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public boolean isActivo() {
        return activo;
    }

    // Setter para activo
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Métodos abstractos
    public abstract double tomarLectura();
    public abstract String evaluarEstado();
    public abstract boolean isCritico();
}
