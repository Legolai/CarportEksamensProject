package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Product;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductType;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;

@JoinedEntity
@Join(main = Product.class, join = {ProductType.class})
public record ProductDTO(Product product, ProductType type) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) {
        if(entity instanceof ProductType foreignKey) {
            product.setProductTypeId(foreignKey.getId());
        }
    }
}
