import Entity.SlotEntity;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class NotificationService {

    public static long period = 10000;
    Thread run;
    Boolean isStart = false;

    public void startThread(){
        isStart = true;
        run = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Date date = new Date(System.currentTimeMillis());
                        List<SlotEntity> slots = MedController.getNotificationSlots(date, 7200000);
                        for(SlotEntity slot : slots){
                            DateTime dateTime = new DateTime(slot.date);
                            System.out.println(date + " | Привет " + slot.user.name + "! Вам через 2 часа к "+ slot.doctor.spec +" в "+ dateTime.toLocalTime());
                        }

                        slots = MedController.getNotificationSlots(date, 86400000);
                        for(SlotEntity slot : slots){
                            DateTime dateTime = new DateTime(slot.date);
                            System.out.println(date + " | Привет " + slot.user.name + "! Напоминаем что вы записаны к "+ slot.doctor.spec +" завтра в "+ dateTime.toLocalTime());
                        }
                        Thread.sleep(period);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });
        run.start();
    }
    public void stopThread(){
        isStart = false;
    }
}
