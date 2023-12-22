import DTO.AddDoctorDTO;
import DTO.AddNullSlotDTO;
import DTO.AddUserDTO;
import DTO.AddWrittenSlotDTO;
import Entity.DoctorEntity;
import Entity.SlotEntity;
import Entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.joda.time.DateTime;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MedController {
    static SessionFactory sessionFactoryObj;

    public static SessionFactory buildSessionFactory() {
        Configuration configObj = new Configuration();
        configObj.addAnnotatedClass(DoctorEntity.class);
        configObj.addAnnotatedClass(SlotEntity.class);
        configObj.addAnnotatedClass(UserEntity.class);
        configObj.configure();

        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }

    public static Session openSession(){
        Session session = sessionFactoryObj.openSession();
        session.beginTransaction();
        return session;
    }

    public static void closeSession(Session session){
        session.getTransaction().commit();
        session.close();
    }

    public static List<UserEntity> getAllUsers() {
        Session session = openSession();
        List<UserEntity> users = session.createCriteria(UserEntity.class).list();
        closeSession(session);
        return users;
    }
    public static void addUser(AddUserDTO addUserDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.name = addUserDTO.name;
        userEntity.phone = addUserDTO.phone;
        Session session = openSession();
        session.save(userEntity);
        closeSession(session);
    }
    public static void deleteUser(long id){
        Session session = openSession();
        UserEntity user = (UserEntity)session.load(UserEntity.class, id);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
    public static List<DoctorEntity> getAllDoctors() {
        Session session = openSession();
        List<DoctorEntity> doctors = session.createCriteria(DoctorEntity.class).list();
        session.getTransaction().commit();
        session.close();
        return doctors;
    }
    public static void addDoctor(AddDoctorDTO addDoctorDTO){
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.spec = addDoctorDTO.spec;
        doctorEntity.name = addDoctorDTO.name;
        Session session = openSession();
        session.save(doctorEntity);
        session.close();
    }
    public static void deleteDoctor(long id){
        Session session = openSession();
        DoctorEntity doctor = (DoctorEntity)session.load(DoctorEntity.class, id);
        session.delete(doctor);
        session.close();
    }
    public static List<SlotEntity> getAllSlots() {
        Session session = openSession();
        List<SlotEntity> slots = session.createCriteria(SlotEntity.class).list();
        session.close();
        return slots;
    }
    public static boolean addWrittenSlot(AddWrittenSlotDTO addSlotDTO){
        List<SlotEntity> slots = getAllSlots();
        for(SlotEntity slot : slots){
            if(slot.doctor.id == addSlotDTO.doctorID && slot.date.getTime() == addSlotDTO.date.getTime()) {
                Session session = openSession();
                slot.user = (UserEntity) session.get(UserEntity.class, addSlotDTO.userID);
                session.merge(slot);
                closeSession(session);
                return true;
            }
        }
        return false;
    }

    public static void addNullSlot(AddNullSlotDTO addNullSlotDTO){
        Session session = openSession();
        DoctorEntity doctor = (DoctorEntity) session.get(DoctorEntity.class, addNullSlotDTO.doctorID);
        SlotEntity slot = new SlotEntity();
        slot.date = addNullSlotDTO.date;
        slot.doctor = doctor;
        session.save(slot);
        session.close();
    }

    public static void deleteSlot(long id){
        Session session = openSession();
        SlotEntity slot = (SlotEntity)session.load(SlotEntity.class, id);
        session.delete(slot);
        session.close();
    }
    public static List<SlotEntity> getNotificationSlots(Date date, long timeBefore){
        List<SlotEntity> slots = getAllSlots();
        List<SlotEntity> notifSlots = new ArrayList<>();
        for(SlotEntity slot : slots){
            long time = slot.date.getTime() - date.getTime();
            if(slot.user != null && time >= timeBefore - NotificationService.period && time <= timeBefore)
                notifSlots.add(slot);
        }
        return notifSlots;
    }

    public static void init(){
        initUsers();
        initDoctors();
        initSlots();
    }

    private static void initUsers(){
        AddUserDTO user1 = new AddUserDTO("888-888", "Миша");
        AddUserDTO user2 = new AddUserDTO("232-323", "Саша");
        AddUserDTO user3 = new AddUserDTO("232-323", "Паша");
        AddUserDTO user4 = new AddUserDTO("232-323", "Гоша");

        MedController.addUser(user1);
        MedController.addUser(user2);
        MedController.addUser(user3);
        MedController.addUser(user4);

    }
    private static void initDoctors(){
        AddDoctorDTO doctor1 = new AddDoctorDTO("Док", "Окулист");
        AddDoctorDTO doctor2 = new AddDoctorDTO("Мэри", "Терапевт");
        AddDoctorDTO doctor3 = new AddDoctorDTO("Мерси", "Дерматолог");
        AddDoctorDTO doctor4 = new AddDoctorDTO("Пит", "Психолог");

        MedController.addDoctor(doctor1);
        MedController.addDoctor(doctor2);
        MedController.addDoctor(doctor3);
        MedController.addDoctor(doctor4);
    }

    private static void initSlots(){
        for(int i = 1; i < 3; i++) {
            AddNullSlotDTO slot = new AddNullSlotDTO();
            slot.doctorID = i;

            DateTime dateTime = DateTime.now();
            dateTime = dateTime.minuteOfHour().roundFloorCopy();
            dateTime = dateTime.plusHours(2);
            dateTime = dateTime.plusMinutes(i);
            slot.date = dateTime.toDate();
            MedController.addNullSlot(slot);

            AddWrittenSlotDTO wrSlot = new AddWrittenSlotDTO();
            wrSlot.date = slot.date;
            wrSlot.doctorID = i;
            wrSlot.userID = i;

            MedController.addWrittenSlot(wrSlot);
        }
        for(int i = 1; i < 3; i++) {
            AddNullSlotDTO slot = new AddNullSlotDTO();
            slot.doctorID = i+2;

            DateTime dateTime = DateTime.now();
            dateTime = dateTime.minuteOfHour().roundFloorCopy();
            dateTime = dateTime.plusDays(1);
            dateTime = dateTime.plusMinutes(i);
            slot.date = dateTime.toDate();
            MedController.addNullSlot(slot);

            AddWrittenSlotDTO wrSlot = new AddWrittenSlotDTO();
            wrSlot.date = slot.date;
            wrSlot.doctorID = i+2;
            wrSlot.userID = i+2;

            MedController.addWrittenSlot(wrSlot);
        }
    }
}
