import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

import java.util.TimeZone;

public class WebServer {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        MedController.buildSessionFactory();
        MedController.init();
        startWebServer();
        startNotificationService();
    }

    private static void startWebServer() {
        int port = 8080;
        Undertow.Builder builder = Undertow.builder().addHttpListener(port, "localhost");
        UndertowJaxrsServer server = new UndertowJaxrsServer().start(builder);
        server.deploy(WebAppSingletons.class);
        System.out.println("Сервер запущен: http://localhost:" + port + "/");
    }
    private static void startNotificationService(){
        NotificationService notificationService = new NotificationService();
        notificationService.startThread();
    }
}
