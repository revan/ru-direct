package me.rutgersdirect.rudirect;

import java.util.HashMap;

public class BusConstants {
    // Nextbus API links
    public static final String VEHICLE_LOCATIONS_LINK = "http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=vehicleLocations";
    public static final String ALL_ROUTES_LINK = "http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=routeConfig";
    public static final String PREDICTIONS_LINK = "http://webservices.nextbus.com/service/publicXMLFeed?a=rutgers&command=predictionsForMultiStops";
    // Intent messages
    public static final String BUS_TAG_MESSAGE = "Bus Tag";
    public static final String BUS_STOP_TITLES_MESSAGE = "Bus Stop Titles";
    public static final String BUS_STOP_TIMES_MESSAGE = "Bus Stop Times";
    // All bus names and tags
    public static final String[] allBusNames = {"A", "B", "C", "EE", "F", "H", "LX", "Rex B", "Rex L", "Weekend 1", "Weekend 2", "Brunsquick Shuttle 1", "Brunsquick Shuttle 2"};
    public static final String[] allBusTags = {"a", "b", "c", "ee", "f", "h", "lx", "rexb", "rexl", "wknd1", "wknd2", "w1", "w2"};
    // Caching of bus names to tags (and vice versa) and tags/titles to stops
    public static HashMap<String, String> TAGS_TO_BUSES;
    public static HashMap<String, String> BUSES_TO_TAGS;
    public static HashMap<String, String[]> TAGS_TO_STOPS;
    public static HashMap<String, String[]> TITLES_TO_STOPS;
}