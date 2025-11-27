package guni.guflex.api.runtime.widget;

import guni.guflex.api.event.FlexWidgetEventHandler;
import guni.guflex.api.event.IEventDispatcher;
import guni.guflex.api.style.FlexRect;
import guni.guflex.api.style.IStyleSpec;

import java.util.List;

public interface IFlexWidget extends IEventDispatcher {
    FlexRect rect();
    FlexRect resetRect();
    IStyleSpec style(String key);
    List<IFlexWidget> children();
    FlexWidgetEventHandler eventHandler();
    boolean displayed();
    boolean renderable();

    void hide();
    void show();

    void addStyle(String key, String value);
    void setDirty();
    boolean isDirty();

    void addChild(IFlexWidget child);
    void removeChild(IFlexWidget child);
    void removeAllChild();

    void recomputeLayout(IFlexWidget parent);

    void setHierarchyDisplayedProperty(boolean value);
    boolean checkDirty();
}
