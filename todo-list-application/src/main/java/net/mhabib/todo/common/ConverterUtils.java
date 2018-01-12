package net.mhabib.todo.common;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.mhabib.todo.converter.Converter;

public class ConverterUtils {

    public static <I, E, C extends Converter<I, E>> List<E> mapInternalToExternal(final Collection<I> entitiesDO, final C mapper) {
        return entitiesDO.stream().map(mapper::internalToExternal).collect(Collectors.toList());
    }

}
