package guni.guflex.api.style;

import guni.guflex.api.utils.IRegistry;

import java.util.HashMap;
import java.util.Map;

public class StyleRegistry implements IRegistry<IStyleParser> {
    private static final StyleRegistry INSTANCE = new StyleRegistry();

    private final Map<String, IStyleParser> styles;

    public StyleRegistry(){
        styles = new HashMap<>();
    }

    public static StyleRegistry getRegistry(){
        return INSTANCE;
    }

    public void register(IStyleParser parser){
        styles.put(parser.getPropertyKey(), parser);
    }

    public IStyleParser get(String key){
        return styles.get(key);
    }

    public Map<String, IStyleSpec> defaultSpecs(){
        Map<String, IStyleSpec> style = new HashMap<>();

        for (String key : styles.keySet()){
            IStyleSpec spec = styles.get(key).getDefault();
            if (spec != null){
                style.put(key, spec);
            }
        }

        return style;
    }
}
