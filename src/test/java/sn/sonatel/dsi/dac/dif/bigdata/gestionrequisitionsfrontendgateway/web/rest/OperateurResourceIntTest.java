package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Clause;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.OperateurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperateurSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperateurService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.OperateurCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperateurQueryService;

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
 * Test class for the OperateurResource REST controller.
 *
 * @see OperateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class OperateurResourceIntTest {

    private static final String DEFAULT_OPERATEUR = "AAAAAAAAAA";
    private static final String UPDATED_OPERATEUR = "BBBBBBBBBB";

    @Autowired
    private OperateurRepository operateurRepository;

    @Autowired
    private OperateurService operateurService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperateurSearchRepositoryMockConfiguration
     */
    @Autowired
    private OperateurSearchRepository mockOperateurSearchRepository;

    @Autowired
    private OperateurQueryService operateurQueryService;

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

    private MockMvc restOperateurMockMvc;

    private Operateur operateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperateurResource operateurResource = new OperateurResource(operateurService, operateurQueryService);
        this.restOperateurMockMvc = MockMvcBuilders.standaloneSetup(operateurResource)
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
    public static Operateur createEntity(EntityManager em) {
        Operateur operateur = new Operateur()
            .operateur(DEFAULT_OPERATEUR);
        return operateur;
    }

    @Before
    public void initTest() {
        operateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperateur() throws Exception {
        int databaseSizeBeforeCreate = operateurRepository.findAll().size();

        // Create the Operateur
        restOperateurMockMvc.perform(post("/api/operateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operateur)))
            .andExpect(status().isCreated());

        // Validate the Operateur in the database
        List<Operateur> operateurList = operateurRepository.findAll();
        assertThat(operateurList).hasSize(databaseSizeBeforeCreate + 1);
        Operateur testOperateur = operateurList.get(operateurList.size() - 1);
        assertThat(testOperateur.getOperateur()).isEqualTo(DEFAULT_OPERATEUR);

        // Validate the Operateur in Elasticsearch
        verify(mockOperateurSearchRepository, times(1)).save(testOperateur);
    }

    @Test
    @Transactional
    public void createOperateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operateurRepository.findAll().size();

        // Create the Operateur with an existing ID
        operateur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperateurMockMvc.perform(post("/api/operateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operateur)))
            .andExpect(status().isBadRequest());

        // Validate the Operateur in the database
        List<Operateur> operateurList = operateurRepository.findAll();
        assertThat(operateurList).hasSize(databaseSizeBeforeCreate);

        // Validate the Operateur in Elasticsearch
        verify(mockOperateurSearchRepository, times(0)).save(operateur);
    }

    @Test
    @Transactional
    public void checkOperateurIsRequired() throws Exception {
        int databaseSizeBeforeTest = operateurRepository.findAll().size();
        // set the field null
        operateur.setOperateur(null);

        // Create the Operateur, which fails.

        restOperateurMockMvc.perform(post("/api/operateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operateur)))
            .andExpect(status().isBadRequest());

        List<Operateur> operateurList = operateurRepository.findAll();
        assertThat(operateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperateurs() throws Exception {
        // Initialize the database
        operateurRepository.saveAndFlush(operateur);

        // Get all the operateurList
        restOperateurMockMvc.perform(get("/api/operateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].operateur").value(hasItem(DEFAULT_OPERATEUR.toString())));
    }
    
    @Test
    @Transactional
    public void getOperateur() throws Exception {
        // Initialize the database
        operateurRepository.saveAndFlush(operateur);

        // Get the operateur
        restOperateurMockMvc.perform(get("/api/operateurs/{id}", operateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operateur.getId().intValue()))
            .andExpect(jsonPath("$.operateur").value(DEFAULT_OPERATEUR.toString()));
    }

    @Test
    @Transactional
    public void getAllOperateursByOperateurIsEqualToSomething() throws Exception {
        // Initialize the database
        operateurRepository.saveAndFlush(operateur);

        // Get all the operateurList where operateur equals to DEFAULT_OPERATEUR
        defaultOperateurShouldBeFound("operateur.equals=" + DEFAULT_OPERATEUR);

        // Get all the operateurList where operateur equals to UPDATED_OPERATEUR
        defaultOperateurShouldNotBeFound("operateur.equals=" + UPDATED_OPERATEUR);
    }

    @Test
    @Transactional
    public void getAllOperateursByOperateurIsInShouldWork() throws Exception {
        // Initialize the database
        operateurRepository.saveAndFlush(operateur);

        // Get all the operateurList where operateur in DEFAULT_OPERATEUR or UPDATED_OPERATEUR
        defaultOperateurShouldBeFound("operateur.in=" + DEFAULT_OPERATEUR + "," + UPDATED_OPERATEUR);

        // Get all the operateurList where operateur equals to UPDATED_OPERATEUR
        defaultOperateurShouldNotBeFound("operateur.in=" + UPDATED_OPERATEUR);
    }

    @Test
    @Transactional
    public void getAllOperateursByOperateurIsNullOrNotNull() throws Exception {
        // Initialize the database
        operateurRepository.saveAndFlush(operateur);

        // Get all the operateurList where operateur is not null
        defaultOperateurShouldBeFound("operateur.specified=true");

        // Get all the operateurList where operateur is null
        defaultOperateurShouldNotBeFound("operateur.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperateursByClauseIsEqualToSomething() throws Exception {
        // Initialize the database
        Clause clause = ClauseResourceIntTest.createEntity(em);
        em.persist(clause);
        em.flush();
        operateur.addClause(clause);
        operateurRepository.saveAndFlush(operateur);
        Long clauseId = clause.getId();

        // Get all the operateurList where clause equals to clauseId
        defaultOperateurShouldBeFound("clauseId.equals=" + clauseId);

        // Get all the operateurList where clause equals to clauseId + 1
        defaultOperateurShouldNotBeFound("clauseId.equals=" + (clauseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOperateurShouldBeFound(String filter) throws Exception {
        restOperateurMockMvc.perform(get("/api/operateurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].operateur").value(hasItem(DEFAULT_OPERATEUR)));

        // Check, that the count call also returns 1
        restOperateurMockMvc.perform(get("/api/operateurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOperateurShouldNotBeFound(String filter) throws Exception {
        restOperateurMockMvc.perform(get("/api/operateurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOperateurMockMvc.perform(get("/api/operateurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOperateur() throws Exception {
        // Get the operateur
        restOperateurMockMvc.perform(get("/api/operateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperateur() throws Exception {
        // Initialize the database
        operateurService.save(operateur);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockOperateurSearchRepository);

        int databaseSizeBeforeUpdate = operateurRepository.findAll().size();

        // Update the operateur
        Operateur updatedOperateur = operateurRepository.findById(operateur.getId()).get();
        // Disconnect from session so that the updates on updatedOperateur are not directly saved in db
        em.detach(updatedOperateur);
        updatedOperateur
            .operateur(UPDATED_OPERATEUR);

        restOperateurMockMvc.perform(put("/api/operateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperateur)))
            .andExpect(status().isOk());

        // Validate the Operateur in the database
        List<Operateur> operateurList = operateurRepository.findAll();
        assertThat(operateurList).hasSize(databaseSizeBeforeUpdate);
        Operateur testOperateur = operateurList.get(operateurList.size() - 1);
        assertThat(testOperateur.getOperateur()).isEqualTo(UPDATED_OPERATEUR);

        // Validate the Operateur in Elasticsearch
        verify(mockOperateurSearchRepository, times(1)).save(testOperateur);
    }

    @Test
    @Transactional
    public void updateNonExistingOperateur() throws Exception {
        int databaseSizeBeforeUpdate = operateurRepository.findAll().size();

        // Create the Operateur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperateurMockMvc.perform(put("/api/operateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operateur)))
            .andExpect(status().isBadRequest());

        // Validate the Operateur in the database
        List<Operateur> operateurList = operateurRepository.findAll();
        assertThat(operateurList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Operateur in Elasticsearch
        verify(mockOperateurSearchRepository, times(0)).save(operateur);
    }

    @Test
    @Transactional
    public void deleteOperateur() throws Exception {
        // Initialize the database
        operateurService.save(operateur);

        int databaseSizeBeforeDelete = operateurRepository.findAll().size();

        // Delete the operateur
        restOperateurMockMvc.perform(delete("/api/operateurs/{id}", operateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Operateur> operateurList = operateurRepository.findAll();
        assertThat(operateurList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Operateur in Elasticsearch
        verify(mockOperateurSearchRepository, times(1)).deleteById(operateur.getId());
    }

    @Test
    @Transactional
    public void searchOperateur() throws Exception {
        // Initialize the database
        operateurService.save(operateur);
        when(mockOperateurSearchRepository.search(queryStringQuery("id:" + operateur.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(operateur), PageRequest.of(0, 1), 1));
        // Search the operateur
        restOperateurMockMvc.perform(get("/api/_search/operateurs?query=id:" + operateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].operateur").value(hasItem(DEFAULT_OPERATEUR)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operateur.class);
        Operateur operateur1 = new Operateur();
        operateur1.setId(1L);
        Operateur operateur2 = new Operateur();
        operateur2.setId(operateur1.getId());
        assertThat(operateur1).isEqualTo(operateur2);
        operateur2.setId(2L);
        assertThat(operateur1).isNotEqualTo(operateur2);
        operateur1.setId(null);
        assertThat(operateur1).isNotEqualTo(operateur2);
    }
}
