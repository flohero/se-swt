package swt6.weather.model;

public interface Sensor extends AutoCloseable{
    void addClient(SensorListener sensorListener);

    void removeClient(SensorListener sensorListener);

    void startMeasuring();

    void stopMeasuring();

}
