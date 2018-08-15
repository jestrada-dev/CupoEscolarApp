package co.jestrada.cupoescolarapp.location.constant;

public class ConstantsMap {
    public static final int MAP_ZOOM = 17;          // limit -> 21 -> altura de la cámara
    public static final int MAP_TILT = 30;          // 0 - 365 -> relieve de los edificios
    public static final int MAP_BEARING = 90;       // limit -> 90 -> ángulo de la cámara

    public static final int MAP_ZOOM_MIN = 14;
    public static final int MAP_ZOOM_MAX = 17;

    public static final int REQUEST_FINE_LOCATION = 1001;
    public static final int PERMISISION_FINE_LOCATION_GRANTED = 0;
    public static final int PERMISISION_FINE_LOCATION_DENIED = -1;

}
