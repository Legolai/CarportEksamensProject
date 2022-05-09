package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Table;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Product;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductType;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

@JoinedEntity
@Table("Product")
@Join(tables = {"Product_type"}, joins = {"product_type_ID"})
public record ProductDTO(Product product, ProductType type) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) throws DatabaseException {
        if(entity instanceof ProductType foreignKey) {
            product.setProductTypeId(foreignKey.getId());
        }
    }
}
