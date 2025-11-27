package guni.guflex.common.style.parser;

import guni.guflex.GuFlex;
import guni.guflex.api.style.IStyleParser;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.common.style.EnumStyle;

import java.util.function.Function;

public class EnumStyleParser implements IStyleParser {

    protected String propertyKey;
    protected String[] values;
    protected IStyleSpec defaultStyle;

    public EnumStyleParser(String key, IStyleSpec defaultStyle, String... values){
        this.propertyKey = key;
        this.values = values;
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
        for (String value : values){
            if (data.equals(value)){
                return new EnumStyle(data);
            }
        }

        GuFlex.LOGGER.error("Impossible value! Cannot Parse!");
        return null;
    }
}
