package guni.guflex.common.style;

import guni.guflex.api.style.IStyleSpec;

public class EnumStyle implements IStyleSpec {
    @Override
    public Object getValue() {
        return value;
    }

    protected String value;

    public EnumStyle(String value){
        this.value = value;
    }
}
