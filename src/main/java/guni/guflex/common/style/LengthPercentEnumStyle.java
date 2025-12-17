package guni.guflex.common.style;

import guni.guflex.GuFlex;

public class LengthPercentEnumStyle {

    public enum Type{
        Pixel,
        Percent,
        Keyword
    }


    protected Type type;
    public Type getType(){
        return type;
    }
    protected Object value;

    public int resolve(int parentAxisLength){
        switch (type) {
            case Type.Pixel -> {
                return (int)value;
            }
            case Type.Percent -> {
                return (int)Math.max(parentAxisLength * (float)value, 0);
            }
        }
        GuFlex.LOGGER.error("Trying to resolve the size of an Enum Style");
        return -1;
    }

    public boolean isKeyword(String keyword){
        if (type != Type.Keyword) return false;
        return value.equals(keyword);
    }

    public LengthPercentEnumStyle(Type type, Object value){
        this.type = type;
        this.value = value;
    }
}