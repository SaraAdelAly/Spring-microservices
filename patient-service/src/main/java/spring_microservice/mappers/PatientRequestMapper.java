package spring_microservice.mappers;

import org.mapstruct.*;
import spring_microservice.dto.PatientRequestDto;
import spring_microservice.entities.Patient;

import java.time.LocalDate;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientRequestMapper {

//    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
//    @Mapping(source = "registeredDate", target = "registeredDate")
    @Mapping(target = "dateOfBirth", expression = "java(stringToLocalDate(dto.getDateOfBirth()))")
    @Mapping(target = "registeredDate", expression = "java(stringToLocalDate(dto.getRegisteredDate()))")
    Patient toEntity(PatientRequestDto dto);

//    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
//    @Mapping(source = "registeredDate", target = "registeredDate")
    @Mapping (target = "dateOfBirth", expression = "java(localDateToString(entity.getDateOfBirth()))")
    @Mapping(target = "registeredDate", expression = "java(localDateToString(entity.getRegisteredDate()))")
    PatientRequestDto toDto (Patient entity);


    default LocalDate stringToLocalDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        return LocalDate.parse(date);
    }

    default String localDateToString(LocalDate date) {
        return date != null ? date.toString() : null;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Patient partialUpdate(PatientRequestDto patientRequestDto, @MappingTarget Patient patient);

}
