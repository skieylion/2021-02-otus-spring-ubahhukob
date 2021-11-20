package spring.project.bot.service;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EventManagerImpl implements EventManager {
    private static final List<EventListener> listeners = new ArrayList<>();

    @Override
    public void subscribe(EventListener listener) {
        listeners.add(listeners.size(),listener);
    }

    @Override
    public void unsubscribe(EventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyAll(Object obj) {
        listeners.forEach(eventListener -> eventListener.update(obj));
    }
}
