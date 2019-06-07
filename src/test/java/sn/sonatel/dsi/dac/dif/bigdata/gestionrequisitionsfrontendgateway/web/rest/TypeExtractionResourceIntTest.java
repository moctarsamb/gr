package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Dimension;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Monitor;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Base;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filtre;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Flux;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Profil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.TypeExtractionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypeExtractionSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypeExtractionService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.TypeExtractionCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypeExtractionQueryService;

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
 * Test class for the TypeExtractionResource REST controller.
 *
 * @see TypeExtractionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class TypeExtractionResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeExtractionRepository typeExtractionRepository;

    @Mock
    private TypeExtractionRepository typeExtractionRepositoryMock;

    @Mock
    private TypeExtractionService typeExtractionServiceMock;

    @Autowired
    private TypeExtractionService typeExtractionService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypeExtractionSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeExtractionSearchRepository mockTypeExtractionSearchRepository;

    @Autowired
    private TypeExtractionQueryService typeExtractionQueryService;

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

    private MockMvc restTypeExtractionMockMvc;

    private TypeExtraction typeExtraction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeExtractionResource typeExtractionResource = new TypeExtractionResource(typeExtractionService, typeExtractionQueryService);
        this.restTypeExtractionMockMvc = MockMvcBuilders.standaloneSetup(typeExtractionResource)
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
    public static TypeExtraction createEntity(EntityManager em) {
        TypeExtraction typeExtraction = new TypeExtraction()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return typeExtraction;
    }

    @Before
    public void initTest() {
        typeExtraction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeExtraction() throws Exception {
        int databaseSizeBeforeCreate = typeExtractionRepository.findAll().size();

        // Create the TypeExtraction
        restTypeExtractionMockMvc.perform(post("/api/type-extractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeExtraction)))
            .andExpect(status().isCreated());

        // Validate the TypeExtraction in the database
        List<TypeExtraction> typeExtractionList = typeExtractionRepository.findAll();
        assertThat(typeExtractionList).hasSize(databaseSizeBeforeCreate + 1);
        TypeExtraction testTypeExtraction = typeExtractionList.get(typeExtractionList.size() - 1);
        assertThat(testTypeExtraction.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeExtraction.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the TypeExtraction in Elasticsearch
        verify(mockTypeExtractionSearchRepository, times(1)).save(testTypeExtraction);
    }

    @Test
    @Transactional
    public void createTypeExtractionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeExtractionRepository.findAll().size();

        // Create the TypeExtraction with an existing ID
        typeExtraction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeExtractionMockMvc.perform(post("/api/type-extractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeExtraction)))
            .andExpect(status().isBadRequest());

        // Validate the TypeExtraction in the database
        List<TypeExtraction> typeExtractionList = typeExtractionRepository.findAll();
        assertThat(typeExtractionList).hasSize(databaseSizeBeforeCreate);

        // Validate the TypeExtraction in Elasticsearch
        verify(mockTypeExtractionSearchRepository, times(0)).save(typeExtraction);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeExtractionRepository.findAll().size();
        // set the field null
        typeExtraction.setCode(null);

        // Create the TypeExtraction, which fails.

        restTypeExtractionMockMvc.perform(post("/api/type-extractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeExtraction)))
            .andExpect(status().isBadRequest());

        List<TypeExtraction> typeExtractionList = typeExtractionRepository.findAll();
        assertThat(typeExtractionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeExtractionRepository.findAll().size();
        // set the field null
        typeExtraction.setLibelle(null);

        // Create the TypeExtraction, which fails.

        restTypeExtractionMockMvc.perform(post("/api/type-extractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeExtraction)))
            .andExpect(status().isBadRequest());

        List<TypeExtraction> typeExtractionList = typeExtractionRepository.findAll();
        assertThat(typeExtractionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeExtractions() throws Exception {
        // Initialize the database
        typeExtractionRepository.saveAndFlush(typeExtraction);

        // Get all the typeExtractionList
        restTypeExtractionMockMvc.perform(get("/api/type-extractions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeExtraction.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTypeExtractionsWithEagerRelationshipsIsEnabled() throws Exception {
        TypeExtractionResource typeExtractionResource = new TypeExtractionResource(typeExtractionServiceMock, typeExtractionQueryService);
        when(typeExtractionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTypeExtractionMockMvc = MockMvcBuilders.standaloneSetup(typeExtractionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTypeExtractionMockMvc.perform(get("/api/type-extractions?eagerload=true"))
        .andExpect(status().isOk());

        verify(typeExtractionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTypeExtractionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        TypeExtractionResource typeExtractionResource = new TypeExtractionResource(typeExtractionServiceMock, typeExtractionQueryService);
            when(typeExtractionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTypeExtractionMockMvc = MockMvcBuilders.standaloneSetup(typeExtractionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTypeExtractionMockMvc.perform(get("/api/type-extractions?eagerload=true"))
        .andExpect(status().isOk());

            verify(typeExtractionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTypeExtraction() throws Exception {
        // Initialize the database
        typeExtractionRepository.saveAndFlush(typeExtraction);

        // Get the typeExtraction
        restTypeExtractionMockMvc.perform(get("/api/type-extractions/{id}", typeExtraction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeExtraction.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getAllTypeExtractionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        typeExtractionRepository.saveAndFlush(typeExtraction);

        // Get all the typeExtractionList where code equals to DEFAULT_CODE
        defaultTypeExtractionShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the typeExtractionList where code equals to UPDATED_CODE
        defaultTypeExtractionShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTypeExtractionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        typeExtractionRepository.saveAndFlush(typeExtraction);

        // Get all the typeExtractionList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTypeExtractionShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the typeExtractionList where code equals to UPDATED_CODE
        defaultTypeExtractionShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTypeExtractionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeExtractionRepository.saveAndFlush(typeExtraction);

        // Get all the typeExtractionList where code is not null
        defaultTypeExtractionShouldBeFound("code.specified=true");

        // Get all the typeExtractionList where code is null
        defaultTypeExtractionShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypeExtractionsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        typeExtractionRepository.saveAndFlush(typeExtraction);

        // Get all the typeExtractionList where libelle equals to DEFAULT_LIBELLE
        defaultTypeExtractionShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the typeExtractionList where libelle equals to UPDATED_LIBELLE
        defaultTypeExtractionShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllTypeExtractionsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        typeExtractionRepository.saveAndFlush(typeExtraction);

        // Get all the typeExtractionList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultTypeExtractionShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the typeExtractionList where libelle equals to UPDATED_LIBELLE
        defaultTypeExtractionShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllTypeExtractionsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeExtractionRepository.saveAndFlush(typeExtraction);

        // Get all the typeExtractionList where libelle is not null
        defaultTypeExtractionShouldBeFound("libelle.specified=true");

        // Get all the typeExtractionList where libelle is null
        defaultTypeExtractionShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypeExtractionsByChampsRechercheIsEqualToSomething() throws Exception {
        // Initialize the database
        ChampsRecherche champsRecherche = ChampsRechercheResourceIntTest.createEntity(em);
        em.persist(champsRecherche);
        em.flush();
        typeExtraction.addChampsRecherche(champsRecherche);
        typeExtractionRepository.saveAndFlush(typeExtraction);
        Long champsRechercheId = champsRecherche.getId();

        // Get all the typeExtractionList where champsRecherche equals to champsRechercheId
        defaultTypeExtractionShouldBeFound("champsRechercheId.equals=" + champsRechercheId);

        // Get all the typeExtractionList where champsRecherche equals to champsRechercheId + 1
        defaultTypeExtractionShouldNotBeFound("champsRechercheId.equals=" + (champsRechercheId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeExtractionsByDimensionIsEqualToSomething() throws Exception {
        // Initialize the database
        Dimension dimension = DimensionResourceIntTest.createEntity(em);
        em.persist(dimension);
        em.flush();
        typeExtraction.addDimension(dimension);
        typeExtractionRepository.saveAndFlush(typeExtraction);
        Long dimensionId = dimension.getId();

        // Get all the typeExtractionList where dimension equals to dimensionId
        defaultTypeExtractionShouldBeFound("dimensionId.equals=" + dimensionId);

        // Get all the typeExtractionList where dimension equals to dimensionId + 1
        defaultTypeExtractionShouldNotBeFound("dimensionId.equals=" + (dimensionId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeExtractionsByMonitorIsEqualToSomething() throws Exception {
        // Initialize the database
        Monitor monitor = MonitorResourceIntTest.createEntity(em);
        em.persist(monitor);
        em.flush();
        typeExtraction.addMonitor(monitor);
        typeExtractionRepository.saveAndFlush(typeExtraction);
        Long monitorId = monitor.getId();

        // Get all the typeExtractionList where monitor equals to monitorId
        defaultTypeExtractionShouldBeFound("monitorId.equals=" + monitorId);

        // Get all the typeExtractionList where monitor equals to monitorId + 1
        defaultTypeExtractionShouldNotBeFound("monitorId.equals=" + (monitorId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeExtractionsByBaseIsEqualToSomething() throws Exception {
        // Initialize the database
        Base base = BaseResourceIntTest.createEntity(em);
        em.persist(base);
        em.flush();
        typeExtraction.setBase(base);
        typeExtractionRepository.saveAndFlush(typeExtraction);
        Long baseId = base.getId();

        // Get all the typeExtractionList where base equals to baseId
        defaultTypeExtractionShouldBeFound("baseId.equals=" + baseId);

        // Get all the typeExtractionList where base equals to baseId + 1
        defaultTypeExtractionShouldNotBeFound("baseId.equals=" + (baseId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeExtractionsByFiltreIsEqualToSomething() throws Exception {
        // Initialize the database
        Filtre filtre = FiltreResourceIntTest.createEntity(em);
        em.persist(filtre);
        em.flush();
        typeExtraction.setFiltre(filtre);
        typeExtractionRepository.saveAndFlush(typeExtraction);
        Long filtreId = filtre.getId();

        // Get all the typeExtractionList where filtre equals to filtreId
        defaultTypeExtractionShouldBeFound("filtreId.equals=" + filtreId);

        // Get all the typeExtractionList where filtre equals to filtreId + 1
        defaultTypeExtractionShouldNotBeFound("filtreId.equals=" + (filtreId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeExtractionsByFluxIsEqualToSomething() throws Exception {
        // Initialize the database
        Flux flux = FluxResourceIntTest.createEntity(em);
        em.persist(flux);
        em.flush();
        typeExtraction.setFlux(flux);
        typeExtractionRepository.saveAndFlush(typeExtraction);
        Long fluxId = flux.getId();

        // Get all the typeExtractionList where flux equals to fluxId
        defaultTypeExtractionShouldBeFound("fluxId.equals=" + fluxId);

        // Get all the typeExtractionList where flux equals to fluxId + 1
        defaultTypeExtractionShouldNotBeFound("fluxId.equals=" + (fluxId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeExtractionsByColonneRequetableIsEqualToSomething() throws Exception {
        // Initialize the database
        Colonne colonneRequetable = ColonneResourceIntTest.createEntity(em);
        em.persist(colonneRequetable);
        em.flush();
        typeExtraction.addColonneRequetable(colonneRequetable);
        typeExtractionRepository.saveAndFlush(typeExtraction);
        Long colonneRequetableId = colonneRequetable.getId();

        // Get all the typeExtractionList where colonneRequetable equals to colonneRequetableId
        defaultTypeExtractionShouldBeFound("colonneRequetableId.equals=" + colonneRequetableId);

        // Get all the typeExtractionList where colonneRequetable equals to colonneRequetableId + 1
        defaultTypeExtractionShouldNotBeFound("colonneRequetableId.equals=" + (colonneRequetableId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeExtractionsByProfilIsEqualToSomething() throws Exception {
        // Initialize the database
        Profil profil = ProfilResourceIntTest.createEntity(em);
        em.persist(profil);
        em.flush();
        typeExtraction.addProfil(profil);
        typeExtractionRepository.saveAndFlush(typeExtraction);
        Long profilId = profil.getId();

        // Get all the typeExtractionList where profil equals to profilId
        defaultTypeExtractionShouldBeFound("profilId.equals=" + profilId);

        // Get all the typeExtractionList where profil equals to profilId + 1
        defaultTypeExtractionShouldNotBeFound("profilId.equals=" + (profilId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTypeExtractionShouldBeFound(String filter) throws Exception {
        restTypeExtractionMockMvc.perform(get("/api/type-extractions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeExtraction.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restTypeExtractionMockMvc.perform(get("/api/type-extractions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTypeExtractionShouldNotBeFound(String filter) throws Exception {
        restTypeExtractionMockMvc.perform(get("/api/type-extractions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypeExtractionMockMvc.perform(get("/api/type-extractions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTypeExtraction() throws Exception {
        // Get the typeExtraction
        restTypeExtractionMockMvc.perform(get("/api/type-extractions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeExtraction() throws Exception {
        // Initialize the database
        typeExtractionService.save(typeExtraction);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTypeExtractionSearchRepository);

        int databaseSizeBeforeUpdate = typeExtractionRepository.findAll().size();

        // Update the typeExtraction
        TypeExtraction updatedTypeExtraction = typeExtractionRepository.findById(typeExtraction.getId()).get();
        // Disconnect from session so that the updates on updatedTypeExtraction are not directly saved in db
        em.detach(updatedTypeExtraction);
        updatedTypeExtraction
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restTypeExtractionMockMvc.perform(put("/api/type-extractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeExtraction)))
            .andExpect(status().isOk());

        // Validate the TypeExtraction in the database
        List<TypeExtraction> typeExtractionList = typeExtractionRepository.findAll();
        assertThat(typeExtractionList).hasSize(databaseSizeBeforeUpdate);
        TypeExtraction testTypeExtraction = typeExtractionList.get(typeExtractionList.size() - 1);
        assertThat(testTypeExtraction.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeExtraction.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the TypeExtraction in Elasticsearch
        verify(mockTypeExtractionSearchRepository, times(1)).save(testTypeExtraction);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeExtraction() throws Exception {
        int databaseSizeBeforeUpdate = typeExtractionRepository.findAll().size();

        // Create the TypeExtraction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeExtractionMockMvc.perform(put("/api/type-extractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeExtraction)))
            .andExpect(status().isBadRequest());

        // Validate the TypeExtraction in the database
        List<TypeExtraction> typeExtractionList = typeExtractionRepository.findAll();
        assertThat(typeExtractionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TypeExtraction in Elasticsearch
        verify(mockTypeExtractionSearchRepository, times(0)).save(typeExtraction);
    }

    @Test
    @Transactional
    public void deleteTypeExtraction() throws Exception {
        // Initialize the database
        typeExtractionService.save(typeExtraction);

        int databaseSizeBeforeDelete = typeExtractionRepository.findAll().size();

        // Delete the typeExtraction
        restTypeExtractionMockMvc.perform(delete("/api/type-extractions/{id}", typeExtraction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeExtraction> typeExtractionList = typeExtractionRepository.findAll();
        assertThat(typeExtractionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TypeExtraction in Elasticsearch
        verify(mockTypeExtractionSearchRepository, times(1)).deleteById(typeExtraction.getId());
    }

    @Test
    @Transactional
    public void searchTypeExtraction() throws Exception {
        // Initialize the database
        typeExtractionService.save(typeExtraction);
        when(mockTypeExtractionSearchRepository.search(queryStringQuery("id:" + typeExtraction.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(typeExtraction), PageRequest.of(0, 1), 1));
        // Search the typeExtraction
        restTypeExtractionMockMvc.perform(get("/api/_search/type-extractions?query=id:" + typeExtraction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeExtraction.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeExtraction.class);
        TypeExtraction typeExtraction1 = new TypeExtraction();
        typeExtraction1.setId(1L);
        TypeExtraction typeExtraction2 = new TypeExtraction();
        typeExtraction2.setId(typeExtraction1.getId());
        assertThat(typeExtraction1).isEqualTo(typeExtraction2);
        typeExtraction2.setId(2L);
        assertThat(typeExtraction1).isNotEqualTo(typeExtraction2);
        typeExtraction1.setId(null);
        assertThat(typeExtraction1).isNotEqualTo(typeExtraction2);
    }
}
