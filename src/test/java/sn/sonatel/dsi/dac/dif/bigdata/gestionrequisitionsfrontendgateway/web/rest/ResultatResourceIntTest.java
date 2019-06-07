package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Resultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.FichierResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ResultatRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ResultatSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ResultatService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ResultatCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ResultatQueryService;

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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.StatusEvolution;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.EtatResultat;
/**
 * Test class for the ResultatResource REST controller.
 *
 * @see ResultatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class ResultatResourceIntTest {

    private static final StatusEvolution DEFAULT_STATUS = StatusEvolution.ENCOURS;
    private static final StatusEvolution UPDATED_STATUS = StatusEvolution.TERMINE;

    private static final EtatResultat DEFAULT_ETAT = EtatResultat.SUCCES;
    private static final EtatResultat UPDATED_ETAT = EtatResultat.SANSREPONSE;

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private ResultatService resultatService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ResultatSearchRepositoryMockConfiguration
     */
    @Autowired
    private ResultatSearchRepository mockResultatSearchRepository;

    @Autowired
    private ResultatQueryService resultatQueryService;

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

    private MockMvc restResultatMockMvc;

    private Resultat resultat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultatResource resultatResource = new ResultatResource(resultatService, resultatQueryService);
        this.restResultatMockMvc = MockMvcBuilders.standaloneSetup(resultatResource)
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
    public static Resultat createEntity(EntityManager em) {
        Resultat resultat = new Resultat()
            .status(DEFAULT_STATUS)
            .etat(DEFAULT_ETAT);
        return resultat;
    }

    @Before
    public void initTest() {
        resultat = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultat() throws Exception {
        int databaseSizeBeforeCreate = resultatRepository.findAll().size();

        // Create the Resultat
        restResultatMockMvc.perform(post("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultat)))
            .andExpect(status().isCreated());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeCreate + 1);
        Resultat testResultat = resultatList.get(resultatList.size() - 1);
        assertThat(testResultat.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testResultat.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the Resultat in Elasticsearch
        verify(mockResultatSearchRepository, times(1)).save(testResultat);
    }

    @Test
    @Transactional
    public void createResultatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultatRepository.findAll().size();

        // Create the Resultat with an existing ID
        resultat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultatMockMvc.perform(post("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultat)))
            .andExpect(status().isBadRequest());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeCreate);

        // Validate the Resultat in Elasticsearch
        verify(mockResultatSearchRepository, times(0)).save(resultat);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultatRepository.findAll().size();
        // set the field null
        resultat.setStatus(null);

        // Create the Resultat, which fails.

        restResultatMockMvc.perform(post("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultat)))
            .andExpect(status().isBadRequest());

        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultats() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList
        restResultatMockMvc.perform(get("/api/resultats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getResultat() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get the resultat
        restResultatMockMvc.perform(get("/api/resultats/{id}", resultat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultat.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getAllResultatsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where status equals to DEFAULT_STATUS
        defaultResultatShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the resultatList where status equals to UPDATED_STATUS
        defaultResultatShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllResultatsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultResultatShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the resultatList where status equals to UPDATED_STATUS
        defaultResultatShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllResultatsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where status is not null
        defaultResultatShouldBeFound("status.specified=true");

        // Get all the resultatList where status is null
        defaultResultatShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultatsByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where etat equals to DEFAULT_ETAT
        defaultResultatShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the resultatList where etat equals to UPDATED_ETAT
        defaultResultatShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllResultatsByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultResultatShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the resultatList where etat equals to UPDATED_ETAT
        defaultResultatShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllResultatsByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where etat is not null
        defaultResultatShouldBeFound("etat.specified=true");

        // Get all the resultatList where etat is null
        defaultResultatShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultatsByChampsRechercheIsEqualToSomething() throws Exception {
        // Initialize the database
        ChampsRecherche champsRecherche = ChampsRechercheResourceIntTest.createEntity(em);
        em.persist(champsRecherche);
        em.flush();
        resultat.setChampsRecherche(champsRecherche);
        resultatRepository.saveAndFlush(resultat);
        Long champsRechercheId = champsRecherche.getId();

        // Get all the resultatList where champsRecherche equals to champsRechercheId
        defaultResultatShouldBeFound("champsRechercheId.equals=" + champsRechercheId);

        // Get all the resultatList where champsRecherche equals to champsRechercheId + 1
        defaultResultatShouldNotBeFound("champsRechercheId.equals=" + (champsRechercheId + 1));
    }


    @Test
    @Transactional
    public void getAllResultatsByFichierResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        FichierResultat fichierResultat = FichierResultatResourceIntTest.createEntity(em);
        em.persist(fichierResultat);
        em.flush();
        resultat.addFichierResultat(fichierResultat);
        resultatRepository.saveAndFlush(resultat);
        Long fichierResultatId = fichierResultat.getId();

        // Get all the resultatList where fichierResultat equals to fichierResultatId
        defaultResultatShouldBeFound("fichierResultatId.equals=" + fichierResultatId);

        // Get all the resultatList where fichierResultat equals to fichierResultatId + 1
        defaultResultatShouldNotBeFound("fichierResultatId.equals=" + (fichierResultatId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultResultatShouldBeFound(String filter) throws Exception {
        restResultatMockMvc.perform(get("/api/resultats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));

        // Check, that the count call also returns 1
        restResultatMockMvc.perform(get("/api/resultats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultResultatShouldNotBeFound(String filter) throws Exception {
        restResultatMockMvc.perform(get("/api/resultats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResultatMockMvc.perform(get("/api/resultats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResultat() throws Exception {
        // Get the resultat
        restResultatMockMvc.perform(get("/api/resultats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultat() throws Exception {
        // Initialize the database
        resultatService.save(resultat);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockResultatSearchRepository);

        int databaseSizeBeforeUpdate = resultatRepository.findAll().size();

        // Update the resultat
        Resultat updatedResultat = resultatRepository.findById(resultat.getId()).get();
        // Disconnect from session so that the updates on updatedResultat are not directly saved in db
        em.detach(updatedResultat);
        updatedResultat
            .status(UPDATED_STATUS)
            .etat(UPDATED_ETAT);

        restResultatMockMvc.perform(put("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultat)))
            .andExpect(status().isOk());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeUpdate);
        Resultat testResultat = resultatList.get(resultatList.size() - 1);
        assertThat(testResultat.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testResultat.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the Resultat in Elasticsearch
        verify(mockResultatSearchRepository, times(1)).save(testResultat);
    }

    @Test
    @Transactional
    public void updateNonExistingResultat() throws Exception {
        int databaseSizeBeforeUpdate = resultatRepository.findAll().size();

        // Create the Resultat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatMockMvc.perform(put("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultat)))
            .andExpect(status().isBadRequest());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Resultat in Elasticsearch
        verify(mockResultatSearchRepository, times(0)).save(resultat);
    }

    @Test
    @Transactional
    public void deleteResultat() throws Exception {
        // Initialize the database
        resultatService.save(resultat);

        int databaseSizeBeforeDelete = resultatRepository.findAll().size();

        // Delete the resultat
        restResultatMockMvc.perform(delete("/api/resultats/{id}", resultat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Resultat in Elasticsearch
        verify(mockResultatSearchRepository, times(1)).deleteById(resultat.getId());
    }

    @Test
    @Transactional
    public void searchResultat() throws Exception {
        // Initialize the database
        resultatService.save(resultat);
        when(mockResultatSearchRepository.search(queryStringQuery("id:" + resultat.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(resultat), PageRequest.of(0, 1), 1));
        // Search the resultat
        restResultatMockMvc.perform(get("/api/_search/resultats?query=id:" + resultat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resultat.class);
        Resultat resultat1 = new Resultat();
        resultat1.setId(1L);
        Resultat resultat2 = new Resultat();
        resultat2.setId(resultat1.getId());
        assertThat(resultat1).isEqualTo(resultat2);
        resultat2.setId(2L);
        assertThat(resultat1).isNotEqualTo(resultat2);
        resultat1.setId(null);
        assertThat(resultat1).isNotEqualTo(resultat2);
    }
}
