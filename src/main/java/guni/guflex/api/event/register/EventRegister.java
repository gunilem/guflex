package guni.guflex.api.event.register;

import java.util.ArrayList;
import java.util.List;

public class EventRegister {
    protected final List<IEventRegistrable> events;

    public EventRegister(){
        events = new ArrayList<>();
    }

    public void register(IEventRegistrable event){
        events.add(event);
    }

    public void unregister(IEventRegistrable event){
        events.remove(event);
    }

    public void invoke(){
        List<IEventRegistrable> snapshot = new ArrayList<>(events);
        for (IEventRegistrable listener : snapshot) {
            listener.invoke();
        }
    }
    public void inverseReversed(){
        List<IEventRegistrable> snapshot = new ArrayList<>(events.reversed());
        for (IEventRegistrable listener : snapshot) {
            listener.invoke();
        }
    }

    public void release(){
        events.clear();
    }
}
