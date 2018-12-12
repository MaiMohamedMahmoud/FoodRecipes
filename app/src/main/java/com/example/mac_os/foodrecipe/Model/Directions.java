
package com.example.mac_os.foodrecipe.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Directions {

    @SerializedName("direction")
    @Expose
    private List<Direction> direction = null;

    public List<Direction> getDirection() {
        return direction;
    }

    public void setDirection(List<Direction> direction) {
        this.direction = direction;
    }

}
