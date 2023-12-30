package be.ehb.aquarium.model.enums;

public enum Pricefilter {
    ASC("Ascedant"),
    DESC("Descedant");

    private final String display;

    Pricefilter(String display) {
        this.display = display;
    }

    public String getDisplay(){
        return display;
    }
}
