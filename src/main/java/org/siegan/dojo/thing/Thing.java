package org.siegan.dojo.thing;

public class Thing {
    private String name;
    private String appearance;

    public Thing(String name, String appearance){
        this.appearance = appearance;
        this.name = name;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
