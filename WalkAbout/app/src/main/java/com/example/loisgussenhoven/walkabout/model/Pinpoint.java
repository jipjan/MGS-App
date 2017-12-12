package com.example.loisgussenhoven.walkabout.model;

import java.util.List;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public class Pinpoint implements ICloneable<Pinpoint> {
    private String name, information, author;
    private int year;
    private List<Integer> images;
    private long longitude, latitude;
    private boolean hasBeenVisitedOrSkipped;

    public Pinpoint(String name, String information, String author, int year, List<Integer> images, long longitude, long latitude, boolean hasBeenVisitedOrSkipped) {
        this.name = name;
        this.information = information;
        this.author = author;
        this.year = year;
        this.images = images;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hasBeenVisitedOrSkipped = hasBeenVisitedOrSkipped;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public List<Integer> getImages() {
        return images;
    }

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public boolean isHasBeenVisitedOrSkipped() {
        return hasBeenVisitedOrSkipped;
    }

    public void setHasBeenVisitedOrSkipped(boolean hasBeenVisitedOrSkipped) {
        this.hasBeenVisitedOrSkipped = hasBeenVisitedOrSkipped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pinpoint pinpoint = (Pinpoint) o;

        if (year != pinpoint.year) return false;
        if (longitude != pinpoint.longitude) return false;
        if (latitude != pinpoint.latitude) return false;
        if (hasBeenVisitedOrSkipped != pinpoint.hasBeenVisitedOrSkipped) return false;
        if (name != null ? !name.equals(pinpoint.name) : pinpoint.name != null) return false;
        if (information != null ? !information.equals(pinpoint.information) : pinpoint.information != null)
            return false;
        if (author != null ? !author.equals(pinpoint.author) : pinpoint.author != null)
            return false;
        return images != null ? images.equals(pinpoint.images) : pinpoint.images == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (information != null ? information.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (int) (longitude ^ (longitude >>> 32));
        result = 31 * result + (int) (latitude ^ (latitude >>> 32));
        result = 31 * result + (hasBeenVisitedOrSkipped ? 1 : 0);
        return result;
    }

    @Override
    public Pinpoint clone() {
        return new Pinpoint(name, information, author, year, images, longitude, latitude, hasBeenVisitedOrSkipped);
    }
}
