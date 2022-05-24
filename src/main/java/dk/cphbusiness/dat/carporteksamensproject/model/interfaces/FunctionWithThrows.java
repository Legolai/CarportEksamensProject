package dk.cphbusiness.dat.carporteksamensproject.model.interfaces;

public interface FunctionWithThrows<T, R> {
    R apply(T t) throws Exception;
}
