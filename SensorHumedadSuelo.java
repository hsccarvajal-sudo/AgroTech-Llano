public class SensorHumedadSuelo extends Sensor {
    
    public SensorHumedadSuelo(String id, String ubicacion) {
        super(id, ubicacion);
    }

    @Override
    public double tomarLectura() {
        // Simula 0% - 100%
        return Math.random() * 100;
    }

    @Override
    public String evaluarEstado() {
        double lectura = tomarLectura();
        if (lectura < 20) {
            return "CRITICO (sequía)";
        } else if (lectura < 40) {
            return "ALERTA (baja humedad)";
        } else if (lectura > 80) {
            return "ALERTA (exceso de agua)";
        } else {
            return "NORMAL";
        }
    }

    @Override
    public boolean isCritico() {
        return tomarLectura() < 20;
    }
}
