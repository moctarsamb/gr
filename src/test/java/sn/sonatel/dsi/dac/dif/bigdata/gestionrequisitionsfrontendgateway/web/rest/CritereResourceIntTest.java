package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Critere;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Jointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Clause;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.OperateurLogique;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.CritereRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.CritereSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.CritereService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.CritereCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.CritereQueryService;

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
 * Test class for the CritereResource REST controller.
 *
 * @see CritereResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class CritereResourceIntTest {

    @Autowired
    private CritereRepository critereRepository;

    @Autowired
    private CritereService critereService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.CritereSearchRepositoryMockConfiguration
     */
    @Autowired
    private CritereSearchRepository mockCritereSearchRepository;

    @Autowired
    private CritereQueryService critereQueryService;

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

    private MockMvc restCritereMockMvc;

    private Critere critere;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CritereResource critereResource = new CritereResource(critereService, critereQueryService);
        this.restCritereMockMvc = MockMvcBuilders.standaloneSetup(critereResource)
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
    public static Critere createEntity(EntityManager em) {
        Critere critere = new Critere();
        return critere;
    }

    @Before
    public void initTest() {
        critere = createEntity(em);
    }

    @Test
    @Transactional
    public void createCritere() throws Exception {
        int databaseSizeBeforeCreate = critereRepository.findAll().size();

        // Create the Critere
        restCritereMockMvc.perform(post("/api/criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critere)))
            .andExpect(status().isCreated());

        // Validate the Critere in the database
        List<Critere> critereList = critereRepository.findAll();
        assertThat(critereList).hasSize(databaseSizeBeforeCreate + 1);
        Critere testCritere = critereList.get(critereList.size() - 1);

        // Validate the Critere in Elasticsearch
        verify(mockCritereSearchRepository, times(1)).save(testCritere);
    }

    @Test
    @Transactional
    public void createCritereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = critereRepository.findAll().size();

        // Create the Critere with an existing ID
        critere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCritereMockMvc.perform(post("/api/criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critere)))
            .andExpect(status().isBadRequest());

        // Validate the Critere in the database
        List<Critere> critereList = critereRepository.findAll();
        assertThat(critereList).hasSize(databaseSizeBeforeCreate);

        // Validate the Critere in Elasticsearch
        verify(mockCritereSearchRepository, times(0)).save(critere);
    }

    @Test
    @Transactional
    public void getAllCriteres() throws Exception {
        // Initialize the database
        critereRepository.saveAndFlush(critere);

        // Get all the critereList
        restCritereMockMvc.perform(get("/api/criteres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(critere.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCritere() throws Exception {
        // Initialize the database
        critereRepository.saveAndFlush(critere);

        // Get the critere
        restCritereMockMvc.perform(get("/api/criteres/{id}", critere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(critere.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllCriteresByJointureIsEqualToSomething() throws Exception {
        // Initialize the database
        Jointure jointure = JointureResourceIntTest.createEntity(em);
        em.persist(jointure);
        em.flush();
        critere.addJointure(jointure);
        critereRepository.saveAndFlush(critere);
        Long jointureId = jointure.getId();

        // Get all the critereList where jointure equals to jointureId
        defaultCritereShouldBeFound("jointureId.equals=" + jointureId);

        // Get all the critereList where jointure equals to jointureId + 1
        defaultCritereShouldNotBeFound("jointureId.equals=" + (jointureId + 1));
    }


    @Test
    @Transactional
    public void getAllCriteresByClauseIsEqualToSomething() throws Exception {
        // Initialize the database
        Clause clause = ClauseResourceIntTest.createEntity(em);
        em.persist(clause);
        em.flush();
        critere.setClause(clause);
        critereRepository.saveAndFlush(critere);
        Long clauseId = clause.getId();

        // Get all the critereList where clause equals to clauseId
        defaultCritereShouldBeFound("clauseId.equals=" + clauseId);

        // Get all the critereList where clause equals to clauseId + 1
        defaultCritereShouldNotBeFound("clauseId.equals=" + (clauseId + 1));
    }


    @Test
    @Transactional
    public void getAllCriteresByOperateurLogiqueIsEqualToSomething() throws Exception {
        // Initialize the database
        OperateurLogique operateurLogique = OperateurLogiqueResourceIntTest.createEntity(em);
        em.persist(operateurLogique);
        em.flush();
        critere.setOperateurLogique(operateurLogique);
        critereRepository.saveAndFlush(critere);
        Long operateurLogiqueId = operateurLogique.getId();

        // Get all the critereList where operateurLogique equals to operateurLogiqueId
        defaultCritereShouldBeFound("operateurLogiqueId.equals=" + operateurLogiqueId);

        // Get all the critereList where operateurLogique equals to operateurLogiqueId + 1
        defaultCritereShouldNotBeFound("operateurLogiqueId.equals=" + (operateurLogiqueId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCritereShouldBeFound(String filter) throws Exception {
        restCritereMockMvc.perform(get("/api/criteres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(critere.getId().intValue())));

        // Check, that the count call also returns 1
        restCritereMockMvc.perform(get("/api/criteres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCritereShouldNotBeFound(String filter) throws Exception {
        restCritereMockMvc.perform(get("/api/criteres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCritereMockMvc.perform(get("/api/criteres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCritere() throws Exception {
        // Get the critere
        restCritereMockMvc.perform(get("/api/criteres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCritere() throws Exception {
        // Initialize the database
        critereService.save(critere);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCritereSearchRepository);

        int databaseSizeBeforeUpdate = critereRepository.findAll().size();

        // Update the critere
        Critere updatedCritere = critereRepository.findById(critere.getId()).get();
        // Disconnect from session so that the updates on updatedCritere are not directly saved in db
        em.detach(updatedCritere);

        restCritereMockMvc.perform(put("/api/criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCritere)))
            .andExpect(status().isOk());

        // Validate the Critere in the database
        List<Critere> critereList = critereRepository.findAll();
        assertThat(critereList).hasSize(databaseSizeBeforeUpdate);
        Critere testCritere = critereList.get(critereList.size() - 1);

        // Validate the Critere in Elasticsearch
        verify(mockCritereSearchRepository, times(1)).save(testCritere);
    }

    @Test
    @Transactional
    public void updateNonExistingCritere() throws Exception {
        int databaseSizeBeforeUpdate = critereRepository.findAll().size();

        // Create the Critere

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCritereMockMvc.perform(put("/api/criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critere)))
            .andExpect(status().isBadRequest());

        // Validate the Critere in the database
        List<Critere> critereList = critereRepository.findAll();
        assertThat(critereList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Critere in Elasticsearch
        verify(mockCritereSearchRepository, times(0)).save(critere);
    }

    @Test
    @Transactional
    public void deleteCritere() throws Exception {
        // Initialize the database
        critereService.save(critere);

        int databaseSizeBeforeDelete = critereRepository.findAll().size();

        // Delete the critere
        restCritereMockMvc.perform(delete("/api/criteres/{id}", critere.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Critere> critereList = critereRepository.findAll();
        assertThat(critereList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Critere in Elasticsearch
        verify(mockCritereSearchRepository, times(1)).deleteById(critere.getId());
    }

    @Test
    @Transactional
    public void searchCritere() throws Exception {
        // Initialize the database
        critereService.save(critere);
        when(mockCritereSearchRepository.search(queryStringQuery("id:" + critere.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(critere), PageRequest.of(0, 1), 1));
        // Search the critere
        restCritereMockMvc.perform(get("/api/_search/criteres?query=id:" + critere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(critere.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Critere.class);
        Critere critere1 = new Critere();
        critere1.setId(1L);
        Critere critere2 = new Critere();
        critere2.setId(critere1.getId());
        assertThat(critere1).isEqualTo(critere2);
        critere2.setId(2L);
        assertThat(critere1).isNotEqualTo(critere2);
        critere1.setId(null);
        assertThat(critere1).isNotEqualTo(critere2);
    }
}
