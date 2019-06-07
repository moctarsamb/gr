package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Clause;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Critere;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operande;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filtre;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ClauseRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ClauseSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ClauseService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ClauseCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ClauseQueryService;

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
 * Test class for the ClauseResource REST controller.
 *
 * @see ClauseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class ClauseResourceIntTest {

    private static final String DEFAULT_PREFIXE = "AAAAAAAAAA";
    private static final String UPDATED_PREFIXE = "BBBBBBBBBB";

    private static final String DEFAULT_SUFFIXE = "AAAAAAAAAA";
    private static final String UPDATED_SUFFIXE = "BBBBBBBBBB";

    @Autowired
    private ClauseRepository clauseRepository;

    @Mock
    private ClauseRepository clauseRepositoryMock;

    @Mock
    private ClauseService clauseServiceMock;

    @Autowired
    private ClauseService clauseService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ClauseSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClauseSearchRepository mockClauseSearchRepository;

    @Autowired
    private ClauseQueryService clauseQueryService;

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

    private MockMvc restClauseMockMvc;

    private Clause clause;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClauseResource clauseResource = new ClauseResource(clauseService, clauseQueryService);
        this.restClauseMockMvc = MockMvcBuilders.standaloneSetup(clauseResource)
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
    public static Clause createEntity(EntityManager em) {
        Clause clause = new Clause()
            .prefixe(DEFAULT_PREFIXE)
            .suffixe(DEFAULT_SUFFIXE);
        return clause;
    }

    @Before
    public void initTest() {
        clause = createEntity(em);
    }

    @Test
    @Transactional
    public void createClause() throws Exception {
        int databaseSizeBeforeCreate = clauseRepository.findAll().size();

        // Create the Clause
        restClauseMockMvc.perform(post("/api/clauses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clause)))
            .andExpect(status().isCreated());

        // Validate the Clause in the database
        List<Clause> clauseList = clauseRepository.findAll();
        assertThat(clauseList).hasSize(databaseSizeBeforeCreate + 1);
        Clause testClause = clauseList.get(clauseList.size() - 1);
        assertThat(testClause.getPrefixe()).isEqualTo(DEFAULT_PREFIXE);
        assertThat(testClause.getSuffixe()).isEqualTo(DEFAULT_SUFFIXE);

        // Validate the Clause in Elasticsearch
        verify(mockClauseSearchRepository, times(1)).save(testClause);
    }

    @Test
    @Transactional
    public void createClauseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clauseRepository.findAll().size();

        // Create the Clause with an existing ID
        clause.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClauseMockMvc.perform(post("/api/clauses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clause)))
            .andExpect(status().isBadRequest());

        // Validate the Clause in the database
        List<Clause> clauseList = clauseRepository.findAll();
        assertThat(clauseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Clause in Elasticsearch
        verify(mockClauseSearchRepository, times(0)).save(clause);
    }

    @Test
    @Transactional
    public void getAllClauses() throws Exception {
        // Initialize the database
        clauseRepository.saveAndFlush(clause);

        // Get all the clauseList
        restClauseMockMvc.perform(get("/api/clauses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clause.getId().intValue())))
            .andExpect(jsonPath("$.[*].prefixe").value(hasItem(DEFAULT_PREFIXE.toString())))
            .andExpect(jsonPath("$.[*].suffixe").value(hasItem(DEFAULT_SUFFIXE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllClausesWithEagerRelationshipsIsEnabled() throws Exception {
        ClauseResource clauseResource = new ClauseResource(clauseServiceMock, clauseQueryService);
        when(clauseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restClauseMockMvc = MockMvcBuilders.standaloneSetup(clauseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restClauseMockMvc.perform(get("/api/clauses?eagerload=true"))
        .andExpect(status().isOk());

        verify(clauseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllClausesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ClauseResource clauseResource = new ClauseResource(clauseServiceMock, clauseQueryService);
            when(clauseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restClauseMockMvc = MockMvcBuilders.standaloneSetup(clauseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restClauseMockMvc.perform(get("/api/clauses?eagerload=true"))
        .andExpect(status().isOk());

            verify(clauseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getClause() throws Exception {
        // Initialize the database
        clauseRepository.saveAndFlush(clause);

        // Get the clause
        restClauseMockMvc.perform(get("/api/clauses/{id}", clause.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clause.getId().intValue()))
            .andExpect(jsonPath("$.prefixe").value(DEFAULT_PREFIXE.toString()))
            .andExpect(jsonPath("$.suffixe").value(DEFAULT_SUFFIXE.toString()));
    }

    @Test
    @Transactional
    public void getAllClausesByPrefixeIsEqualToSomething() throws Exception {
        // Initialize the database
        clauseRepository.saveAndFlush(clause);

        // Get all the clauseList where prefixe equals to DEFAULT_PREFIXE
        defaultClauseShouldBeFound("prefixe.equals=" + DEFAULT_PREFIXE);

        // Get all the clauseList where prefixe equals to UPDATED_PREFIXE
        defaultClauseShouldNotBeFound("prefixe.equals=" + UPDATED_PREFIXE);
    }

    @Test
    @Transactional
    public void getAllClausesByPrefixeIsInShouldWork() throws Exception {
        // Initialize the database
        clauseRepository.saveAndFlush(clause);

        // Get all the clauseList where prefixe in DEFAULT_PREFIXE or UPDATED_PREFIXE
        defaultClauseShouldBeFound("prefixe.in=" + DEFAULT_PREFIXE + "," + UPDATED_PREFIXE);

        // Get all the clauseList where prefixe equals to UPDATED_PREFIXE
        defaultClauseShouldNotBeFound("prefixe.in=" + UPDATED_PREFIXE);
    }

    @Test
    @Transactional
    public void getAllClausesByPrefixeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clauseRepository.saveAndFlush(clause);

        // Get all the clauseList where prefixe is not null
        defaultClauseShouldBeFound("prefixe.specified=true");

        // Get all the clauseList where prefixe is null
        defaultClauseShouldNotBeFound("prefixe.specified=false");
    }

    @Test
    @Transactional
    public void getAllClausesBySuffixeIsEqualToSomething() throws Exception {
        // Initialize the database
        clauseRepository.saveAndFlush(clause);

        // Get all the clauseList where suffixe equals to DEFAULT_SUFFIXE
        defaultClauseShouldBeFound("suffixe.equals=" + DEFAULT_SUFFIXE);

        // Get all the clauseList where suffixe equals to UPDATED_SUFFIXE
        defaultClauseShouldNotBeFound("suffixe.equals=" + UPDATED_SUFFIXE);
    }

    @Test
    @Transactional
    public void getAllClausesBySuffixeIsInShouldWork() throws Exception {
        // Initialize the database
        clauseRepository.saveAndFlush(clause);

        // Get all the clauseList where suffixe in DEFAULT_SUFFIXE or UPDATED_SUFFIXE
        defaultClauseShouldBeFound("suffixe.in=" + DEFAULT_SUFFIXE + "," + UPDATED_SUFFIXE);

        // Get all the clauseList where suffixe equals to UPDATED_SUFFIXE
        defaultClauseShouldNotBeFound("suffixe.in=" + UPDATED_SUFFIXE);
    }

    @Test
    @Transactional
    public void getAllClausesBySuffixeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clauseRepository.saveAndFlush(clause);

        // Get all the clauseList where suffixe is not null
        defaultClauseShouldBeFound("suffixe.specified=true");

        // Get all the clauseList where suffixe is null
        defaultClauseShouldNotBeFound("suffixe.specified=false");
    }

    @Test
    @Transactional
    public void getAllClausesByCritereIsEqualToSomething() throws Exception {
        // Initialize the database
        Critere critere = CritereResourceIntTest.createEntity(em);
        em.persist(critere);
        em.flush();
        clause.addCritere(critere);
        clauseRepository.saveAndFlush(clause);
        Long critereId = critere.getId();

        // Get all the clauseList where critere equals to critereId
        defaultClauseShouldBeFound("critereId.equals=" + critereId);

        // Get all the clauseList where critere equals to critereId + 1
        defaultClauseShouldNotBeFound("critereId.equals=" + (critereId + 1));
    }


    @Test
    @Transactional
    public void getAllClausesByOperandeIsEqualToSomething() throws Exception {
        // Initialize the database
        Operande operande = OperandeResourceIntTest.createEntity(em);
        em.persist(operande);
        em.flush();
        clause.addOperande(operande);
        clauseRepository.saveAndFlush(clause);
        Long operandeId = operande.getId();

        // Get all the clauseList where operande equals to operandeId
        defaultClauseShouldBeFound("operandeId.equals=" + operandeId);

        // Get all the clauseList where operande equals to operandeId + 1
        defaultClauseShouldNotBeFound("operandeId.equals=" + (operandeId + 1));
    }


    @Test
    @Transactional
    public void getAllClausesByOperateurIsEqualToSomething() throws Exception {
        // Initialize the database
        Operateur operateur = OperateurResourceIntTest.createEntity(em);
        em.persist(operateur);
        em.flush();
        clause.setOperateur(operateur);
        clauseRepository.saveAndFlush(clause);
        Long operateurId = operateur.getId();

        // Get all the clauseList where operateur equals to operateurId
        defaultClauseShouldBeFound("operateurId.equals=" + operateurId);

        // Get all the clauseList where operateur equals to operateurId + 1
        defaultClauseShouldNotBeFound("operateurId.equals=" + (operateurId + 1));
    }


    @Test
    @Transactional
    public void getAllClausesByFiltreIsEqualToSomething() throws Exception {
        // Initialize the database
        Filtre filtre = FiltreResourceIntTest.createEntity(em);
        em.persist(filtre);
        em.flush();
        clause.addFiltre(filtre);
        clauseRepository.saveAndFlush(clause);
        Long filtreId = filtre.getId();

        // Get all the clauseList where filtre equals to filtreId
        defaultClauseShouldBeFound("filtreId.equals=" + filtreId);

        // Get all the clauseList where filtre equals to filtreId + 1
        defaultClauseShouldNotBeFound("filtreId.equals=" + (filtreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClauseShouldBeFound(String filter) throws Exception {
        restClauseMockMvc.perform(get("/api/clauses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clause.getId().intValue())))
            .andExpect(jsonPath("$.[*].prefixe").value(hasItem(DEFAULT_PREFIXE)))
            .andExpect(jsonPath("$.[*].suffixe").value(hasItem(DEFAULT_SUFFIXE)));

        // Check, that the count call also returns 1
        restClauseMockMvc.perform(get("/api/clauses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClauseShouldNotBeFound(String filter) throws Exception {
        restClauseMockMvc.perform(get("/api/clauses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClauseMockMvc.perform(get("/api/clauses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingClause() throws Exception {
        // Get the clause
        restClauseMockMvc.perform(get("/api/clauses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClause() throws Exception {
        // Initialize the database
        clauseService.save(clause);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockClauseSearchRepository);

        int databaseSizeBeforeUpdate = clauseRepository.findAll().size();

        // Update the clause
        Clause updatedClause = clauseRepository.findById(clause.getId()).get();
        // Disconnect from session so that the updates on updatedClause are not directly saved in db
        em.detach(updatedClause);
        updatedClause
            .prefixe(UPDATED_PREFIXE)
            .suffixe(UPDATED_SUFFIXE);

        restClauseMockMvc.perform(put("/api/clauses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClause)))
            .andExpect(status().isOk());

        // Validate the Clause in the database
        List<Clause> clauseList = clauseRepository.findAll();
        assertThat(clauseList).hasSize(databaseSizeBeforeUpdate);
        Clause testClause = clauseList.get(clauseList.size() - 1);
        assertThat(testClause.getPrefixe()).isEqualTo(UPDATED_PREFIXE);
        assertThat(testClause.getSuffixe()).isEqualTo(UPDATED_SUFFIXE);

        // Validate the Clause in Elasticsearch
        verify(mockClauseSearchRepository, times(1)).save(testClause);
    }

    @Test
    @Transactional
    public void updateNonExistingClause() throws Exception {
        int databaseSizeBeforeUpdate = clauseRepository.findAll().size();

        // Create the Clause

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClauseMockMvc.perform(put("/api/clauses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clause)))
            .andExpect(status().isBadRequest());

        // Validate the Clause in the database
        List<Clause> clauseList = clauseRepository.findAll();
        assertThat(clauseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Clause in Elasticsearch
        verify(mockClauseSearchRepository, times(0)).save(clause);
    }

    @Test
    @Transactional
    public void deleteClause() throws Exception {
        // Initialize the database
        clauseService.save(clause);

        int databaseSizeBeforeDelete = clauseRepository.findAll().size();

        // Delete the clause
        restClauseMockMvc.perform(delete("/api/clauses/{id}", clause.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clause> clauseList = clauseRepository.findAll();
        assertThat(clauseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Clause in Elasticsearch
        verify(mockClauseSearchRepository, times(1)).deleteById(clause.getId());
    }

    @Test
    @Transactional
    public void searchClause() throws Exception {
        // Initialize the database
        clauseService.save(clause);
        when(mockClauseSearchRepository.search(queryStringQuery("id:" + clause.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(clause), PageRequest.of(0, 1), 1));
        // Search the clause
        restClauseMockMvc.perform(get("/api/_search/clauses?query=id:" + clause.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clause.getId().intValue())))
            .andExpect(jsonPath("$.[*].prefixe").value(hasItem(DEFAULT_PREFIXE)))
            .andExpect(jsonPath("$.[*].suffixe").value(hasItem(DEFAULT_SUFFIXE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clause.class);
        Clause clause1 = new Clause();
        clause1.setId(1L);
        Clause clause2 = new Clause();
        clause2.setId(clause1.getId());
        assertThat(clause1).isEqualTo(clause2);
        clause2.setId(2L);
        assertThat(clause1).isNotEqualTo(clause2);
        clause1.setId(null);
        assertThat(clause1).isNotEqualTo(clause2);
    }
}
