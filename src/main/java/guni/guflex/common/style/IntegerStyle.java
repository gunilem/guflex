package guni.guflex.common.style;

import guni.guflex.api.style.IStyleSpec;

public class IntegerStyle implements IStyleSpec {
    @Override
    public Integer getValue() {
        return value;
    }

    protected Integer value;

    public IntegerStyle(Integer value){
        this.value = value;
    }
}
