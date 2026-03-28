package guni.guflex.api.event.register;

public interface IEventRegistrable2<T1, T2> {
    void invoke(T1 arg0, T2 arg2);
}
