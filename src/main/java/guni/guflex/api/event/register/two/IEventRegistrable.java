package guni.guflex.api.event.register.two;

public interface IEventRegistrable<T1, T2> {
    void invoke(T1 arg0, T2 arg2);
}
