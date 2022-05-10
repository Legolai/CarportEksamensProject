package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Product;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductType;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductVariant;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Size;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;


@JoinedEntity
@Join(main = ProductVariant.class, join = {Product.class, ProductType.class, Size.class})
public record ProductVariantDTO(ProductVariant variant, ProductDTO product, Size size) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) {
        if (entity instanceof PersonDTO foreignKey) {
            variant.setProductId(foreignKey.person().getId());
        } else if (entity instanceof Size foreignKey) {
            variant.setSizeId(foreignKey.getId());
        }
    }
}
