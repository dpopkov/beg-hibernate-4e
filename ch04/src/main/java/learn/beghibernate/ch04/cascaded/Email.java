package learn.beghibernate.ch04.cascaded;

import javax.persistence.*;

@Entity(name = "Email3")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String subject;

    @OneToOne (mappedBy = "email")
    private Message message;

    public Email() {
    }

    public Email(String subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", message.content=" + (message != null ? message.getContent() : "null") +
                '}';
    }
}
