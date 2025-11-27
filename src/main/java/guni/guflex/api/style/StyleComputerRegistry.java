package guni.guflex.api.style;

import guni.guflex.api.runtime.widget.IFlexWidget;
import guni.guflex.common.style.computer.PositionComputer;
import guni.guflex.common.style.computer.SizeComputer;

public class StyleComputerRegistry {
    private static StyleComputerRegistry INSTANCE;

    private final SizeComputer sizeComputer;
    private final PositionComputer positionComputer;

    public StyleComputerRegistry(SizeComputer sizeComputer, PositionComputer positionComputer){
        this.sizeComputer = sizeComputer;
        this.positionComputer = positionComputer;

        setInstance(this);
    }

    private static void setInstance(StyleComputerRegistry instance){
        INSTANCE = instance;
    }
    public static StyleComputerRegistry getRegistry(){
        return INSTANCE;
    }

    public void measure(FlexRect parentContentRect, IFlexWidget widget){
        sizeComputer.compute(parentContentRect, widget);
    }

    public void position(FlexRect parentContentRect, IFlexWidget widget){
        positionComputer.compute(parentContentRect, widget);
    }
}
