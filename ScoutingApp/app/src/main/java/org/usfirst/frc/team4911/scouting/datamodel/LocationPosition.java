package org.usfirst.frc.team4911.scouting.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Data object that stores a location position. Coordinates are measured in feet and the zero
 * point is the boiler of the team the robot belongs to.
 */

public class LocationPosition {
    // Normalized shot X coordinate in feet from boiler
    @SerializedName("XInFeet") private float xInFeet = 0;

    // Normalized shot Y coordinate in feet from boiler
    @SerializedName("YInFeet") private float yInFeet = 0;

    public LocationPosition(float xIinFeet, float yInFeet)
    {
        this.setXInFeet(xIinFeet);
        this.setYInFeet(yInFeet);
    }
    public float getXInFeet() { return xInFeet; }

    public void setXInFeet(float XInFeet) { this.xInFeet = XInFeet; }

    public float getYInFeet() { return yInFeet; }

    public void setYInFeet(float YInFeet) { this.yInFeet = YInFeet; }

    public static LocationPosition getInvalidPosition() {
        return new LocationPosition(-1, -1);
    }

    /**
     * We want equals to compare the position not the object reference.
     *
     * @param obj The LocationPosition to check the equality of.
     * @return True if the two locationpositions describe the same position, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LocationPosition)) {
            return false;
        }

        LocationPosition positionAsObject = (LocationPosition) obj;
        return positionAsObject.getXInFeet() == this.getXInFeet()
                && positionAsObject.getYInFeet() == this.getYInFeet();
    }

    @Override
    public String toString() {
        return "X: " + getXInFeet() + " Y: " + getYInFeet();
    }
}
