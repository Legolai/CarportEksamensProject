package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public enum Unit {
    METER("cm"),
    PIECE("stk");

    private final String value;

    Unit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
