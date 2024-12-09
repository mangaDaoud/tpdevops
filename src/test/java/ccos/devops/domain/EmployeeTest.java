package ccos.devops.domain;

import static ccos.devops.domain.EmployeeTestSamples.*;
import static ccos.devops.domain.PointageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ccos.devops.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void pointageTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Pointage pointageBack = getPointageRandomSampleGenerator();

        employee.addPointage(pointageBack);
        assertThat(employee.getPointages()).containsOnly(pointageBack);
        assertThat(pointageBack.getEmployee()).isEqualTo(employee);

        employee.removePointage(pointageBack);
        assertThat(employee.getPointages()).doesNotContain(pointageBack);
        assertThat(pointageBack.getEmployee()).isNull();

        employee.pointages(new HashSet<>(Set.of(pointageBack)));
        assertThat(employee.getPointages()).containsOnly(pointageBack);
        assertThat(pointageBack.getEmployee()).isEqualTo(employee);

        employee.setPointages(new HashSet<>());
        assertThat(employee.getPointages()).doesNotContain(pointageBack);
        assertThat(pointageBack.getEmployee()).isNull();
    }
}
