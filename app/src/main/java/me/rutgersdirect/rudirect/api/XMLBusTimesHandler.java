package me.rutgersdirect.rudirect.api;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
import java.util.ArrayList;

import me.rutgersdirect.rudirect.data.constants.RUDirectApplication;
import me.rutgersdirect.rudirect.data.model.BusData;
import me.rutgersdirect.rudirect.data.model.BusStop;
import me.rutgersdirect.rudirect.data.model.BusStopTime;

public class XMLBusTimesHandler extends DefaultHandler {

    private static final String TAG = XMLBusTimesHandler.class.getSimpleName();
    private BusData busData;
    private BusStop[] busStops;
    private String busTag;
    private boolean inBusTag;
    private int currentStopIndex;
    private ArrayList<BusStopTime> busStopTimes;

    public XMLBusTimesHandler(String busTag) {
        this.busTag = busTag;
    }

    public void startDocument() throws SAXException {
        busData = RUDirectApplication.getBusData();
        busStops = busData.getBusTagToBusStops().get(busTag);
        inBusTag = false;
        currentStopIndex = 0;
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
            throws SAXException {
        if (qName.equalsIgnoreCase("predictions")) {
            inBusTag = true;
            busStopTimes = new ArrayList<>();
        }
        if (inBusTag && qName.equalsIgnoreCase("prediction")) {
            BusStopTime time = new BusStopTime(Integer.parseInt(atts.getValue("minutes")),
                    Integer.parseInt(atts.getValue("vehicle")));
            busStopTimes.add(time);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (inBusTag && qName.equalsIgnoreCase("predictions")) {
            inBusTag = false;

            if (busStopTimes.size() == 0) {
                busStopTimes.add(new BusStopTime(-1)); // Offline bus
            }

            busStops[currentStopIndex].setTimes(busStopTimes);
            currentStopIndex++;
        }
    }

    public void endDocument() throws SAXException {
        // Update bus data
        try {
            RUDirectApplication.getDatabaseHelper().getDao().createOrUpdate(busData);
        } catch (SQLException e) {
            Log.e(TAG, e.toString(), e);
        }
    }
}