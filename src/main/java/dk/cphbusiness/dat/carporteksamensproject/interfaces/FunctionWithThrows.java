package dk.cphbusiness.dat.carporteksamensproject.interfaces;



@FunctionalInterface
public interface FunctionWithThrows<T, R> {
   R apply(T t) throws Exception;
}
