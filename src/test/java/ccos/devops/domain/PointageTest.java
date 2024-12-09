package ccos.devops.domain;

import static ccos.devops.domain.EmployeeTestSamples.*;
import static ccos.devops.domain.PointageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ccos.devops.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pointage.class);
        Pointage pointage1 = getPointageSample1();
        Pointage pointage2 = new Pointage();
        assertThat(pointage1).isNotEqualTo(pointage2);

        pointage2.setId(pointage1.getId());
        assertThat(pointage1).isEqualTo(pointage2);

        pointage2 = getPointageSample2();
        assertThat(pointage1).isNotEqualTo(pointage2);
    }

    @Test
    void employeeTest() throws Exception {
        Pointage pointage = getPointageRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        pointage.setEmployee(employeeBack);
        assertThat(pointage.getEmployee()).isEqualTo(employeeBack);

        pointage.employee(null);
        assertThat(pointage.getEmployee()).isNull();
    }
}
