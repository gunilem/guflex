package guni.guflex.api.runtime.widget;

import guni.guflex.api.event.FlexWidgetEventHandler;
import guni.guflex.api.style.FlexRect;
import guni.guflex.api.style.FlexStyle;

import java.util.ArrayList;

public class RootFlexWidget extends FlexWidget {
    public RootFlexWidget(){
        eventHandler = new FlexWidgetEventHandler();
        bounds = FlexRect.EMPTY();
        style = new FlexStyle(eventHandler);
        children = new ArrayList<>();

        show();
    }

    public void setSize(int width, int height){
        bounds = new FlexRect(0, 0, width, height);
    }
}
