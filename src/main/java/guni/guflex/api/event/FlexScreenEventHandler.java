package guni.guflex.api.event;

import guni.guflex.api.event.screenEvents.IScreenInitEvent;
import guni.guflex.api.event.screenEvents.IScreenTickEvent;

import java.util.ArrayList;
import java.util.List;

public class FlexScreenEventHandler {
    protected List<IScreenTickEvent> screenTickEventRegister = new ArrayList<>();
    protected List<IScreenInitEvent> screenInitEventRegister = new ArrayList<>();

    public void register(IScreenTickEvent event) { screenTickEventRegister.add(event); }
    public void register(IScreenInitEvent event) { screenInitEventRegister.add(event); }

    public void unregister(IScreenTickEvent event) { screenTickEventRegister.remove(event); }
    public void unregister(IScreenInitEvent event) { screenInitEventRegister.remove(event); }


    public void invoke(IScreenTickEvent.Data data) {
        int size = screenTickEventRegister.size();
        for (int i = 0 ; i <= size - 1; i++) {
            screenTickEventRegister.get(i).onEvent(data);
        }
    }

    public void invoke(IScreenInitEvent.Data data) {
        int size = screenInitEventRegister.size();
        for (int i = 0 ; i <= size - 1; i++) {
            screenInitEventRegister.get(i).onEvent(data);
        }
    }

    public void release(){
        screenTickEventRegister.clear();
        screenInitEventRegister.clear();
    }
}
