package org.demis.familh.core.jpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name="name")
public class Name extends AbstractModel implements Model {

    static private final Logger logger = LoggerFactory.getLogger(Name.class);

    private Long id;
    private String firstName;
    private String lastName;
    private NameType type;
    private Person person;
    private User user;
    private Integer version;

    public Name() {
        // no op
    }


    @Id
    @Column(name="name_id")
    @SequenceGenerator(name="name_sequence", sequenceName="name_sequence")
    @GeneratedValue(generator="name_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="lastname")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name="firstname")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "name_type_id")
    public NameType getType() {
        return type;
    }

    public void setType(NameType type) {
        this.type = type;
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

    @JoinColumn(name = "person_id", nullable=true)
    @ManyToOne()
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
