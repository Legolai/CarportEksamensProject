package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.util.Objects;

@Entity
@Table("Shack")
public class Shack {

    @Id
    @GeneratedValue(strategy = 1)
    @Column("shack_ID")
    private int id;

    @Column("shack_width")
    private int width;

    @Column("shack_length")
    private int length;

    @Column("shack_left_align")
    private boolean isLeftAligned;


    public Shack(int id, int width, int length, boolean isLeftAligned) {
        this.id = id;
        this.width = width;
        this.length = length;
        this.isLeftAligned = isLeftAligned;
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

    @Override
    public String toString() {
        return "Shack{" +
                "id=" + id +
                ", width=" + width +
                ", length=" + length +
                ", isLeftAligned=" + isLeftAligned +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shack shack)) return false;
        return getId() == shack.getId() && getWidth() == shack.getWidth() && getLength() == shack.getLength() && isLeftAligned() == shack.isLeftAligned();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWidth(), getLength(), isLeftAligned());
    }
}
