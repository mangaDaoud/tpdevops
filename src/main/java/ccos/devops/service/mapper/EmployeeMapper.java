package ccos.devops.service.mapper;

import ccos.devops.domain.Employee;
import ccos.devops.domain.User;
import ccos.devops.service.dto.EmployeeDTO;
import ccos.devops.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    EmployeeDTO toDto(Employee s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    EmployeeDTO toDto(EmployeeDTO employeeDTO);
}
