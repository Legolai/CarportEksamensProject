package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public enum RoofType {
    FLAT("fladt tag"),
    SLOPE("rejsning");
    private final String value;
    RoofType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
