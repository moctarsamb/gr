package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.OperateurLogique;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Critere;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filtre;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.OperateurLogiqueRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperateurLogiqueSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperateurLogiqueService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.OperateurLogiqueCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperateurLogiqueQueryService;

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
 * Test class for the OperateurLogiqueResource REST controller.
 *
 * @see OperateurLogiqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class OperateurLogiqueResourceIntTest {

    private static final String DEFAULT_OPERATEUR_LOGIQUE = "AAAAAAAAAA";
    private static final String UPDATED_OPERATEUR_LOGIQUE = "BBBBBBBBBB";

    @Autowired
    private OperateurLogiqueRepository operateurLogiqueRepository;

    @Autowired
    private OperateurLogiqueService operateurLogiqueService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperateurLogiqueSearchRepositoryMockConfiguration
     */
    @Autowired
    private OperateurLogiqueSearchRepository mockOperateurLogiqueSearchRepository;

    @Autowired
    private OperateurLogiqueQueryService operateurLogiqueQueryService;

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

    private MockMvc restOperateurLogiqueMockMvc;

    private OperateurLogique operateurLogique;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperateurLogiqueResource operateurLogiqueResource = new OperateurLogiqueResource(operateurLogiqueService, operateurLogiqueQueryService);
        this.restOperateurLogiqueMockMvc = MockMvcBuilders.standaloneSetup(operateurLogiqueResource)
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
    public static OperateurLogique createEntity(EntityManager em) {
        OperateurLogique operateurLogique = new OperateurLogique()
            .operateurLogique(DEFAULT_OPERATEUR_LOGIQUE);
        return operateurLogique;
    }

    @Before
    public void initTest() {
        operateurLogique = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperateurLogique() throws Exception {
        int databaseSizeBeforeCreate = operateurLogiqueRepository.findAll().size();

        // Create the OperateurLogique
        restOperateurLogiqueMockMvc.perform(post("/api/operateur-logiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operateurLogique)))
            .andExpect(status().isCreated());

        // Validate the OperateurLogique in the database
        List<OperateurLogique> operateurLogiqueList = operateurLogiqueRepository.findAll();
        assertThat(operateurLogiqueList).hasSize(databaseSizeBeforeCreate + 1);
        OperateurLogique testOperateurLogique = operateurLogiqueList.get(operateurLogiqueList.size() - 1);
        assertThat(testOperateurLogique.getOperateurLogique()).isEqualTo(DEFAULT_OPERATEUR_LOGIQUE);

        // Validate the OperateurLogique in Elasticsearch
        verify(mockOperateurLogiqueSearchRepository, times(1)).save(testOperateurLogique);
    }

    @Test
    @Transactional
    public void createOperateurLogiqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operateurLogiqueRepository.findAll().size();

        // Create the OperateurLogique with an existing ID
        operateurLogique.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperateurLogiqueMockMvc.perform(post("/api/operateur-logiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operateurLogique)))
            .andExpect(status().isBadRequest());

        // Validate the OperateurLogique in the database
        List<OperateurLogique> operateurLogiqueList = operateurLogiqueRepository.findAll();
        assertThat(operateurLogiqueList).hasSize(databaseSizeBeforeCreate);

        // Validate the OperateurLogique in Elasticsearch
        verify(mockOperateurLogiqueSearchRepository, times(0)).save(operateurLogique);
    }

    @Test
    @Transactional
    public void checkOperateurLogiqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = operateurLogiqueRepository.findAll().size();
        // set the field null
        operateurLogique.setOperateurLogique(null);

        // Create the OperateurLogique, which fails.

        restOperateurLogiqueMockMvc.perform(post("/api/operateur-logiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operateurLogique)))
            .andExpect(status().isBadRequest());

        List<OperateurLogique> operateurLogiqueList = operateurLogiqueRepository.findAll();
        assertThat(operateurLogiqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperateurLogiques() throws Exception {
        // Initialize the database
        operateurLogiqueRepository.saveAndFlush(operateurLogique);

        // Get all the operateurLogiqueList
        restOperateurLogiqueMockMvc.perform(get("/api/operateur-logiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operateurLogique.getId().intValue())))
            .andExpect(jsonPath("$.[*].operateurLogique").value(hasItem(DEFAULT_OPERATEUR_LOGIQUE.toString())));
    }
    
    @Test
    @Transactional
    public void getOperateurLogique() throws Exception {
        // Initialize the database
        operateurLogiqueRepository.saveAndFlush(operateurLogique);

        // Get the operateurLogique
        restOperateurLogiqueMockMvc.perform(get("/api/operateur-logiques/{id}", operateurLogique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operateurLogique.getId().intValue()))
            .andExpect(jsonPath("$.operateurLogique").value(DEFAULT_OPERATEUR_LOGIQUE.toString()));
    }

    @Test
    @Transactional
    public void getAllOperateurLogiquesByOperateurLogiqueIsEqualToSomething() throws Exception {
        // Initialize the database
        operateurLogiqueRepository.saveAndFlush(operateurLogique);

        // Get all the operateurLogiqueList where operateurLogique equals to DEFAULT_OPERATEUR_LOGIQUE
        defaultOperateurLogiqueShouldBeFound("operateurLogique.equals=" + DEFAULT_OPERATEUR_LOGIQUE);

        // Get all the operateurLogiqueList where operateurLogique equals to UPDATED_OPERATEUR_LOGIQUE
        defaultOperateurLogiqueShouldNotBeFound("operateurLogique.equals=" + UPDATED_OPERATEUR_LOGIQUE);
    }

    @Test
    @Transactional
    public void getAllOperateurLogiquesByOperateurLogiqueIsInShouldWork() throws Exception {
        // Initialize the database
        operateurLogiqueRepository.saveAndFlush(operateurLogique);

        // Get all the operateurLogiqueList where operateurLogique in DEFAULT_OPERATEUR_LOGIQUE or UPDATED_OPERATEUR_LOGIQUE
        defaultOperateurLogiqueShouldBeFound("operateurLogique.in=" + DEFAULT_OPERATEUR_LOGIQUE + "," + UPDATED_OPERATEUR_LOGIQUE);

        // Get all the operateurLogiqueList where operateurLogique equals to UPDATED_OPERATEUR_LOGIQUE
        defaultOperateurLogiqueShouldNotBeFound("operateurLogique.in=" + UPDATED_OPERATEUR_LOGIQUE);
    }

    @Test
    @Transactional
    public void getAllOperateurLogiquesByOperateurLogiqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        operateurLogiqueRepository.saveAndFlush(operateurLogique);

        // Get all the operateurLogiqueList where operateurLogique is not null
        defaultOperateurLogiqueShouldBeFound("operateurLogique.specified=true");

        // Get all the operateurLogiqueList where operateurLogique is null
        defaultOperateurLogiqueShouldNotBeFound("operateurLogique.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperateurLogiquesByCritereIsEqualToSomething() throws Exception {
        // Initialize the database
        Critere critere = CritereResourceIntTest.createEntity(em);
        em.persist(critere);
        em.flush();
        operateurLogique.addCritere(critere);
        operateurLogiqueRepository.saveAndFlush(operateurLogique);
        Long critereId = critere.getId();

        // Get all the operateurLogiqueList where critere equals to critereId
        defaultOperateurLogiqueShouldBeFound("critereId.equals=" + critereId);

        // Get all the operateurLogiqueList where critere equals to critereId + 1
        defaultOperateurLogiqueShouldNotBeFound("critereId.equals=" + (critereId + 1));
    }


    @Test
    @Transactional
    public void getAllOperateurLogiquesByFiltreIsEqualToSomething() throws Exception {
        // Initialize the database
        Filtre filtre = FiltreResourceIntTest.createEntity(em);
        em.persist(filtre);
        em.flush();
        operateurLogique.addFiltre(filtre);
        operateurLogiqueRepository.saveAndFlush(operateurLogique);
        Long filtreId = filtre.getId();

        // Get all the operateurLogiqueList where filtre equals to filtreId
        defaultOperateurLogiqueShouldBeFound("filtreId.equals=" + filtreId);

        // Get all the operateurLogiqueList where filtre equals to filtreId + 1
        defaultOperateurLogiqueShouldNotBeFound("filtreId.equals=" + (filtreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOperateurLogiqueShouldBeFound(String filter) throws Exception {
        restOperateurLogiqueMockMvc.perform(get("/api/operateur-logiques?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operateurLogique.getId().intValue())))
            .andExpect(jsonPath("$.[*].operateurLogique").value(hasItem(DEFAULT_OPERATEUR_LOGIQUE)));

        // Check, that the count call also returns 1
        restOperateurLogiqueMockMvc.perform(get("/api/operateur-logiques/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOperateurLogiqueShouldNotBeFound(String filter) throws Exception {
        restOperateurLogiqueMockMvc.perform(get("/api/operateur-logiques?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOperateurLogiqueMockMvc.perform(get("/api/operateur-logiques/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOperateurLogique() throws Exception {
        // Get the operateurLogique
        restOperateurLogiqueMockMvc.perform(get("/api/operateur-logiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperateurLogique() throws Exception {
        // Initialize the database
        operateurLogiqueService.save(operateurLogique);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockOperateurLogiqueSearchRepository);

        int databaseSizeBeforeUpdate = operateurLogiqueRepository.findAll().size();

        // Update the operateurLogique
        OperateurLogique updatedOperateurLogique = operateurLogiqueRepository.findById(operateurLogique.getId()).get();
        // Disconnect from session so that the updates on updatedOperateurLogique are not directly saved in db
        em.detach(updatedOperateurLogique);
        updatedOperateurLogique
            .operateurLogique(UPDATED_OPERATEUR_LOGIQUE);

        restOperateurLogiqueMockMvc.perform(put("/api/operateur-logiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperateurLogique)))
            .andExpect(status().isOk());

        // Validate the OperateurLogique in the database
        List<OperateurLogique> operateurLogiqueList = operateurLogiqueRepository.findAll();
        assertThat(operateurLogiqueList).hasSize(databaseSizeBeforeUpdate);
        OperateurLogique testOperateurLogique = operateurLogiqueList.get(operateurLogiqueList.size() - 1);
        assertThat(testOperateurLogique.getOperateurLogique()).isEqualTo(UPDATED_OPERATEUR_LOGIQUE);

        // Validate the OperateurLogique in Elasticsearch
        verify(mockOperateurLogiqueSearchRepository, times(1)).save(testOperateurLogique);
    }

    @Test
    @Transactional
    public void updateNonExistingOperateurLogique() throws Exception {
        int databaseSizeBeforeUpdate = operateurLogiqueRepository.findAll().size();

        // Create the OperateurLogique

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperateurLogiqueMockMvc.perform(put("/api/operateur-logiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operateurLogique)))
            .andExpect(status().isBadRequest());

        // Validate the OperateurLogique in the database
        List<OperateurLogique> operateurLogiqueList = operateurLogiqueRepository.findAll();
        assertThat(operateurLogiqueList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OperateurLogique in Elasticsearch
        verify(mockOperateurLogiqueSearchRepository, times(0)).save(operateurLogique);
    }

    @Test
    @Transactional
    public void deleteOperateurLogique() throws Exception {
        // Initialize the database
        operateurLogiqueService.save(operateurLogique);

        int databaseSizeBeforeDelete = operateurLogiqueRepository.findAll().size();

        // Delete the operateurLogique
        restOperateurLogiqueMockMvc.perform(delete("/api/operateur-logiques/{id}", operateurLogique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OperateurLogique> operateurLogiqueList = operateurLogiqueRepository.findAll();
        assertThat(operateurLogiqueList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OperateurLogique in Elasticsearch
        verify(mockOperateurLogiqueSearchRepository, times(1)).deleteById(operateurLogique.getId());
    }

    @Test
    @Transactional
    public void searchOperateurLogique() throws Exception {
        // Initialize the database
        operateurLogiqueService.save(operateurLogique);
        when(mockOperateurLogiqueSearchRepository.search(queryStringQuery("id:" + operateurLogique.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(operateurLogique), PageRequest.of(0, 1), 1));
        // Search the operateurLogique
        restOperateurLogiqueMockMvc.perform(get("/api/_search/operateur-logiques?query=id:" + operateurLogique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operateurLogique.getId().intValue())))
            .andExpect(jsonPath("$.[*].operateurLogique").value(hasItem(DEFAULT_OPERATEUR_LOGIQUE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperateurLogique.class);
        OperateurLogique operateurLogique1 = new OperateurLogique();
        operateurLogique1.setId(1L);
        OperateurLogique operateurLogique2 = new OperateurLogique();
        operateurLogique2.setId(operateurLogique1.getId());
        assertThat(operateurLogique1).isEqualTo(operateurLogique2);
        operateurLogique2.setId(2L);
        assertThat(operateurLogique1).isNotEqualTo(operateurLogique2);
        operateurLogique1.setId(null);
        assertThat(operateurLogique1).isNotEqualTo(operateurLogique2);
    }
}
