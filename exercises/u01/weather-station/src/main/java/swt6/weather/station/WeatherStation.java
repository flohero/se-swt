package swt6.weather.station;

import swt6.weather.model.Measurement;

import java.util.List;

public interface WeatherStation extends AutoCloseable{
    List<Measurement> getMeasurements();

    Measurement getCurrentMeasurement();

    double getAverage();
}
