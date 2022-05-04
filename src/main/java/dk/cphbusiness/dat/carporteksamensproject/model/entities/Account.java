package dk.cphbusiness.dat.carporteksamensproject.model.entities;

<<<<<<< HEAD
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table("Account")
public class Account {

    @Id
    @GeneratedValue(strategy = 1)
    @Column("account_ID")
    private int id;

    @Column("account_created")
    private LocalDateTime created;

    @Column("account_updated")
    private LocalDateTime updated;

    @Column("person_ID")
    private int personId;

    @Column("account_password")
    private String password;

    @Column("account_role")
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
