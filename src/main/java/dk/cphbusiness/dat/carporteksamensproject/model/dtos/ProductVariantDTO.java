package dk.cphbusiness.dat.carporteksamensproject.model.dtos;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Join;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.JoinedEntity;
import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Table;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.ProductVariant;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Size;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.interfaces.IForeignKey;


@JoinedEntity
@Table("Product_variant")
@Join(tables = {"Product","Size","Product_type"} , joins = {"product_ID","size_ID","product_type_ID"})
public record ProductVariantDTO(ProductVariant variant, ProductDTO product, Size size) implements IForeignKey {
    @Override
    public void updateForeignKey(Object entity) throws DatabaseException {
        if (entity instanceof PersonDTO foreignKey) {
            variant.setProductId(foreignKey.person().getId());
        } else if (entity instanceof Size foreignKey) {
            variant.setSizeId(foreignKey.getId());
        }

        ;
    }
}
