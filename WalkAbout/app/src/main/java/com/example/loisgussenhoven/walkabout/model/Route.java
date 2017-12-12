package com.example.loisgussenhoven.walkabout.model;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public class Route implements ICloneable<Route> {
    private String name, information;

    public Route(String name, String information) {
        this.name = name;
        this.information = information;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (name != null ? !name.equals(route.name) : route.name != null) return false;
        return information != null ? information.equals(route.information) : route.information == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (information != null ? information.hashCode() : 0);
        return result;
    }

    @Override
    public Route clone() {
        return new Route(name, information);
    }
}
