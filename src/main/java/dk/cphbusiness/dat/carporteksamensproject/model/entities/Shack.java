package dk.cphbusiness.dat.carporteksamensproject.model.entities;

public class Shack {
    private int id;
    private int width;
    private int length;
    private boolean isLeftAligned;
    private int facingId;
    private int flooringId;
    private int carportId;

    public Shack(int id, int width, int length, boolean isLeftAligned, int facingId, int flooringId, int carportId) {
        this.id = id;
        this.width = width;
        this.length = length;
        this.isLeftAligned = isLeftAligned;
        this.facingId = facingId;
        this.flooringId = flooringId;
        this.carportId = carportId;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public boolean isLeftAligned() {
        return isLeftAligned;
    }

    public int getFacingId() {
        return facingId;
    }

    public int getFlooringId() {
        return flooringId;
    }

    public int getCarportId() {
        return carportId;
    }
}
