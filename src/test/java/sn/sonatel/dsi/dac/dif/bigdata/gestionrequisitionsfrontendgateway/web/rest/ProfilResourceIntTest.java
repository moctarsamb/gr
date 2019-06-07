package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Profil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Utilisateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ProfilRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ProfilSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ProfilService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ProfilCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ProfilQueryService;

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
 * Test class for the ProfilResource REST controller.
 *
 * @see ProfilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class ProfilResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProfilRepository profilRepository;

    @Mock
    private ProfilRepository profilRepositoryMock;

    @Mock
    private ProfilService profilServiceMock;

    @Autowired
    private ProfilService profilService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ProfilSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProfilSearchRepository mockProfilSearchRepository;

    @Autowired
    private ProfilQueryService profilQueryService;

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

    private MockMvc restProfilMockMvc;

    private Profil profil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfilResource profilResource = new ProfilResource(profilService, profilQueryService);
        this.restProfilMockMvc = MockMvcBuilders.standaloneSetup(profilResource)
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
    public static Profil createEntity(EntityManager em) {
        Profil profil = new Profil()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION);
        return profil;
    }

    @Before
    public void initTest() {
        profil = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfil() throws Exception {
        int databaseSizeBeforeCreate = profilRepository.findAll().size();

        // Create the Profil
        restProfilMockMvc.perform(post("/api/profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isCreated());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeCreate + 1);
        Profil testProfil = profilList.get(profilList.size() - 1);
        assertThat(testProfil.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testProfil.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Profil in Elasticsearch
        verify(mockProfilSearchRepository, times(1)).save(testProfil);
    }

    @Test
    @Transactional
    public void createProfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profilRepository.findAll().size();

        // Create the Profil with an existing ID
        profil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfilMockMvc.perform(post("/api/profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isBadRequest());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeCreate);

        // Validate the Profil in Elasticsearch
        verify(mockProfilSearchRepository, times(0)).save(profil);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = profilRepository.findAll().size();
        // set the field null
        profil.setLibelle(null);

        // Create the Profil, which fails.

        restProfilMockMvc.perform(post("/api/profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isBadRequest());

        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = profilRepository.findAll().size();
        // set the field null
        profil.setDescription(null);

        // Create the Profil, which fails.

        restProfilMockMvc.perform(post("/api/profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isBadRequest());

        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfils() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get all the profilList
        restProfilMockMvc.perform(get("/api/profils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profil.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProfilsWithEagerRelationshipsIsEnabled() throws Exception {
        ProfilResource profilResource = new ProfilResource(profilServiceMock, profilQueryService);
        when(profilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProfilMockMvc = MockMvcBuilders.standaloneSetup(profilResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProfilMockMvc.perform(get("/api/profils?eagerload=true"))
        .andExpect(status().isOk());

        verify(profilServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProfilsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProfilResource profilResource = new ProfilResource(profilServiceMock, profilQueryService);
            when(profilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProfilMockMvc = MockMvcBuilders.standaloneSetup(profilResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProfilMockMvc.perform(get("/api/profils?eagerload=true"))
        .andExpect(status().isOk());

            verify(profilServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProfil() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get the profil
        restProfilMockMvc.perform(get("/api/profils/{id}", profil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profil.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProfilsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get all the profilList where libelle equals to DEFAULT_LIBELLE
        defaultProfilShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the profilList where libelle equals to UPDATED_LIBELLE
        defaultProfilShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllProfilsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get all the profilList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultProfilShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the profilList where libelle equals to UPDATED_LIBELLE
        defaultProfilShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllProfilsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get all the profilList where libelle is not null
        defaultProfilShouldBeFound("libelle.specified=true");

        // Get all the profilList where libelle is null
        defaultProfilShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get all the profilList where description equals to DEFAULT_DESCRIPTION
        defaultProfilShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the profilList where description equals to UPDATED_DESCRIPTION
        defaultProfilShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProfilsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get all the profilList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProfilShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the profilList where description equals to UPDATED_DESCRIPTION
        defaultProfilShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProfilsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        profilRepository.saveAndFlush(profil);

        // Get all the profilList where description is not null
        defaultProfilShouldBeFound("description.specified=true");

        // Get all the profilList where description is null
        defaultProfilShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilsByUtilisateurIsEqualToSomething() throws Exception {
        // Initialize the database
        Utilisateur utilisateur = UtilisateurResourceIntTest.createEntity(em);
        em.persist(utilisateur);
        em.flush();
        profil.addUtilisateur(utilisateur);
        profilRepository.saveAndFlush(profil);
        Long utilisateurId = utilisateur.getId();

        // Get all the profilList where utilisateur equals to utilisateurId
        defaultProfilShouldBeFound("utilisateurId.equals=" + utilisateurId);

        // Get all the profilList where utilisateur equals to utilisateurId + 1
        defaultProfilShouldNotBeFound("utilisateurId.equals=" + (utilisateurId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilsByColonneIsEqualToSomething() throws Exception {
        // Initialize the database
        Colonne colonne = ColonneResourceIntTest.createEntity(em);
        em.persist(colonne);
        em.flush();
        profil.addColonne(colonne);
        profilRepository.saveAndFlush(profil);
        Long colonneId = colonne.getId();

        // Get all the profilList where colonne equals to colonneId
        defaultProfilShouldBeFound("colonneId.equals=" + colonneId);

        // Get all the profilList where colonne equals to colonneId + 1
        defaultProfilShouldNotBeFound("colonneId.equals=" + (colonneId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilsByTypeExtractionIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeExtraction typeExtraction = TypeExtractionResourceIntTest.createEntity(em);
        em.persist(typeExtraction);
        em.flush();
        profil.addTypeExtraction(typeExtraction);
        profilRepository.saveAndFlush(profil);
        Long typeExtractionId = typeExtraction.getId();

        // Get all the profilList where typeExtraction equals to typeExtractionId
        defaultProfilShouldBeFound("typeExtractionId.equals=" + typeExtractionId);

        // Get all the profilList where typeExtraction equals to typeExtractionId + 1
        defaultProfilShouldNotBeFound("typeExtractionId.equals=" + (typeExtractionId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilsByAdministrateurProfilIsEqualToSomething() throws Exception {
        // Initialize the database
        Utilisateur administrateurProfil = UtilisateurResourceIntTest.createEntity(em);
        em.persist(administrateurProfil);
        em.flush();
        profil.setAdministrateurProfil(administrateurProfil);
        profilRepository.saveAndFlush(profil);
        Long administrateurProfilId = administrateurProfil.getId();

        // Get all the profilList where administrateurProfil equals to administrateurProfilId
        defaultProfilShouldBeFound("administrateurProfilId.equals=" + administrateurProfilId);

        // Get all the profilList where administrateurProfil equals to administrateurProfilId + 1
        defaultProfilShouldNotBeFound("administrateurProfilId.equals=" + (administrateurProfilId + 1));
    }


    @Test
    @Transactional
    public void getAllProfilsByFilialeIsEqualToSomething() throws Exception {
        // Initialize the database
        Filiale filiale = FilialeResourceIntTest.createEntity(em);
        em.persist(filiale);
        em.flush();
        profil.addFiliale(filiale);
        profilRepository.saveAndFlush(profil);
        Long filialeId = filiale.getId();

        // Get all the profilList where filiale equals to filialeId
        defaultProfilShouldBeFound("filialeId.equals=" + filialeId);

        // Get all the profilList where filiale equals to filialeId + 1
        defaultProfilShouldNotBeFound("filialeId.equals=" + (filialeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProfilShouldBeFound(String filter) throws Exception {
        restProfilMockMvc.perform(get("/api/profils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profil.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProfilMockMvc.perform(get("/api/profils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProfilShouldNotBeFound(String filter) throws Exception {
        restProfilMockMvc.perform(get("/api/profils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfilMockMvc.perform(get("/api/profils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProfil() throws Exception {
        // Get the profil
        restProfilMockMvc.perform(get("/api/profils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfil() throws Exception {
        // Initialize the database
        profilService.save(profil);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockProfilSearchRepository);

        int databaseSizeBeforeUpdate = profilRepository.findAll().size();

        // Update the profil
        Profil updatedProfil = profilRepository.findById(profil.getId()).get();
        // Disconnect from session so that the updates on updatedProfil are not directly saved in db
        em.detach(updatedProfil);
        updatedProfil
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION);

        restProfilMockMvc.perform(put("/api/profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfil)))
            .andExpect(status().isOk());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);
        Profil testProfil = profilList.get(profilList.size() - 1);
        assertThat(testProfil.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testProfil.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Profil in Elasticsearch
        verify(mockProfilSearchRepository, times(1)).save(testProfil);
    }

    @Test
    @Transactional
    public void updateNonExistingProfil() throws Exception {
        int databaseSizeBeforeUpdate = profilRepository.findAll().size();

        // Create the Profil

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfilMockMvc.perform(put("/api/profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profil)))
            .andExpect(status().isBadRequest());

        // Validate the Profil in the database
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Profil in Elasticsearch
        verify(mockProfilSearchRepository, times(0)).save(profil);
    }

    @Test
    @Transactional
    public void deleteProfil() throws Exception {
        // Initialize the database
        profilService.save(profil);

        int databaseSizeBeforeDelete = profilRepository.findAll().size();

        // Delete the profil
        restProfilMockMvc.perform(delete("/api/profils/{id}", profil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Profil> profilList = profilRepository.findAll();
        assertThat(profilList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Profil in Elasticsearch
        verify(mockProfilSearchRepository, times(1)).deleteById(profil.getId());
    }

    @Test
    @Transactional
    public void searchProfil() throws Exception {
        // Initialize the database
        profilService.save(profil);
        when(mockProfilSearchRepository.search(queryStringQuery("id:" + profil.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(profil), PageRequest.of(0, 1), 1));
        // Search the profil
        restProfilMockMvc.perform(get("/api/_search/profils?query=id:" + profil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profil.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profil.class);
        Profil profil1 = new Profil();
        profil1.setId(1L);
        Profil profil2 = new Profil();
        profil2.setId(profil1.getId());
        assertThat(profil1).isEqualTo(profil2);
        profil2.setId(2L);
        assertThat(profil1).isNotEqualTo(profil2);
        profil1.setId(null);
        assertThat(profil1).isNotEqualTo(profil2);
    }
}
