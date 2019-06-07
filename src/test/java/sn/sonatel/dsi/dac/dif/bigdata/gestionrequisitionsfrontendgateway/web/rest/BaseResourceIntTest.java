package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Base;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Dimension;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Flux;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.BaseRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.BaseSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.BaseService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.BaseCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.BaseQueryService;

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
 * Test class for the BaseResource REST controller.
 *
 * @see BaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class BaseResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private BaseService baseService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.BaseSearchRepositoryMockConfiguration
     */
    @Autowired
    private BaseSearchRepository mockBaseSearchRepository;

    @Autowired
    private BaseQueryService baseQueryService;

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

    private MockMvc restBaseMockMvc;

    private Base base;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BaseResource baseResource = new BaseResource(baseService, baseQueryService);
        this.restBaseMockMvc = MockMvcBuilders.standaloneSetup(baseResource)
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
    public static Base createEntity(EntityManager em) {
        Base base = new Base()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION);
        return base;
    }

    @Before
    public void initTest() {
        base = createEntity(em);
    }

    @Test
    @Transactional
    public void createBase() throws Exception {
        int databaseSizeBeforeCreate = baseRepository.findAll().size();

        // Create the Base
        restBaseMockMvc.perform(post("/api/bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(base)))
            .andExpect(status().isCreated());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeCreate + 1);
        Base testBase = baseList.get(baseList.size() - 1);
        assertThat(testBase.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testBase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Base in Elasticsearch
        verify(mockBaseSearchRepository, times(1)).save(testBase);
    }

    @Test
    @Transactional
    public void createBaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baseRepository.findAll().size();

        // Create the Base with an existing ID
        base.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaseMockMvc.perform(post("/api/bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(base)))
            .andExpect(status().isBadRequest());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Base in Elasticsearch
        verify(mockBaseSearchRepository, times(0)).save(base);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = baseRepository.findAll().size();
        // set the field null
        base.setLibelle(null);

        // Create the Base, which fails.

        restBaseMockMvc.perform(post("/api/bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(base)))
            .andExpect(status().isBadRequest());

        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBases() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get all the baseList
        restBaseMockMvc.perform(get("/api/bases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(base.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getBase() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get the base
        restBaseMockMvc.perform(get("/api/bases/{id}", base.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(base.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllBasesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get all the baseList where libelle equals to DEFAULT_LIBELLE
        defaultBaseShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the baseList where libelle equals to UPDATED_LIBELLE
        defaultBaseShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllBasesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get all the baseList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultBaseShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the baseList where libelle equals to UPDATED_LIBELLE
        defaultBaseShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllBasesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get all the baseList where libelle is not null
        defaultBaseShouldBeFound("libelle.specified=true");

        // Get all the baseList where libelle is null
        defaultBaseShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get all the baseList where description equals to DEFAULT_DESCRIPTION
        defaultBaseShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the baseList where description equals to UPDATED_DESCRIPTION
        defaultBaseShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBasesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get all the baseList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBaseShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the baseList where description equals to UPDATED_DESCRIPTION
        defaultBaseShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBasesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        baseRepository.saveAndFlush(base);

        // Get all the baseList where description is not null
        defaultBaseShouldBeFound("description.specified=true");

        // Get all the baseList where description is null
        defaultBaseShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasesByDimensionIsEqualToSomething() throws Exception {
        // Initialize the database
        Dimension dimension = DimensionResourceIntTest.createEntity(em);
        em.persist(dimension);
        em.flush();
        base.addDimension(dimension);
        baseRepository.saveAndFlush(base);
        Long dimensionId = dimension.getId();

        // Get all the baseList where dimension equals to dimensionId
        defaultBaseShouldBeFound("dimensionId.equals=" + dimensionId);

        // Get all the baseList where dimension equals to dimensionId + 1
        defaultBaseShouldNotBeFound("dimensionId.equals=" + (dimensionId + 1));
    }


    @Test
    @Transactional
    public void getAllBasesByFluxIsEqualToSomething() throws Exception {
        // Initialize the database
        Flux flux = FluxResourceIntTest.createEntity(em);
        em.persist(flux);
        em.flush();
        base.addFlux(flux);
        baseRepository.saveAndFlush(base);
        Long fluxId = flux.getId();

        // Get all the baseList where flux equals to fluxId
        defaultBaseShouldBeFound("fluxId.equals=" + fluxId);

        // Get all the baseList where flux equals to fluxId + 1
        defaultBaseShouldNotBeFound("fluxId.equals=" + (fluxId + 1));
    }


    @Test
    @Transactional
    public void getAllBasesByTypeExtractionIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeExtraction typeExtraction = TypeExtractionResourceIntTest.createEntity(em);
        em.persist(typeExtraction);
        em.flush();
        base.addTypeExtraction(typeExtraction);
        baseRepository.saveAndFlush(base);
        Long typeExtractionId = typeExtraction.getId();

        // Get all the baseList where typeExtraction equals to typeExtractionId
        defaultBaseShouldBeFound("typeExtractionId.equals=" + typeExtractionId);

        // Get all the baseList where typeExtraction equals to typeExtractionId + 1
        defaultBaseShouldNotBeFound("typeExtractionId.equals=" + (typeExtractionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBaseShouldBeFound(String filter) throws Exception {
        restBaseMockMvc.perform(get("/api/bases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(base.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restBaseMockMvc.perform(get("/api/bases/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBaseShouldNotBeFound(String filter) throws Exception {
        restBaseMockMvc.perform(get("/api/bases?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBaseMockMvc.perform(get("/api/bases/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBase() throws Exception {
        // Get the base
        restBaseMockMvc.perform(get("/api/bases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBase() throws Exception {
        // Initialize the database
        baseService.save(base);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockBaseSearchRepository);

        int databaseSizeBeforeUpdate = baseRepository.findAll().size();

        // Update the base
        Base updatedBase = baseRepository.findById(base.getId()).get();
        // Disconnect from session so that the updates on updatedBase are not directly saved in db
        em.detach(updatedBase);
        updatedBase
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION);

        restBaseMockMvc.perform(put("/api/bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBase)))
            .andExpect(status().isOk());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeUpdate);
        Base testBase = baseList.get(baseList.size() - 1);
        assertThat(testBase.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testBase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Base in Elasticsearch
        verify(mockBaseSearchRepository, times(1)).save(testBase);
    }

    @Test
    @Transactional
    public void updateNonExistingBase() throws Exception {
        int databaseSizeBeforeUpdate = baseRepository.findAll().size();

        // Create the Base

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaseMockMvc.perform(put("/api/bases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(base)))
            .andExpect(status().isBadRequest());

        // Validate the Base in the database
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Base in Elasticsearch
        verify(mockBaseSearchRepository, times(0)).save(base);
    }

    @Test
    @Transactional
    public void deleteBase() throws Exception {
        // Initialize the database
        baseService.save(base);

        int databaseSizeBeforeDelete = baseRepository.findAll().size();

        // Delete the base
        restBaseMockMvc.perform(delete("/api/bases/{id}", base.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Base> baseList = baseRepository.findAll();
        assertThat(baseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Base in Elasticsearch
        verify(mockBaseSearchRepository, times(1)).deleteById(base.getId());
    }

    @Test
    @Transactional
    public void searchBase() throws Exception {
        // Initialize the database
        baseService.save(base);
        when(mockBaseSearchRepository.search(queryStringQuery("id:" + base.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(base), PageRequest.of(0, 1), 1));
        // Search the base
        restBaseMockMvc.perform(get("/api/_search/bases?query=id:" + base.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(base.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Base.class);
        Base base1 = new Base();
        base1.setId(1L);
        Base base2 = new Base();
        base2.setId(base1.getId());
        assertThat(base1).isEqualTo(base2);
        base2.setId(2L);
        assertThat(base1).isNotEqualTo(base2);
        base1.setId(null);
        assertThat(base1).isNotEqualTo(base2);
    }
}
