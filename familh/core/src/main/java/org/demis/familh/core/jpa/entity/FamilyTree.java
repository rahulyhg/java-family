package org.demis.familh.core.jpa.entity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table ( name="family_tree")
public class FamilyTree extends AbstractModel implements Model {

    static private final Logger logger = LoggerFactory.getLogger(FamilyTree.class);

    private Long id;
    private Integer version;
    private String ident;
    private User user;


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
}
