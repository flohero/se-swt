package swt6.weather.model;


public interface SensorProvider {
    Capability getCapability();
    Sensor createSensor();
}
