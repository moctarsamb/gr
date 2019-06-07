package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Structure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Utilisateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Environnement;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Profil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FilialeRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FilialeSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FilialeService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FilialeCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FilialeQueryService;

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
 * Test class for the FilialeResource REST controller.
 *
 * @see FilialeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class FilialeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private FilialeRepository filialeRepository;

    @Mock
    private FilialeRepository filialeRepositoryMock;

    @Mock
    private FilialeService filialeServiceMock;

    @Autowired
    private FilialeService filialeService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FilialeSearchRepositoryMockConfiguration
     */
    @Autowired
    private FilialeSearchRepository mockFilialeSearchRepository;

    @Autowired
    private FilialeQueryService filialeQueryService;

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

    private MockMvc restFilialeMockMvc;

    private Filiale filiale;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilialeResource filialeResource = new FilialeResource(filialeService, filialeQueryService);
        this.restFilialeMockMvc = MockMvcBuilders.standaloneSetup(filialeResource)
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
    public static Filiale createEntity(EntityManager em) {
        Filiale filiale = new Filiale()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return filiale;
    }

    @Before
    public void initTest() {
        filiale = createEntity(em);
    }

    @Test
    @Transactional
    public void createFiliale() throws Exception {
        int databaseSizeBeforeCreate = filialeRepository.findAll().size();

        // Create the Filiale
        restFilialeMockMvc.perform(post("/api/filiales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filiale)))
            .andExpect(status().isCreated());

        // Validate the Filiale in the database
        List<Filiale> filialeList = filialeRepository.findAll();
        assertThat(filialeList).hasSize(databaseSizeBeforeCreate + 1);
        Filiale testFiliale = filialeList.get(filialeList.size() - 1);
        assertThat(testFiliale.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFiliale.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the Filiale in Elasticsearch
        verify(mockFilialeSearchRepository, times(1)).save(testFiliale);
    }

    @Test
    @Transactional
    public void createFilialeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filialeRepository.findAll().size();

        // Create the Filiale with an existing ID
        filiale.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilialeMockMvc.perform(post("/api/filiales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filiale)))
            .andExpect(status().isBadRequest());

        // Validate the Filiale in the database
        List<Filiale> filialeList = filialeRepository.findAll();
        assertThat(filialeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Filiale in Elasticsearch
        verify(mockFilialeSearchRepository, times(0)).save(filiale);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = filialeRepository.findAll().size();
        // set the field null
        filiale.setCode(null);

        // Create the Filiale, which fails.

        restFilialeMockMvc.perform(post("/api/filiales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filiale)))
            .andExpect(status().isBadRequest());

        List<Filiale> filialeList = filialeRepository.findAll();
        assertThat(filialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = filialeRepository.findAll().size();
        // set the field null
        filiale.setLibelle(null);

        // Create the Filiale, which fails.

        restFilialeMockMvc.perform(post("/api/filiales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filiale)))
            .andExpect(status().isBadRequest());

        List<Filiale> filialeList = filialeRepository.findAll();
        assertThat(filialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFiliales() throws Exception {
        // Initialize the database
        filialeRepository.saveAndFlush(filiale);

        // Get all the filialeList
        restFilialeMockMvc.perform(get("/api/filiales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filiale.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFilialesWithEagerRelationshipsIsEnabled() throws Exception {
        FilialeResource filialeResource = new FilialeResource(filialeServiceMock, filialeQueryService);
        when(filialeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFilialeMockMvc = MockMvcBuilders.standaloneSetup(filialeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFilialeMockMvc.perform(get("/api/filiales?eagerload=true"))
        .andExpect(status().isOk());

        verify(filialeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFilialesWithEagerRelationshipsIsNotEnabled() throws Exception {
        FilialeResource filialeResource = new FilialeResource(filialeServiceMock, filialeQueryService);
            when(filialeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFilialeMockMvc = MockMvcBuilders.standaloneSetup(filialeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFilialeMockMvc.perform(get("/api/filiales?eagerload=true"))
        .andExpect(status().isOk());

            verify(filialeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFiliale() throws Exception {
        // Initialize the database
        filialeRepository.saveAndFlush(filiale);

        // Get the filiale
        restFilialeMockMvc.perform(get("/api/filiales/{id}", filiale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filiale.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getAllFilialesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        filialeRepository.saveAndFlush(filiale);

        // Get all the filialeList where code equals to DEFAULT_CODE
        defaultFilialeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the filialeList where code equals to UPDATED_CODE
        defaultFilialeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFilialesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        filialeRepository.saveAndFlush(filiale);

        // Get all the filialeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultFilialeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the filialeList where code equals to UPDATED_CODE
        defaultFilialeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFilialesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        filialeRepository.saveAndFlush(filiale);

        // Get all the filialeList where code is not null
        defaultFilialeShouldBeFound("code.specified=true");

        // Get all the filialeList where code is null
        defaultFilialeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllFilialesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        filialeRepository.saveAndFlush(filiale);

        // Get all the filialeList where libelle equals to DEFAULT_LIBELLE
        defaultFilialeShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the filialeList where libelle equals to UPDATED_LIBELLE
        defaultFilialeShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllFilialesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        filialeRepository.saveAndFlush(filiale);

        // Get all the filialeList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultFilialeShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the filialeList where libelle equals to UPDATED_LIBELLE
        defaultFilialeShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllFilialesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        filialeRepository.saveAndFlush(filiale);

        // Get all the filialeList where libelle is not null
        defaultFilialeShouldBeFound("libelle.specified=true");

        // Get all the filialeList where libelle is null
        defaultFilialeShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllFilialesByChampsRechercheIsEqualToSomething() throws Exception {
        // Initialize the database
        ChampsRecherche champsRecherche = ChampsRechercheResourceIntTest.createEntity(em);
        em.persist(champsRecherche);
        em.flush();
        filiale.addChampsRecherche(champsRecherche);
        filialeRepository.saveAndFlush(filiale);
        Long champsRechercheId = champsRecherche.getId();

        // Get all the filialeList where champsRecherche equals to champsRechercheId
        defaultFilialeShouldBeFound("champsRechercheId.equals=" + champsRechercheId);

        // Get all the filialeList where champsRecherche equals to champsRechercheId + 1
        defaultFilialeShouldNotBeFound("champsRechercheId.equals=" + (champsRechercheId + 1));
    }


    @Test
    @Transactional
    public void getAllFilialesByStructureIsEqualToSomething() throws Exception {
        // Initialize the database
        Structure structure = StructureResourceIntTest.createEntity(em);
        em.persist(structure);
        em.flush();
        filiale.addStructure(structure);
        filialeRepository.saveAndFlush(filiale);
        Long structureId = structure.getId();

        // Get all the filialeList where structure equals to structureId
        defaultFilialeShouldBeFound("structureId.equals=" + structureId);

        // Get all the filialeList where structure equals to structureId + 1
        defaultFilialeShouldNotBeFound("structureId.equals=" + (structureId + 1));
    }


    @Test
    @Transactional
    public void getAllFilialesByUtilisateurIsEqualToSomething() throws Exception {
        // Initialize the database
        Utilisateur utilisateur = UtilisateurResourceIntTest.createEntity(em);
        em.persist(utilisateur);
        em.flush();
        filiale.addUtilisateur(utilisateur);
        filialeRepository.saveAndFlush(filiale);
        Long utilisateurId = utilisateur.getId();

        // Get all the filialeList where utilisateur equals to utilisateurId
        defaultFilialeShouldBeFound("utilisateurId.equals=" + utilisateurId);

        // Get all the filialeList where utilisateur equals to utilisateurId + 1
        defaultFilialeShouldNotBeFound("utilisateurId.equals=" + (utilisateurId + 1));
    }


    @Test
    @Transactional
    public void getAllFilialesByEnvironnementIsEqualToSomething() throws Exception {
        // Initialize the database
        Environnement environnement = EnvironnementResourceIntTest.createEntity(em);
        em.persist(environnement);
        em.flush();
        filiale.addEnvironnement(environnement);
        filialeRepository.saveAndFlush(filiale);
        Long environnementId = environnement.getId();

        // Get all the filialeList where environnement equals to environnementId
        defaultFilialeShouldBeFound("environnementId.equals=" + environnementId);

        // Get all the filialeList where environnement equals to environnementId + 1
        defaultFilialeShouldNotBeFound("environnementId.equals=" + (environnementId + 1));
    }


    @Test
    @Transactional
    public void getAllFilialesByProfilIsEqualToSomething() throws Exception {
        // Initialize the database
        Profil profil = ProfilResourceIntTest.createEntity(em);
        em.persist(profil);
        em.flush();
        filiale.addProfil(profil);
        filialeRepository.saveAndFlush(filiale);
        Long profilId = profil.getId();

        // Get all the filialeList where profil equals to profilId
        defaultFilialeShouldBeFound("profilId.equals=" + profilId);

        // Get all the filialeList where profil equals to profilId + 1
        defaultFilialeShouldNotBeFound("profilId.equals=" + (profilId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFilialeShouldBeFound(String filter) throws Exception {
        restFilialeMockMvc.perform(get("/api/filiales?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filiale.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restFilialeMockMvc.perform(get("/api/filiales/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFilialeShouldNotBeFound(String filter) throws Exception {
        restFilialeMockMvc.perform(get("/api/filiales?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFilialeMockMvc.perform(get("/api/filiales/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFiliale() throws Exception {
        // Get the filiale
        restFilialeMockMvc.perform(get("/api/filiales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFiliale() throws Exception {
        // Initialize the database
        filialeService.save(filiale);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFilialeSearchRepository);

        int databaseSizeBeforeUpdate = filialeRepository.findAll().size();

        // Update the filiale
        Filiale updatedFiliale = filialeRepository.findById(filiale.getId()).get();
        // Disconnect from session so that the updates on updatedFiliale are not directly saved in db
        em.detach(updatedFiliale);
        updatedFiliale
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restFilialeMockMvc.perform(put("/api/filiales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFiliale)))
            .andExpect(status().isOk());

        // Validate the Filiale in the database
        List<Filiale> filialeList = filialeRepository.findAll();
        assertThat(filialeList).hasSize(databaseSizeBeforeUpdate);
        Filiale testFiliale = filialeList.get(filialeList.size() - 1);
        assertThat(testFiliale.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFiliale.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the Filiale in Elasticsearch
        verify(mockFilialeSearchRepository, times(1)).save(testFiliale);
    }

    @Test
    @Transactional
    public void updateNonExistingFiliale() throws Exception {
        int databaseSizeBeforeUpdate = filialeRepository.findAll().size();

        // Create the Filiale

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilialeMockMvc.perform(put("/api/filiales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filiale)))
            .andExpect(status().isBadRequest());

        // Validate the Filiale in the database
        List<Filiale> filialeList = filialeRepository.findAll();
        assertThat(filialeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Filiale in Elasticsearch
        verify(mockFilialeSearchRepository, times(0)).save(filiale);
    }

    @Test
    @Transactional
    public void deleteFiliale() throws Exception {
        // Initialize the database
        filialeService.save(filiale);

        int databaseSizeBeforeDelete = filialeRepository.findAll().size();

        // Delete the filiale
        restFilialeMockMvc.perform(delete("/api/filiales/{id}", filiale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Filiale> filialeList = filialeRepository.findAll();
        assertThat(filialeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Filiale in Elasticsearch
        verify(mockFilialeSearchRepository, times(1)).deleteById(filiale.getId());
    }

    @Test
    @Transactional
    public void searchFiliale() throws Exception {
        // Initialize the database
        filialeService.save(filiale);
        when(mockFilialeSearchRepository.search(queryStringQuery("id:" + filiale.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(filiale), PageRequest.of(0, 1), 1));
        // Search the filiale
        restFilialeMockMvc.perform(get("/api/_search/filiales?query=id:" + filiale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filiale.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filiale.class);
        Filiale filiale1 = new Filiale();
        filiale1.setId(1L);
        Filiale filiale2 = new Filiale();
        filiale2.setId(filiale1.getId());
        assertThat(filiale1).isEqualTo(filiale2);
        filiale2.setId(2L);
        assertThat(filiale1).isNotEqualTo(filiale2);
        filiale1.setId(null);
        assertThat(filiale1).isNotEqualTo(filiale2);
    }
}
