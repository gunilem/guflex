package guni.guflex.common.style.parser;

import guni.guflex.GuFlex;
import guni.guflex.api.style.IStyleParser;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.common.style.LengthPercentStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LengthPercentParser {

    public static LengthPercentStyle parse(String data) {
        LengthPercentStyle spec;

        spec = parsePercent(data);
        if (spec != null) return spec;
        spec = parsePixel(data);
        if (spec != null) return spec;

        GuFlex.LOGGER.error("Impossible value! Cannot Parse!");
        return null;
    }

    protected static LengthPercentStyle parsePercent(String data){
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+|\\d+)%");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()){
            String capturedNumbers = matcher.group(1);
            try {
                float value = Float.parseFloat(capturedNumbers);
                return new LengthPercentStyle(value / 100f, LengthPercentStyle.Type.Percent);
            }
            catch (Exception e){
                return null;
            }
        }
        return null;
    }

    protected static LengthPercentStyle parsePixel(String data){
        String capturedNumbers = data.replace("px", "");
        try {
            int value = Integer.parseInt(capturedNumbers);
            return new LengthPercentStyle(value, LengthPercentStyle.Type.Pixel);
        }
        catch (Exception e){
            return null;
        }
    }
}
