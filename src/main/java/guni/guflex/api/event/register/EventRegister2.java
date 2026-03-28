package guni.guflex.api.event.register;

import java.util.ArrayList;
import java.util.List;

public class EventRegister2<T1, T2> {
    protected final List<IEventRegistrable2<T1, T2>> events;

    public EventRegister2(){
        events = new ArrayList<>();
    }

    public void register(IEventRegistrable2<T1, T2> event){
        events.add(event);
    }

    public void unregister(IEventRegistrable2<T1, T2> event){
        events.remove(event);
    }

    public void invoke(T1 arg0, T2 arg1){
        List<IEventRegistrable2<T1, T2>> snapshot = new ArrayList<>(events);
        for (IEventRegistrable2<T1, T2> listener : snapshot) {
            listener.invoke(arg0, arg1);
        }
    }

    public void release(){
        events.clear();
    }
}
