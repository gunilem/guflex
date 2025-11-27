package guni.guflex.common.style.parser;

import guni.guflex.GuFlex;
import guni.guflex.api.style.IStyleParser;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.common.style.LengthPercentStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LengthPercentParser implements IStyleParser {
    protected String propertyKey;
    protected IStyleSpec defaultStyle;

    public LengthPercentParser(String key, IStyleSpec defaultStyle){
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
        IStyleSpec spec;

        spec = parsePercent(data);
        if (spec != null) return spec;
        spec = parsePixel(data);
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
                return new LengthPercentStyle(value / 100f, LengthPercentStyle.Type.Percent);
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
            return new LengthPercentStyle(value, LengthPercentStyle.Type.Pixel);
        }
        catch (Exception e){
            return null;
        }
    }
}
