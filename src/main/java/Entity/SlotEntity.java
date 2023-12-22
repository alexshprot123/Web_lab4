package Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "slots")
public class SlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @JsonIgnore
    @ManyToOne
    public DoctorEntity doctor;

    @Column(name = "date", columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    public Date date;
    @JsonIgnore
    @ManyToOne
    public UserEntity user;

    public SlotEntity() {

    }

    public SlotEntity(long id, DoctorEntity doctor, Date date, UserEntity user) {
        this.doctor = doctor;
        this.date = date;
        this.user = user;
    }
}
