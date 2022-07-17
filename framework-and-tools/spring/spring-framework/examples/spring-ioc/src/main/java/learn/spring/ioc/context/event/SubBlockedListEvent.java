package learn.spring.ioc.context.event;

/**
 * SubBlockedListEvent.
 */
public class SubBlockedListEvent extends BlockedListEvent {

    public SubBlockedListEvent(Object source, String address, String content) {
        super(source, address, content);
    }
}
