
package com.example.loisgussenhoven.walkabout.controller.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bounds {

    @SerializedName("northeast")
    @Expose
    public Northeast northeast;
    @SerializedName("southwest")
    @Expose
    public Southwest southwest;

}
