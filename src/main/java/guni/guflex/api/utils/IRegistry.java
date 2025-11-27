package guni.guflex.api.utils;

public interface IRegistry<T> {
    void register(T obj);
    T get(String key);
}
