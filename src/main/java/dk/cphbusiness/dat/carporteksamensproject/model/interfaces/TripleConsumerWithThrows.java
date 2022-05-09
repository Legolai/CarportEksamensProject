package dk.cphbusiness.dat.carporteksamensproject.model.interfaces;

public interface TripleConsumerWithThrows<T,U,P>{
    Void apply(T t, U u, P p) throws Exception;
}
