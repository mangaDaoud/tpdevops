package ccos.devops.web.rest;

import static ccos.devops.domain.PointageAsserts.*;
import static ccos.devops.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ccos.devops.IntegrationTest;
import ccos.devops.domain.Pointage;
import ccos.devops.repository.PointageRepository;
import ccos.devops.service.dto.PointageDTO;
import ccos.devops.service.mapper.PointageMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PointageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointageResourceIT {

    private static final LocalDate DEFAULT_DATU_DU_JOUR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATU_DU_JOUR = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HEURE_ARRIVE = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_ARRIVE = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_DEPART = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_DEPART = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pointages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PointageRepository pointageRepository;

    @Autowired
    private PointageMapper pointageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointageMockMvc;

    private Pointage pointage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pointage createEntity(EntityManager em) {
        Pointage pointage = new Pointage()
            .datuDuJour(DEFAULT_DATU_DU_JOUR)
            .heureArrive(DEFAULT_HEURE_ARRIVE)
            .heureDepart(DEFAULT_HEURE_DEPART);
        return pointage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pointage createUpdatedEntity(EntityManager em) {
        Pointage pointage = new Pointage()
            .datuDuJour(UPDATED_DATU_DU_JOUR)
            .heureArrive(UPDATED_HEURE_ARRIVE)
            .heureDepart(UPDATED_HEURE_DEPART);
        return pointage;
    }

    @BeforeEach
    public void initTest() {
        pointage = createEntity(em);
    }

    @Test
    @Transactional
    void createPointage() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pointage
        PointageDTO pointageDTO = pointageMapper.toDto(pointage);
        var returnedPointageDTO = om.readValue(
            restPointageMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointageDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PointageDTO.class
        );

        // Validate the Pointage in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPointage = pointageMapper.toEntity(returnedPointageDTO);
        assertPointageUpdatableFieldsEquals(returnedPointage, getPersistedPointage(returnedPointage));
    }

    @Test
    @Transactional
    void createPointageWithExistingId() throws Exception {
        // Create the Pointage with an existing ID
        pointage.setId(1L);
        PointageDTO pointageDTO = pointageMapper.toDto(pointage);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pointage in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPointages() throws Exception {
        // Initialize the database
        pointageRepository.saveAndFlush(pointage);

        // Get all the pointageList
        restPointageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointage.getId().intValue())))
            .andExpect(jsonPath("$.[*].datuDuJour").value(hasItem(DEFAULT_DATU_DU_JOUR.toString())))
            .andExpect(jsonPath("$.[*].heureArrive").value(hasItem(DEFAULT_HEURE_ARRIVE)))
            .andExpect(jsonPath("$.[*].heureDepart").value(hasItem(DEFAULT_HEURE_DEPART)));
    }

    @Test
    @Transactional
    void getPointage() throws Exception {
        // Initialize the database
        pointageRepository.saveAndFlush(pointage);

        // Get the pointage
        restPointageMockMvc
            .perform(get(ENTITY_API_URL_ID, pointage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointage.getId().intValue()))
            .andExpect(jsonPath("$.datuDuJour").value(DEFAULT_DATU_DU_JOUR.toString()))
            .andExpect(jsonPath("$.heureArrive").value(DEFAULT_HEURE_ARRIVE))
            .andExpect(jsonPath("$.heureDepart").value(DEFAULT_HEURE_DEPART));
    }

    @Test
    @Transactional
    void getNonExistingPointage() throws Exception {
        // Get the pointage
        restPointageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPointage() throws Exception {
        // Initialize the database
        pointageRepository.saveAndFlush(pointage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointage
        Pointage updatedPointage = pointageRepository.findById(pointage.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPointage are not directly saved in db
        em.detach(updatedPointage);
        updatedPointage.datuDuJour(UPDATED_DATU_DU_JOUR).heureArrive(UPDATED_HEURE_ARRIVE).heureDepart(UPDATED_HEURE_DEPART);
        PointageDTO pointageDTO = pointageMapper.toDto(updatedPointage);

        restPointageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pointageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pointage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPointageToMatchAllProperties(updatedPointage);
    }

    @Test
    @Transactional
    void putNonExistingPointage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointage.setId(longCount.incrementAndGet());

        // Create the Pointage
        PointageDTO pointageDTO = pointageMapper.toDto(pointage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pointageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pointage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPointage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointage.setId(longCount.incrementAndGet());

        // Create the Pointage
        PointageDTO pointageDTO = pointageMapper.toDto(pointage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pointageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pointage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPointage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointage.setId(longCount.incrementAndGet());

        // Create the Pointage
        PointageDTO pointageDTO = pointageMapper.toDto(pointage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pointageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pointage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointageWithPatch() throws Exception {
        // Initialize the database
        pointageRepository.saveAndFlush(pointage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointage using partial update
        Pointage partialUpdatedPointage = new Pointage();
        partialUpdatedPointage.setId(pointage.getId());

        partialUpdatedPointage.heureArrive(UPDATED_HEURE_ARRIVE);

        restPointageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPointage))
            )
            .andExpect(status().isOk());

        // Validate the Pointage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPointageUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPointage, pointage), getPersistedPointage(pointage));
    }

    @Test
    @Transactional
    void fullUpdatePointageWithPatch() throws Exception {
        // Initialize the database
        pointageRepository.saveAndFlush(pointage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pointage using partial update
        Pointage partialUpdatedPointage = new Pointage();
        partialUpdatedPointage.setId(pointage.getId());

        partialUpdatedPointage.datuDuJour(UPDATED_DATU_DU_JOUR).heureArrive(UPDATED_HEURE_ARRIVE).heureDepart(UPDATED_HEURE_DEPART);

        restPointageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPointage))
            )
            .andExpect(status().isOk());

        // Validate the Pointage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPointageUpdatableFieldsEquals(partialUpdatedPointage, getPersistedPointage(partialUpdatedPointage));
    }

    @Test
    @Transactional
    void patchNonExistingPointage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointage.setId(longCount.incrementAndGet());

        // Create the Pointage
        PointageDTO pointageDTO = pointageMapper.toDto(pointage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pointageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pointage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPointage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointage.setId(longCount.incrementAndGet());

        // Create the Pointage
        PointageDTO pointageDTO = pointageMapper.toDto(pointage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pointageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pointage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPointage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pointage.setId(longCount.incrementAndGet());

        // Create the Pointage
        PointageDTO pointageDTO = pointageMapper.toDto(pointage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pointageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pointage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePointage() throws Exception {
        // Initialize the database
        pointageRepository.saveAndFlush(pointage);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pointage
        restPointageMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pointageRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Pointage getPersistedPointage(Pointage pointage) {
        return pointageRepository.findById(pointage.getId()).orElseThrow();
    }

    protected void assertPersistedPointageToMatchAllProperties(Pointage expectedPointage) {
        assertPointageAllPropertiesEquals(expectedPointage, getPersistedPointage(expectedPointage));
    }

    protected void assertPersistedPointageToMatchUpdatableProperties(Pointage expectedPointage) {
        assertPointageAllUpdatablePropertiesEquals(expectedPointage, getPersistedPointage(expectedPointage));
    }
}
