package guni.guflex.common.style.parser;

import guni.guflex.GuFlex;
import guni.guflex.api.style.IStyleParser;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.common.style.EnumStyle;
import guni.guflex.common.style.LengthPercentEnumStyle;
import guni.guflex.common.style.LengthPercentStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LengthPercentEnumParser implements IStyleParser {
    protected String propertyKey;
    protected String[] values;
    protected IStyleSpec defaultStyle;

    public LengthPercentEnumParser(String key, IStyleSpec defaultStyle, String... values){
        this.propertyKey = key;
        this.defaultStyle = defaultStyle;
        this.values = values;
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
        IStyleSpec spec;

        spec = parsePercent(data);
        if (spec != null) return spec;
        spec = parsePixel(data);
        if (spec != null) return spec;
        spec = parseEnum(data);
        if (spec != null) return spec;

        GuFlex.LOGGER.error("Impossible value! Cannot Parse!");
        return null;
    }

    protected IStyleSpec parsePercent(String data){
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+|\\d+)%");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()){
            String capturedNumbers = matcher.group(1);
            try {
                float value = Float.parseFloat(capturedNumbers);
                return new LengthPercentEnumStyle(value / 100f, LengthPercentEnumStyle.Type.Percent);
            }
            catch (Exception e){
                return null;
            }
        }
        return null;
    }

    protected IStyleSpec parsePixel(String data){
        String capturedNumbers = data.replace("px", "");
        try {
            int value = Integer.parseInt(capturedNumbers);
            return new LengthPercentEnumStyle(value, LengthPercentEnumStyle.Type.Pixel);
        }
        catch (Exception e){
            return null;
        }
    }

    protected IStyleSpec parseEnum(String data) {
        for (String value : values){
            if (data.equals(value)){
                return new LengthPercentEnumStyle(data, LengthPercentEnumStyle.Type.Keyword);
            }
        }
        return null;
    }
}
