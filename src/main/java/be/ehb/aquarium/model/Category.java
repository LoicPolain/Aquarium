package be.ehb.aquarium.model;

public enum Category {
    KORAAL("Koraal"),
    AQUARIUMS("Aquariums"),
    TROPISCHE_VISSEN("Tropische vissen"),
    VOEDING_VISSEN("Voeding vissen");

    private final String display;

    Category(String display) {
        this.display = display;
    }

    public String getDisplay(){
        return display;
    }
}
