package cz.cvut.fel.AnonymousCommunication.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteLink extends AbstractEntity {
    @JsonIgnore
    @ManyToOne
    private Subject subject;

    private String uuid;

    private Date expiringAt;

    public InviteLink(Subject subject, long expiresSecondsFromNow) {
        this.subject = subject;
        this.uuid = UUID.randomUUID().toString();
        this.expiringAt = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresSecondsFromNow));
    }

    @Override
    public String toString() {
        return "InviteLink{" +
                ", uuid='" + uuid + '\'' +
                ", expiringAt=" + expiringAt +
                '}';
    }
}
