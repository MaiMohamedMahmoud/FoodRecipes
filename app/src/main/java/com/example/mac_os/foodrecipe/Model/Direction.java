
package com.example.mac_os.foodrecipe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Direction {

    @SerializedName("direction_description")
    @Expose
    private String directionDescription;
    @SerializedName("direction_number")
    @Expose
    private String directionNumber;

    public String getDirectionDescription() {
        return directionDescription;
    }

    public void setDirectionDescription(String directionDescription) {
        this.directionDescription = directionDescription;
    }

    public String getDirectionNumber() {
        return directionNumber;
    }

    public void setDirectionNumber(String directionNumber) {
        this.directionNumber = directionNumber;
    }

}
