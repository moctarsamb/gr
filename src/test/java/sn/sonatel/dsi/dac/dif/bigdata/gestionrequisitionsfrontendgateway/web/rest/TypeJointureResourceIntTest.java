package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeJointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Jointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.TypeJointureRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypeJointureSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypeJointureService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.TypeJointureCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypeJointureQueryService;

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
 * Test class for the TypeJointureResource REST controller.
 *
 * @see TypeJointureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class TypeJointureResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private TypeJointureRepository typeJointureRepository;

    @Autowired
    private TypeJointureService typeJointureService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypeJointureSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeJointureSearchRepository mockTypeJointureSearchRepository;

    @Autowired
    private TypeJointureQueryService typeJointureQueryService;

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

    private MockMvc restTypeJointureMockMvc;

    private TypeJointure typeJointure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeJointureResource typeJointureResource = new TypeJointureResource(typeJointureService, typeJointureQueryService);
        this.restTypeJointureMockMvc = MockMvcBuilders.standaloneSetup(typeJointureResource)
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
    public static TypeJointure createEntity(EntityManager em) {
        TypeJointure typeJointure = new TypeJointure()
            .type(DEFAULT_TYPE);
        return typeJointure;
    }

    @Before
    public void initTest() {
        typeJointure = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeJointure() throws Exception {
        int databaseSizeBeforeCreate = typeJointureRepository.findAll().size();

        // Create the TypeJointure
        restTypeJointureMockMvc.perform(post("/api/type-jointures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeJointure)))
            .andExpect(status().isCreated());

        // Validate the TypeJointure in the database
        List<TypeJointure> typeJointureList = typeJointureRepository.findAll();
        assertThat(typeJointureList).hasSize(databaseSizeBeforeCreate + 1);
        TypeJointure testTypeJointure = typeJointureList.get(typeJointureList.size() - 1);
        assertThat(testTypeJointure.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the TypeJointure in Elasticsearch
        verify(mockTypeJointureSearchRepository, times(1)).save(testTypeJointure);
    }

    @Test
    @Transactional
    public void createTypeJointureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeJointureRepository.findAll().size();

        // Create the TypeJointure with an existing ID
        typeJointure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeJointureMockMvc.perform(post("/api/type-jointures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeJointure)))
            .andExpect(status().isBadRequest());

        // Validate the TypeJointure in the database
        List<TypeJointure> typeJointureList = typeJointureRepository.findAll();
        assertThat(typeJointureList).hasSize(databaseSizeBeforeCreate);

        // Validate the TypeJointure in Elasticsearch
        verify(mockTypeJointureSearchRepository, times(0)).save(typeJointure);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeJointureRepository.findAll().size();
        // set the field null
        typeJointure.setType(null);

        // Create the TypeJointure, which fails.

        restTypeJointureMockMvc.perform(post("/api/type-jointures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeJointure)))
            .andExpect(status().isBadRequest());

        List<TypeJointure> typeJointureList = typeJointureRepository.findAll();
        assertThat(typeJointureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeJointures() throws Exception {
        // Initialize the database
        typeJointureRepository.saveAndFlush(typeJointure);

        // Get all the typeJointureList
        restTypeJointureMockMvc.perform(get("/api/type-jointures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeJointure.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeJointure() throws Exception {
        // Initialize the database
        typeJointureRepository.saveAndFlush(typeJointure);

        // Get the typeJointure
        restTypeJointureMockMvc.perform(get("/api/type-jointures/{id}", typeJointure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeJointure.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllTypeJointuresByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        typeJointureRepository.saveAndFlush(typeJointure);

        // Get all the typeJointureList where type equals to DEFAULT_TYPE
        defaultTypeJointureShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the typeJointureList where type equals to UPDATED_TYPE
        defaultTypeJointureShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypeJointuresByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        typeJointureRepository.saveAndFlush(typeJointure);

        // Get all the typeJointureList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTypeJointureShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the typeJointureList where type equals to UPDATED_TYPE
        defaultTypeJointureShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypeJointuresByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeJointureRepository.saveAndFlush(typeJointure);

        // Get all the typeJointureList where type is not null
        defaultTypeJointureShouldBeFound("type.specified=true");

        // Get all the typeJointureList where type is null
        defaultTypeJointureShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypeJointuresByJointureIsEqualToSomething() throws Exception {
        // Initialize the database
        Jointure jointure = JointureResourceIntTest.createEntity(em);
        em.persist(jointure);
        em.flush();
        typeJointure.addJointure(jointure);
        typeJointureRepository.saveAndFlush(typeJointure);
        Long jointureId = jointure.getId();

        // Get all the typeJointureList where jointure equals to jointureId
        defaultTypeJointureShouldBeFound("jointureId.equals=" + jointureId);

        // Get all the typeJointureList where jointure equals to jointureId + 1
        defaultTypeJointureShouldNotBeFound("jointureId.equals=" + (jointureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTypeJointureShouldBeFound(String filter) throws Exception {
        restTypeJointureMockMvc.perform(get("/api/type-jointures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeJointure.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restTypeJointureMockMvc.perform(get("/api/type-jointures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTypeJointureShouldNotBeFound(String filter) throws Exception {
        restTypeJointureMockMvc.perform(get("/api/type-jointures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypeJointureMockMvc.perform(get("/api/type-jointures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTypeJointure() throws Exception {
        // Get the typeJointure
        restTypeJointureMockMvc.perform(get("/api/type-jointures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeJointure() throws Exception {
        // Initialize the database
        typeJointureService.save(typeJointure);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTypeJointureSearchRepository);

        int databaseSizeBeforeUpdate = typeJointureRepository.findAll().size();

        // Update the typeJointure
        TypeJointure updatedTypeJointure = typeJointureRepository.findById(typeJointure.getId()).get();
        // Disconnect from session so that the updates on updatedTypeJointure are not directly saved in db
        em.detach(updatedTypeJointure);
        updatedTypeJointure
            .type(UPDATED_TYPE);

        restTypeJointureMockMvc.perform(put("/api/type-jointures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeJointure)))
            .andExpect(status().isOk());

        // Validate the TypeJointure in the database
        List<TypeJointure> typeJointureList = typeJointureRepository.findAll();
        assertThat(typeJointureList).hasSize(databaseSizeBeforeUpdate);
        TypeJointure testTypeJointure = typeJointureList.get(typeJointureList.size() - 1);
        assertThat(testTypeJointure.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the TypeJointure in Elasticsearch
        verify(mockTypeJointureSearchRepository, times(1)).save(testTypeJointure);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeJointure() throws Exception {
        int databaseSizeBeforeUpdate = typeJointureRepository.findAll().size();

        // Create the TypeJointure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeJointureMockMvc.perform(put("/api/type-jointures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeJointure)))
            .andExpect(status().isBadRequest());

        // Validate the TypeJointure in the database
        List<TypeJointure> typeJointureList = typeJointureRepository.findAll();
        assertThat(typeJointureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TypeJointure in Elasticsearch
        verify(mockTypeJointureSearchRepository, times(0)).save(typeJointure);
    }

    @Test
    @Transactional
    public void deleteTypeJointure() throws Exception {
        // Initialize the database
        typeJointureService.save(typeJointure);

        int databaseSizeBeforeDelete = typeJointureRepository.findAll().size();

        // Delete the typeJointure
        restTypeJointureMockMvc.perform(delete("/api/type-jointures/{id}", typeJointure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeJointure> typeJointureList = typeJointureRepository.findAll();
        assertThat(typeJointureList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TypeJointure in Elasticsearch
        verify(mockTypeJointureSearchRepository, times(1)).deleteById(typeJointure.getId());
    }

    @Test
    @Transactional
    public void searchTypeJointure() throws Exception {
        // Initialize the database
        typeJointureService.save(typeJointure);
        when(mockTypeJointureSearchRepository.search(queryStringQuery("id:" + typeJointure.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(typeJointure), PageRequest.of(0, 1), 1));
        // Search the typeJointure
        restTypeJointureMockMvc.perform(get("/api/_search/type-jointures?query=id:" + typeJointure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeJointure.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeJointure.class);
        TypeJointure typeJointure1 = new TypeJointure();
        typeJointure1.setId(1L);
        TypeJointure typeJointure2 = new TypeJointure();
        typeJointure2.setId(typeJointure1.getId());
        assertThat(typeJointure1).isEqualTo(typeJointure2);
        typeJointure2.setId(2L);
        assertThat(typeJointure1).isNotEqualTo(typeJointure2);
        typeJointure1.setId(null);
        assertThat(typeJointure1).isNotEqualTo(typeJointure2);
    }
}
