package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Flux;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Dimension;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Jointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Base;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FluxRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FluxSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FluxService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FluxCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FluxQueryService;

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
 * Test class for the FluxResource REST controller.
 *
 * @see FluxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class FluxResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FluxRepository fluxRepository;

    @Autowired
    private FluxService fluxService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FluxSearchRepositoryMockConfiguration
     */
    @Autowired
    private FluxSearchRepository mockFluxSearchRepository;

    @Autowired
    private FluxQueryService fluxQueryService;

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

    private MockMvc restFluxMockMvc;

    private Flux flux;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FluxResource fluxResource = new FluxResource(fluxService, fluxQueryService);
        this.restFluxMockMvc = MockMvcBuilders.standaloneSetup(fluxResource)
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
    public static Flux createEntity(EntityManager em) {
        Flux flux = new Flux()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION);
        return flux;
    }

    @Before
    public void initTest() {
        flux = createEntity(em);
    }

    @Test
    @Transactional
    public void createFlux() throws Exception {
        int databaseSizeBeforeCreate = fluxRepository.findAll().size();

        // Create the Flux
        restFluxMockMvc.perform(post("/api/fluxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flux)))
            .andExpect(status().isCreated());

        // Validate the Flux in the database
        List<Flux> fluxList = fluxRepository.findAll();
        assertThat(fluxList).hasSize(databaseSizeBeforeCreate + 1);
        Flux testFlux = fluxList.get(fluxList.size() - 1);
        assertThat(testFlux.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testFlux.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Flux in Elasticsearch
        verify(mockFluxSearchRepository, times(1)).save(testFlux);
    }

    @Test
    @Transactional
    public void createFluxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fluxRepository.findAll().size();

        // Create the Flux with an existing ID
        flux.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFluxMockMvc.perform(post("/api/fluxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flux)))
            .andExpect(status().isBadRequest());

        // Validate the Flux in the database
        List<Flux> fluxList = fluxRepository.findAll();
        assertThat(fluxList).hasSize(databaseSizeBeforeCreate);

        // Validate the Flux in Elasticsearch
        verify(mockFluxSearchRepository, times(0)).save(flux);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = fluxRepository.findAll().size();
        // set the field null
        flux.setLibelle(null);

        // Create the Flux, which fails.

        restFluxMockMvc.perform(post("/api/fluxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flux)))
            .andExpect(status().isBadRequest());

        List<Flux> fluxList = fluxRepository.findAll();
        assertThat(fluxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = fluxRepository.findAll().size();
        // set the field null
        flux.setDescription(null);

        // Create the Flux, which fails.

        restFluxMockMvc.perform(post("/api/fluxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flux)))
            .andExpect(status().isBadRequest());

        List<Flux> fluxList = fluxRepository.findAll();
        assertThat(fluxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFluxes() throws Exception {
        // Initialize the database
        fluxRepository.saveAndFlush(flux);

        // Get all the fluxList
        restFluxMockMvc.perform(get("/api/fluxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flux.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getFlux() throws Exception {
        // Initialize the database
        fluxRepository.saveAndFlush(flux);

        // Get the flux
        restFluxMockMvc.perform(get("/api/fluxes/{id}", flux.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(flux.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllFluxesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxRepository.saveAndFlush(flux);

        // Get all the fluxList where libelle equals to DEFAULT_LIBELLE
        defaultFluxShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the fluxList where libelle equals to UPDATED_LIBELLE
        defaultFluxShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllFluxesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        fluxRepository.saveAndFlush(flux);

        // Get all the fluxList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultFluxShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the fluxList where libelle equals to UPDATED_LIBELLE
        defaultFluxShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllFluxesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        fluxRepository.saveAndFlush(flux);

        // Get all the fluxList where libelle is not null
        defaultFluxShouldBeFound("libelle.specified=true");

        // Get all the fluxList where libelle is null
        defaultFluxShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllFluxesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxRepository.saveAndFlush(flux);

        // Get all the fluxList where description equals to DEFAULT_DESCRIPTION
        defaultFluxShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the fluxList where description equals to UPDATED_DESCRIPTION
        defaultFluxShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFluxesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        fluxRepository.saveAndFlush(flux);

        // Get all the fluxList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultFluxShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the fluxList where description equals to UPDATED_DESCRIPTION
        defaultFluxShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFluxesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fluxRepository.saveAndFlush(flux);

        // Get all the fluxList where description is not null
        defaultFluxShouldBeFound("description.specified=true");

        // Get all the fluxList where description is null
        defaultFluxShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllFluxesByColonneIsEqualToSomething() throws Exception {
        // Initialize the database
        Colonne colonne = ColonneResourceIntTest.createEntity(em);
        em.persist(colonne);
        em.flush();
        flux.addColonne(colonne);
        fluxRepository.saveAndFlush(flux);
        Long colonneId = colonne.getId();

        // Get all the fluxList where colonne equals to colonneId
        defaultFluxShouldBeFound("colonneId.equals=" + colonneId);

        // Get all the fluxList where colonne equals to colonneId + 1
        defaultFluxShouldNotBeFound("colonneId.equals=" + (colonneId + 1));
    }


    @Test
    @Transactional
    public void getAllFluxesByDimensionIsEqualToSomething() throws Exception {
        // Initialize the database
        Dimension dimension = DimensionResourceIntTest.createEntity(em);
        em.persist(dimension);
        em.flush();
        flux.addDimension(dimension);
        fluxRepository.saveAndFlush(flux);
        Long dimensionId = dimension.getId();

        // Get all the fluxList where dimension equals to dimensionId
        defaultFluxShouldBeFound("dimensionId.equals=" + dimensionId);

        // Get all the fluxList where dimension equals to dimensionId + 1
        defaultFluxShouldNotBeFound("dimensionId.equals=" + (dimensionId + 1));
    }


    @Test
    @Transactional
    public void getAllFluxesByJointureIsEqualToSomething() throws Exception {
        // Initialize the database
        Jointure jointure = JointureResourceIntTest.createEntity(em);
        em.persist(jointure);
        em.flush();
        flux.addJointure(jointure);
        fluxRepository.saveAndFlush(flux);
        Long jointureId = jointure.getId();

        // Get all the fluxList where jointure equals to jointureId
        defaultFluxShouldBeFound("jointureId.equals=" + jointureId);

        // Get all the fluxList where jointure equals to jointureId + 1
        defaultFluxShouldNotBeFound("jointureId.equals=" + (jointureId + 1));
    }


    @Test
    @Transactional
    public void getAllFluxesByTypeExtractionIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeExtraction typeExtraction = TypeExtractionResourceIntTest.createEntity(em);
        em.persist(typeExtraction);
        em.flush();
        flux.addTypeExtraction(typeExtraction);
        fluxRepository.saveAndFlush(flux);
        Long typeExtractionId = typeExtraction.getId();

        // Get all the fluxList where typeExtraction equals to typeExtractionId
        defaultFluxShouldBeFound("typeExtractionId.equals=" + typeExtractionId);

        // Get all the fluxList where typeExtraction equals to typeExtractionId + 1
        defaultFluxShouldNotBeFound("typeExtractionId.equals=" + (typeExtractionId + 1));
    }


    @Test
    @Transactional
    public void getAllFluxesByBaseIsEqualToSomething() throws Exception {
        // Initialize the database
        Base base = BaseResourceIntTest.createEntity(em);
        em.persist(base);
        em.flush();
        flux.setBase(base);
        fluxRepository.saveAndFlush(flux);
        Long baseId = base.getId();

        // Get all the fluxList where base equals to baseId
        defaultFluxShouldBeFound("baseId.equals=" + baseId);

        // Get all the fluxList where base equals to baseId + 1
        defaultFluxShouldNotBeFound("baseId.equals=" + (baseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFluxShouldBeFound(String filter) throws Exception {
        restFluxMockMvc.perform(get("/api/fluxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flux.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restFluxMockMvc.perform(get("/api/fluxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFluxShouldNotBeFound(String filter) throws Exception {
        restFluxMockMvc.perform(get("/api/fluxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFluxMockMvc.perform(get("/api/fluxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFlux() throws Exception {
        // Get the flux
        restFluxMockMvc.perform(get("/api/fluxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFlux() throws Exception {
        // Initialize the database
        fluxService.save(flux);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFluxSearchRepository);

        int databaseSizeBeforeUpdate = fluxRepository.findAll().size();

        // Update the flux
        Flux updatedFlux = fluxRepository.findById(flux.getId()).get();
        // Disconnect from session so that the updates on updatedFlux are not directly saved in db
        em.detach(updatedFlux);
        updatedFlux
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION);

        restFluxMockMvc.perform(put("/api/fluxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFlux)))
            .andExpect(status().isOk());

        // Validate the Flux in the database
        List<Flux> fluxList = fluxRepository.findAll();
        assertThat(fluxList).hasSize(databaseSizeBeforeUpdate);
        Flux testFlux = fluxList.get(fluxList.size() - 1);
        assertThat(testFlux.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testFlux.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Flux in Elasticsearch
        verify(mockFluxSearchRepository, times(1)).save(testFlux);
    }

    @Test
    @Transactional
    public void updateNonExistingFlux() throws Exception {
        int databaseSizeBeforeUpdate = fluxRepository.findAll().size();

        // Create the Flux

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFluxMockMvc.perform(put("/api/fluxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flux)))
            .andExpect(status().isBadRequest());

        // Validate the Flux in the database
        List<Flux> fluxList = fluxRepository.findAll();
        assertThat(fluxList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Flux in Elasticsearch
        verify(mockFluxSearchRepository, times(0)).save(flux);
    }

    @Test
    @Transactional
    public void deleteFlux() throws Exception {
        // Initialize the database
        fluxService.save(flux);

        int databaseSizeBeforeDelete = fluxRepository.findAll().size();

        // Delete the flux
        restFluxMockMvc.perform(delete("/api/fluxes/{id}", flux.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Flux> fluxList = fluxRepository.findAll();
        assertThat(fluxList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Flux in Elasticsearch
        verify(mockFluxSearchRepository, times(1)).deleteById(flux.getId());
    }

    @Test
    @Transactional
    public void searchFlux() throws Exception {
        // Initialize the database
        fluxService.save(flux);
        when(mockFluxSearchRepository.search(queryStringQuery("id:" + flux.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(flux), PageRequest.of(0, 1), 1));
        // Search the flux
        restFluxMockMvc.perform(get("/api/_search/fluxes?query=id:" + flux.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flux.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Flux.class);
        Flux flux1 = new Flux();
        flux1.setId(1L);
        Flux flux2 = new Flux();
        flux2.setId(flux1.getId());
        assertThat(flux1).isEqualTo(flux2);
        flux2.setId(2L);
        assertThat(flux1).isNotEqualTo(flux2);
        flux1.setId(null);
        assertThat(flux1).isNotEqualTo(flux2);
    }
}
