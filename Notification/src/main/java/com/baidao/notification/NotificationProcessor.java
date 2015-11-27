package com.baidao.notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by burizado on 15-1-14.
 */
public class NotificationProcessor {
    List<NotificationHandler> handlers = new ArrayList<>();
    static private NotificationProcessor processor;

    private NotificationProcessor(){}

    static public NotificationProcessor getInstance() {
        if (processor == null) {
            processor = new NotificationProcessor();
        }
        return processor;
    }

    public void clear() {
        processor.handlers.clear();
    }

    public void registerHandler(NotificationHandler handler) {
        if (handlers.contains(handler)) return;
        handlers.add(handler);
    }

    public void processNotification(NotificationMessage message) {
        for (NotificationHandler handler : handlers) {
            if (handler.canHandle(message.type)) {
                handler.handle(message);
            }
        }
    }
}
