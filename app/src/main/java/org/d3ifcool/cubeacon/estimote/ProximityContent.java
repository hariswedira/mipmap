package org.d3ifcool.cubeacon.estimote;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class ProximityContent {

    private String title;
    private String subtitle;

    public ProximityContent(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    String getTitle() {
        return title;
    }

    String getSubtitle() {
        return subtitle;
    }
}
