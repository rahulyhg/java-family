package org.demis.familh.core.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table ( name="family_tree")
public class FamilyTree extends AbstractModel implements Model {

    private static final Logger LOGGER = LoggerFactory.getLogger(FamilyTree.class);

    private Long id;
    private Integer version;
    private String name;
    private User user;
    private Access access;


    public FamilyTree() {
        // no op
    }

    @Id
    @Column(name="family_tree_id")
    @SequenceGenerator(name="family_tree_sequence", sequenceName="family_tree_sequence")
    @GeneratedValue(generator="family_tree_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @JoinColumn(name = "user_id", nullable=true)
    @ManyToOne()
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column (name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "access_id")
    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamilyTree that = (FamilyTree) o;

        if (!name.equals(that.name)) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
