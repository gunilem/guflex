package guni.guflex.api.event.register;

import java.util.ArrayList;
import java.util.List;

public class EventRegister1<T1> {
    protected final List<IEventRegistrable1<T1>> events;

    public EventRegister1(){
        events = new ArrayList<>();
    }

    public void register(IEventRegistrable1<T1> event){
        events.add(event);
    }

    public void unregister(IEventRegistrable1<T1> event){
        events.remove(event);
    }

    public void invoke(T1 arg0){
        List<IEventRegistrable1<T1>> snapshot = new ArrayList<>(events);
        for (IEventRegistrable1<T1> listener : snapshot) {
            listener.invoke(arg0);
        }
    }
    public void inverseReversed(T1 arg0){
        List<IEventRegistrable1<T1>> snapshot = new ArrayList<>(events.reversed());
        for (IEventRegistrable1<T1> listener : snapshot) {
            listener.invoke(arg0);
        }
    }

    public void release(){
        events.clear();
    }
}
