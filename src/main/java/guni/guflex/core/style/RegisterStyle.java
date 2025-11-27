package guni.guflex.core.style;

import guni.guflex.api.style.Style;
import guni.guflex.api.style.StyleComputerRegistry;
import guni.guflex.api.style.StyleRegistry;
import guni.guflex.common.style.EnumStyle;
import guni.guflex.common.style.IntegerStyle;
import guni.guflex.common.style.LengthPercentEnumStyle;
import guni.guflex.common.style.LengthPercentStyle;
import guni.guflex.common.style.computer.*;
import guni.guflex.common.style.parser.EnumStyleParser;
import guni.guflex.common.style.parser.IntegerStyleParser;
import guni.guflex.common.style.parser.LengthPercentEnumParser;
import guni.guflex.common.style.parser.LengthPercentParser;

public class RegisterStyle {

    public static void registerStyles(){
        StyleRegistry styleRegistry = StyleRegistry.getRegistry();

        styleRegistry.register(new IntegerStyleParser(Style.X, new IntegerStyle(0)));
        styleRegistry.register(new IntegerStyleParser(Style.Y, new IntegerStyle(0)));

        styleRegistry.register(new LengthPercentParser(Style.LEFT, null));
        styleRegistry.register(new LengthPercentParser(Style.RIGHT, null));
        styleRegistry.register(new LengthPercentParser(Style.TOP, null));
        styleRegistry.register(new LengthPercentParser(Style.BOTTOM, null));

        styleRegistry.register(new LengthPercentEnumParser(Style.WIDTH, new LengthPercentEnumStyle(1f, LengthPercentEnumStyle.Type.Percent), "wrap", "auto"));
        styleRegistry.register(new LengthPercentEnumParser(Style.HEIGHT, new LengthPercentEnumStyle(1f, LengthPercentEnumStyle.Type.Percent), "wrap", "auto"));

        styleRegistry.register(new LengthPercentParser(Style.MIN_WIDTH, null));
        styleRegistry.register(new LengthPercentParser(Style.MIN_HEIGHT, null));

        styleRegistry.register(new LengthPercentParser(Style.MAX_WIDTH, null));
        styleRegistry.register(new LengthPercentParser(Style.MAX_HEIGHT, null));


        styleRegistry.register(new LengthPercentParser(Style.PADDING_LEFT, null));
        styleRegistry.register(new LengthPercentParser(Style.PADDING_RIGHT, null));
        styleRegistry.register(new LengthPercentParser(Style.PADDING_TOP, null));
        styleRegistry.register(new LengthPercentParser(Style.PADDING_BOTTOM, null));

        styleRegistry.register(new LengthPercentParser(Style.MARGIN_LEFT, null));
        styleRegistry.register(new LengthPercentParser(Style.MARGIN_RIGHT, null));
        styleRegistry.register(new LengthPercentParser(Style.MARGIN_TOP, null));
        styleRegistry.register(new LengthPercentParser(Style.MARGIN_BOTTOM, null));

        styleRegistry.register(new LengthPercentParser(Style.ITEM_SPACING, null));


        styleRegistry.register(new EnumStyleParser(Style.BOX_SIZING_X, new EnumStyle(Style.CONTENT_BOX), Style.CONTENT_BOX, Style.BORDER_BOX, Style.MARGIN_BOX));
        styleRegistry.register(new EnumStyleParser(Style.BOX_SIZING_Y, new EnumStyle(Style.CONTENT_BOX), Style.CONTENT_BOX, Style.BORDER_BOX, Style.MARGIN_BOX));
        styleRegistry.register(new EnumStyleParser(Style.FLEX_DIRECTION, new EnumStyle(Style.NONE), Style.NONE, Style.VERTICAL, Style.HORIZONTAL));
        styleRegistry.register(new EnumStyleParser(Style.FLEX_LAYOUT, null, Style.GRID));
        styleRegistry.register(new EnumStyleParser(Style.POSITION, new EnumStyle(Style.FLEX), Style.RELATIVE, Style.FIXED, Style.FLEX));

        styleRegistry.register(new IntegerStyleParser(Style.LAYOUT_PRIORITY, new IntegerStyle(0)));
        styleRegistry.register(new IntegerStyleParser(Style.FLEX_GROW, null));

        styleRegistry.register(new EnumStyleParser(Style.ANCHOR, new EnumStyle(Style.TOP_LEFT),
                Style.TOP_LEFT, Style.TOP_RIGHT,
                Style.BOTTOM_LEFT, Style.BOTTOM_RIGHT,
                Style.LEFT_CENTER, Style.RIGHT_CENTER,
                Style.TOP_CENTER, Style.BOTTOM_CENTER,
                Style.CENTER
        ));
        styleRegistry.register(new EnumStyleParser(Style.PIVOT, new EnumStyle(Style.TOP_LEFT),
                Style.TOP_LEFT, Style.TOP_RIGHT,
                Style.BOTTOM_LEFT, Style.BOTTOM_RIGHT,
                Style.LEFT_CENTER, Style.RIGHT_CENTER,
                Style.TOP_CENTER, Style.BOTTOM_CENTER,
                Style.CENTER
        ));

        SizeComputer sizeComputer = new SizeComputer();
        PositionComputer positionComputer = new PositionComputer();

        new StyleComputerRegistry(sizeComputer, positionComputer);
    }
}
