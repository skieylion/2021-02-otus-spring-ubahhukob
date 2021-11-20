package spring.project.bot.service;

public interface EventManager {
    void subscribe(EventListener listener);
    void unsubscribe(EventListener listener);
    void notifyAll(Object obj);
}
