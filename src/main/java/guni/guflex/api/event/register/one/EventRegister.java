package guni.guflex.api.event.register.one;

import java.util.ArrayList;
import java.util.List;

public class EventRegister<T1> {
    protected final List<IEventRegistrable<T1>> events;

    public EventRegister(){
        events = new ArrayList<>();
    }

    public void register(IEventRegistrable<T1> event){
        events.add(event);
    }

    public void unregister(IEventRegistrable<T1> event){
        events.remove(event);
    }

    public void invoke(T1 arg0){
        for (var event : events){
            event.invoke(arg0);
        }
    }

    public void release(){
        events.clear();
    }
}
