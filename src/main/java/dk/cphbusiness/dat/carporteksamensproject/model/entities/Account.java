package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import java.time.LocalDateTime;

public class Account {
    private int id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private int personId;
    private String password;
    private Role role;

    public Account(int id, LocalDateTime created, LocalDateTime updated, int personId, String password, Role role) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.personId = personId;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public int getPersonId() {
        return personId;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
