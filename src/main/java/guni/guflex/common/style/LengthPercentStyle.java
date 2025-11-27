package guni.guflex.common.style;

import guni.guflex.api.style.IStyleSpec;

public class LengthPercentStyle implements IStyleSpec {
    @Override
    public Object getValue() {
        return data;
    }

    public enum Type{
        Pixel,
        Percent
    }
    public record Data(
            Type type,
            float value
            )
    {
        public int resolve(int parentAxisLength){
            switch (type) {
                case Type.Pixel -> {
                    return (int)value;
                }
                case Type.Percent -> {
                    return Math.max((int)(parentAxisLength * value), 0);
                }
            }
            return 0;
        }
    }

    protected Data data;

    public LengthPercentStyle(float value, Type type){
        this.data = new Data(type, value);
    }


}
