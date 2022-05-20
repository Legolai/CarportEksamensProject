package dk.cphbusiness.dat.carporteksamensproject.model.services.facade;

import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.ConnectionPool;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers.BillOfMaterialMapper;

public class BillOfMaterialFacade {

    private BillOfMaterialFacade() {}

    public static boolean resetBillForInquiry(int inquiryId, BillOfMaterialDTO bill, ConnectionPool connectionPool) throws DatabaseException {
        BillOfMaterialMapper billOfMaterialMapper = new BillOfMaterialMapper(new EntityManager(connectionPool));
        boolean hasBeenDeleted = billOfMaterialMapper.deleteBillForInquiry(inquiryId) != -1;
        boolean hasBeenInsert = billOfMaterialMapper.insert(bill).lineItems().size() == bill.lineItems().size();
        return hasBeenDeleted && hasBeenInsert;
    }
}
