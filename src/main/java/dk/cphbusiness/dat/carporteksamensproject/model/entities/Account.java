package dk.cphbusiness.dat.carporteksamensproject.model.entities;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.*;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return getId() == account.getId() && getPersonId() == account.getPersonId() && Objects.equals(getCreated(), account.getCreated()) && Objects.equals(getUpdated(), account.getUpdated()) && Objects.equals(getPassword(), account.getPassword()) && getRole() == account.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreated(), getUpdated(), getPersonId(), getPassword(), getRole());
    }
}
