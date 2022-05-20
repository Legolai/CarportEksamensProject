package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.BillOfMaterialLineItem;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BillOfMaterialMapper implements DataMapper<BillOfMaterialDTO> {

    private final EntityManager entityManager;

    public BillOfMaterialMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<BillOfMaterialDTO> find(Map<String, Object> properties) throws DatabaseException {
        return Optional.empty();
    }

    @Override
    public List<BillOfMaterialDTO> getAll() throws DatabaseException {
        return null;
    }

    @Override
    public Optional<List<BillOfMaterialDTO>> findAll(Map<String, Object> properties) throws DatabaseException {
        return Optional.empty();
    }

    @Override
    public BillOfMaterialDTO insert(BillOfMaterialDTO billOfMaterialDTO) throws DatabaseException {
        List<BillOfMaterialLineItemDTO> itemDTOList = entityManager.insertBatch(BillOfMaterialLineItemDTO.class, billOfMaterialDTO.lineItems());
        return new BillOfMaterialDTO(itemDTOList);
    }

    @Override
    public boolean delete(BillOfMaterialDTO billOfMaterialDTO) throws DatabaseException {

        return false;
    }

    @Override
    public boolean update(BillOfMaterialDTO billOfMaterialDTO) throws DatabaseException {
        return false;
    }

    public int deleteBillForInquiry(int inquiryId) throws DatabaseException {
        return entityManager.delete(BillOfMaterialLineItem.class, Map.of("inquiry_ID", inquiryId));
    }
}
