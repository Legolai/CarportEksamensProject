package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public enum AmountUnit {
    PIECE("Stk"),
    PACK("Pakke"),
    SET("Sæt"),
    ROLL("Rolle");
    private final String value;

    AmountUnit(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
