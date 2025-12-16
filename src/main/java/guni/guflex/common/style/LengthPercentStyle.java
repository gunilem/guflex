package guni.guflex.common.style;

public class LengthPercentStyle {
    public enum Type{
        Pixel,
        Percent
    }

    protected Type type;
    public Type getType(){
        return type;
    }
    protected float value;

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

    public LengthPercentStyle(float value, Type type){
        this.type = type;
        this.value = value;
    }


}
