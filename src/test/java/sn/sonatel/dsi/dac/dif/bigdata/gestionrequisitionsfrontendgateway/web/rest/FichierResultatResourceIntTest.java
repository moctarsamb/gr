package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.FichierResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.EnvoiResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Resultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FichierResultatRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FichierResultatSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FichierResultatService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FichierResultatCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FichierResultatQueryService;

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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.FormatResultat;
/**
 * Test class for the FichierResultatResource REST controller.
 *
 * @see FichierResultatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class FichierResultatResourceIntTest {

    private static final String DEFAULT_CHEMIN_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_CHEMIN_FICHIER = "BBBBBBBBBB";

    private static final FormatResultat DEFAULT_FORMAT = FormatResultat.CSV;
    private static final FormatResultat UPDATED_FORMAT = FormatResultat.EXCEL;

    private static final Boolean DEFAULT_AVEC_STATISTIQUES = false;
    private static final Boolean UPDATED_AVEC_STATISTIQUES = true;

    @Autowired
    private FichierResultatRepository fichierResultatRepository;

    @Autowired
    private FichierResultatService fichierResultatService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FichierResultatSearchRepositoryMockConfiguration
     */
    @Autowired
    private FichierResultatSearchRepository mockFichierResultatSearchRepository;

    @Autowired
    private FichierResultatQueryService fichierResultatQueryService;

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

    private MockMvc restFichierResultatMockMvc;

    private FichierResultat fichierResultat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FichierResultatResource fichierResultatResource = new FichierResultatResource(fichierResultatService, fichierResultatQueryService);
        this.restFichierResultatMockMvc = MockMvcBuilders.standaloneSetup(fichierResultatResource)
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
    public static FichierResultat createEntity(EntityManager em) {
        FichierResultat fichierResultat = new FichierResultat()
            .cheminFichier(DEFAULT_CHEMIN_FICHIER)
            .format(DEFAULT_FORMAT)
            .avecStatistiques(DEFAULT_AVEC_STATISTIQUES);
        return fichierResultat;
    }

    @Before
    public void initTest() {
        fichierResultat = createEntity(em);
    }

    @Test
    @Transactional
    public void createFichierResultat() throws Exception {
        int databaseSizeBeforeCreate = fichierResultatRepository.findAll().size();

        // Create the FichierResultat
        restFichierResultatMockMvc.perform(post("/api/fichier-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichierResultat)))
            .andExpect(status().isCreated());

        // Validate the FichierResultat in the database
        List<FichierResultat> fichierResultatList = fichierResultatRepository.findAll();
        assertThat(fichierResultatList).hasSize(databaseSizeBeforeCreate + 1);
        FichierResultat testFichierResultat = fichierResultatList.get(fichierResultatList.size() - 1);
        assertThat(testFichierResultat.getCheminFichier()).isEqualTo(DEFAULT_CHEMIN_FICHIER);
        assertThat(testFichierResultat.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testFichierResultat.isAvecStatistiques()).isEqualTo(DEFAULT_AVEC_STATISTIQUES);

        // Validate the FichierResultat in Elasticsearch
        verify(mockFichierResultatSearchRepository, times(1)).save(testFichierResultat);
    }

    @Test
    @Transactional
    public void createFichierResultatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichierResultatRepository.findAll().size();

        // Create the FichierResultat with an existing ID
        fichierResultat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichierResultatMockMvc.perform(post("/api/fichier-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichierResultat)))
            .andExpect(status().isBadRequest());

        // Validate the FichierResultat in the database
        List<FichierResultat> fichierResultatList = fichierResultatRepository.findAll();
        assertThat(fichierResultatList).hasSize(databaseSizeBeforeCreate);

        // Validate the FichierResultat in Elasticsearch
        verify(mockFichierResultatSearchRepository, times(0)).save(fichierResultat);
    }

    @Test
    @Transactional
    public void checkFormatIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichierResultatRepository.findAll().size();
        // set the field null
        fichierResultat.setFormat(null);

        // Create the FichierResultat, which fails.

        restFichierResultatMockMvc.perform(post("/api/fichier-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichierResultat)))
            .andExpect(status().isBadRequest());

        List<FichierResultat> fichierResultatList = fichierResultatRepository.findAll();
        assertThat(fichierResultatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvecStatistiquesIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichierResultatRepository.findAll().size();
        // set the field null
        fichierResultat.setAvecStatistiques(null);

        // Create the FichierResultat, which fails.

        restFichierResultatMockMvc.perform(post("/api/fichier-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichierResultat)))
            .andExpect(status().isBadRequest());

        List<FichierResultat> fichierResultatList = fichierResultatRepository.findAll();
        assertThat(fichierResultatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFichierResultats() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get all the fichierResultatList
        restFichierResultatMockMvc.perform(get("/api/fichier-resultats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichierResultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].cheminFichier").value(hasItem(DEFAULT_CHEMIN_FICHIER.toString())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].avecStatistiques").value(hasItem(DEFAULT_AVEC_STATISTIQUES.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFichierResultat() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get the fichierResultat
        restFichierResultatMockMvc.perform(get("/api/fichier-resultats/{id}", fichierResultat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fichierResultat.getId().intValue()))
            .andExpect(jsonPath("$.cheminFichier").value(DEFAULT_CHEMIN_FICHIER.toString()))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()))
            .andExpect(jsonPath("$.avecStatistiques").value(DEFAULT_AVEC_STATISTIQUES.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllFichierResultatsByCheminFichierIsEqualToSomething() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get all the fichierResultatList where cheminFichier equals to DEFAULT_CHEMIN_FICHIER
        defaultFichierResultatShouldBeFound("cheminFichier.equals=" + DEFAULT_CHEMIN_FICHIER);

        // Get all the fichierResultatList where cheminFichier equals to UPDATED_CHEMIN_FICHIER
        defaultFichierResultatShouldNotBeFound("cheminFichier.equals=" + UPDATED_CHEMIN_FICHIER);
    }

    @Test
    @Transactional
    public void getAllFichierResultatsByCheminFichierIsInShouldWork() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get all the fichierResultatList where cheminFichier in DEFAULT_CHEMIN_FICHIER or UPDATED_CHEMIN_FICHIER
        defaultFichierResultatShouldBeFound("cheminFichier.in=" + DEFAULT_CHEMIN_FICHIER + "," + UPDATED_CHEMIN_FICHIER);

        // Get all the fichierResultatList where cheminFichier equals to UPDATED_CHEMIN_FICHIER
        defaultFichierResultatShouldNotBeFound("cheminFichier.in=" + UPDATED_CHEMIN_FICHIER);
    }

    @Test
    @Transactional
    public void getAllFichierResultatsByCheminFichierIsNullOrNotNull() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get all the fichierResultatList where cheminFichier is not null
        defaultFichierResultatShouldBeFound("cheminFichier.specified=true");

        // Get all the fichierResultatList where cheminFichier is null
        defaultFichierResultatShouldNotBeFound("cheminFichier.specified=false");
    }

    @Test
    @Transactional
    public void getAllFichierResultatsByFormatIsEqualToSomething() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get all the fichierResultatList where format equals to DEFAULT_FORMAT
        defaultFichierResultatShouldBeFound("format.equals=" + DEFAULT_FORMAT);

        // Get all the fichierResultatList where format equals to UPDATED_FORMAT
        defaultFichierResultatShouldNotBeFound("format.equals=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllFichierResultatsByFormatIsInShouldWork() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get all the fichierResultatList where format in DEFAULT_FORMAT or UPDATED_FORMAT
        defaultFichierResultatShouldBeFound("format.in=" + DEFAULT_FORMAT + "," + UPDATED_FORMAT);

        // Get all the fichierResultatList where format equals to UPDATED_FORMAT
        defaultFichierResultatShouldNotBeFound("format.in=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllFichierResultatsByFormatIsNullOrNotNull() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get all the fichierResultatList where format is not null
        defaultFichierResultatShouldBeFound("format.specified=true");

        // Get all the fichierResultatList where format is null
        defaultFichierResultatShouldNotBeFound("format.specified=false");
    }

    @Test
    @Transactional
    public void getAllFichierResultatsByAvecStatistiquesIsEqualToSomething() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get all the fichierResultatList where avecStatistiques equals to DEFAULT_AVEC_STATISTIQUES
        defaultFichierResultatShouldBeFound("avecStatistiques.equals=" + DEFAULT_AVEC_STATISTIQUES);

        // Get all the fichierResultatList where avecStatistiques equals to UPDATED_AVEC_STATISTIQUES
        defaultFichierResultatShouldNotBeFound("avecStatistiques.equals=" + UPDATED_AVEC_STATISTIQUES);
    }

    @Test
    @Transactional
    public void getAllFichierResultatsByAvecStatistiquesIsInShouldWork() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get all the fichierResultatList where avecStatistiques in DEFAULT_AVEC_STATISTIQUES or UPDATED_AVEC_STATISTIQUES
        defaultFichierResultatShouldBeFound("avecStatistiques.in=" + DEFAULT_AVEC_STATISTIQUES + "," + UPDATED_AVEC_STATISTIQUES);

        // Get all the fichierResultatList where avecStatistiques equals to UPDATED_AVEC_STATISTIQUES
        defaultFichierResultatShouldNotBeFound("avecStatistiques.in=" + UPDATED_AVEC_STATISTIQUES);
    }

    @Test
    @Transactional
    public void getAllFichierResultatsByAvecStatistiquesIsNullOrNotNull() throws Exception {
        // Initialize the database
        fichierResultatRepository.saveAndFlush(fichierResultat);

        // Get all the fichierResultatList where avecStatistiques is not null
        defaultFichierResultatShouldBeFound("avecStatistiques.specified=true");

        // Get all the fichierResultatList where avecStatistiques is null
        defaultFichierResultatShouldNotBeFound("avecStatistiques.specified=false");
    }

    @Test
    @Transactional
    public void getAllFichierResultatsByEnvoiResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        EnvoiResultat envoiResultat = EnvoiResultatResourceIntTest.createEntity(em);
        em.persist(envoiResultat);
        em.flush();
        fichierResultat.addEnvoiResultat(envoiResultat);
        fichierResultatRepository.saveAndFlush(fichierResultat);
        Long envoiResultatId = envoiResultat.getId();

        // Get all the fichierResultatList where envoiResultat equals to envoiResultatId
        defaultFichierResultatShouldBeFound("envoiResultatId.equals=" + envoiResultatId);

        // Get all the fichierResultatList where envoiResultat equals to envoiResultatId + 1
        defaultFichierResultatShouldNotBeFound("envoiResultatId.equals=" + (envoiResultatId + 1));
    }


    @Test
    @Transactional
    public void getAllFichierResultatsByResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        Resultat resultat = ResultatResourceIntTest.createEntity(em);
        em.persist(resultat);
        em.flush();
        fichierResultat.setResultat(resultat);
        fichierResultatRepository.saveAndFlush(fichierResultat);
        Long resultatId = resultat.getId();

        // Get all the fichierResultatList where resultat equals to resultatId
        defaultFichierResultatShouldBeFound("resultatId.equals=" + resultatId);

        // Get all the fichierResultatList where resultat equals to resultatId + 1
        defaultFichierResultatShouldNotBeFound("resultatId.equals=" + (resultatId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFichierResultatShouldBeFound(String filter) throws Exception {
        restFichierResultatMockMvc.perform(get("/api/fichier-resultats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichierResultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].cheminFichier").value(hasItem(DEFAULT_CHEMIN_FICHIER)))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].avecStatistiques").value(hasItem(DEFAULT_AVEC_STATISTIQUES.booleanValue())));

        // Check, that the count call also returns 1
        restFichierResultatMockMvc.perform(get("/api/fichier-resultats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFichierResultatShouldNotBeFound(String filter) throws Exception {
        restFichierResultatMockMvc.perform(get("/api/fichier-resultats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFichierResultatMockMvc.perform(get("/api/fichier-resultats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFichierResultat() throws Exception {
        // Get the fichierResultat
        restFichierResultatMockMvc.perform(get("/api/fichier-resultats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFichierResultat() throws Exception {
        // Initialize the database
        fichierResultatService.save(fichierResultat);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFichierResultatSearchRepository);

        int databaseSizeBeforeUpdate = fichierResultatRepository.findAll().size();

        // Update the fichierResultat
        FichierResultat updatedFichierResultat = fichierResultatRepository.findById(fichierResultat.getId()).get();
        // Disconnect from session so that the updates on updatedFichierResultat are not directly saved in db
        em.detach(updatedFichierResultat);
        updatedFichierResultat
            .cheminFichier(UPDATED_CHEMIN_FICHIER)
            .format(UPDATED_FORMAT)
            .avecStatistiques(UPDATED_AVEC_STATISTIQUES);

        restFichierResultatMockMvc.perform(put("/api/fichier-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFichierResultat)))
            .andExpect(status().isOk());

        // Validate the FichierResultat in the database
        List<FichierResultat> fichierResultatList = fichierResultatRepository.findAll();
        assertThat(fichierResultatList).hasSize(databaseSizeBeforeUpdate);
        FichierResultat testFichierResultat = fichierResultatList.get(fichierResultatList.size() - 1);
        assertThat(testFichierResultat.getCheminFichier()).isEqualTo(UPDATED_CHEMIN_FICHIER);
        assertThat(testFichierResultat.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testFichierResultat.isAvecStatistiques()).isEqualTo(UPDATED_AVEC_STATISTIQUES);

        // Validate the FichierResultat in Elasticsearch
        verify(mockFichierResultatSearchRepository, times(1)).save(testFichierResultat);
    }

    @Test
    @Transactional
    public void updateNonExistingFichierResultat() throws Exception {
        int databaseSizeBeforeUpdate = fichierResultatRepository.findAll().size();

        // Create the FichierResultat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichierResultatMockMvc.perform(put("/api/fichier-resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichierResultat)))
            .andExpect(status().isBadRequest());

        // Validate the FichierResultat in the database
        List<FichierResultat> fichierResultatList = fichierResultatRepository.findAll();
        assertThat(fichierResultatList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FichierResultat in Elasticsearch
        verify(mockFichierResultatSearchRepository, times(0)).save(fichierResultat);
    }

    @Test
    @Transactional
    public void deleteFichierResultat() throws Exception {
        // Initialize the database
        fichierResultatService.save(fichierResultat);

        int databaseSizeBeforeDelete = fichierResultatRepository.findAll().size();

        // Delete the fichierResultat
        restFichierResultatMockMvc.perform(delete("/api/fichier-resultats/{id}", fichierResultat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FichierResultat> fichierResultatList = fichierResultatRepository.findAll();
        assertThat(fichierResultatList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FichierResultat in Elasticsearch
        verify(mockFichierResultatSearchRepository, times(1)).deleteById(fichierResultat.getId());
    }

    @Test
    @Transactional
    public void searchFichierResultat() throws Exception {
        // Initialize the database
        fichierResultatService.save(fichierResultat);
        when(mockFichierResultatSearchRepository.search(queryStringQuery("id:" + fichierResultat.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fichierResultat), PageRequest.of(0, 1), 1));
        // Search the fichierResultat
        restFichierResultatMockMvc.perform(get("/api/_search/fichier-resultats?query=id:" + fichierResultat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichierResultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].cheminFichier").value(hasItem(DEFAULT_CHEMIN_FICHIER)))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].avecStatistiques").value(hasItem(DEFAULT_AVEC_STATISTIQUES.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichierResultat.class);
        FichierResultat fichierResultat1 = new FichierResultat();
        fichierResultat1.setId(1L);
        FichierResultat fichierResultat2 = new FichierResultat();
        fichierResultat2.setId(fichierResultat1.getId());
        assertThat(fichierResultat1).isEqualTo(fichierResultat2);
        fichierResultat2.setId(2L);
        assertThat(fichierResultat1).isNotEqualTo(fichierResultat2);
        fichierResultat1.setId(null);
        assertThat(fichierResultat1).isNotEqualTo(fichierResultat2);
    }
}
