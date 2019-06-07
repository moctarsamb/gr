package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.DroitAcces;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Utilisateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.DroitAccesRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.DroitAccesSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.DroitAccesService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.DroitAccesCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.DroitAccesQueryService;

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
 * Test class for the DroitAccesResource REST controller.
 *
 * @see DroitAccesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class DroitAccesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private DroitAccesRepository droitAccesRepository;

    @Autowired
    private DroitAccesService droitAccesService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.DroitAccesSearchRepositoryMockConfiguration
     */
    @Autowired
    private DroitAccesSearchRepository mockDroitAccesSearchRepository;

    @Autowired
    private DroitAccesQueryService droitAccesQueryService;

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

    private MockMvc restDroitAccesMockMvc;

    private DroitAcces droitAcces;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DroitAccesResource droitAccesResource = new DroitAccesResource(droitAccesService, droitAccesQueryService);
        this.restDroitAccesMockMvc = MockMvcBuilders.standaloneSetup(droitAccesResource)
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
    public static DroitAcces createEntity(EntityManager em) {
        DroitAcces droitAcces = new DroitAcces()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return droitAcces;
    }

    @Before
    public void initTest() {
        droitAcces = createEntity(em);
    }

    @Test
    @Transactional
    public void createDroitAcces() throws Exception {
        int databaseSizeBeforeCreate = droitAccesRepository.findAll().size();

        // Create the DroitAcces
        restDroitAccesMockMvc.perform(post("/api/droit-acces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(droitAcces)))
            .andExpect(status().isCreated());

        // Validate the DroitAcces in the database
        List<DroitAcces> droitAccesList = droitAccesRepository.findAll();
        assertThat(droitAccesList).hasSize(databaseSizeBeforeCreate + 1);
        DroitAcces testDroitAcces = droitAccesList.get(droitAccesList.size() - 1);
        assertThat(testDroitAcces.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDroitAcces.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the DroitAcces in Elasticsearch
        verify(mockDroitAccesSearchRepository, times(1)).save(testDroitAcces);
    }

    @Test
    @Transactional
    public void createDroitAccesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = droitAccesRepository.findAll().size();

        // Create the DroitAcces with an existing ID
        droitAcces.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDroitAccesMockMvc.perform(post("/api/droit-acces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(droitAcces)))
            .andExpect(status().isBadRequest());

        // Validate the DroitAcces in the database
        List<DroitAcces> droitAccesList = droitAccesRepository.findAll();
        assertThat(droitAccesList).hasSize(databaseSizeBeforeCreate);

        // Validate the DroitAcces in Elasticsearch
        verify(mockDroitAccesSearchRepository, times(0)).save(droitAcces);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitAccesRepository.findAll().size();
        // set the field null
        droitAcces.setCode(null);

        // Create the DroitAcces, which fails.

        restDroitAccesMockMvc.perform(post("/api/droit-acces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(droitAcces)))
            .andExpect(status().isBadRequest());

        List<DroitAcces> droitAccesList = droitAccesRepository.findAll();
        assertThat(droitAccesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitAccesRepository.findAll().size();
        // set the field null
        droitAcces.setLibelle(null);

        // Create the DroitAcces, which fails.

        restDroitAccesMockMvc.perform(post("/api/droit-acces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(droitAcces)))
            .andExpect(status().isBadRequest());

        List<DroitAcces> droitAccesList = droitAccesRepository.findAll();
        assertThat(droitAccesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDroitAcces() throws Exception {
        // Initialize the database
        droitAccesRepository.saveAndFlush(droitAcces);

        // Get all the droitAccesList
        restDroitAccesMockMvc.perform(get("/api/droit-acces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(droitAcces.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getDroitAcces() throws Exception {
        // Initialize the database
        droitAccesRepository.saveAndFlush(droitAcces);

        // Get the droitAcces
        restDroitAccesMockMvc.perform(get("/api/droit-acces/{id}", droitAcces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(droitAcces.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getAllDroitAccesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        droitAccesRepository.saveAndFlush(droitAcces);

        // Get all the droitAccesList where code equals to DEFAULT_CODE
        defaultDroitAccesShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the droitAccesList where code equals to UPDATED_CODE
        defaultDroitAccesShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDroitAccesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        droitAccesRepository.saveAndFlush(droitAcces);

        // Get all the droitAccesList where code in DEFAULT_CODE or UPDATED_CODE
        defaultDroitAccesShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the droitAccesList where code equals to UPDATED_CODE
        defaultDroitAccesShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDroitAccesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        droitAccesRepository.saveAndFlush(droitAcces);

        // Get all the droitAccesList where code is not null
        defaultDroitAccesShouldBeFound("code.specified=true");

        // Get all the droitAccesList where code is null
        defaultDroitAccesShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllDroitAccesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        droitAccesRepository.saveAndFlush(droitAcces);

        // Get all the droitAccesList where libelle equals to DEFAULT_LIBELLE
        defaultDroitAccesShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the droitAccesList where libelle equals to UPDATED_LIBELLE
        defaultDroitAccesShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllDroitAccesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        droitAccesRepository.saveAndFlush(droitAcces);

        // Get all the droitAccesList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultDroitAccesShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the droitAccesList where libelle equals to UPDATED_LIBELLE
        defaultDroitAccesShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllDroitAccesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        droitAccesRepository.saveAndFlush(droitAcces);

        // Get all the droitAccesList where libelle is not null
        defaultDroitAccesShouldBeFound("libelle.specified=true");

        // Get all the droitAccesList where libelle is null
        defaultDroitAccesShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllDroitAccesByUtilisateurIsEqualToSomething() throws Exception {
        // Initialize the database
        Utilisateur utilisateur = UtilisateurResourceIntTest.createEntity(em);
        em.persist(utilisateur);
        em.flush();
        droitAcces.addUtilisateur(utilisateur);
        droitAccesRepository.saveAndFlush(droitAcces);
        Long utilisateurId = utilisateur.getId();

        // Get all the droitAccesList where utilisateur equals to utilisateurId
        defaultDroitAccesShouldBeFound("utilisateurId.equals=" + utilisateurId);

        // Get all the droitAccesList where utilisateur equals to utilisateurId + 1
        defaultDroitAccesShouldNotBeFound("utilisateurId.equals=" + (utilisateurId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDroitAccesShouldBeFound(String filter) throws Exception {
        restDroitAccesMockMvc.perform(get("/api/droit-acces?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(droitAcces.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restDroitAccesMockMvc.perform(get("/api/droit-acces/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDroitAccesShouldNotBeFound(String filter) throws Exception {
        restDroitAccesMockMvc.perform(get("/api/droit-acces?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDroitAccesMockMvc.perform(get("/api/droit-acces/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDroitAcces() throws Exception {
        // Get the droitAcces
        restDroitAccesMockMvc.perform(get("/api/droit-acces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDroitAcces() throws Exception {
        // Initialize the database
        droitAccesService.save(droitAcces);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDroitAccesSearchRepository);

        int databaseSizeBeforeUpdate = droitAccesRepository.findAll().size();

        // Update the droitAcces
        DroitAcces updatedDroitAcces = droitAccesRepository.findById(droitAcces.getId()).get();
        // Disconnect from session so that the updates on updatedDroitAcces are not directly saved in db
        em.detach(updatedDroitAcces);
        updatedDroitAcces
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restDroitAccesMockMvc.perform(put("/api/droit-acces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDroitAcces)))
            .andExpect(status().isOk());

        // Validate the DroitAcces in the database
        List<DroitAcces> droitAccesList = droitAccesRepository.findAll();
        assertThat(droitAccesList).hasSize(databaseSizeBeforeUpdate);
        DroitAcces testDroitAcces = droitAccesList.get(droitAccesList.size() - 1);
        assertThat(testDroitAcces.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDroitAcces.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the DroitAcces in Elasticsearch
        verify(mockDroitAccesSearchRepository, times(1)).save(testDroitAcces);
    }

    @Test
    @Transactional
    public void updateNonExistingDroitAcces() throws Exception {
        int databaseSizeBeforeUpdate = droitAccesRepository.findAll().size();

        // Create the DroitAcces

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitAccesMockMvc.perform(put("/api/droit-acces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(droitAcces)))
            .andExpect(status().isBadRequest());

        // Validate the DroitAcces in the database
        List<DroitAcces> droitAccesList = droitAccesRepository.findAll();
        assertThat(droitAccesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DroitAcces in Elasticsearch
        verify(mockDroitAccesSearchRepository, times(0)).save(droitAcces);
    }

    @Test
    @Transactional
    public void deleteDroitAcces() throws Exception {
        // Initialize the database
        droitAccesService.save(droitAcces);

        int databaseSizeBeforeDelete = droitAccesRepository.findAll().size();

        // Delete the droitAcces
        restDroitAccesMockMvc.perform(delete("/api/droit-acces/{id}", droitAcces.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DroitAcces> droitAccesList = droitAccesRepository.findAll();
        assertThat(droitAccesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DroitAcces in Elasticsearch
        verify(mockDroitAccesSearchRepository, times(1)).deleteById(droitAcces.getId());
    }

    @Test
    @Transactional
    public void searchDroitAcces() throws Exception {
        // Initialize the database
        droitAccesService.save(droitAcces);
        when(mockDroitAccesSearchRepository.search(queryStringQuery("id:" + droitAcces.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(droitAcces), PageRequest.of(0, 1), 1));
        // Search the droitAcces
        restDroitAccesMockMvc.perform(get("/api/_search/droit-acces?query=id:" + droitAcces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(droitAcces.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroitAcces.class);
        DroitAcces droitAcces1 = new DroitAcces();
        droitAcces1.setId(1L);
        DroitAcces droitAcces2 = new DroitAcces();
        droitAcces2.setId(droitAcces1.getId());
        assertThat(droitAcces1).isEqualTo(droitAcces2);
        droitAcces2.setId(2L);
        assertThat(droitAcces1).isNotEqualTo(droitAcces2);
        droitAcces1.setId(null);
        assertThat(droitAcces1).isNotEqualTo(droitAcces2);
    }
}
