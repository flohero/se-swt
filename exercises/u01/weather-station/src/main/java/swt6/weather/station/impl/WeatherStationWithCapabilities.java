package swt6.weather.station.impl;

import swt6.weather.model.*;
import swt6.weather.station.WeatherStation;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CopyOnWriteArrayList;

public class WeatherStationWithCapabilities implements WeatherStation {
    private final List<Measurement> measurements = new CopyOnWriteArrayList<>();
    private final List<Sensor> sensors = new CopyOnWriteArrayList<>();

    public WeatherStationWithCapabilities(List<Capability> capabilities) {
        addSensors(capabilities);
    }

    @Override
    public List<Measurement> getMeasurements() {
        return new ArrayList<>(measurements);
    }

    @Override
    public Measurement getCurrentMeasurement() {
        return measurements.get(measurements.size() - 1);
    }

    @Override
    public double getAverage() {
        return measurements.stream()
                .mapToDouble(Measurement::value)
                .average()
                .orElse(0.0);

    }

    @Override
    public void close() throws Exception {
        for (Sensor sensor : sensors) {
            sensor.close();
        }
    }

    private void sendMeasurement(MeasurementEvent measurementEvent) {
        measurements.add(measurementEvent.getMeasurement());
    }

    private void addSensors(List<Capability> capabilities) {
        ServiceLoader<SensorProvider> sl = ServiceLoader.load(SensorProvider.class);
        for (var provider : sl) {
            if (capabilities.contains(provider.getCapability())) {
                var sensor = provider.createSensor();
                sensors.add(sensor);
                sensor.addClient(this::sendMeasurement);
                sensor.startMeasuring();
            }
        }
    }


}
