package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public enum SizeType {
    LENGTH("cm"),
    PIECES("stk.");

    private final String value;

    SizeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
