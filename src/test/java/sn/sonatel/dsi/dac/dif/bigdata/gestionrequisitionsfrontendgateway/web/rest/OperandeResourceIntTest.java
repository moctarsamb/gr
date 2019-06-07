package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operande;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Valeur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Fonction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Clause;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.OperandeRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperandeSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperandeService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.OperandeCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperandeQueryService;

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
 * Test class for the OperandeResource REST controller.
 *
 * @see OperandeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class OperandeResourceIntTest {

    @Autowired
    private OperandeRepository operandeRepository;

    @Mock
    private OperandeRepository operandeRepositoryMock;

    @Mock
    private OperandeService operandeServiceMock;

    @Autowired
    private OperandeService operandeService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperandeSearchRepositoryMockConfiguration
     */
    @Autowired
    private OperandeSearchRepository mockOperandeSearchRepository;

    @Autowired
    private OperandeQueryService operandeQueryService;

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

    private MockMvc restOperandeMockMvc;

    private Operande operande;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperandeResource operandeResource = new OperandeResource(operandeService, operandeQueryService);
        this.restOperandeMockMvc = MockMvcBuilders.standaloneSetup(operandeResource)
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
    public static Operande createEntity(EntityManager em) {
        Operande operande = new Operande();
        return operande;
    }

    @Before
    public void initTest() {
        operande = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperande() throws Exception {
        int databaseSizeBeforeCreate = operandeRepository.findAll().size();

        // Create the Operande
        restOperandeMockMvc.perform(post("/api/operandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operande)))
            .andExpect(status().isCreated());

        // Validate the Operande in the database
        List<Operande> operandeList = operandeRepository.findAll();
        assertThat(operandeList).hasSize(databaseSizeBeforeCreate + 1);
        Operande testOperande = operandeList.get(operandeList.size() - 1);

        // Validate the Operande in Elasticsearch
        verify(mockOperandeSearchRepository, times(1)).save(testOperande);
    }

    @Test
    @Transactional
    public void createOperandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operandeRepository.findAll().size();

        // Create the Operande with an existing ID
        operande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperandeMockMvc.perform(post("/api/operandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operande)))
            .andExpect(status().isBadRequest());

        // Validate the Operande in the database
        List<Operande> operandeList = operandeRepository.findAll();
        assertThat(operandeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Operande in Elasticsearch
        verify(mockOperandeSearchRepository, times(0)).save(operande);
    }

    @Test
    @Transactional
    public void getAllOperandes() throws Exception {
        // Initialize the database
        operandeRepository.saveAndFlush(operande);

        // Get all the operandeList
        restOperandeMockMvc.perform(get("/api/operandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operande.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllOperandesWithEagerRelationshipsIsEnabled() throws Exception {
        OperandeResource operandeResource = new OperandeResource(operandeServiceMock, operandeQueryService);
        when(operandeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restOperandeMockMvc = MockMvcBuilders.standaloneSetup(operandeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOperandeMockMvc.perform(get("/api/operandes?eagerload=true"))
        .andExpect(status().isOk());

        verify(operandeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllOperandesWithEagerRelationshipsIsNotEnabled() throws Exception {
        OperandeResource operandeResource = new OperandeResource(operandeServiceMock, operandeQueryService);
            when(operandeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restOperandeMockMvc = MockMvcBuilders.standaloneSetup(operandeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOperandeMockMvc.perform(get("/api/operandes?eagerload=true"))
        .andExpect(status().isOk());

            verify(operandeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getOperande() throws Exception {
        // Initialize the database
        operandeRepository.saveAndFlush(operande);

        // Get the operande
        restOperandeMockMvc.perform(get("/api/operandes/{id}", operande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operande.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllOperandesByColonneIsEqualToSomething() throws Exception {
        // Initialize the database
        Colonne colonne = ColonneResourceIntTest.createEntity(em);
        em.persist(colonne);
        em.flush();
        operande.addColonne(colonne);
        operandeRepository.saveAndFlush(operande);
        Long colonneId = colonne.getId();

        // Get all the operandeList where colonne equals to colonneId
        defaultOperandeShouldBeFound("colonneId.equals=" + colonneId);

        // Get all the operandeList where colonne equals to colonneId + 1
        defaultOperandeShouldNotBeFound("colonneId.equals=" + (colonneId + 1));
    }


    @Test
    @Transactional
    public void getAllOperandesByValeurIsEqualToSomething() throws Exception {
        // Initialize the database
        Valeur valeur = ValeurResourceIntTest.createEntity(em);
        em.persist(valeur);
        em.flush();
        operande.addValeur(valeur);
        operandeRepository.saveAndFlush(operande);
        Long valeurId = valeur.getId();

        // Get all the operandeList where valeur equals to valeurId
        defaultOperandeShouldBeFound("valeurId.equals=" + valeurId);

        // Get all the operandeList where valeur equals to valeurId + 1
        defaultOperandeShouldNotBeFound("valeurId.equals=" + (valeurId + 1));
    }


    @Test
    @Transactional
    public void getAllOperandesByFonctionIsEqualToSomething() throws Exception {
        // Initialize the database
        Fonction fonction = FonctionResourceIntTest.createEntity(em);
        em.persist(fonction);
        em.flush();
        operande.setFonction(fonction);
        operandeRepository.saveAndFlush(operande);
        Long fonctionId = fonction.getId();

        // Get all the operandeList where fonction equals to fonctionId
        defaultOperandeShouldBeFound("fonctionId.equals=" + fonctionId);

        // Get all the operandeList where fonction equals to fonctionId + 1
        defaultOperandeShouldNotBeFound("fonctionId.equals=" + (fonctionId + 1));
    }


    @Test
    @Transactional
    public void getAllOperandesByClauseIsEqualToSomething() throws Exception {
        // Initialize the database
        Clause clause = ClauseResourceIntTest.createEntity(em);
        em.persist(clause);
        em.flush();
        operande.addClause(clause);
        operandeRepository.saveAndFlush(operande);
        Long clauseId = clause.getId();

        // Get all the operandeList where clause equals to clauseId
        defaultOperandeShouldBeFound("clauseId.equals=" + clauseId);

        // Get all the operandeList where clause equals to clauseId + 1
        defaultOperandeShouldNotBeFound("clauseId.equals=" + (clauseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOperandeShouldBeFound(String filter) throws Exception {
        restOperandeMockMvc.perform(get("/api/operandes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operande.getId().intValue())));

        // Check, that the count call also returns 1
        restOperandeMockMvc.perform(get("/api/operandes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOperandeShouldNotBeFound(String filter) throws Exception {
        restOperandeMockMvc.perform(get("/api/operandes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOperandeMockMvc.perform(get("/api/operandes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOperande() throws Exception {
        // Get the operande
        restOperandeMockMvc.perform(get("/api/operandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperande() throws Exception {
        // Initialize the database
        operandeService.save(operande);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockOperandeSearchRepository);

        int databaseSizeBeforeUpdate = operandeRepository.findAll().size();

        // Update the operande
        Operande updatedOperande = operandeRepository.findById(operande.getId()).get();
        // Disconnect from session so that the updates on updatedOperande are not directly saved in db
        em.detach(updatedOperande);

        restOperandeMockMvc.perform(put("/api/operandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperande)))
            .andExpect(status().isOk());

        // Validate the Operande in the database
        List<Operande> operandeList = operandeRepository.findAll();
        assertThat(operandeList).hasSize(databaseSizeBeforeUpdate);
        Operande testOperande = operandeList.get(operandeList.size() - 1);

        // Validate the Operande in Elasticsearch
        verify(mockOperandeSearchRepository, times(1)).save(testOperande);
    }

    @Test
    @Transactional
    public void updateNonExistingOperande() throws Exception {
        int databaseSizeBeforeUpdate = operandeRepository.findAll().size();

        // Create the Operande

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperandeMockMvc.perform(put("/api/operandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operande)))
            .andExpect(status().isBadRequest());

        // Validate the Operande in the database
        List<Operande> operandeList = operandeRepository.findAll();
        assertThat(operandeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Operande in Elasticsearch
        verify(mockOperandeSearchRepository, times(0)).save(operande);
    }

    @Test
    @Transactional
    public void deleteOperande() throws Exception {
        // Initialize the database
        operandeService.save(operande);

        int databaseSizeBeforeDelete = operandeRepository.findAll().size();

        // Delete the operande
        restOperandeMockMvc.perform(delete("/api/operandes/{id}", operande.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Operande> operandeList = operandeRepository.findAll();
        assertThat(operandeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Operande in Elasticsearch
        verify(mockOperandeSearchRepository, times(1)).deleteById(operande.getId());
    }

    @Test
    @Transactional
    public void searchOperande() throws Exception {
        // Initialize the database
        operandeService.save(operande);
        when(mockOperandeSearchRepository.search(queryStringQuery("id:" + operande.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(operande), PageRequest.of(0, 1), 1));
        // Search the operande
        restOperandeMockMvc.perform(get("/api/_search/operandes?query=id:" + operande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operande.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operande.class);
        Operande operande1 = new Operande();
        operande1.setId(1L);
        Operande operande2 = new Operande();
        operande2.setId(operande1.getId());
        assertThat(operande1).isEqualTo(operande2);
        operande2.setId(2L);
        assertThat(operande1).isNotEqualTo(operande2);
        operande1.setId(null);
        assertThat(operande1).isNotEqualTo(operande2);
    }
}
