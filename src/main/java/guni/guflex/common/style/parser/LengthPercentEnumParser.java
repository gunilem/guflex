package guni.guflex.common.style.parser;

import guni.guflex.GuFlex;
import guni.guflex.api.style.IStyleParser;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.common.style.EnumStyle;
import guni.guflex.common.style.LengthPercentEnumStyle;
import guni.guflex.common.style.LengthPercentStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LengthPercentEnumParser {
    public static LengthPercentEnumStyle parse(String data, String... values) {
        LengthPercentEnumStyle spec;

        spec = parsePercent(data);
        if (spec != null) return spec;
        spec = parsePixel(data);
        if (spec != null) return spec;
        spec = parseEnum(data, values);
        if (spec != null) return spec;

        GuFlex.LOGGER.error("Impossible value! Cannot Parse!");
        return null;
    }

    protected static LengthPercentEnumStyle parsePercent(String data){
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+|\\d+)%");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()){
            String capturedNumbers = matcher.group(1);
            try {
                float value = Float.parseFloat(capturedNumbers);
                return new LengthPercentEnumStyle(LengthPercentEnumStyle.Type.Percent, value / 100f);
            }
            catch (Exception e){
                return null;
            }
        }
        return null;
    }

    protected static LengthPercentEnumStyle parsePixel(String data){
        String capturedNumbers = data.replace("px", "");
        try {
            int value = Integer.parseInt(capturedNumbers);
            return new LengthPercentEnumStyle(LengthPercentEnumStyle.Type.Pixel, value);
        }
        catch (Exception e){
            return null;
        }
    }

    protected static LengthPercentEnumStyle parseEnum(String data, String... values) {
        for (String value : values){
            if (data.equals(value)){
                return new LengthPercentEnumStyle(LengthPercentEnumStyle.Type.Keyword, data);
            }
        }
        return null;
    }
}
