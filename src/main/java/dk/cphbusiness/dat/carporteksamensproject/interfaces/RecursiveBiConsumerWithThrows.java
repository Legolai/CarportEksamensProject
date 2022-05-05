package dk.cphbusiness.dat.carporteksamensproject.interfaces;

import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;

@FunctionalInterface
public interface RecursiveBiConsumerWithThrows<T, U> {
    void accept(final T t, final U u, final RecursiveBiConsumerWithThrows<T, U> self) throws DatabaseException;
}
