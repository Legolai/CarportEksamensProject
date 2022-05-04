package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public class Size {
    private int id;
    private int length;
    private boolean deleted;

    public Size(int id, int length, boolean deleted) {
        this.id = id;
        this.length = length;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
