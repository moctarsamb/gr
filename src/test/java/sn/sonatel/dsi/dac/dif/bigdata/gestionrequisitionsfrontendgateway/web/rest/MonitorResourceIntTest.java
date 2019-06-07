package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Monitor;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Fonction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Dimension;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.MonitorRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.MonitorSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.MonitorService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.MonitorCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.MonitorQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.Collections;
import java.util.List;


import static sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.TypeMonitor;
/**
 * Test class for the MonitorResource REST controller.
 *
 * @see MonitorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class MonitorResourceIntTest {

    private static final TypeMonitor DEFAULT_TYPE = TypeMonitor.COLONNE;
    private static final TypeMonitor UPDATED_TYPE = TypeMonitor.FONCTION;

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private MonitorService monitorService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.MonitorSearchRepositoryMockConfiguration
     */
    @Autowired
    private MonitorSearchRepository mockMonitorSearchRepository;

    @Autowired
    private MonitorQueryService monitorQueryService;

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

    private MockMvc restMonitorMockMvc;

    private Monitor monitor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonitorResource monitorResource = new MonitorResource(monitorService, monitorQueryService);
        this.restMonitorMockMvc = MockMvcBuilders.standaloneSetup(monitorResource)
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
    public static Monitor createEntity(EntityManager em) {
        Monitor monitor = new Monitor()
            .type(DEFAULT_TYPE);
        return monitor;
    }

    @Before
    public void initTest() {
        monitor = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonitor() throws Exception {
        int databaseSizeBeforeCreate = monitorRepository.findAll().size();

        // Create the Monitor
        restMonitorMockMvc.perform(post("/api/monitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitor)))
            .andExpect(status().isCreated());

        // Validate the Monitor in the database
        List<Monitor> monitorList = monitorRepository.findAll();
        assertThat(monitorList).hasSize(databaseSizeBeforeCreate + 1);
        Monitor testMonitor = monitorList.get(monitorList.size() - 1);
        assertThat(testMonitor.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the Monitor in Elasticsearch
        verify(mockMonitorSearchRepository, times(1)).save(testMonitor);
    }

    @Test
    @Transactional
    public void createMonitorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monitorRepository.findAll().size();

        // Create the Monitor with an existing ID
        monitor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonitorMockMvc.perform(post("/api/monitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitor)))
            .andExpect(status().isBadRequest());

        // Validate the Monitor in the database
        List<Monitor> monitorList = monitorRepository.findAll();
        assertThat(monitorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Monitor in Elasticsearch
        verify(mockMonitorSearchRepository, times(0)).save(monitor);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = monitorRepository.findAll().size();
        // set the field null
        monitor.setType(null);

        // Create the Monitor, which fails.

        restMonitorMockMvc.perform(post("/api/monitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitor)))
            .andExpect(status().isBadRequest());

        List<Monitor> monitorList = monitorRepository.findAll();
        assertThat(monitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMonitors() throws Exception {
        // Initialize the database
        monitorRepository.saveAndFlush(monitor);

        // Get all the monitorList
        restMonitorMockMvc.perform(get("/api/monitors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getMonitor() throws Exception {
        // Initialize the database
        monitorRepository.saveAndFlush(monitor);

        // Get the monitor
        restMonitorMockMvc.perform(get("/api/monitors/{id}", monitor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monitor.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllMonitorsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        monitorRepository.saveAndFlush(monitor);

        // Get all the monitorList where type equals to DEFAULT_TYPE
        defaultMonitorShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the monitorList where type equals to UPDATED_TYPE
        defaultMonitorShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMonitorsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        monitorRepository.saveAndFlush(monitor);

        // Get all the monitorList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMonitorShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the monitorList where type equals to UPDATED_TYPE
        defaultMonitorShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMonitorsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        monitorRepository.saveAndFlush(monitor);

        // Get all the monitorList where type is not null
        defaultMonitorShouldBeFound("type.specified=true");

        // Get all the monitorList where type is null
        defaultMonitorShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonitorsByColonneIsEqualToSomething() throws Exception {
        // Initialize the database
        Colonne colonne = ColonneResourceIntTest.createEntity(em);
        em.persist(colonne);
        em.flush();
        monitor.setColonne(colonne);
        monitorRepository.saveAndFlush(monitor);
        Long colonneId = colonne.getId();

        // Get all the monitorList where colonne equals to colonneId
        defaultMonitorShouldBeFound("colonneId.equals=" + colonneId);

        // Get all the monitorList where colonne equals to colonneId + 1
        defaultMonitorShouldNotBeFound("colonneId.equals=" + (colonneId + 1));
    }


    @Test
    @Transactional
    public void getAllMonitorsByFonctionIsEqualToSomething() throws Exception {
        // Initialize the database
        Fonction fonction = FonctionResourceIntTest.createEntity(em);
        em.persist(fonction);
        em.flush();
        monitor.setFonction(fonction);
        monitorRepository.saveAndFlush(monitor);
        Long fonctionId = fonction.getId();

        // Get all the monitorList where fonction equals to fonctionId
        defaultMonitorShouldBeFound("fonctionId.equals=" + fonctionId);

        // Get all the monitorList where fonction equals to fonctionId + 1
        defaultMonitorShouldNotBeFound("fonctionId.equals=" + (fonctionId + 1));
    }


    @Test
    @Transactional
    public void getAllMonitorsByDimensionIsEqualToSomething() throws Exception {
        // Initialize the database
        Dimension dimension = DimensionResourceIntTest.createEntity(em);
        em.persist(dimension);
        em.flush();
        monitor.addDimension(dimension);
        monitorRepository.saveAndFlush(monitor);
        Long dimensionId = dimension.getId();

        // Get all the monitorList where dimension equals to dimensionId
        defaultMonitorShouldBeFound("dimensionId.equals=" + dimensionId);

        // Get all the monitorList where dimension equals to dimensionId + 1
        defaultMonitorShouldNotBeFound("dimensionId.equals=" + (dimensionId + 1));
    }


    @Test
    @Transactional
    public void getAllMonitorsByTypeExtractionIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeExtraction typeExtraction = TypeExtractionResourceIntTest.createEntity(em);
        em.persist(typeExtraction);
        em.flush();
        monitor.addTypeExtraction(typeExtraction);
        monitorRepository.saveAndFlush(monitor);
        Long typeExtractionId = typeExtraction.getId();

        // Get all the monitorList where typeExtraction equals to typeExtractionId
        defaultMonitorShouldBeFound("typeExtractionId.equals=" + typeExtractionId);

        // Get all the monitorList where typeExtraction equals to typeExtractionId + 1
        defaultMonitorShouldNotBeFound("typeExtractionId.equals=" + (typeExtractionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMonitorShouldBeFound(String filter) throws Exception {
        restMonitorMockMvc.perform(get("/api/monitors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

        // Check, that the count call also returns 1
        restMonitorMockMvc.perform(get("/api/monitors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMonitorShouldNotBeFound(String filter) throws Exception {
        restMonitorMockMvc.perform(get("/api/monitors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMonitorMockMvc.perform(get("/api/monitors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMonitor() throws Exception {
        // Get the monitor
        restMonitorMockMvc.perform(get("/api/monitors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonitor() throws Exception {
        // Initialize the database
        monitorService.save(monitor);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMonitorSearchRepository);

        int databaseSizeBeforeUpdate = monitorRepository.findAll().size();

        // Update the monitor
        Monitor updatedMonitor = monitorRepository.findById(monitor.getId()).get();
        // Disconnect from session so that the updates on updatedMonitor are not directly saved in db
        em.detach(updatedMonitor);
        updatedMonitor
            .type(UPDATED_TYPE);

        restMonitorMockMvc.perform(put("/api/monitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonitor)))
            .andExpect(status().isOk());

        // Validate the Monitor in the database
        List<Monitor> monitorList = monitorRepository.findAll();
        assertThat(monitorList).hasSize(databaseSizeBeforeUpdate);
        Monitor testMonitor = monitorList.get(monitorList.size() - 1);
        assertThat(testMonitor.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the Monitor in Elasticsearch
        verify(mockMonitorSearchRepository, times(1)).save(testMonitor);
    }

    @Test
    @Transactional
    public void updateNonExistingMonitor() throws Exception {
        int databaseSizeBeforeUpdate = monitorRepository.findAll().size();

        // Create the Monitor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonitorMockMvc.perform(put("/api/monitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitor)))
            .andExpect(status().isBadRequest());

        // Validate the Monitor in the database
        List<Monitor> monitorList = monitorRepository.findAll();
        assertThat(monitorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Monitor in Elasticsearch
        verify(mockMonitorSearchRepository, times(0)).save(monitor);
    }

    @Test
    @Transactional
    public void deleteMonitor() throws Exception {
        // Initialize the database
        monitorService.save(monitor);

        int databaseSizeBeforeDelete = monitorRepository.findAll().size();

        // Delete the monitor
        restMonitorMockMvc.perform(delete("/api/monitors/{id}", monitor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Monitor> monitorList = monitorRepository.findAll();
        assertThat(monitorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Monitor in Elasticsearch
        verify(mockMonitorSearchRepository, times(1)).deleteById(monitor.getId());
    }

    @Test
    @Transactional
    public void searchMonitor() throws Exception {
        // Initialize the database
        monitorService.save(monitor);
        when(mockMonitorSearchRepository.search(queryStringQuery("id:" + monitor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(monitor), PageRequest.of(0, 1), 1));
        // Search the monitor
        restMonitorMockMvc.perform(get("/api/_search/monitors?query=id:" + monitor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Monitor.class);
        Monitor monitor1 = new Monitor();
        monitor1.setId(1L);
        Monitor monitor2 = new Monitor();
        monitor2.setId(monitor1.getId());
        assertThat(monitor1).isEqualTo(monitor2);
        monitor2.setId(2L);
        assertThat(monitor1).isNotEqualTo(monitor2);
        monitor1.setId(null);
        assertThat(monitor1).isNotEqualTo(monitor2);
    }
}
