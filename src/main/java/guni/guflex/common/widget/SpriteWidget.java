package guni.guflex.common.widget;

import guni.guflex.api.event.screenEvents.IRenderEvent;
import guni.guflex.api.runtime.drawable.IDrawable;
import guni.guflex.api.runtime.widget.FlexWidget;

public class SpriteWidget extends FlexWidget {
    protected IDrawable sprite;

    public SpriteWidget(IDrawable sprite){
        super();
        this.sprite = sprite;

        eventHandler.registerRenderEvent(this::onRender);
    }

    protected void onRender(IRenderEvent.Data event){
        sprite.draw(event.guiGraphics(), bounds);
    }

    public void changeSprite(IDrawable sprite){
        this.sprite = sprite;
    }
}
