package guni.guflex.api.event.screenEvents;

import guni.guflex.api.event.register.IEventRegistrable1;

public interface IScreenTickEvent extends IEventRegistrable1<IScreenTickEvent.Data> {
    record Data(int tickSinceScreenOpened) {}
}
