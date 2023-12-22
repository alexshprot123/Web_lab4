package Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String phone;
    public String name;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<SlotEntity> slots = new ArrayList<SlotEntity>();
    public UserEntity(){

    }
    public UserEntity(long id, String phone, String name){
        this.id = id;
        this.phone = phone;
        this.name = name;
    }
}
