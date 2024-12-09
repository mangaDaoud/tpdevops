package ccos.devops.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ccos.devops.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointageDTO.class);
        PointageDTO pointageDTO1 = new PointageDTO();
        pointageDTO1.setId(1L);
        PointageDTO pointageDTO2 = new PointageDTO();
        assertThat(pointageDTO1).isNotEqualTo(pointageDTO2);
        pointageDTO2.setId(pointageDTO1.getId());
        assertThat(pointageDTO1).isEqualTo(pointageDTO2);
        pointageDTO2.setId(2L);
        assertThat(pointageDTO1).isNotEqualTo(pointageDTO2);
        pointageDTO1.setId(null);
        assertThat(pointageDTO1).isNotEqualTo(pointageDTO2);
    }
}
