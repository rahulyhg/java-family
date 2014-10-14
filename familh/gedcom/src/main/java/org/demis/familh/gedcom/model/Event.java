package org.demis.familh.gedcom.model;

public class Event implements PlaceContainer, AddressContainer {

    private String type;

    private Place place;

    private GEDCOMDate eventDate = null;

    private Address address;

    public Event() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setEventDate(GEDCOMDate eventDate) {
        this.eventDate = eventDate;
    }

    public GEDCOMDate getEventDate() {
        return eventDate;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void setAddress(Address address) {
        this.address = address;
    }
}
