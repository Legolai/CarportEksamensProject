package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.util.Objects;

public class Size {
    private int id;
    private int detail;

    private SizeType type;
    private boolean deleted;

    public Size(int id, int detail, SizeType type, boolean deleted) {
        this.id = id;
        this.detail = detail;
        this.type = type;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public int getDetail() {
        return detail;
    }

    public SizeType getType() {
        return type;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }

    public void setType(SizeType type) {
        this.type = type;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Size size)) return false;
        return getId() == size.getId() && getDetail() == size.getDetail() && isDeleted() == size.isDeleted() && getType() == size.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDetail(), getType(), isDeleted());
    }
}
