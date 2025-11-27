package guni.guflex.api.style;

import guni.guflex.GuFlex;
import guni.guflex.api.event.widgetEvents.IWidgetMeasuredEvent;
import guni.guflex.api.event.widgetEvents.IWidgetPositionedEvent;
import guni.guflex.api.runtime.widget.IFlexWidget;

import java.util.*;

public class FlexSpecs {
    protected Map<String, IStyleSpec> specs;
    public boolean isDirty;
    public boolean dependsOnChildren = true;

    public static FlexSpecs DEFAULT(){
        return new FlexSpecs(StyleRegistry.getRegistry().defaultSpecs());
    }

    protected FlexSpecs(Map<String, IStyleSpec> specs){
        this.specs = specs;
    }

    public void addStyle(String key, String value){
        IStyleParser parser = StyleRegistry.getRegistry().get(key);
        if (parser == null){
            GuFlex.LOGGER.error("Trying to add a style that is not registered");
            return;
        }
        if (value == null){
            IStyleSpec spec = parser.parse(Style.NONE);
            if (spec != null){
                specs.put(key, spec);
                isDirty = true;
                return;
            }
            specs.remove(key);
            isDirty = true;
            return;
        }
        IStyleSpec spec = parser.parse(value);
        if (spec == null){
            return;
        }
        specs.put(key, spec);
        isDirty = true;
    }

    @SuppressWarnings("unchecked")
    public <T extends IStyleSpec> T getStyle(String key){
        try {
            return (T)specs.get(key);
        } catch (ClassCastException e){
            GuFlex.LOGGER.error("");
            return null;
        }
    }

    public void clearStyle(String key){
        specs.remove(key);
    }

    public void clearStyle(){
        specs.clear();
    }

    public void measure(IFlexWidget parent, IFlexWidget widget){
        if (parent == null){
            StyleComputerRegistry.getRegistry().measure(FlexRect.EMPTY(), widget);
            return;
        }
        FlexRect parentContentRect = parent.rect().getContentRect();
        StyleComputerRegistry.getRegistry().measure(parentContentRect, widget);
    }
    public void position(IFlexWidget parent, IFlexWidget widget){
        if (parent == null){
            StyleComputerRegistry.getRegistry().position(FlexRect.EMPTY() , widget);
            return;
        }
        FlexRect parentContentRect = parent.rect().getContentRect();
        StyleComputerRegistry.getRegistry().position(parentContentRect, widget);
    }
}
