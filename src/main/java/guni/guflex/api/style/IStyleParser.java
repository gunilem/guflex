package guni.guflex.api.style;

public interface IStyleParser {
    String getPropertyKey();
    IStyleSpec getDefault();
    IStyleSpec parse(String data);
}
