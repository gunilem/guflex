package guni.guflex.api.runtime.screen;

import guni.guflex.api.event.FlexScreenEventHandler;
import guni.guflex.api.runtime.widget.BaseFlexPopup;
import guni.guflex.api.runtime.widget.IFlexWidget;

public interface IFlexScreen {

    interface EmptyEvent{
        void invoke();
    }

    FlexScreenEventHandler screenEventHandler();
    IFlexWidget root();
    void openPopup(BaseFlexPopup popup);
    void closePopup(BaseFlexPopup popup);

    void onHierarchyUpdated();
    void addDelayOperation(EmptyEvent event);
}
