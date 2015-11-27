package com.baidao.notification;

/**
 * Created by rjhy on 15-4-30.
 */
public class NotificationBusEvent {
    public NotificationType type;

    public NotificationBusEvent(NotificationType type) {
        this.type = type;
    }
}
