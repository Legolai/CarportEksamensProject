package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class BillOfMaterial {
    private int id;
    private LocalDateTime updated;
    private LocalDateTime created;

    public BillOfMaterial(int id, LocalDateTime updated, LocalDateTime created) {
        this.id = id;
        this.updated = updated;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillOfMaterial that)) return false;
        return getId() == that.getId() && Objects.equals(getUpdated(), that.getUpdated()) && Objects.equals(getCreated(), that.getCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUpdated(), getCreated());
    }
}
