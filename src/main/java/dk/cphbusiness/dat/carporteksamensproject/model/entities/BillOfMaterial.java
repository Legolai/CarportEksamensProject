package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.time.LocalDateTime;

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
}
