package guni.guflex.common.style.parser;

import guni.guflex.GuFlex;
import guni.guflex.api.style.IStyleParser;
import guni.guflex.api.style.IStyleSpec;
import guni.guflex.common.style.EnumStyle;

import java.util.function.Function;

public class EnumStyleParser {
    public static String parse(String data, String... values) {
        for (String value : values){
            if (data.equals(value)){
                return data;
            }
        }

        GuFlex.LOGGER.error("Impossible value! Cannot Parse!");
        return null;
    }
}
