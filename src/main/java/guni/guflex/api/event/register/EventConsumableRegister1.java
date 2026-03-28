package guni.guflex.api.event.register;

import java.util.ArrayList;
import java.util.List;

public class EventConsumableRegister1<T1> {
    protected final List<IEventConsumableRegistrable1<T1>> events;

    public EventConsumableRegister1(){
        events = new ArrayList<>();
    }

    public void register(IEventConsumableRegistrable1<T1> event){
        events.add(event);
    }

    public void unregister(IEventConsumableRegistrable1<T1> event){
        events.remove(event);
    }

    public boolean invoke(T1 arg0){
        boolean consumed = false;
        List<IEventConsumableRegistrable1<T1>> snapshot = new ArrayList<>(events);
        for (IEventConsumableRegistrable1<T1> listener : snapshot) {
            if (listener.invoke(arg0)) consumed = true;
        }
        return consumed;
    }

    public void release(){
        events.clear();
    }
}
