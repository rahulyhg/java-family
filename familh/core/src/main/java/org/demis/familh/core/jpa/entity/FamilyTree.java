package org.demis.familh.core.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table ( name="family_tree")
public class FamilyTree extends AbstractModel implements Model {

    private static final Logger LOGGER = LoggerFactory.getLogger(FamilyTree.class);

    private Long id;
    private Integer version;
    private String ident;
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

    @Column (name="ident")
    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "access_id")
    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }
}
