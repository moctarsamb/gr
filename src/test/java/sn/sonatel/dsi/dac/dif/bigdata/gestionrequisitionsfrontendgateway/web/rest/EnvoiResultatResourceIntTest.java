package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.EnvoiResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.FichierResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Utilisateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.EnvoiResultatRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.EnvoiResultatSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.EnvoiResultatService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.EnvoiResultatCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.EnvoiResultatQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Test class for the EnvoiResultatResource REST controller.
 *
 * @see EnvoiResultatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class EnvoiResultatResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_ENVOI = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ENVOI = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EnvoiResultatRepository envoiResultatRepository;

    @Autowired
    private EnvoiResultatService envoiResultatService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.EnvoiResultatSearchRepositoryMockConfiguration
     */
    @Autowired
    private EnvoiResultatSearchRepository mockEnvoiResultatSearchRepository;

    @Autowired
    private EnvoiResultatQueryService envoiResultatQueryService;

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

    private MockMvc restEnvoiResultatMockMvc;

    private EnvoiResultat envoiResultat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnvoiResultatResource envoiResultatResource = new EnvoiResultatResource(envoiResultatService, envoiResultatQueryService);
        this.restEnvoiResultatMockMvc = MockMvcBuilders.standaloneSetup(envoiResultatResource)
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
    public static EnvoiResultat createEntity(EntityManager em) {
        EnvoiResultat envoiResultat = new EnvoiResultat()
            .email(DEFAULT_EMAIL)
            .dateEnvoi(DEFAULT_DATE_ENVOI);
        return envoiResultat;
    }

    @Before
    public void initTest() {
        envoiResultat = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvoiResultat() throws Exception {
        int databaseSizeBeforeCreate = envoiResultatRepository.findAll().size();

        // Create the EnvoiResultat
        restEnvoiResultatMockMvc.perform(post("/api/envoi-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(envoiResultat)))
            .andExpect(status().isCreated());

        // Validate the EnvoiResultat in the database
        List<EnvoiResultat> envoiResultatList = envoiResultatRepository.findAll();
        assertThat(envoiResultatList).hasSize(databaseSizeBeforeCreate + 1);
        EnvoiResultat testEnvoiResultat = envoiResultatList.get(envoiResultatList.size() - 1);
        assertThat(testEnvoiResultat.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEnvoiResultat.getDateEnvoi()).isEqualTo(DEFAULT_DATE_ENVOI);

        // Validate the EnvoiResultat in Elasticsearch
        verify(mockEnvoiResultatSearchRepository, times(1)).save(testEnvoiResultat);
    }

    @Test
    @Transactional
    public void createEnvoiResultatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = envoiResultatRepository.findAll().size();

        // Create the EnvoiResultat with an existing ID
        envoiResultat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvoiResultatMockMvc.perform(post("/api/envoi-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(envoiResultat)))
            .andExpect(status().isBadRequest());

        // Validate the EnvoiResultat in the database
        List<EnvoiResultat> envoiResultatList = envoiResultatRepository.findAll();
        assertThat(envoiResultatList).hasSize(databaseSizeBeforeCreate);

        // Validate the EnvoiResultat in Elasticsearch
        verify(mockEnvoiResultatSearchRepository, times(0)).save(envoiResultat);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = envoiResultatRepository.findAll().size();
        // set the field null
        envoiResultat.setEmail(null);

        // Create the EnvoiResultat, which fails.

        restEnvoiResultatMockMvc.perform(post("/api/envoi-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(envoiResultat)))
            .andExpect(status().isBadRequest());

        List<EnvoiResultat> envoiResultatList = envoiResultatRepository.findAll();
        assertThat(envoiResultatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnvoiResultats() throws Exception {
        // Initialize the database
        envoiResultatRepository.saveAndFlush(envoiResultat);

        // Get all the envoiResultatList
        restEnvoiResultatMockMvc.perform(get("/api/envoi-resultats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envoiResultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].dateEnvoi").value(hasItem(DEFAULT_DATE_ENVOI.toString())));
    }
    
    @Test
    @Transactional
    public void getEnvoiResultat() throws Exception {
        // Initialize the database
        envoiResultatRepository.saveAndFlush(envoiResultat);

        // Get the envoiResultat
        restEnvoiResultatMockMvc.perform(get("/api/envoi-resultats/{id}", envoiResultat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(envoiResultat.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.dateEnvoi").value(DEFAULT_DATE_ENVOI.toString()));
    }

    @Test
    @Transactional
    public void getAllEnvoiResultatsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        envoiResultatRepository.saveAndFlush(envoiResultat);

        // Get all the envoiResultatList where email equals to DEFAULT_EMAIL
        defaultEnvoiResultatShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the envoiResultatList where email equals to UPDATED_EMAIL
        defaultEnvoiResultatShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEnvoiResultatsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        envoiResultatRepository.saveAndFlush(envoiResultat);

        // Get all the envoiResultatList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEnvoiResultatShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the envoiResultatList where email equals to UPDATED_EMAIL
        defaultEnvoiResultatShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEnvoiResultatsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        envoiResultatRepository.saveAndFlush(envoiResultat);

        // Get all the envoiResultatList where email is not null
        defaultEnvoiResultatShouldBeFound("email.specified=true");

        // Get all the envoiResultatList where email is null
        defaultEnvoiResultatShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnvoiResultatsByDateEnvoiIsEqualToSomething() throws Exception {
        // Initialize the database
        envoiResultatRepository.saveAndFlush(envoiResultat);

        // Get all the envoiResultatList where dateEnvoi equals to DEFAULT_DATE_ENVOI
        defaultEnvoiResultatShouldBeFound("dateEnvoi.equals=" + DEFAULT_DATE_ENVOI);

        // Get all the envoiResultatList where dateEnvoi equals to UPDATED_DATE_ENVOI
        defaultEnvoiResultatShouldNotBeFound("dateEnvoi.equals=" + UPDATED_DATE_ENVOI);
    }

    @Test
    @Transactional
    public void getAllEnvoiResultatsByDateEnvoiIsInShouldWork() throws Exception {
        // Initialize the database
        envoiResultatRepository.saveAndFlush(envoiResultat);

        // Get all the envoiResultatList where dateEnvoi in DEFAULT_DATE_ENVOI or UPDATED_DATE_ENVOI
        defaultEnvoiResultatShouldBeFound("dateEnvoi.in=" + DEFAULT_DATE_ENVOI + "," + UPDATED_DATE_ENVOI);

        // Get all the envoiResultatList where dateEnvoi equals to UPDATED_DATE_ENVOI
        defaultEnvoiResultatShouldNotBeFound("dateEnvoi.in=" + UPDATED_DATE_ENVOI);
    }

    @Test
    @Transactional
    public void getAllEnvoiResultatsByDateEnvoiIsNullOrNotNull() throws Exception {
        // Initialize the database
        envoiResultatRepository.saveAndFlush(envoiResultat);

        // Get all the envoiResultatList where dateEnvoi is not null
        defaultEnvoiResultatShouldBeFound("dateEnvoi.specified=true");

        // Get all the envoiResultatList where dateEnvoi is null
        defaultEnvoiResultatShouldNotBeFound("dateEnvoi.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnvoiResultatsByFichierResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        FichierResultat fichierResultat = FichierResultatResourceIntTest.createEntity(em);
        em.persist(fichierResultat);
        em.flush();
        envoiResultat.setFichierResultat(fichierResultat);
        envoiResultatRepository.saveAndFlush(envoiResultat);
        Long fichierResultatId = fichierResultat.getId();

        // Get all the envoiResultatList where fichierResultat equals to fichierResultatId
        defaultEnvoiResultatShouldBeFound("fichierResultatId.equals=" + fichierResultatId);

        // Get all the envoiResultatList where fichierResultat equals to fichierResultatId + 1
        defaultEnvoiResultatShouldNotBeFound("fichierResultatId.equals=" + (fichierResultatId + 1));
    }


    @Test
    @Transactional
    public void getAllEnvoiResultatsByUtilisateurIsEqualToSomething() throws Exception {
        // Initialize the database
        Utilisateur utilisateur = UtilisateurResourceIntTest.createEntity(em);
        em.persist(utilisateur);
        em.flush();
        envoiResultat.setUtilisateur(utilisateur);
        envoiResultatRepository.saveAndFlush(envoiResultat);
        Long utilisateurId = utilisateur.getId();

        // Get all the envoiResultatList where utilisateur equals to utilisateurId
        defaultEnvoiResultatShouldBeFound("utilisateurId.equals=" + utilisateurId);

        // Get all the envoiResultatList where utilisateur equals to utilisateurId + 1
        defaultEnvoiResultatShouldNotBeFound("utilisateurId.equals=" + (utilisateurId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEnvoiResultatShouldBeFound(String filter) throws Exception {
        restEnvoiResultatMockMvc.perform(get("/api/envoi-resultats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envoiResultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateEnvoi").value(hasItem(DEFAULT_DATE_ENVOI.toString())));

        // Check, that the count call also returns 1
        restEnvoiResultatMockMvc.perform(get("/api/envoi-resultats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEnvoiResultatShouldNotBeFound(String filter) throws Exception {
        restEnvoiResultatMockMvc.perform(get("/api/envoi-resultats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnvoiResultatMockMvc.perform(get("/api/envoi-resultats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEnvoiResultat() throws Exception {
        // Get the envoiResultat
        restEnvoiResultatMockMvc.perform(get("/api/envoi-resultats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvoiResultat() throws Exception {
        // Initialize the database
        envoiResultatService.save(envoiResultat);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEnvoiResultatSearchRepository);

        int databaseSizeBeforeUpdate = envoiResultatRepository.findAll().size();

        // Update the envoiResultat
        EnvoiResultat updatedEnvoiResultat = envoiResultatRepository.findById(envoiResultat.getId()).get();
        // Disconnect from session so that the updates on updatedEnvoiResultat are not directly saved in db
        em.detach(updatedEnvoiResultat);
        updatedEnvoiResultat
            .email(UPDATED_EMAIL)
            .dateEnvoi(UPDATED_DATE_ENVOI);

        restEnvoiResultatMockMvc.perform(put("/api/envoi-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnvoiResultat)))
            .andExpect(status().isOk());

        // Validate the EnvoiResultat in the database
        List<EnvoiResultat> envoiResultatList = envoiResultatRepository.findAll();
        assertThat(envoiResultatList).hasSize(databaseSizeBeforeUpdate);
        EnvoiResultat testEnvoiResultat = envoiResultatList.get(envoiResultatList.size() - 1);
        assertThat(testEnvoiResultat.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEnvoiResultat.getDateEnvoi()).isEqualTo(UPDATED_DATE_ENVOI);

        // Validate the EnvoiResultat in Elasticsearch
        verify(mockEnvoiResultatSearchRepository, times(1)).save(testEnvoiResultat);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvoiResultat() throws Exception {
        int databaseSizeBeforeUpdate = envoiResultatRepository.findAll().size();

        // Create the EnvoiResultat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvoiResultatMockMvc.perform(put("/api/envoi-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(envoiResultat)))
            .andExpect(status().isBadRequest());

        // Validate the EnvoiResultat in the database
        List<EnvoiResultat> envoiResultatList = envoiResultatRepository.findAll();
        assertThat(envoiResultatList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EnvoiResultat in Elasticsearch
        verify(mockEnvoiResultatSearchRepository, times(0)).save(envoiResultat);
    }

    @Test
    @Transactional
    public void deleteEnvoiResultat() throws Exception {
        // Initialize the database
        envoiResultatService.save(envoiResultat);

        int databaseSizeBeforeDelete = envoiResultatRepository.findAll().size();

        // Delete the envoiResultat
        restEnvoiResultatMockMvc.perform(delete("/api/envoi-resultats/{id}", envoiResultat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EnvoiResultat> envoiResultatList = envoiResultatRepository.findAll();
        assertThat(envoiResultatList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EnvoiResultat in Elasticsearch
        verify(mockEnvoiResultatSearchRepository, times(1)).deleteById(envoiResultat.getId());
    }

    @Test
    @Transactional
    public void searchEnvoiResultat() throws Exception {
        // Initialize the database
        envoiResultatService.save(envoiResultat);
        when(mockEnvoiResultatSearchRepository.search(queryStringQuery("id:" + envoiResultat.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(envoiResultat), PageRequest.of(0, 1), 1));
        // Search the envoiResultat
        restEnvoiResultatMockMvc.perform(get("/api/_search/envoi-resultats?query=id:" + envoiResultat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envoiResultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateEnvoi").value(hasItem(DEFAULT_DATE_ENVOI.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnvoiResultat.class);
        EnvoiResultat envoiResultat1 = new EnvoiResultat();
        envoiResultat1.setId(1L);
        EnvoiResultat envoiResultat2 = new EnvoiResultat();
        envoiResultat2.setId(envoiResultat1.getId());
        assertThat(envoiResultat1).isEqualTo(envoiResultat2);
        envoiResultat2.setId(2L);
        assertThat(envoiResultat1).isNotEqualTo(envoiResultat2);
        envoiResultat1.setId(null);
        assertThat(envoiResultat1).isNotEqualTo(envoiResultat2);
    }
}
