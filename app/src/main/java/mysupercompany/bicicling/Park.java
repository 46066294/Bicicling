package mysupercompany.bicicling;

/**
 * Created by Mat on 08/02/2017.
 */

public class Park {

    private String id,
            type,
            latitude,
            longitude,
            streetName,
            streetNumber,
            altitude,
            slots,
            bikes,
            nearbyStations,
            status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public String getBikes() {
        return bikes;
    }

    public void setBikes(String bikes) {
        this.bikes = bikes;
    }

    public String getNearbyStations() {
        return nearbyStations;
    }

    public void setNearbyStations(String nearbyStations) {
        this.nearbyStations = nearbyStations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String textLocaionOnMarker() {
        return  "Tipus de bici: " + type + "\n\tBicis disponibles: " + bikes;
    }

    public float getIndicator(){
        float totalParks = Float.parseFloat(slots) + Float.parseFloat(bikes);
        float indicator = (Float.parseFloat(slots) / totalParks) * 100;
        return indicator;
    }

    @Override
    public String toString() {
        return "Park{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", altitude='" + altitude + '\'' +
                ", slots='" + slots + '\'' +
                ", bikes='" + bikes + '\'' +
                ", nearbyStations='" + nearbyStations + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}