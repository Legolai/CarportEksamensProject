package dk.cphbusiness.dat.carporteksamensproject.model.interfaces;

import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;

@FunctionalInterface
public interface IForeignKey {
  void updateForeignKey(Object entity) throws DatabaseException;
}
