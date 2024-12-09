package ccos.devops.service.mapper;

import ccos.devops.domain.Employee;
import ccos.devops.domain.Pointage;
import ccos.devops.service.dto.EmployeeDTO;
import ccos.devops.service.dto.PointageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pointage} and its DTO {@link PointageDTO}.
 */
@Mapper(componentModel = "spring")
public interface PointageMapper extends EntityMapper<PointageDTO, Pointage> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    PointageDTO toDto(Pointage s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
