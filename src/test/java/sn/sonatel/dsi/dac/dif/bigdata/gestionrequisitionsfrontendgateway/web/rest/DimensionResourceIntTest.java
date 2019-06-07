package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Dimension;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Monitor;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Base;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Flux;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.DimensionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.DimensionSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.DimensionService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.DimensionCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.DimensionQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DimensionResource REST controller.
 *
 * @see DimensionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class DimensionResourceIntTest {

    @Autowired
    private DimensionRepository dimensionRepository;

    @Mock
    private DimensionRepository dimensionRepositoryMock;

    @Mock
    private DimensionService dimensionServiceMock;

    @Autowired
    private DimensionService dimensionService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.DimensionSearchRepositoryMockConfiguration
     */
    @Autowired
    private DimensionSearchRepository mockDimensionSearchRepository;

    @Autowired
    private DimensionQueryService dimensionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDimensionMockMvc;

    private Dimension dimension;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DimensionResource dimensionResource = new DimensionResource(dimensionService, dimensionQueryService);
        this.restDimensionMockMvc = MockMvcBuilders.standaloneSetup(dimensionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dimension createEntity(EntityManager em) {
        Dimension dimension = new Dimension();
        return dimension;
    }

    @Before
    public void initTest() {
        dimension = createEntity(em);
    }

    @Test
    @Transactional
    public void createDimension() throws Exception {
        int databaseSizeBeforeCreate = dimensionRepository.findAll().size();

        // Create the Dimension
        restDimensionMockMvc.perform(post("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isCreated());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeCreate + 1);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);

        // Validate the Dimension in Elasticsearch
        verify(mockDimensionSearchRepository, times(1)).save(testDimension);
    }

    @Test
    @Transactional
    public void createDimensionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dimensionRepository.findAll().size();

        // Create the Dimension with an existing ID
        dimension.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDimensionMockMvc.perform(post("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Dimension in Elasticsearch
        verify(mockDimensionSearchRepository, times(0)).save(dimension);
    }

    @Test
    @Transactional
    public void getAllDimensions() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList
        restDimensionMockMvc.perform(get("/api/dimensions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimension.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDimensionsWithEagerRelationshipsIsEnabled() throws Exception {
        DimensionResource dimensionResource = new DimensionResource(dimensionServiceMock, dimensionQueryService);
        when(dimensionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDimensionMockMvc = MockMvcBuilders.standaloneSetup(dimensionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDimensionMockMvc.perform(get("/api/dimensions?eagerload=true"))
        .andExpect(status().isOk());

        verify(dimensionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDimensionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        DimensionResource dimensionResource = new DimensionResource(dimensionServiceMock, dimensionQueryService);
            when(dimensionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDimensionMockMvc = MockMvcBuilders.standaloneSetup(dimensionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDimensionMockMvc.perform(get("/api/dimensions?eagerload=true"))
        .andExpect(status().isOk());

            verify(dimensionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get the dimension
        restDimensionMockMvc.perform(get("/api/dimensions/{id}", dimension.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dimension.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllDimensionsByMonitorIsEqualToSomething() throws Exception {
        // Initialize the database
        Monitor monitor = MonitorResourceIntTest.createEntity(em);
        em.persist(monitor);
        em.flush();
        dimension.addMonitor(monitor);
        dimensionRepository.saveAndFlush(dimension);
        Long monitorId = monitor.getId();

        // Get all the dimensionList where monitor equals to monitorId
        defaultDimensionShouldBeFound("monitorId.equals=" + monitorId);

        // Get all the dimensionList where monitor equals to monitorId + 1
        defaultDimensionShouldNotBeFound("monitorId.equals=" + (monitorId + 1));
    }


    @Test
    @Transactional
    public void getAllDimensionsByBaseIsEqualToSomething() throws Exception {
        // Initialize the database
        Base base = BaseResourceIntTest.createEntity(em);
        em.persist(base);
        em.flush();
        dimension.setBase(base);
        dimensionRepository.saveAndFlush(dimension);
        Long baseId = base.getId();

        // Get all the dimensionList where base equals to baseId
        defaultDimensionShouldBeFound("baseId.equals=" + baseId);

        // Get all the dimensionList where base equals to baseId + 1
        defaultDimensionShouldNotBeFound("baseId.equals=" + (baseId + 1));
    }


    @Test
    @Transactional
    public void getAllDimensionsByFluxIsEqualToSomething() throws Exception {
        // Initialize the database
        Flux flux = FluxResourceIntTest.createEntity(em);
        em.persist(flux);
        em.flush();
        dimension.setFlux(flux);
        dimensionRepository.saveAndFlush(dimension);
        Long fluxId = flux.getId();

        // Get all the dimensionList where flux equals to fluxId
        defaultDimensionShouldBeFound("fluxId.equals=" + fluxId);

        // Get all the dimensionList where flux equals to fluxId + 1
        defaultDimensionShouldNotBeFound("fluxId.equals=" + (fluxId + 1));
    }


    @Test
    @Transactional
    public void getAllDimensionsByTypeExtractionIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeExtraction typeExtraction = TypeExtractionResourceIntTest.createEntity(em);
        em.persist(typeExtraction);
        em.flush();
        dimension.setTypeExtraction(typeExtraction);
        dimensionRepository.saveAndFlush(dimension);
        Long typeExtractionId = typeExtraction.getId();

        // Get all the dimensionList where typeExtraction equals to typeExtractionId
        defaultDimensionShouldBeFound("typeExtractionId.equals=" + typeExtractionId);

        // Get all the dimensionList where typeExtraction equals to typeExtractionId + 1
        defaultDimensionShouldNotBeFound("typeExtractionId.equals=" + (typeExtractionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDimensionShouldBeFound(String filter) throws Exception {
        restDimensionMockMvc.perform(get("/api/dimensions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimension.getId().intValue())));

        // Check, that the count call also returns 1
        restDimensionMockMvc.perform(get("/api/dimensions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDimensionShouldNotBeFound(String filter) throws Exception {
        restDimensionMockMvc.perform(get("/api/dimensions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDimensionMockMvc.perform(get("/api/dimensions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDimension() throws Exception {
        // Get the dimension
        restDimensionMockMvc.perform(get("/api/dimensions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDimension() throws Exception {
        // Initialize the database
        dimensionService.save(dimension);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDimensionSearchRepository);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension
        Dimension updatedDimension = dimensionRepository.findById(dimension.getId()).get();
        // Disconnect from session so that the updates on updatedDimension are not directly saved in db
        em.detach(updatedDimension);

        restDimensionMockMvc.perform(put("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDimension)))
            .andExpect(status().isOk());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);

        // Validate the Dimension in Elasticsearch
        verify(mockDimensionSearchRepository, times(1)).save(testDimension);
    }

    @Test
    @Transactional
    public void updateNonExistingDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Create the Dimension

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDimensionMockMvc.perform(put("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dimension in Elasticsearch
        verify(mockDimensionSearchRepository, times(0)).save(dimension);
    }

    @Test
    @Transactional
    public void deleteDimension() throws Exception {
        // Initialize the database
        dimensionService.save(dimension);

        int databaseSizeBeforeDelete = dimensionRepository.findAll().size();

        // Delete the dimension
        restDimensionMockMvc.perform(delete("/api/dimensions/{id}", dimension.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Dimension in Elasticsearch
        verify(mockDimensionSearchRepository, times(1)).deleteById(dimension.getId());
    }

    @Test
    @Transactional
    public void searchDimension() throws Exception {
        // Initialize the database
        dimensionService.save(dimension);
        when(mockDimensionSearchRepository.search(queryStringQuery("id:" + dimension.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dimension), PageRequest.of(0, 1), 1));
        // Search the dimension
        restDimensionMockMvc.perform(get("/api/_search/dimensions?query=id:" + dimension.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimension.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dimension.class);
        Dimension dimension1 = new Dimension();
        dimension1.setId(1L);
        Dimension dimension2 = new Dimension();
        dimension2.setId(dimension1.getId());
        assertThat(dimension1).isEqualTo(dimension2);
        dimension2.setId(2L);
        assertThat(dimension1).isNotEqualTo(dimension2);
        dimension1.setId(null);
        assertThat(dimension1).isNotEqualTo(dimension2);
    }
}
