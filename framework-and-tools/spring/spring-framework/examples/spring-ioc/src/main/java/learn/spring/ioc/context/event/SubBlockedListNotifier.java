package learn.spring.ioc.context.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SubBlockedListNotifier implements ApplicationListener<SubBlockedListEvent> {

    private String notificationAddress;

    public void setNotificationAddress(String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    public void onApplicationEvent(SubBlockedListEvent event) {
        // notify appropriate parties via notificationAddress...
        System.out.println(event);
    }
}