package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filtre;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Clause;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.OperateurLogique;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FiltreRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FiltreSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FiltreService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FiltreCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FiltreQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.ArrayList;
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
 * Test class for the FiltreResource REST controller.
 *
 * @see FiltreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class FiltreResourceIntTest {

    @Autowired
    private FiltreRepository filtreRepository;

    @Mock
    private FiltreRepository filtreRepositoryMock;

    @Mock
    private FiltreService filtreServiceMock;

    @Autowired
    private FiltreService filtreService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FiltreSearchRepositoryMockConfiguration
     */
    @Autowired
    private FiltreSearchRepository mockFiltreSearchRepository;

    @Autowired
    private FiltreQueryService filtreQueryService;

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

    private MockMvc restFiltreMockMvc;

    private Filtre filtre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FiltreResource filtreResource = new FiltreResource(filtreService, filtreQueryService);
        this.restFiltreMockMvc = MockMvcBuilders.standaloneSetup(filtreResource)
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
    public static Filtre createEntity(EntityManager em) {
        Filtre filtre = new Filtre();
        return filtre;
    }

    @Before
    public void initTest() {
        filtre = createEntity(em);
    }

    @Test
    @Transactional
    public void createFiltre() throws Exception {
        int databaseSizeBeforeCreate = filtreRepository.findAll().size();

        // Create the Filtre
        restFiltreMockMvc.perform(post("/api/filtres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filtre)))
            .andExpect(status().isCreated());

        // Validate the Filtre in the database
        List<Filtre> filtreList = filtreRepository.findAll();
        assertThat(filtreList).hasSize(databaseSizeBeforeCreate + 1);
        Filtre testFiltre = filtreList.get(filtreList.size() - 1);

        // Validate the Filtre in Elasticsearch
        verify(mockFiltreSearchRepository, times(1)).save(testFiltre);
    }

    @Test
    @Transactional
    public void createFiltreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filtreRepository.findAll().size();

        // Create the Filtre with an existing ID
        filtre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFiltreMockMvc.perform(post("/api/filtres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filtre)))
            .andExpect(status().isBadRequest());

        // Validate the Filtre in the database
        List<Filtre> filtreList = filtreRepository.findAll();
        assertThat(filtreList).hasSize(databaseSizeBeforeCreate);

        // Validate the Filtre in Elasticsearch
        verify(mockFiltreSearchRepository, times(0)).save(filtre);
    }

    @Test
    @Transactional
    public void getAllFiltres() throws Exception {
        // Initialize the database
        filtreRepository.saveAndFlush(filtre);

        // Get all the filtreList
        restFiltreMockMvc.perform(get("/api/filtres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filtre.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFiltresWithEagerRelationshipsIsEnabled() throws Exception {
        FiltreResource filtreResource = new FiltreResource(filtreServiceMock, filtreQueryService);
        when(filtreServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFiltreMockMvc = MockMvcBuilders.standaloneSetup(filtreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFiltreMockMvc.perform(get("/api/filtres?eagerload=true"))
        .andExpect(status().isOk());

        verify(filtreServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFiltresWithEagerRelationshipsIsNotEnabled() throws Exception {
        FiltreResource filtreResource = new FiltreResource(filtreServiceMock, filtreQueryService);
            when(filtreServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFiltreMockMvc = MockMvcBuilders.standaloneSetup(filtreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFiltreMockMvc.perform(get("/api/filtres?eagerload=true"))
        .andExpect(status().isOk());

            verify(filtreServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFiltre() throws Exception {
        // Initialize the database
        filtreRepository.saveAndFlush(filtre);

        // Get the filtre
        restFiltreMockMvc.perform(get("/api/filtres/{id}", filtre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filtre.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllFiltresByTypeExtractionIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeExtraction typeExtraction = TypeExtractionResourceIntTest.createEntity(em);
        em.persist(typeExtraction);
        em.flush();
        filtre.addTypeExtraction(typeExtraction);
        filtreRepository.saveAndFlush(filtre);
        Long typeExtractionId = typeExtraction.getId();

        // Get all the filtreList where typeExtraction equals to typeExtractionId
        defaultFiltreShouldBeFound("typeExtractionId.equals=" + typeExtractionId);

        // Get all the filtreList where typeExtraction equals to typeExtractionId + 1
        defaultFiltreShouldNotBeFound("typeExtractionId.equals=" + (typeExtractionId + 1));
    }


    @Test
    @Transactional
    public void getAllFiltresByClauseIsEqualToSomething() throws Exception {
        // Initialize the database
        Clause clause = ClauseResourceIntTest.createEntity(em);
        em.persist(clause);
        em.flush();
        filtre.addClause(clause);
        filtreRepository.saveAndFlush(filtre);
        Long clauseId = clause.getId();

        // Get all the filtreList where clause equals to clauseId
        defaultFiltreShouldBeFound("clauseId.equals=" + clauseId);

        // Get all the filtreList where clause equals to clauseId + 1
        defaultFiltreShouldNotBeFound("clauseId.equals=" + (clauseId + 1));
    }


    @Test
    @Transactional
    public void getAllFiltresByOperateurLogiqueIsEqualToSomething() throws Exception {
        // Initialize the database
        OperateurLogique operateurLogique = OperateurLogiqueResourceIntTest.createEntity(em);
        em.persist(operateurLogique);
        em.flush();
        filtre.setOperateurLogique(operateurLogique);
        filtreRepository.saveAndFlush(filtre);
        Long operateurLogiqueId = operateurLogique.getId();

        // Get all the filtreList where operateurLogique equals to operateurLogiqueId
        defaultFiltreShouldBeFound("operateurLogiqueId.equals=" + operateurLogiqueId);

        // Get all the filtreList where operateurLogique equals to operateurLogiqueId + 1
        defaultFiltreShouldNotBeFound("operateurLogiqueId.equals=" + (operateurLogiqueId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFiltreShouldBeFound(String filter) throws Exception {
        restFiltreMockMvc.perform(get("/api/filtres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filtre.getId().intValue())));

        // Check, that the count call also returns 1
        restFiltreMockMvc.perform(get("/api/filtres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFiltreShouldNotBeFound(String filter) throws Exception {
        restFiltreMockMvc.perform(get("/api/filtres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFiltreMockMvc.perform(get("/api/filtres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFiltre() throws Exception {
        // Get the filtre
        restFiltreMockMvc.perform(get("/api/filtres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFiltre() throws Exception {
        // Initialize the database
        filtreService.save(filtre);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFiltreSearchRepository);

        int databaseSizeBeforeUpdate = filtreRepository.findAll().size();

        // Update the filtre
        Filtre updatedFiltre = filtreRepository.findById(filtre.getId()).get();
        // Disconnect from session so that the updates on updatedFiltre are not directly saved in db
        em.detach(updatedFiltre);

        restFiltreMockMvc.perform(put("/api/filtres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFiltre)))
            .andExpect(status().isOk());

        // Validate the Filtre in the database
        List<Filtre> filtreList = filtreRepository.findAll();
        assertThat(filtreList).hasSize(databaseSizeBeforeUpdate);
        Filtre testFiltre = filtreList.get(filtreList.size() - 1);

        // Validate the Filtre in Elasticsearch
        verify(mockFiltreSearchRepository, times(1)).save(testFiltre);
    }

    @Test
    @Transactional
    public void updateNonExistingFiltre() throws Exception {
        int databaseSizeBeforeUpdate = filtreRepository.findAll().size();

        // Create the Filtre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFiltreMockMvc.perform(put("/api/filtres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filtre)))
            .andExpect(status().isBadRequest());

        // Validate the Filtre in the database
        List<Filtre> filtreList = filtreRepository.findAll();
        assertThat(filtreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Filtre in Elasticsearch
        verify(mockFiltreSearchRepository, times(0)).save(filtre);
    }

    @Test
    @Transactional
    public void deleteFiltre() throws Exception {
        // Initialize the database
        filtreService.save(filtre);

        int databaseSizeBeforeDelete = filtreRepository.findAll().size();

        // Delete the filtre
        restFiltreMockMvc.perform(delete("/api/filtres/{id}", filtre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Filtre> filtreList = filtreRepository.findAll();
        assertThat(filtreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Filtre in Elasticsearch
        verify(mockFiltreSearchRepository, times(1)).deleteById(filtre.getId());
    }

    @Test
    @Transactional
    public void searchFiltre() throws Exception {
        // Initialize the database
        filtreService.save(filtre);
        when(mockFiltreSearchRepository.search(queryStringQuery("id:" + filtre.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(filtre), PageRequest.of(0, 1), 1));
        // Search the filtre
        restFiltreMockMvc.perform(get("/api/_search/filtres?query=id:" + filtre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filtre.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filtre.class);
        Filtre filtre1 = new Filtre();
        filtre1.setId(1L);
        Filtre filtre2 = new Filtre();
        filtre2.setId(filtre1.getId());
        assertThat(filtre1).isEqualTo(filtre2);
        filtre2.setId(2L);
        assertThat(filtre1).isNotEqualTo(filtre2);
        filtre1.setId(null);
        assertThat(filtre1).isNotEqualTo(filtre2);
    }
}
