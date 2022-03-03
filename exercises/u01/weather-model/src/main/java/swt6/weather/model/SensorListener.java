package swt6.weather.model;

import java.util.EventListener;

public interface SensorListener extends EventListener {
    void sendMeasurement(MeasurementEvent measurementEvent);
}
