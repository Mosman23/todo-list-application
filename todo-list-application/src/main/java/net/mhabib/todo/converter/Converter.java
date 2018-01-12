package net.mhabib.todo.converter;

public interface Converter<I, E> {

    E internalToExternal(I i);

    I externalToInternal(E e);

}
