package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Jointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Critere;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Flux;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeJointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.JointureRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.JointureSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.JointureService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.JointureCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.JointureQueryService;

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
 * Test class for the JointureResource REST controller.
 *
 * @see JointureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class JointureResourceIntTest {

    @Autowired
    private JointureRepository jointureRepository;

    @Autowired
    private JointureService jointureService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.JointureSearchRepositoryMockConfiguration
     */
    @Autowired
    private JointureSearchRepository mockJointureSearchRepository;

    @Autowired
    private JointureQueryService jointureQueryService;

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

    private MockMvc restJointureMockMvc;

    private Jointure jointure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JointureResource jointureResource = new JointureResource(jointureService, jointureQueryService);
        this.restJointureMockMvc = MockMvcBuilders.standaloneSetup(jointureResource)
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
    public static Jointure createEntity(EntityManager em) {
        Jointure jointure = new Jointure();
        return jointure;
    }

    @Before
    public void initTest() {
        jointure = createEntity(em);
    }

    @Test
    @Transactional
    public void createJointure() throws Exception {
        int databaseSizeBeforeCreate = jointureRepository.findAll().size();

        // Create the Jointure
        restJointureMockMvc.perform(post("/api/jointures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jointure)))
            .andExpect(status().isCreated());

        // Validate the Jointure in the database
        List<Jointure> jointureList = jointureRepository.findAll();
        assertThat(jointureList).hasSize(databaseSizeBeforeCreate + 1);
        Jointure testJointure = jointureList.get(jointureList.size() - 1);

        // Validate the Jointure in Elasticsearch
        verify(mockJointureSearchRepository, times(1)).save(testJointure);
    }

    @Test
    @Transactional
    public void createJointureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jointureRepository.findAll().size();

        // Create the Jointure with an existing ID
        jointure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJointureMockMvc.perform(post("/api/jointures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jointure)))
            .andExpect(status().isBadRequest());

        // Validate the Jointure in the database
        List<Jointure> jointureList = jointureRepository.findAll();
        assertThat(jointureList).hasSize(databaseSizeBeforeCreate);

        // Validate the Jointure in Elasticsearch
        verify(mockJointureSearchRepository, times(0)).save(jointure);
    }

    @Test
    @Transactional
    public void getAllJointures() throws Exception {
        // Initialize the database
        jointureRepository.saveAndFlush(jointure);

        // Get all the jointureList
        restJointureMockMvc.perform(get("/api/jointures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jointure.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getJointure() throws Exception {
        // Initialize the database
        jointureRepository.saveAndFlush(jointure);

        // Get the jointure
        restJointureMockMvc.perform(get("/api/jointures/{id}", jointure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jointure.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllJointuresByCritereIsEqualToSomething() throws Exception {
        // Initialize the database
        Critere critere = CritereResourceIntTest.createEntity(em);
        em.persist(critere);
        em.flush();
        jointure.setCritere(critere);
        jointureRepository.saveAndFlush(jointure);
        Long critereId = critere.getId();

        // Get all the jointureList where critere equals to critereId
        defaultJointureShouldBeFound("critereId.equals=" + critereId);

        // Get all the jointureList where critere equals to critereId + 1
        defaultJointureShouldNotBeFound("critereId.equals=" + (critereId + 1));
    }


    @Test
    @Transactional
    public void getAllJointuresByFluxIsEqualToSomething() throws Exception {
        // Initialize the database
        Flux flux = FluxResourceIntTest.createEntity(em);
        em.persist(flux);
        em.flush();
        jointure.setFlux(flux);
        jointureRepository.saveAndFlush(jointure);
        Long fluxId = flux.getId();

        // Get all the jointureList where flux equals to fluxId
        defaultJointureShouldBeFound("fluxId.equals=" + fluxId);

        // Get all the jointureList where flux equals to fluxId + 1
        defaultJointureShouldNotBeFound("fluxId.equals=" + (fluxId + 1));
    }


    @Test
    @Transactional
    public void getAllJointuresByTypeJointureIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeJointure typeJointure = TypeJointureResourceIntTest.createEntity(em);
        em.persist(typeJointure);
        em.flush();
        jointure.setTypeJointure(typeJointure);
        jointureRepository.saveAndFlush(jointure);
        Long typeJointureId = typeJointure.getId();

        // Get all the jointureList where typeJointure equals to typeJointureId
        defaultJointureShouldBeFound("typeJointureId.equals=" + typeJointureId);

        // Get all the jointureList where typeJointure equals to typeJointureId + 1
        defaultJointureShouldNotBeFound("typeJointureId.equals=" + (typeJointureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultJointureShouldBeFound(String filter) throws Exception {
        restJointureMockMvc.perform(get("/api/jointures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jointure.getId().intValue())));

        // Check, that the count call also returns 1
        restJointureMockMvc.perform(get("/api/jointures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultJointureShouldNotBeFound(String filter) throws Exception {
        restJointureMockMvc.perform(get("/api/jointures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJointureMockMvc.perform(get("/api/jointures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingJointure() throws Exception {
        // Get the jointure
        restJointureMockMvc.perform(get("/api/jointures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJointure() throws Exception {
        // Initialize the database
        jointureService.save(jointure);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockJointureSearchRepository);

        int databaseSizeBeforeUpdate = jointureRepository.findAll().size();

        // Update the jointure
        Jointure updatedJointure = jointureRepository.findById(jointure.getId()).get();
        // Disconnect from session so that the updates on updatedJointure are not directly saved in db
        em.detach(updatedJointure);

        restJointureMockMvc.perform(put("/api/jointures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJointure)))
            .andExpect(status().isOk());

        // Validate the Jointure in the database
        List<Jointure> jointureList = jointureRepository.findAll();
        assertThat(jointureList).hasSize(databaseSizeBeforeUpdate);
        Jointure testJointure = jointureList.get(jointureList.size() - 1);

        // Validate the Jointure in Elasticsearch
        verify(mockJointureSearchRepository, times(1)).save(testJointure);
    }

    @Test
    @Transactional
    public void updateNonExistingJointure() throws Exception {
        int databaseSizeBeforeUpdate = jointureRepository.findAll().size();

        // Create the Jointure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJointureMockMvc.perform(put("/api/jointures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jointure)))
            .andExpect(status().isBadRequest());

        // Validate the Jointure in the database
        List<Jointure> jointureList = jointureRepository.findAll();
        assertThat(jointureList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Jointure in Elasticsearch
        verify(mockJointureSearchRepository, times(0)).save(jointure);
    }

    @Test
    @Transactional
    public void deleteJointure() throws Exception {
        // Initialize the database
        jointureService.save(jointure);

        int databaseSizeBeforeDelete = jointureRepository.findAll().size();

        // Delete the jointure
        restJointureMockMvc.perform(delete("/api/jointures/{id}", jointure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Jointure> jointureList = jointureRepository.findAll();
        assertThat(jointureList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Jointure in Elasticsearch
        verify(mockJointureSearchRepository, times(1)).deleteById(jointure.getId());
    }

    @Test
    @Transactional
    public void searchJointure() throws Exception {
        // Initialize the database
        jointureService.save(jointure);
        when(mockJointureSearchRepository.search(queryStringQuery("id:" + jointure.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(jointure), PageRequest.of(0, 1), 1));
        // Search the jointure
        restJointureMockMvc.perform(get("/api/_search/jointures?query=id:" + jointure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jointure.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jointure.class);
        Jointure jointure1 = new Jointure();
        jointure1.setId(1L);
        Jointure jointure2 = new Jointure();
        jointure2.setId(jointure1.getId());
        assertThat(jointure1).isEqualTo(jointure2);
        jointure2.setId(2L);
        assertThat(jointure1).isNotEqualTo(jointure2);
        jointure1.setId(null);
        assertThat(jointure1).isNotEqualTo(jointure2);
    }
}
