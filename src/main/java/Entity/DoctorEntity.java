package Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String name;
    public String spec;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<SlotEntity> slots = new ArrayList<SlotEntity>();
    public DoctorEntity(){

    }
    public DoctorEntity(long id, String name, String spec, List<SlotEntity> slots){
        this.id = id;
        this.spec = spec;
        this.name = name;
        if(slots != null)
            this.slots = slots;
    }
}
