package project.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Entity(name="MEMO")
public class Memo {

    @Id @GeneratedValue
    Long id;

    @ManyToOne
    User user;

    String content;

    Instant createdAt;

    public Memo() {
    }

    public Memo(User user, String content) {
        this.user = user;
        this.content = content;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return user.getName();
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
