package com.mycodefu.dashboard.light;

import com.mycodefu.afterburner.views.FXMLView;

public class LightView extends FXMLView {
    public LightView(int red, int green, int blue, LightClickHandler lightClickHandler) {
        super(key -> switch (key) {
            case "red" -> red;
            case "green" -> green;
            case "blue" -> blue;
            case "lightClickHandler" -> lightClickHandler;
            default -> null;
        });
    }
}
