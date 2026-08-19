public class SensorTemperatura extends Sensor {
    
    public SensorTemperatura(String id, String ubicacion) {
        super(id, ubicacion);
    }

    @Override
    public double tomarLectura() {
        // Simula 15°C - 45°C
        return 15 + Math.random() * 30;
    }

    @Override
    public String evaluarEstado() {
        double lectura = tomarLectura();
        if (lectura > 38) {
            return "CRITICO (calor extremo)";
        } else if (lectura < 5) {
            return "CRITICO (helada)";
        } else if (lectura > 32) {
            return "ALERTA (alta temperatura)";
        } else {
            return "NORMAL";
        }
    }

    @Override
    public boolean isCritico() {
        double temp = tomarLectura();
        return temp > 38 || temp < 5;
    }
}
