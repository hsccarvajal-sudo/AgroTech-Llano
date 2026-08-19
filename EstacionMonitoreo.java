import java.util.ArrayList;
import java.util.List;

public class EstacionMonitoreo {
    
    private List<Sensor> sensores;

    public EstacionMonitoreo() {
        sensores = new ArrayList<>();
    }

    public void agregarSensor(Sensor s) {
        sensores.add(s);
    }

    // Procesamiento polimórfico (sin instanceof)
    public void procesarLecturas() {
        System.out.println("\n=== PROCESANDO LECTURAS ===");
        for (Sensor s : sensores) {
            double lectura = s.tomarLectura();
            String estado = s.evaluarEstado();
            System.out.println(s.getId() + " (" + s.getUbicacion() + "): "
                               + String.format("%.2f", lectura) + " - " + estado);
        }
    }

    // Filtrado sin instanceof (gracias a isCritico())
    public void filtrarCriticos() {
        System.out.println("\n=== SENSORES CRITICOS ===");
        for (Sensor s : sensores) {
            if (s.isCritico()) {
                System.out.println("⚠ CRITICO: " + s.getId() 
                                   + " (" + s.getUbicacion() + ")");
            }
        }
    }

    // MAIN
    public static void main(String[] args) {
        EstacionMonitoreo estacion = new EstacionMonitoreo();

        // Registrar sensores
        estacion.agregarSensor(new SensorHumedadSuelo("H001", "Lote Norte"));
        estacion.agregarSensor(new SensorTemperatura("T001", "Lote Sur"));
        estacion.agregarSensor(new SensorHumedadSuelo("H002", "Lote Este"));
        estacion.agregarSensor(new SensorTemperatura("T002", "Lote Oeste"));

        // Procesar
        estacion.procesarLecturas();
        estacion.filtrarCriticos();
    }
}
