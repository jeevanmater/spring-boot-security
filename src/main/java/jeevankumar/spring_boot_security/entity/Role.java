package jeevankumar.spring_boot_security.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLES_TABLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType name;

    public enum RoleType{
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_MODERATOR
    }

    public Role() {
        super();
    }

    public Role(RoleType name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }
}
