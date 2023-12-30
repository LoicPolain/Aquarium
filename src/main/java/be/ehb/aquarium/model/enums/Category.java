package be.ehb.aquarium.model.enums;

public enum Category {
    KORAAL("Koraal"),
    AQUARIUMS("Aquariums"),
    TROPISCHE_VISSEN("Tropische vissen"),
    VOEDING_VISSEN("Voeding vissen"),
    DECORATIE("Decoratie");

    private final String display;

    Category(String display) {
        this.display = display;
    }

    public String getDisplay(){
        return display;
    }
}
