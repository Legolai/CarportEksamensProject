package dk.cphbusiness.dat.carporteksamensproject.model.persistence.mappers;

import dk.cphbusiness.dat.carporteksamensproject.model.annotations.Column;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.BillOfMaterialLineItemDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.CarportDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.InquiryDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.dtos.PersonDTO;
import dk.cphbusiness.dat.carporteksamensproject.model.entities.Inquiry;
import dk.cphbusiness.dat.carporteksamensproject.model.exceptions.DatabaseException;
import dk.cphbusiness.dat.carporteksamensproject.model.persistence.manager.EntityManager;
import dk.cphbusiness.dat.carporteksamensproject.model.services.facade.PersonFacade;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InquiryMapper implements DataMapper<InquiryDTO> {

    private final EntityManager entityManager;

    public InquiryMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<InquiryDTO> find(Map<String, Object> properties) throws DatabaseException {
        return findAll(properties).orElseThrow().stream().findFirst();
    }

    @Override
    public List<InquiryDTO> getAll() throws DatabaseException {
        List<Inquiry> inquiries = entityManager.getAll(Inquiry.class);
        List<CarportDTO> carportDTOS = entityManager.getAll(CarportDTO.class);
        List<PersonDTO> personDTOS = PersonFacade.getAll(entityManager.connectionPool());
        List<BillOfMaterialLineItemDTO> billOfMaterialLineItemDTOS = entityManager.getAll(BillOfMaterialLineItemDTO.class);

        Map<Integer, BillOfMaterialDTO> bill = inquiries.stream().map(inquiry -> {
            List<BillOfMaterialLineItemDTO> lineItems = billOfMaterialLineItemDTOS.parallelStream().filter(billOfMaterialLineItemDTO -> billOfMaterialLineItemDTO.lineItem().getInquiryId() == inquiry.getId()).toList();
            return Map.entry(inquiry.getId(), new BillOfMaterialDTO(lineItems));
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<InquiryDTO> inquiryDTOS = new ArrayList<>();

        for (Inquiry inquiry : inquiries) {
            Optional<PersonDTO> personDTO = personDTOS.stream().filter(personDTO1 -> personDTO1.person().getId() == inquiry.getPersonId()).findFirst();
            if (personDTO.isEmpty()){
                throw new DatabaseException(String.format("Person for inquiry numbered %s could not be found!", inquiry.getId()));
            }

            Optional<CarportDTO> carportDTO = carportDTOS.stream().filter(carportDTO1 -> carportDTO1.carport().getId() == inquiry.getCarportId()).findFirst();
            if (carportDTO.isEmpty()){
                throw new DatabaseException(String.format("Carport for inquiry numbered %s could not be found!", inquiry.getId()));
            }

            Optional<BillOfMaterialDTO> billOfMaterialDTO = Optional.ofNullable(bill.get(inquiry.getId()));

            inquiryDTOS.add(new InquiryDTO(inquiry, personDTO.get(), billOfMaterialDTO, carportDTO.get()));
        }

        return inquiryDTOS;
    }

    @Override
    public Optional<List<InquiryDTO>> findAll(Map<String, Object> properties) throws DatabaseException {
        List<InquiryDTO> list = getAll();

        for (Map.Entry<String, Object> p: properties.entrySet()) {
            for (InquiryDTO inquiryDTO : list) {
                Field field = Arrays.stream(Inquiry.class.getDeclaredFields())
                        .filter(field1 -> field1.getAnnotation(Column.class).value().equals(p.getKey()))
                        .findAny().orElseThrow();
                String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                try{
                    Object value = Inquiry.class.getDeclaredMethod(methodName).invoke(inquiryDTO.inquiry());
                    if (!p.getValue().equals(value))
                        list.remove(inquiryDTO);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    throw new DatabaseException("Could not access field value by invoke!");
                }
            }
        }
        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }

    @Override
    public InquiryDTO insert(InquiryDTO inquiryDTO) throws DatabaseException {
        PersonDTO personDTO = inquiryDTO.person();
        CarportDTO carportDTO = inquiryDTO.carport();

        CarportDTO dbCarport = entityManager.insert(CarportDTO.class, carportDTO);
        inquiryDTO.updateForeignKey(dbCarport);
        PersonDTO dbPerson = entityManager.insert(PersonDTO.class, personDTO);
        inquiryDTO.updateForeignKey(dbPerson);
        Inquiry dbInquiry = entityManager.insert(Inquiry.class, inquiryDTO.inquiry());

        Optional<BillOfMaterialDTO> bill = inquiryDTO.billOfMaterial();
        if (bill.isEmpty()){
            return new InquiryDTO(dbInquiry, dbPerson, Optional.empty(), dbCarport);
        }

        bill.get().updateForeignKey(dbInquiry);
        List<BillOfMaterialLineItemDTO> lineItems = entityManager.insertBatch(BillOfMaterialLineItemDTO.class, bill.get().lineItems());
        BillOfMaterialDTO dbBill = new BillOfMaterialDTO(lineItems);

        return new InquiryDTO(dbInquiry, dbPerson, Optional.of(dbBill), dbCarport);
    }

    @Override
    public boolean delete(InquiryDTO inquiryDTO) throws DatabaseException {
        return false;
    }

    @Override
    public boolean update(InquiryDTO inquiryDTO) throws DatabaseException {
        return false;
    }
}
