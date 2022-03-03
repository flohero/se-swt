module swt.weather.station {
    uses swt6.weather.model.SensorProvider;
    requires swt.weather.model;
    exports swt6.weather.station;
    requires swt.weather.temperature.sensor;
}