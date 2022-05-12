package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.util.Objects;

@Entity
@Table("Size")
public class Size {
    @Id
    @GeneratedValue(strategy = 1)
    @Column("size_ID")
    private int id;

    @Column("size_detail")
    private int detail;

    @Column("size_type")
    private SizeType type;

    @Column("size_deleted")
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
