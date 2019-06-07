package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Provenance;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Requisition;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ProvenanceRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ProvenanceSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ProvenanceService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ProvenanceCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ProvenanceQueryService;

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
 * Test class for the ProvenanceResource REST controller.
 *
 * @see ProvenanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class ProvenanceResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private ProvenanceRepository provenanceRepository;

    @Autowired
    private ProvenanceService provenanceService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ProvenanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProvenanceSearchRepository mockProvenanceSearchRepository;

    @Autowired
    private ProvenanceQueryService provenanceQueryService;

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

    private MockMvc restProvenanceMockMvc;

    private Provenance provenance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProvenanceResource provenanceResource = new ProvenanceResource(provenanceService, provenanceQueryService);
        this.restProvenanceMockMvc = MockMvcBuilders.standaloneSetup(provenanceResource)
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
    public static Provenance createEntity(EntityManager em) {
        Provenance provenance = new Provenance()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .email(DEFAULT_EMAIL);
        return provenance;
    }

    @Before
    public void initTest() {
        provenance = createEntity(em);
    }

    @Test
    @Transactional
    public void createProvenance() throws Exception {
        int databaseSizeBeforeCreate = provenanceRepository.findAll().size();

        // Create the Provenance
        restProvenanceMockMvc.perform(post("/api/provenances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(provenance)))
            .andExpect(status().isCreated());

        // Validate the Provenance in the database
        List<Provenance> provenanceList = provenanceRepository.findAll();
        assertThat(provenanceList).hasSize(databaseSizeBeforeCreate + 1);
        Provenance testProvenance = provenanceList.get(provenanceList.size() - 1);
        assertThat(testProvenance.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProvenance.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testProvenance.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the Provenance in Elasticsearch
        verify(mockProvenanceSearchRepository, times(1)).save(testProvenance);
    }

    @Test
    @Transactional
    public void createProvenanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = provenanceRepository.findAll().size();

        // Create the Provenance with an existing ID
        provenance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvenanceMockMvc.perform(post("/api/provenances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(provenance)))
            .andExpect(status().isBadRequest());

        // Validate the Provenance in the database
        List<Provenance> provenanceList = provenanceRepository.findAll();
        assertThat(provenanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Provenance in Elasticsearch
        verify(mockProvenanceSearchRepository, times(0)).save(provenance);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = provenanceRepository.findAll().size();
        // set the field null
        provenance.setCode(null);

        // Create the Provenance, which fails.

        restProvenanceMockMvc.perform(post("/api/provenances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(provenance)))
            .andExpect(status().isBadRequest());

        List<Provenance> provenanceList = provenanceRepository.findAll();
        assertThat(provenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = provenanceRepository.findAll().size();
        // set the field null
        provenance.setLibelle(null);

        // Create the Provenance, which fails.

        restProvenanceMockMvc.perform(post("/api/provenances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(provenance)))
            .andExpect(status().isBadRequest());

        List<Provenance> provenanceList = provenanceRepository.findAll();
        assertThat(provenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = provenanceRepository.findAll().size();
        // set the field null
        provenance.setEmail(null);

        // Create the Provenance, which fails.

        restProvenanceMockMvc.perform(post("/api/provenances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(provenance)))
            .andExpect(status().isBadRequest());

        List<Provenance> provenanceList = provenanceRepository.findAll();
        assertThat(provenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProvenances() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get all the provenanceList
        restProvenanceMockMvc.perform(get("/api/provenances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(provenance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getProvenance() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get the provenance
        restProvenanceMockMvc.perform(get("/api/provenances/{id}", provenance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(provenance.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getAllProvenancesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get all the provenanceList where code equals to DEFAULT_CODE
        defaultProvenanceShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the provenanceList where code equals to UPDATED_CODE
        defaultProvenanceShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProvenancesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get all the provenanceList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProvenanceShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the provenanceList where code equals to UPDATED_CODE
        defaultProvenanceShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProvenancesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get all the provenanceList where code is not null
        defaultProvenanceShouldBeFound("code.specified=true");

        // Get all the provenanceList where code is null
        defaultProvenanceShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProvenancesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get all the provenanceList where libelle equals to DEFAULT_LIBELLE
        defaultProvenanceShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the provenanceList where libelle equals to UPDATED_LIBELLE
        defaultProvenanceShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllProvenancesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get all the provenanceList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultProvenanceShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the provenanceList where libelle equals to UPDATED_LIBELLE
        defaultProvenanceShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllProvenancesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get all the provenanceList where libelle is not null
        defaultProvenanceShouldBeFound("libelle.specified=true");

        // Get all the provenanceList where libelle is null
        defaultProvenanceShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllProvenancesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get all the provenanceList where email equals to DEFAULT_EMAIL
        defaultProvenanceShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the provenanceList where email equals to UPDATED_EMAIL
        defaultProvenanceShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllProvenancesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get all the provenanceList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultProvenanceShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the provenanceList where email equals to UPDATED_EMAIL
        defaultProvenanceShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllProvenancesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        provenanceRepository.saveAndFlush(provenance);

        // Get all the provenanceList where email is not null
        defaultProvenanceShouldBeFound("email.specified=true");

        // Get all the provenanceList where email is null
        defaultProvenanceShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllProvenancesByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        provenance.addRequisition(requisition);
        provenanceRepository.saveAndFlush(provenance);
        Long requisitionId = requisition.getId();

        // Get all the provenanceList where requisition equals to requisitionId
        defaultProvenanceShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the provenanceList where requisition equals to requisitionId + 1
        defaultProvenanceShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProvenanceShouldBeFound(String filter) throws Exception {
        restProvenanceMockMvc.perform(get("/api/provenances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(provenance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restProvenanceMockMvc.perform(get("/api/provenances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProvenanceShouldNotBeFound(String filter) throws Exception {
        restProvenanceMockMvc.perform(get("/api/provenances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProvenanceMockMvc.perform(get("/api/provenances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProvenance() throws Exception {
        // Get the provenance
        restProvenanceMockMvc.perform(get("/api/provenances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProvenance() throws Exception {
        // Initialize the database
        provenanceService.save(provenance);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockProvenanceSearchRepository);

        int databaseSizeBeforeUpdate = provenanceRepository.findAll().size();

        // Update the provenance
        Provenance updatedProvenance = provenanceRepository.findById(provenance.getId()).get();
        // Disconnect from session so that the updates on updatedProvenance are not directly saved in db
        em.detach(updatedProvenance);
        updatedProvenance
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .email(UPDATED_EMAIL);

        restProvenanceMockMvc.perform(put("/api/provenances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProvenance)))
            .andExpect(status().isOk());

        // Validate the Provenance in the database
        List<Provenance> provenanceList = provenanceRepository.findAll();
        assertThat(provenanceList).hasSize(databaseSizeBeforeUpdate);
        Provenance testProvenance = provenanceList.get(provenanceList.size() - 1);
        assertThat(testProvenance.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProvenance.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testProvenance.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the Provenance in Elasticsearch
        verify(mockProvenanceSearchRepository, times(1)).save(testProvenance);
    }

    @Test
    @Transactional
    public void updateNonExistingProvenance() throws Exception {
        int databaseSizeBeforeUpdate = provenanceRepository.findAll().size();

        // Create the Provenance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvenanceMockMvc.perform(put("/api/provenances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(provenance)))
            .andExpect(status().isBadRequest());

        // Validate the Provenance in the database
        List<Provenance> provenanceList = provenanceRepository.findAll();
        assertThat(provenanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Provenance in Elasticsearch
        verify(mockProvenanceSearchRepository, times(0)).save(provenance);
    }

    @Test
    @Transactional
    public void deleteProvenance() throws Exception {
        // Initialize the database
        provenanceService.save(provenance);

        int databaseSizeBeforeDelete = provenanceRepository.findAll().size();

        // Delete the provenance
        restProvenanceMockMvc.perform(delete("/api/provenances/{id}", provenance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Provenance> provenanceList = provenanceRepository.findAll();
        assertThat(provenanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Provenance in Elasticsearch
        verify(mockProvenanceSearchRepository, times(1)).deleteById(provenance.getId());
    }

    @Test
    @Transactional
    public void searchProvenance() throws Exception {
        // Initialize the database
        provenanceService.save(provenance);
        when(mockProvenanceSearchRepository.search(queryStringQuery("id:" + provenance.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(provenance), PageRequest.of(0, 1), 1));
        // Search the provenance
        restProvenanceMockMvc.perform(get("/api/_search/provenances?query=id:" + provenance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(provenance.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Provenance.class);
        Provenance provenance1 = new Provenance();
        provenance1.setId(1L);
        Provenance provenance2 = new Provenance();
        provenance2.setId(provenance1.getId());
        assertThat(provenance1).isEqualTo(provenance2);
        provenance2.setId(2L);
        assertThat(provenance1).isNotEqualTo(provenance2);
        provenance1.setId(null);
        assertThat(provenance1).isNotEqualTo(provenance2);
    }
}
