package engine.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "permission")
    private String permission;

    @ManyToMany(mappedBy = "permissions")
    private Collection<Role> role;


    public Permission() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }


}
