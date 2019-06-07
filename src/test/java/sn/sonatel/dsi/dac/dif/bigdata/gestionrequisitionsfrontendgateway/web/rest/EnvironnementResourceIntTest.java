package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Environnement;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.EnvironnementRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.EnvironnementSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.EnvironnementService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.EnvironnementCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.EnvironnementQueryService;

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
 * Test class for the EnvironnementResource REST controller.
 *
 * @see EnvironnementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class EnvironnementResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private EnvironnementRepository environnementRepository;

    @Autowired
    private EnvironnementService environnementService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.EnvironnementSearchRepositoryMockConfiguration
     */
    @Autowired
    private EnvironnementSearchRepository mockEnvironnementSearchRepository;

    @Autowired
    private EnvironnementQueryService environnementQueryService;

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

    private MockMvc restEnvironnementMockMvc;

    private Environnement environnement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnvironnementResource environnementResource = new EnvironnementResource(environnementService, environnementQueryService);
        this.restEnvironnementMockMvc = MockMvcBuilders.standaloneSetup(environnementResource)
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
    public static Environnement createEntity(EntityManager em) {
        Environnement environnement = new Environnement()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return environnement;
    }

    @Before
    public void initTest() {
        environnement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvironnement() throws Exception {
        int databaseSizeBeforeCreate = environnementRepository.findAll().size();

        // Create the Environnement
        restEnvironnementMockMvc.perform(post("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isCreated());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeCreate + 1);
        Environnement testEnvironnement = environnementList.get(environnementList.size() - 1);
        assertThat(testEnvironnement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEnvironnement.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the Environnement in Elasticsearch
        verify(mockEnvironnementSearchRepository, times(1)).save(testEnvironnement);
    }

    @Test
    @Transactional
    public void createEnvironnementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = environnementRepository.findAll().size();

        // Create the Environnement with an existing ID
        environnement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvironnementMockMvc.perform(post("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isBadRequest());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeCreate);

        // Validate the Environnement in Elasticsearch
        verify(mockEnvironnementSearchRepository, times(0)).save(environnement);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = environnementRepository.findAll().size();
        // set the field null
        environnement.setCode(null);

        // Create the Environnement, which fails.

        restEnvironnementMockMvc.perform(post("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isBadRequest());

        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = environnementRepository.findAll().size();
        // set the field null
        environnement.setLibelle(null);

        // Create the Environnement, which fails.

        restEnvironnementMockMvc.perform(post("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isBadRequest());

        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnvironnements() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get all the environnementList
        restEnvironnementMockMvc.perform(get("/api/environnements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(environnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getEnvironnement() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get the environnement
        restEnvironnementMockMvc.perform(get("/api/environnements/{id}", environnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(environnement.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getAllEnvironnementsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get all the environnementList where code equals to DEFAULT_CODE
        defaultEnvironnementShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the environnementList where code equals to UPDATED_CODE
        defaultEnvironnementShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEnvironnementsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get all the environnementList where code in DEFAULT_CODE or UPDATED_CODE
        defaultEnvironnementShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the environnementList where code equals to UPDATED_CODE
        defaultEnvironnementShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllEnvironnementsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get all the environnementList where code is not null
        defaultEnvironnementShouldBeFound("code.specified=true");

        // Get all the environnementList where code is null
        defaultEnvironnementShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnvironnementsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get all the environnementList where libelle equals to DEFAULT_LIBELLE
        defaultEnvironnementShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the environnementList where libelle equals to UPDATED_LIBELLE
        defaultEnvironnementShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllEnvironnementsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get all the environnementList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultEnvironnementShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the environnementList where libelle equals to UPDATED_LIBELLE
        defaultEnvironnementShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllEnvironnementsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        environnementRepository.saveAndFlush(environnement);

        // Get all the environnementList where libelle is not null
        defaultEnvironnementShouldBeFound("libelle.specified=true");

        // Get all the environnementList where libelle is null
        defaultEnvironnementShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnvironnementsByChampsRechercheIsEqualToSomething() throws Exception {
        // Initialize the database
        ChampsRecherche champsRecherche = ChampsRechercheResourceIntTest.createEntity(em);
        em.persist(champsRecherche);
        em.flush();
        environnement.addChampsRecherche(champsRecherche);
        environnementRepository.saveAndFlush(environnement);
        Long champsRechercheId = champsRecherche.getId();

        // Get all the environnementList where champsRecherche equals to champsRechercheId
        defaultEnvironnementShouldBeFound("champsRechercheId.equals=" + champsRechercheId);

        // Get all the environnementList where champsRecherche equals to champsRechercheId + 1
        defaultEnvironnementShouldNotBeFound("champsRechercheId.equals=" + (champsRechercheId + 1));
    }


    @Test
    @Transactional
    public void getAllEnvironnementsByFilialeIsEqualToSomething() throws Exception {
        // Initialize the database
        Filiale filiale = FilialeResourceIntTest.createEntity(em);
        em.persist(filiale);
        em.flush();
        environnement.addFiliale(filiale);
        environnementRepository.saveAndFlush(environnement);
        Long filialeId = filiale.getId();

        // Get all the environnementList where filiale equals to filialeId
        defaultEnvironnementShouldBeFound("filialeId.equals=" + filialeId);

        // Get all the environnementList where filiale equals to filialeId + 1
        defaultEnvironnementShouldNotBeFound("filialeId.equals=" + (filialeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEnvironnementShouldBeFound(String filter) throws Exception {
        restEnvironnementMockMvc.perform(get("/api/environnements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(environnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restEnvironnementMockMvc.perform(get("/api/environnements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEnvironnementShouldNotBeFound(String filter) throws Exception {
        restEnvironnementMockMvc.perform(get("/api/environnements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnvironnementMockMvc.perform(get("/api/environnements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEnvironnement() throws Exception {
        // Get the environnement
        restEnvironnementMockMvc.perform(get("/api/environnements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvironnement() throws Exception {
        // Initialize the database
        environnementService.save(environnement);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEnvironnementSearchRepository);

        int databaseSizeBeforeUpdate = environnementRepository.findAll().size();

        // Update the environnement
        Environnement updatedEnvironnement = environnementRepository.findById(environnement.getId()).get();
        // Disconnect from session so that the updates on updatedEnvironnement are not directly saved in db
        em.detach(updatedEnvironnement);
        updatedEnvironnement
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restEnvironnementMockMvc.perform(put("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnvironnement)))
            .andExpect(status().isOk());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeUpdate);
        Environnement testEnvironnement = environnementList.get(environnementList.size() - 1);
        assertThat(testEnvironnement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEnvironnement.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the Environnement in Elasticsearch
        verify(mockEnvironnementSearchRepository, times(1)).save(testEnvironnement);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvironnement() throws Exception {
        int databaseSizeBeforeUpdate = environnementRepository.findAll().size();

        // Create the Environnement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvironnementMockMvc.perform(put("/api/environnements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environnement)))
            .andExpect(status().isBadRequest());

        // Validate the Environnement in the database
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Environnement in Elasticsearch
        verify(mockEnvironnementSearchRepository, times(0)).save(environnement);
    }

    @Test
    @Transactional
    public void deleteEnvironnement() throws Exception {
        // Initialize the database
        environnementService.save(environnement);

        int databaseSizeBeforeDelete = environnementRepository.findAll().size();

        // Delete the environnement
        restEnvironnementMockMvc.perform(delete("/api/environnements/{id}", environnement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Environnement> environnementList = environnementRepository.findAll();
        assertThat(environnementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Environnement in Elasticsearch
        verify(mockEnvironnementSearchRepository, times(1)).deleteById(environnement.getId());
    }

    @Test
    @Transactional
    public void searchEnvironnement() throws Exception {
        // Initialize the database
        environnementService.save(environnement);
        when(mockEnvironnementSearchRepository.search(queryStringQuery("id:" + environnement.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(environnement), PageRequest.of(0, 1), 1));
        // Search the environnement
        restEnvironnementMockMvc.perform(get("/api/_search/environnements?query=id:" + environnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(environnement.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Environnement.class);
        Environnement environnement1 = new Environnement();
        environnement1.setId(1L);
        Environnement environnement2 = new Environnement();
        environnement2.setId(environnement1.getId());
        assertThat(environnement1).isEqualTo(environnement2);
        environnement2.setId(2L);
        assertThat(environnement1).isNotEqualTo(environnement2);
        environnement1.setId(null);
        assertThat(environnement1).isNotEqualTo(environnement2);
    }
}
