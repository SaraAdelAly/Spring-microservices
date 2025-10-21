package spring_microservice.mappers;

import org.mapstruct.*;
import spring_microservice.dto.PatientResponseDto;
import spring_microservice.entities.Patient;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {
    @Mapping(target = "id", expression = "java(stringToUuid(dto.getId()))")
//    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
//    @Mapping(source = "id", target = "id")
    @Mapping(target = "dateOfBirth", expression = "java(stringToLocalDate(dto.getDateOfBirth()))")
    Patient toEntity(PatientResponseDto dto);


//    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
//    @Mapping(source = "id", target = "id")
    @Mapping (target = "id", expression = "java(uuidToString(entity.getId()))")
    @Mapping (target = "dateOfBirth", expression = "java(localDateToString(entity.getDateOfBirth()))")
    PatientResponseDto toDto (Patient entity);

    default UUID stringToUuid(String id) {
        return id != null ? UUID.fromString(id) : null;
    }
    default String uuidToString(UUID id) {
        return id != null ? id.toString() : null;
    }
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
    Patient partialUpdate(PatientResponseDto patientDto, @MappingTarget Patient patient);
}
