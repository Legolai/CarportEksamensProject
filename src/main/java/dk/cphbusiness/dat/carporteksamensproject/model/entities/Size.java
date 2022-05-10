package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.util.Objects;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Size size)) return false;
        return getId() == size.getId() && getLength() == size.getLength() && isDeleted() == size.isDeleted();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLength(), isDeleted());
    }
}
