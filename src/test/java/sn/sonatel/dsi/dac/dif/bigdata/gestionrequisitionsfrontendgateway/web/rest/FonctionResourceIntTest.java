package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Fonction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Monitor;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operande;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FonctionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FonctionSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FonctionService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FonctionCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FonctionQueryService;

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

/**
 * Test class for the FonctionResource REST controller.
 *
 * @see FonctionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class FonctionResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private FonctionRepository fonctionRepository;

    @Autowired
    private FonctionService fonctionService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FonctionSearchRepositoryMockConfiguration
     */
    @Autowired
    private FonctionSearchRepository mockFonctionSearchRepository;

    @Autowired
    private FonctionQueryService fonctionQueryService;

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

    private MockMvc restFonctionMockMvc;

    private Fonction fonction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FonctionResource fonctionResource = new FonctionResource(fonctionService, fonctionQueryService);
        this.restFonctionMockMvc = MockMvcBuilders.standaloneSetup(fonctionResource)
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
    public static Fonction createEntity(EntityManager em) {
        Fonction fonction = new Fonction()
            .nom(DEFAULT_NOM);
        return fonction;
    }

    @Before
    public void initTest() {
        fonction = createEntity(em);
    }

    @Test
    @Transactional
    public void createFonction() throws Exception {
        int databaseSizeBeforeCreate = fonctionRepository.findAll().size();

        // Create the Fonction
        restFonctionMockMvc.perform(post("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isCreated());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeCreate + 1);
        Fonction testFonction = fonctionList.get(fonctionList.size() - 1);
        assertThat(testFonction.getNom()).isEqualTo(DEFAULT_NOM);

        // Validate the Fonction in Elasticsearch
        verify(mockFonctionSearchRepository, times(1)).save(testFonction);
    }

    @Test
    @Transactional
    public void createFonctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fonctionRepository.findAll().size();

        // Create the Fonction with an existing ID
        fonction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFonctionMockMvc.perform(post("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isBadRequest());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Fonction in Elasticsearch
        verify(mockFonctionSearchRepository, times(0)).save(fonction);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = fonctionRepository.findAll().size();
        // set the field null
        fonction.setNom(null);

        // Create the Fonction, which fails.

        restFonctionMockMvc.perform(post("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isBadRequest());

        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFonctions() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList
        restFonctionMockMvc.perform(get("/api/fonctions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonction.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }
    
    @Test
    @Transactional
    public void getFonction() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get the fonction
        restFonctionMockMvc.perform(get("/api/fonctions/{id}", fonction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fonction.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getAllFonctionsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where nom equals to DEFAULT_NOM
        defaultFonctionShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the fonctionList where nom equals to UPDATED_NOM
        defaultFonctionShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllFonctionsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultFonctionShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the fonctionList where nom equals to UPDATED_NOM
        defaultFonctionShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllFonctionsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where nom is not null
        defaultFonctionShouldBeFound("nom.specified=true");

        // Get all the fonctionList where nom is null
        defaultFonctionShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllFonctionsByMonitorIsEqualToSomething() throws Exception {
        // Initialize the database
        Monitor monitor = MonitorResourceIntTest.createEntity(em);
        em.persist(monitor);
        em.flush();
        fonction.addMonitor(monitor);
        fonctionRepository.saveAndFlush(fonction);
        Long monitorId = monitor.getId();

        // Get all the fonctionList where monitor equals to monitorId
        defaultFonctionShouldBeFound("monitorId.equals=" + monitorId);

        // Get all the fonctionList where monitor equals to monitorId + 1
        defaultFonctionShouldNotBeFound("monitorId.equals=" + (monitorId + 1));
    }


    @Test
    @Transactional
    public void getAllFonctionsByOperandeIsEqualToSomething() throws Exception {
        // Initialize the database
        Operande operande = OperandeResourceIntTest.createEntity(em);
        em.persist(operande);
        em.flush();
        fonction.addOperande(operande);
        fonctionRepository.saveAndFlush(fonction);
        Long operandeId = operande.getId();

        // Get all the fonctionList where operande equals to operandeId
        defaultFonctionShouldBeFound("operandeId.equals=" + operandeId);

        // Get all the fonctionList where operande equals to operandeId + 1
        defaultFonctionShouldNotBeFound("operandeId.equals=" + (operandeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFonctionShouldBeFound(String filter) throws Exception {
        restFonctionMockMvc.perform(get("/api/fonctions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonction.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));

        // Check, that the count call also returns 1
        restFonctionMockMvc.perform(get("/api/fonctions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFonctionShouldNotBeFound(String filter) throws Exception {
        restFonctionMockMvc.perform(get("/api/fonctions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFonctionMockMvc.perform(get("/api/fonctions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFonction() throws Exception {
        // Get the fonction
        restFonctionMockMvc.perform(get("/api/fonctions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFonction() throws Exception {
        // Initialize the database
        fonctionService.save(fonction);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFonctionSearchRepository);

        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();

        // Update the fonction
        Fonction updatedFonction = fonctionRepository.findById(fonction.getId()).get();
        // Disconnect from session so that the updates on updatedFonction are not directly saved in db
        em.detach(updatedFonction);
        updatedFonction
            .nom(UPDATED_NOM);

        restFonctionMockMvc.perform(put("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFonction)))
            .andExpect(status().isOk());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
        Fonction testFonction = fonctionList.get(fonctionList.size() - 1);
        assertThat(testFonction.getNom()).isEqualTo(UPDATED_NOM);

        // Validate the Fonction in Elasticsearch
        verify(mockFonctionSearchRepository, times(1)).save(testFonction);
    }

    @Test
    @Transactional
    public void updateNonExistingFonction() throws Exception {
        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();

        // Create the Fonction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFonctionMockMvc.perform(put("/api/fonctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isBadRequest());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Fonction in Elasticsearch
        verify(mockFonctionSearchRepository, times(0)).save(fonction);
    }

    @Test
    @Transactional
    public void deleteFonction() throws Exception {
        // Initialize the database
        fonctionService.save(fonction);

        int databaseSizeBeforeDelete = fonctionRepository.findAll().size();

        // Delete the fonction
        restFonctionMockMvc.perform(delete("/api/fonctions/{id}", fonction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Fonction in Elasticsearch
        verify(mockFonctionSearchRepository, times(1)).deleteById(fonction.getId());
    }

    @Test
    @Transactional
    public void searchFonction() throws Exception {
        // Initialize the database
        fonctionService.save(fonction);
        when(mockFonctionSearchRepository.search(queryStringQuery("id:" + fonction.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fonction), PageRequest.of(0, 1), 1));
        // Search the fonction
        restFonctionMockMvc.perform(get("/api/_search/fonctions?query=id:" + fonction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonction.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fonction.class);
        Fonction fonction1 = new Fonction();
        fonction1.setId(1L);
        Fonction fonction2 = new Fonction();
        fonction2.setId(fonction1.getId());
        assertThat(fonction1).isEqualTo(fonction2);
        fonction2.setId(2L);
        assertThat(fonction1).isNotEqualTo(fonction2);
        fonction1.setId(null);
        assertThat(fonction1).isNotEqualTo(fonction2);
    }
}
