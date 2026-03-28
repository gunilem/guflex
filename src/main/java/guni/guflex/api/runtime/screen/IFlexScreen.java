package guni.guflex.api.runtime.screen;

import guni.guflex.api.event.FlexScreenEventHandler;
import guni.guflex.api.event.register.IEventRegistrable;
import guni.guflex.api.runtime.widget.BaseFlexPopup;
import guni.guflex.api.runtime.widget.IFlexWidget;

public interface IFlexScreen {
    FlexScreenEventHandler screenEventHandler();
    IFlexWidget root();
    void openPopup(BaseFlexPopup popup);
    void closePopup(BaseFlexPopup popup);

    void onHierarchyUpdated();
    void updateHierarchy();
    void addDelayOperation(IEventRegistrable event);
}
