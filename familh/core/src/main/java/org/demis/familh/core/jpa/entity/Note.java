package org.demis.familh.core.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="note")
public class Note extends AbstractModel implements Model {

    static private final Logger logger = LoggerFactory.getLogger(Note.class);

    private Long id;
    private Integer version;
    private String content;
    private FamilyTree familyTree;
    private User user;

    public Note() {
        // no op
    }

    @Id
    @Column(name="note_id")
    @SequenceGenerator(name="note_sequence", sequenceName="note_sequence")
    @GeneratedValue(generator="note_sequence")
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

    @JoinColumn(name = "family_tree_id", nullable=true)
    @ManyToOne()
    public FamilyTree getFamilyTree() {
        return familyTree;
    }

    @Column(name="content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
