package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.util.Objects;

public class Shack {
    private int id;
    private int width;
    private int length;
    private boolean isLeftAligned;
    private int carportId;

    public Shack(int id, int width, int length, boolean isLeftAligned, int carportId) {
        this.id = id;
        this.width = width;
        this.length = length;
        this.isLeftAligned = isLeftAligned;
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

    public int getCarportId() {
        return carportId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setLeftAligned(boolean leftAligned) {
        isLeftAligned = leftAligned;
    }

    public void setCarportId(int carportId) {
        this.carportId = carportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shack shack)) return false;
        return getId() == shack.getId() && getWidth() == shack.getWidth() && getLength() == shack.getLength() && isLeftAligned() == shack.isLeftAligned() && getCarportId() == shack.getCarportId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWidth(), getLength(), isLeftAligned(), getCarportId());
    }
}
