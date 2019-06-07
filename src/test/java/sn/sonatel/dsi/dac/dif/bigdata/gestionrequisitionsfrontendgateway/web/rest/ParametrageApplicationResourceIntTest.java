package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ParametrageApplication;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ParametrageApplicationRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ParametrageApplicationSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ParametrageApplicationService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ParametrageApplicationCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ParametrageApplicationQueryService;

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
 * Test class for the ParametrageApplicationResource REST controller.
 *
 * @see ParametrageApplicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class ParametrageApplicationResourceIntTest {

    private static final String DEFAULT_NOM_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_NOM_FICHIER = "BBBBBBBBBB";

    private static final String DEFAULT_CHEMIN_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_CHEMIN_FICHIER = "BBBBBBBBBB";

    @Autowired
    private ParametrageApplicationRepository parametrageApplicationRepository;

    @Autowired
    private ParametrageApplicationService parametrageApplicationService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ParametrageApplicationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParametrageApplicationSearchRepository mockParametrageApplicationSearchRepository;

    @Autowired
    private ParametrageApplicationQueryService parametrageApplicationQueryService;

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

    private MockMvc restParametrageApplicationMockMvc;

    private ParametrageApplication parametrageApplication;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParametrageApplicationResource parametrageApplicationResource = new ParametrageApplicationResource(parametrageApplicationService, parametrageApplicationQueryService);
        this.restParametrageApplicationMockMvc = MockMvcBuilders.standaloneSetup(parametrageApplicationResource)
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
    public static ParametrageApplication createEntity(EntityManager em) {
        ParametrageApplication parametrageApplication = new ParametrageApplication()
            .nomFichier(DEFAULT_NOM_FICHIER)
            .cheminFichier(DEFAULT_CHEMIN_FICHIER);
        return parametrageApplication;
    }

    @Before
    public void initTest() {
        parametrageApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametrageApplication() throws Exception {
        int databaseSizeBeforeCreate = parametrageApplicationRepository.findAll().size();

        // Create the ParametrageApplication
        restParametrageApplicationMockMvc.perform(post("/api/parametrage-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametrageApplication)))
            .andExpect(status().isCreated());

        // Validate the ParametrageApplication in the database
        List<ParametrageApplication> parametrageApplicationList = parametrageApplicationRepository.findAll();
        assertThat(parametrageApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        ParametrageApplication testParametrageApplication = parametrageApplicationList.get(parametrageApplicationList.size() - 1);
        assertThat(testParametrageApplication.getNomFichier()).isEqualTo(DEFAULT_NOM_FICHIER);
        assertThat(testParametrageApplication.getCheminFichier()).isEqualTo(DEFAULT_CHEMIN_FICHIER);

        // Validate the ParametrageApplication in Elasticsearch
        verify(mockParametrageApplicationSearchRepository, times(1)).save(testParametrageApplication);
    }

    @Test
    @Transactional
    public void createParametrageApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametrageApplicationRepository.findAll().size();

        // Create the ParametrageApplication with an existing ID
        parametrageApplication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametrageApplicationMockMvc.perform(post("/api/parametrage-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametrageApplication)))
            .andExpect(status().isBadRequest());

        // Validate the ParametrageApplication in the database
        List<ParametrageApplication> parametrageApplicationList = parametrageApplicationRepository.findAll();
        assertThat(parametrageApplicationList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParametrageApplication in Elasticsearch
        verify(mockParametrageApplicationSearchRepository, times(0)).save(parametrageApplication);
    }

    @Test
    @Transactional
    public void checkNomFichierIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametrageApplicationRepository.findAll().size();
        // set the field null
        parametrageApplication.setNomFichier(null);

        // Create the ParametrageApplication, which fails.

        restParametrageApplicationMockMvc.perform(post("/api/parametrage-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametrageApplication)))
            .andExpect(status().isBadRequest());

        List<ParametrageApplication> parametrageApplicationList = parametrageApplicationRepository.findAll();
        assertThat(parametrageApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheminFichierIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametrageApplicationRepository.findAll().size();
        // set the field null
        parametrageApplication.setCheminFichier(null);

        // Create the ParametrageApplication, which fails.

        restParametrageApplicationMockMvc.perform(post("/api/parametrage-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametrageApplication)))
            .andExpect(status().isBadRequest());

        List<ParametrageApplication> parametrageApplicationList = parametrageApplicationRepository.findAll();
        assertThat(parametrageApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametrageApplications() throws Exception {
        // Initialize the database
        parametrageApplicationRepository.saveAndFlush(parametrageApplication);

        // Get all the parametrageApplicationList
        restParametrageApplicationMockMvc.perform(get("/api/parametrage-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametrageApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomFichier").value(hasItem(DEFAULT_NOM_FICHIER.toString())))
            .andExpect(jsonPath("$.[*].cheminFichier").value(hasItem(DEFAULT_CHEMIN_FICHIER.toString())));
    }
    
    @Test
    @Transactional
    public void getParametrageApplication() throws Exception {
        // Initialize the database
        parametrageApplicationRepository.saveAndFlush(parametrageApplication);

        // Get the parametrageApplication
        restParametrageApplicationMockMvc.perform(get("/api/parametrage-applications/{id}", parametrageApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametrageApplication.getId().intValue()))
            .andExpect(jsonPath("$.nomFichier").value(DEFAULT_NOM_FICHIER.toString()))
            .andExpect(jsonPath("$.cheminFichier").value(DEFAULT_CHEMIN_FICHIER.toString()));
    }

    @Test
    @Transactional
    public void getAllParametrageApplicationsByNomFichierIsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageApplicationRepository.saveAndFlush(parametrageApplication);

        // Get all the parametrageApplicationList where nomFichier equals to DEFAULT_NOM_FICHIER
        defaultParametrageApplicationShouldBeFound("nomFichier.equals=" + DEFAULT_NOM_FICHIER);

        // Get all the parametrageApplicationList where nomFichier equals to UPDATED_NOM_FICHIER
        defaultParametrageApplicationShouldNotBeFound("nomFichier.equals=" + UPDATED_NOM_FICHIER);
    }

    @Test
    @Transactional
    public void getAllParametrageApplicationsByNomFichierIsInShouldWork() throws Exception {
        // Initialize the database
        parametrageApplicationRepository.saveAndFlush(parametrageApplication);

        // Get all the parametrageApplicationList where nomFichier in DEFAULT_NOM_FICHIER or UPDATED_NOM_FICHIER
        defaultParametrageApplicationShouldBeFound("nomFichier.in=" + DEFAULT_NOM_FICHIER + "," + UPDATED_NOM_FICHIER);

        // Get all the parametrageApplicationList where nomFichier equals to UPDATED_NOM_FICHIER
        defaultParametrageApplicationShouldNotBeFound("nomFichier.in=" + UPDATED_NOM_FICHIER);
    }

    @Test
    @Transactional
    public void getAllParametrageApplicationsByNomFichierIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageApplicationRepository.saveAndFlush(parametrageApplication);

        // Get all the parametrageApplicationList where nomFichier is not null
        defaultParametrageApplicationShouldBeFound("nomFichier.specified=true");

        // Get all the parametrageApplicationList where nomFichier is null
        defaultParametrageApplicationShouldNotBeFound("nomFichier.specified=false");
    }

    @Test
    @Transactional
    public void getAllParametrageApplicationsByCheminFichierIsEqualToSomething() throws Exception {
        // Initialize the database
        parametrageApplicationRepository.saveAndFlush(parametrageApplication);

        // Get all the parametrageApplicationList where cheminFichier equals to DEFAULT_CHEMIN_FICHIER
        defaultParametrageApplicationShouldBeFound("cheminFichier.equals=" + DEFAULT_CHEMIN_FICHIER);

        // Get all the parametrageApplicationList where cheminFichier equals to UPDATED_CHEMIN_FICHIER
        defaultParametrageApplicationShouldNotBeFound("cheminFichier.equals=" + UPDATED_CHEMIN_FICHIER);
    }

    @Test
    @Transactional
    public void getAllParametrageApplicationsByCheminFichierIsInShouldWork() throws Exception {
        // Initialize the database
        parametrageApplicationRepository.saveAndFlush(parametrageApplication);

        // Get all the parametrageApplicationList where cheminFichier in DEFAULT_CHEMIN_FICHIER or UPDATED_CHEMIN_FICHIER
        defaultParametrageApplicationShouldBeFound("cheminFichier.in=" + DEFAULT_CHEMIN_FICHIER + "," + UPDATED_CHEMIN_FICHIER);

        // Get all the parametrageApplicationList where cheminFichier equals to UPDATED_CHEMIN_FICHIER
        defaultParametrageApplicationShouldNotBeFound("cheminFichier.in=" + UPDATED_CHEMIN_FICHIER);
    }

    @Test
    @Transactional
    public void getAllParametrageApplicationsByCheminFichierIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrageApplicationRepository.saveAndFlush(parametrageApplication);

        // Get all the parametrageApplicationList where cheminFichier is not null
        defaultParametrageApplicationShouldBeFound("cheminFichier.specified=true");

        // Get all the parametrageApplicationList where cheminFichier is null
        defaultParametrageApplicationShouldNotBeFound("cheminFichier.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParametrageApplicationShouldBeFound(String filter) throws Exception {
        restParametrageApplicationMockMvc.perform(get("/api/parametrage-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametrageApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomFichier").value(hasItem(DEFAULT_NOM_FICHIER)))
            .andExpect(jsonPath("$.[*].cheminFichier").value(hasItem(DEFAULT_CHEMIN_FICHIER)));

        // Check, that the count call also returns 1
        restParametrageApplicationMockMvc.perform(get("/api/parametrage-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParametrageApplicationShouldNotBeFound(String filter) throws Exception {
        restParametrageApplicationMockMvc.perform(get("/api/parametrage-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParametrageApplicationMockMvc.perform(get("/api/parametrage-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParametrageApplication() throws Exception {
        // Get the parametrageApplication
        restParametrageApplicationMockMvc.perform(get("/api/parametrage-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametrageApplication() throws Exception {
        // Initialize the database
        parametrageApplicationService.save(parametrageApplication);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockParametrageApplicationSearchRepository);

        int databaseSizeBeforeUpdate = parametrageApplicationRepository.findAll().size();

        // Update the parametrageApplication
        ParametrageApplication updatedParametrageApplication = parametrageApplicationRepository.findById(parametrageApplication.getId()).get();
        // Disconnect from session so that the updates on updatedParametrageApplication are not directly saved in db
        em.detach(updatedParametrageApplication);
        updatedParametrageApplication
            .nomFichier(UPDATED_NOM_FICHIER)
            .cheminFichier(UPDATED_CHEMIN_FICHIER);

        restParametrageApplicationMockMvc.perform(put("/api/parametrage-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParametrageApplication)))
            .andExpect(status().isOk());

        // Validate the ParametrageApplication in the database
        List<ParametrageApplication> parametrageApplicationList = parametrageApplicationRepository.findAll();
        assertThat(parametrageApplicationList).hasSize(databaseSizeBeforeUpdate);
        ParametrageApplication testParametrageApplication = parametrageApplicationList.get(parametrageApplicationList.size() - 1);
        assertThat(testParametrageApplication.getNomFichier()).isEqualTo(UPDATED_NOM_FICHIER);
        assertThat(testParametrageApplication.getCheminFichier()).isEqualTo(UPDATED_CHEMIN_FICHIER);

        // Validate the ParametrageApplication in Elasticsearch
        verify(mockParametrageApplicationSearchRepository, times(1)).save(testParametrageApplication);
    }

    @Test
    @Transactional
    public void updateNonExistingParametrageApplication() throws Exception {
        int databaseSizeBeforeUpdate = parametrageApplicationRepository.findAll().size();

        // Create the ParametrageApplication

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametrageApplicationMockMvc.perform(put("/api/parametrage-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametrageApplication)))
            .andExpect(status().isBadRequest());

        // Validate the ParametrageApplication in the database
        List<ParametrageApplication> parametrageApplicationList = parametrageApplicationRepository.findAll();
        assertThat(parametrageApplicationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParametrageApplication in Elasticsearch
        verify(mockParametrageApplicationSearchRepository, times(0)).save(parametrageApplication);
    }

    @Test
    @Transactional
    public void deleteParametrageApplication() throws Exception {
        // Initialize the database
        parametrageApplicationService.save(parametrageApplication);

        int databaseSizeBeforeDelete = parametrageApplicationRepository.findAll().size();

        // Delete the parametrageApplication
        restParametrageApplicationMockMvc.perform(delete("/api/parametrage-applications/{id}", parametrageApplication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParametrageApplication> parametrageApplicationList = parametrageApplicationRepository.findAll();
        assertThat(parametrageApplicationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParametrageApplication in Elasticsearch
        verify(mockParametrageApplicationSearchRepository, times(1)).deleteById(parametrageApplication.getId());
    }

    @Test
    @Transactional
    public void searchParametrageApplication() throws Exception {
        // Initialize the database
        parametrageApplicationService.save(parametrageApplication);
        when(mockParametrageApplicationSearchRepository.search(queryStringQuery("id:" + parametrageApplication.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(parametrageApplication), PageRequest.of(0, 1), 1));
        // Search the parametrageApplication
        restParametrageApplicationMockMvc.perform(get("/api/_search/parametrage-applications?query=id:" + parametrageApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametrageApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomFichier").value(hasItem(DEFAULT_NOM_FICHIER)))
            .andExpect(jsonPath("$.[*].cheminFichier").value(hasItem(DEFAULT_CHEMIN_FICHIER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametrageApplication.class);
        ParametrageApplication parametrageApplication1 = new ParametrageApplication();
        parametrageApplication1.setId(1L);
        ParametrageApplication parametrageApplication2 = new ParametrageApplication();
        parametrageApplication2.setId(parametrageApplication1.getId());
        assertThat(parametrageApplication1).isEqualTo(parametrageApplication2);
        parametrageApplication2.setId(2L);
        assertThat(parametrageApplication1).isNotEqualTo(parametrageApplication2);
        parametrageApplication1.setId(null);
        assertThat(parametrageApplication1).isNotEqualTo(parametrageApplication2);
    }
}
