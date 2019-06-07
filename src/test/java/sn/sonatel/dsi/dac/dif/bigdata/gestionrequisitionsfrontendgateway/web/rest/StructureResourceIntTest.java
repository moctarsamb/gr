package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Structure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Requisition;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.StructureRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.StructureSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.StructureService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.StructureCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.StructureQueryService;

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
 * Test class for the StructureResource REST controller.
 *
 * @see StructureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class StructureResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private StructureService structureService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.StructureSearchRepositoryMockConfiguration
     */
    @Autowired
    private StructureSearchRepository mockStructureSearchRepository;

    @Autowired
    private StructureQueryService structureQueryService;

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

    private MockMvc restStructureMockMvc;

    private Structure structure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StructureResource structureResource = new StructureResource(structureService, structureQueryService);
        this.restStructureMockMvc = MockMvcBuilders.standaloneSetup(structureResource)
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
    public static Structure createEntity(EntityManager em) {
        Structure structure = new Structure()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return structure;
    }

    @Before
    public void initTest() {
        structure = createEntity(em);
    }

    @Test
    @Transactional
    public void createStructure() throws Exception {
        int databaseSizeBeforeCreate = structureRepository.findAll().size();

        // Create the Structure
        restStructureMockMvc.perform(post("/api/structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isCreated());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeCreate + 1);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStructure.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the Structure in Elasticsearch
        verify(mockStructureSearchRepository, times(1)).save(testStructure);
    }

    @Test
    @Transactional
    public void createStructureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = structureRepository.findAll().size();

        // Create the Structure with an existing ID
        structure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStructureMockMvc.perform(post("/api/structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeCreate);

        // Validate the Structure in Elasticsearch
        verify(mockStructureSearchRepository, times(0)).save(structure);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = structureRepository.findAll().size();
        // set the field null
        structure.setCode(null);

        // Create the Structure, which fails.

        restStructureMockMvc.perform(post("/api/structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = structureRepository.findAll().size();
        // set the field null
        structure.setLibelle(null);

        // Create the Structure, which fails.

        restStructureMockMvc.perform(post("/api/structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStructures() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get all the structureList
        restStructureMockMvc.perform(get("/api/structures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(structure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getStructure() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get the structure
        restStructureMockMvc.perform(get("/api/structures/{id}", structure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(structure.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getAllStructuresByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get all the structureList where code equals to DEFAULT_CODE
        defaultStructureShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the structureList where code equals to UPDATED_CODE
        defaultStructureShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllStructuresByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get all the structureList where code in DEFAULT_CODE or UPDATED_CODE
        defaultStructureShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the structureList where code equals to UPDATED_CODE
        defaultStructureShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllStructuresByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get all the structureList where code is not null
        defaultStructureShouldBeFound("code.specified=true");

        // Get all the structureList where code is null
        defaultStructureShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllStructuresByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get all the structureList where libelle equals to DEFAULT_LIBELLE
        defaultStructureShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the structureList where libelle equals to UPDATED_LIBELLE
        defaultStructureShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllStructuresByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get all the structureList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultStructureShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the structureList where libelle equals to UPDATED_LIBELLE
        defaultStructureShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllStructuresByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        structureRepository.saveAndFlush(structure);

        // Get all the structureList where libelle is not null
        defaultStructureShouldBeFound("libelle.specified=true");

        // Get all the structureList where libelle is null
        defaultStructureShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllStructuresByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        structure.addRequisition(requisition);
        structureRepository.saveAndFlush(structure);
        Long requisitionId = requisition.getId();

        // Get all the structureList where requisition equals to requisitionId
        defaultStructureShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the structureList where requisition equals to requisitionId + 1
        defaultStructureShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }


    @Test
    @Transactional
    public void getAllStructuresByFilialeIsEqualToSomething() throws Exception {
        // Initialize the database
        Filiale filiale = FilialeResourceIntTest.createEntity(em);
        em.persist(filiale);
        em.flush();
        structure.setFiliale(filiale);
        structureRepository.saveAndFlush(structure);
        Long filialeId = filiale.getId();

        // Get all the structureList where filiale equals to filialeId
        defaultStructureShouldBeFound("filialeId.equals=" + filialeId);

        // Get all the structureList where filiale equals to filialeId + 1
        defaultStructureShouldNotBeFound("filialeId.equals=" + (filialeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStructureShouldBeFound(String filter) throws Exception {
        restStructureMockMvc.perform(get("/api/structures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(structure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restStructureMockMvc.perform(get("/api/structures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStructureShouldNotBeFound(String filter) throws Exception {
        restStructureMockMvc.perform(get("/api/structures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStructureMockMvc.perform(get("/api/structures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStructure() throws Exception {
        // Get the structure
        restStructureMockMvc.perform(get("/api/structures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStructure() throws Exception {
        // Initialize the database
        structureService.save(structure);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockStructureSearchRepository);

        int databaseSizeBeforeUpdate = structureRepository.findAll().size();

        // Update the structure
        Structure updatedStructure = structureRepository.findById(structure.getId()).get();
        // Disconnect from session so that the updates on updatedStructure are not directly saved in db
        em.detach(updatedStructure);
        updatedStructure
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restStructureMockMvc.perform(put("/api/structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStructure)))
            .andExpect(status().isOk());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);
        Structure testStructure = structureList.get(structureList.size() - 1);
        assertThat(testStructure.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStructure.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the Structure in Elasticsearch
        verify(mockStructureSearchRepository, times(1)).save(testStructure);
    }

    @Test
    @Transactional
    public void updateNonExistingStructure() throws Exception {
        int databaseSizeBeforeUpdate = structureRepository.findAll().size();

        // Create the Structure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStructureMockMvc.perform(put("/api/structures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(structure)))
            .andExpect(status().isBadRequest());

        // Validate the Structure in the database
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Structure in Elasticsearch
        verify(mockStructureSearchRepository, times(0)).save(structure);
    }

    @Test
    @Transactional
    public void deleteStructure() throws Exception {
        // Initialize the database
        structureService.save(structure);

        int databaseSizeBeforeDelete = structureRepository.findAll().size();

        // Delete the structure
        restStructureMockMvc.perform(delete("/api/structures/{id}", structure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Structure> structureList = structureRepository.findAll();
        assertThat(structureList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Structure in Elasticsearch
        verify(mockStructureSearchRepository, times(1)).deleteById(structure.getId());
    }

    @Test
    @Transactional
    public void searchStructure() throws Exception {
        // Initialize the database
        structureService.save(structure);
        when(mockStructureSearchRepository.search(queryStringQuery("id:" + structure.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(structure), PageRequest.of(0, 1), 1));
        // Search the structure
        restStructureMockMvc.perform(get("/api/_search/structures?query=id:" + structure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(structure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Structure.class);
        Structure structure1 = new Structure();
        structure1.setId(1L);
        Structure structure2 = new Structure();
        structure2.setId(structure1.getId());
        assertThat(structure1).isEqualTo(structure2);
        structure2.setId(2L);
        assertThat(structure1).isNotEqualTo(structure2);
        structure1.setId(null);
        assertThat(structure1).isNotEqualTo(structure2);
    }
}
