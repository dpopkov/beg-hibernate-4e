package learn.beghibernate.ch04.cascaded;

import javax.persistence.*;

@Entity(name = "Message3")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String content;

    @OneToOne
            (cascade = {CascadeType.ALL})
//            (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    private Email email;

    public Message() {
    }

    public Message(String content) {
        setContent(content);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", email.subject=" + (email != null ? email.getSubject() : "null") +
                '}';
    }
}