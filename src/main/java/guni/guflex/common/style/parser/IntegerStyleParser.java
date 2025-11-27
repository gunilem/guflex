package guni.guflex.common.style.parser;

import guni.guflex.GuFlex;
import guni.guflex.api.style.IStyleParser;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.common.style.IntegerStyle;

public class IntegerStyleParser implements IStyleParser {
    protected String propertyKey;
    protected IStyleSpec defaultStyle;

    public IntegerStyleParser(String key, IStyleSpec defaultStyle){
        this.propertyKey = key;
        this.defaultStyle = defaultStyle;
    }

    @Override
    public String getPropertyKey() {
        return propertyKey;
    }

    @Override
    public IStyleSpec getDefault() {
        return defaultStyle;
    }

    @Override
    public IStyleSpec parse(String data) {
        try {
            int value = Integer.parseInt(data);
            return new IntegerStyle(value);
        } catch (Exception e){
            GuFlex.LOGGER.error("Impossible value! Cannot Parse!");
            return null;
        }
    }
}