package swt6.weather.station;

import swt6.weather.model.Capability;
import swt6.weather.station.impl.WeatherStationWithCapabilities;

import java.util.List;

public class WeatherStationFactory {
    public static WeatherStation createWeatherStation(List<Capability> capabilities) {
        return new WeatherStationWithCapabilities(capabilities);
    }
}
