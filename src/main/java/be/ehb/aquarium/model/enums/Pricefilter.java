package be.ehb.aquarium.model.enums;

public enum Pricefilter {
    ASCEDANT("Ascedant"),
    DESCENDANT("Descedant");

    private final String display;

    Pricefilter(String display) {
        this.display = display;
    }

    public String getDisplay(){
        return display;
    }
}
