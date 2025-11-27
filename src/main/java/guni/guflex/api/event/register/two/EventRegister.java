package guni.guflex.api.event.register.two;

import java.util.ArrayList;
import java.util.List;

public class EventRegister<T1, T2> {
    protected final List<IEventRegistrable<T1, T2>> events;

    public EventRegister(){
        events = new ArrayList<>();
    }

    public void register(IEventRegistrable<T1, T2> event){
        events.add(event);
    }

    public void unregister(IEventRegistrable<T1, T2> event){
        events.remove(event);
    }

    public void invoke(T1 arg0, T2 arg1){
        for (var event : events){
            event.invoke(arg0, arg1);
        }
    }

    public void release(){
        events.clear();
    }
}
