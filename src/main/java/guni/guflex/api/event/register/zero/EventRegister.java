package guni.guflex.api.event.register.zero;

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
        for (var event : events){
            event.invoke();
        }
    }

    public void release(){
        events.clear();
    }
}
