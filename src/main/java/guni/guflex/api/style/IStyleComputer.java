package guni.guflex.api.style;

import guni.guflex.api.runtime.widget.IFlexWidget;

public interface IStyleComputer {
    void compute(FlexRect parentContentRect, IFlexWidget widget);
}
