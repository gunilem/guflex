package guni.guflex.api.event;

import guni.guflex.api.event.register.EventRegister1;
import guni.guflex.api.event.screenEvents.IScreenInitEvent;
import guni.guflex.api.event.screenEvents.IScreenTickEvent;

import java.util.ArrayList;
import java.util.List;

public class FlexScreenEventHandler {
    public final EventRegister1<IScreenTickEvent.Data> tick = new EventRegister1<>();
    public final EventRegister1<IScreenInitEvent.Data> init = new EventRegister1<>();
}
