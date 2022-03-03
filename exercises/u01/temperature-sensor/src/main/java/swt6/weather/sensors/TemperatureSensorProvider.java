package swt6.weather.sensors;

import swt6.weather.model.Capability;
import swt6.weather.model.Sensor;
import swt6.weather.model.SensorProvider;

public class TemperatureSensorProvider implements SensorProvider {
    @Override
    public Capability getCapability() {
        return Capability.TEMPERATURE;
    }

    @Override
    public Sensor createSensor() {
        return new TemperatureSensor();
    }
}
