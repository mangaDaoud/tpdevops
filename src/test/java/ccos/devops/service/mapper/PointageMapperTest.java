package ccos.devops.service.mapper;

import static ccos.devops.domain.PointageAsserts.*;
import static ccos.devops.domain.PointageTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointageMapperTest {

    private PointageMapper pointageMapper;

    @BeforeEach
    void setUp() {
        pointageMapper = new PointageMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPointageSample1();
        var actual = pointageMapper.toEntity(pointageMapper.toDto(expected));
        assertPointageAllPropertiesEquals(expected, actual);
    }
}
