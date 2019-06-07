package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Typologie;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.TypologieRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypologieSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypologieService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.TypologieCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypologieQueryService;

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
 * Test class for the TypologieResource REST controller.
 *
 * @see TypologieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class TypologieResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION = "BBBBBBBBBB";

    private static final String DEFAULT_TRAITEMENT = "AAAAAAAAAA";
    private static final String UPDATED_TRAITEMENT = "BBBBBBBBBB";

    @Autowired
    private TypologieRepository typologieRepository;

    @Autowired
    private TypologieService typologieService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypologieSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypologieSearchRepository mockTypologieSearchRepository;

    @Autowired
    private TypologieQueryService typologieQueryService;

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

    private MockMvc restTypologieMockMvc;

    private Typologie typologie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypologieResource typologieResource = new TypologieResource(typologieService, typologieQueryService);
        this.restTypologieMockMvc = MockMvcBuilders.standaloneSetup(typologieResource)
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
    public static Typologie createEntity(EntityManager em) {
        Typologie typologie = new Typologie()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .condition(DEFAULT_CONDITION)
            .traitement(DEFAULT_TRAITEMENT);
        return typologie;
    }

    @Before
    public void initTest() {
        typologie = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypologie() throws Exception {
        int databaseSizeBeforeCreate = typologieRepository.findAll().size();

        // Create the Typologie
        restTypologieMockMvc.perform(post("/api/typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typologie)))
            .andExpect(status().isCreated());

        // Validate the Typologie in the database
        List<Typologie> typologieList = typologieRepository.findAll();
        assertThat(typologieList).hasSize(databaseSizeBeforeCreate + 1);
        Typologie testTypologie = typologieList.get(typologieList.size() - 1);
        assertThat(testTypologie.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypologie.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypologie.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testTypologie.getTraitement()).isEqualTo(DEFAULT_TRAITEMENT);

        // Validate the Typologie in Elasticsearch
        verify(mockTypologieSearchRepository, times(1)).save(testTypologie);
    }

    @Test
    @Transactional
    public void createTypologieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typologieRepository.findAll().size();

        // Create the Typologie with an existing ID
        typologie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypologieMockMvc.perform(post("/api/typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typologie)))
            .andExpect(status().isBadRequest());

        // Validate the Typologie in the database
        List<Typologie> typologieList = typologieRepository.findAll();
        assertThat(typologieList).hasSize(databaseSizeBeforeCreate);

        // Validate the Typologie in Elasticsearch
        verify(mockTypologieSearchRepository, times(0)).save(typologie);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typologieRepository.findAll().size();
        // set the field null
        typologie.setCode(null);

        // Create the Typologie, which fails.

        restTypologieMockMvc.perform(post("/api/typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typologie)))
            .andExpect(status().isBadRequest());

        List<Typologie> typologieList = typologieRepository.findAll();
        assertThat(typologieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typologieRepository.findAll().size();
        // set the field null
        typologie.setLibelle(null);

        // Create the Typologie, which fails.

        restTypologieMockMvc.perform(post("/api/typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typologie)))
            .andExpect(status().isBadRequest());

        List<Typologie> typologieList = typologieRepository.findAll();
        assertThat(typologieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = typologieRepository.findAll().size();
        // set the field null
        typologie.setCondition(null);

        // Create the Typologie, which fails.

        restTypologieMockMvc.perform(post("/api/typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typologie)))
            .andExpect(status().isBadRequest());

        List<Typologie> typologieList = typologieRepository.findAll();
        assertThat(typologieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTraitementIsRequired() throws Exception {
        int databaseSizeBeforeTest = typologieRepository.findAll().size();
        // set the field null
        typologie.setTraitement(null);

        // Create the Typologie, which fails.

        restTypologieMockMvc.perform(post("/api/typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typologie)))
            .andExpect(status().isBadRequest());

        List<Typologie> typologieList = typologieRepository.findAll();
        assertThat(typologieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypologies() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList
        restTypologieMockMvc.perform(get("/api/typologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typologie.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].traitement").value(hasItem(DEFAULT_TRAITEMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getTypologie() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get the typologie
        restTypologieMockMvc.perform(get("/api/typologies/{id}", typologie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typologie.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION.toString()))
            .andExpect(jsonPath("$.traitement").value(DEFAULT_TRAITEMENT.toString()));
    }

    @Test
    @Transactional
    public void getAllTypologiesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where code equals to DEFAULT_CODE
        defaultTypologieShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the typologieList where code equals to UPDATED_CODE
        defaultTypologieShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTypologiesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTypologieShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the typologieList where code equals to UPDATED_CODE
        defaultTypologieShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTypologiesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where code is not null
        defaultTypologieShouldBeFound("code.specified=true");

        // Get all the typologieList where code is null
        defaultTypologieShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypologiesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where libelle equals to DEFAULT_LIBELLE
        defaultTypologieShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the typologieList where libelle equals to UPDATED_LIBELLE
        defaultTypologieShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllTypologiesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultTypologieShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the typologieList where libelle equals to UPDATED_LIBELLE
        defaultTypologieShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllTypologiesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where libelle is not null
        defaultTypologieShouldBeFound("libelle.specified=true");

        // Get all the typologieList where libelle is null
        defaultTypologieShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypologiesByConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where condition equals to DEFAULT_CONDITION
        defaultTypologieShouldBeFound("condition.equals=" + DEFAULT_CONDITION);

        // Get all the typologieList where condition equals to UPDATED_CONDITION
        defaultTypologieShouldNotBeFound("condition.equals=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    public void getAllTypologiesByConditionIsInShouldWork() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where condition in DEFAULT_CONDITION or UPDATED_CONDITION
        defaultTypologieShouldBeFound("condition.in=" + DEFAULT_CONDITION + "," + UPDATED_CONDITION);

        // Get all the typologieList where condition equals to UPDATED_CONDITION
        defaultTypologieShouldNotBeFound("condition.in=" + UPDATED_CONDITION);
    }

    @Test
    @Transactional
    public void getAllTypologiesByConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where condition is not null
        defaultTypologieShouldBeFound("condition.specified=true");

        // Get all the typologieList where condition is null
        defaultTypologieShouldNotBeFound("condition.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypologiesByTraitementIsEqualToSomething() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where traitement equals to DEFAULT_TRAITEMENT
        defaultTypologieShouldBeFound("traitement.equals=" + DEFAULT_TRAITEMENT);

        // Get all the typologieList where traitement equals to UPDATED_TRAITEMENT
        defaultTypologieShouldNotBeFound("traitement.equals=" + UPDATED_TRAITEMENT);
    }

    @Test
    @Transactional
    public void getAllTypologiesByTraitementIsInShouldWork() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where traitement in DEFAULT_TRAITEMENT or UPDATED_TRAITEMENT
        defaultTypologieShouldBeFound("traitement.in=" + DEFAULT_TRAITEMENT + "," + UPDATED_TRAITEMENT);

        // Get all the typologieList where traitement equals to UPDATED_TRAITEMENT
        defaultTypologieShouldNotBeFound("traitement.in=" + UPDATED_TRAITEMENT);
    }

    @Test
    @Transactional
    public void getAllTypologiesByTraitementIsNullOrNotNull() throws Exception {
        // Initialize the database
        typologieRepository.saveAndFlush(typologie);

        // Get all the typologieList where traitement is not null
        defaultTypologieShouldBeFound("traitement.specified=true");

        // Get all the typologieList where traitement is null
        defaultTypologieShouldNotBeFound("traitement.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTypologieShouldBeFound(String filter) throws Exception {
        restTypologieMockMvc.perform(get("/api/typologies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typologie.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION)))
            .andExpect(jsonPath("$.[*].traitement").value(hasItem(DEFAULT_TRAITEMENT)));

        // Check, that the count call also returns 1
        restTypologieMockMvc.perform(get("/api/typologies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTypologieShouldNotBeFound(String filter) throws Exception {
        restTypologieMockMvc.perform(get("/api/typologies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypologieMockMvc.perform(get("/api/typologies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTypologie() throws Exception {
        // Get the typologie
        restTypologieMockMvc.perform(get("/api/typologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypologie() throws Exception {
        // Initialize the database
        typologieService.save(typologie);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTypologieSearchRepository);

        int databaseSizeBeforeUpdate = typologieRepository.findAll().size();

        // Update the typologie
        Typologie updatedTypologie = typologieRepository.findById(typologie.getId()).get();
        // Disconnect from session so that the updates on updatedTypologie are not directly saved in db
        em.detach(updatedTypologie);
        updatedTypologie
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .condition(UPDATED_CONDITION)
            .traitement(UPDATED_TRAITEMENT);

        restTypologieMockMvc.perform(put("/api/typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypologie)))
            .andExpect(status().isOk());

        // Validate the Typologie in the database
        List<Typologie> typologieList = typologieRepository.findAll();
        assertThat(typologieList).hasSize(databaseSizeBeforeUpdate);
        Typologie testTypologie = typologieList.get(typologieList.size() - 1);
        assertThat(testTypologie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypologie.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypologie.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testTypologie.getTraitement()).isEqualTo(UPDATED_TRAITEMENT);

        // Validate the Typologie in Elasticsearch
        verify(mockTypologieSearchRepository, times(1)).save(testTypologie);
    }

    @Test
    @Transactional
    public void updateNonExistingTypologie() throws Exception {
        int databaseSizeBeforeUpdate = typologieRepository.findAll().size();

        // Create the Typologie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypologieMockMvc.perform(put("/api/typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typologie)))
            .andExpect(status().isBadRequest());

        // Validate the Typologie in the database
        List<Typologie> typologieList = typologieRepository.findAll();
        assertThat(typologieList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Typologie in Elasticsearch
        verify(mockTypologieSearchRepository, times(0)).save(typologie);
    }

    @Test
    @Transactional
    public void deleteTypologie() throws Exception {
        // Initialize the database
        typologieService.save(typologie);

        int databaseSizeBeforeDelete = typologieRepository.findAll().size();

        // Delete the typologie
        restTypologieMockMvc.perform(delete("/api/typologies/{id}", typologie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Typologie> typologieList = typologieRepository.findAll();
        assertThat(typologieList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Typologie in Elasticsearch
        verify(mockTypologieSearchRepository, times(1)).deleteById(typologie.getId());
    }

    @Test
    @Transactional
    public void searchTypologie() throws Exception {
        // Initialize the database
        typologieService.save(typologie);
        when(mockTypologieSearchRepository.search(queryStringQuery("id:" + typologie.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(typologie), PageRequest.of(0, 1), 1));
        // Search the typologie
        restTypologieMockMvc.perform(get("/api/_search/typologies?query=id:" + typologie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typologie.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION)))
            .andExpect(jsonPath("$.[*].traitement").value(hasItem(DEFAULT_TRAITEMENT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Typologie.class);
        Typologie typologie1 = new Typologie();
        typologie1.setId(1L);
        Typologie typologie2 = new Typologie();
        typologie2.setId(typologie1.getId());
        assertThat(typologie1).isEqualTo(typologie2);
        typologie2.setId(2L);
        assertThat(typologie1).isNotEqualTo(typologie2);
        typologie1.setId(null);
        assertThat(typologie1).isNotEqualTo(typologie2);
    }
}
