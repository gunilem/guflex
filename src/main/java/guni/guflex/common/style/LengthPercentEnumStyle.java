package guni.guflex.common.style;

import guni.guflex.api.style.IStyleSpec;

public class LengthPercentEnumStyle implements IStyleSpec {
    @Override
    public Object getValue() {
        return data;
    }

    public enum Type{
        Pixel,
        Percent,
        Keyword
    }
    public record Data(
            Type type,
            float floatValue,
            String stringValue
    )
    {
        public int resolve(int parentAxisLength){
            switch (type) {
                case Type.Pixel -> {
                    return (int)floatValue;
                }
                case Type.Percent -> {
                    return Math.max((int)(parentAxisLength * floatValue), 0);
                }
                case Type.Keyword -> {
                    return 0;
                }
            }
            return 0;
        }

        public boolean isString(String value){
            if (type != Type.Keyword) return false;
            return stringValue.equals(value);
        }
    }

    protected Data data;

    public LengthPercentEnumStyle(float value, Type type){
        this.data = new Data(type, value, "");
    }

    public LengthPercentEnumStyle(String value, Type type){
        this.data = new Data(type, 0, value);
    }


}