package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Serveur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ServeurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ServeurSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ServeurService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ServeurCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ServeurQueryService;

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
 * Test class for the ServeurResource REST controller.
 *
 * @see ServeurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class ServeurResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_IP = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_IP = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_MOT_DE_PASSE = "AAAAAAAAAA";
    private static final String UPDATED_MOT_DE_PASSE = "BBBBBBBBBB";

    private static final String DEFAULT_REPERTOIRE_DEPOTS = "AAAAAAAAAA";
    private static final String UPDATED_REPERTOIRE_DEPOTS = "BBBBBBBBBB";

    private static final String DEFAULT_REPERTOIRE_RESULTATS = "AAAAAAAAAA";
    private static final String UPDATED_REPERTOIRE_RESULTATS = "BBBBBBBBBB";

    private static final String DEFAULT_REPERTOIRE_PARAMETRES = "AAAAAAAAAA";
    private static final String UPDATED_REPERTOIRE_PARAMETRES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EST_ACTIF = false;
    private static final Boolean UPDATED_EST_ACTIF = true;

    @Autowired
    private ServeurRepository serveurRepository;

    @Autowired
    private ServeurService serveurService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ServeurSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServeurSearchRepository mockServeurSearchRepository;

    @Autowired
    private ServeurQueryService serveurQueryService;

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

    private MockMvc restServeurMockMvc;

    private Serveur serveur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServeurResource serveurResource = new ServeurResource(serveurService, serveurQueryService);
        this.restServeurMockMvc = MockMvcBuilders.standaloneSetup(serveurResource)
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
    public static Serveur createEntity(EntityManager em) {
        Serveur serveur = new Serveur()
            .nom(DEFAULT_NOM)
            .adresseIp(DEFAULT_ADRESSE_IP)
            .login(DEFAULT_LOGIN)
            .motDePasse(DEFAULT_MOT_DE_PASSE)
            .repertoireDepots(DEFAULT_REPERTOIRE_DEPOTS)
            .repertoireResultats(DEFAULT_REPERTOIRE_RESULTATS)
            .repertoireParametres(DEFAULT_REPERTOIRE_PARAMETRES)
            .estActif(DEFAULT_EST_ACTIF);
        return serveur;
    }

    @Before
    public void initTest() {
        serveur = createEntity(em);
    }

    @Test
    @Transactional
    public void createServeur() throws Exception {
        int databaseSizeBeforeCreate = serveurRepository.findAll().size();

        // Create the Serveur
        restServeurMockMvc.perform(post("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isCreated());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeCreate + 1);
        Serveur testServeur = serveurList.get(serveurList.size() - 1);
        assertThat(testServeur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testServeur.getAdresseIp()).isEqualTo(DEFAULT_ADRESSE_IP);
        assertThat(testServeur.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testServeur.getMotDePasse()).isEqualTo(DEFAULT_MOT_DE_PASSE);
        assertThat(testServeur.getRepertoireDepots()).isEqualTo(DEFAULT_REPERTOIRE_DEPOTS);
        assertThat(testServeur.getRepertoireResultats()).isEqualTo(DEFAULT_REPERTOIRE_RESULTATS);
        assertThat(testServeur.getRepertoireParametres()).isEqualTo(DEFAULT_REPERTOIRE_PARAMETRES);
        assertThat(testServeur.isEstActif()).isEqualTo(DEFAULT_EST_ACTIF);

        // Validate the Serveur in Elasticsearch
        verify(mockServeurSearchRepository, times(1)).save(testServeur);
    }

    @Test
    @Transactional
    public void createServeurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serveurRepository.findAll().size();

        // Create the Serveur with an existing ID
        serveur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServeurMockMvc.perform(post("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeCreate);

        // Validate the Serveur in Elasticsearch
        verify(mockServeurSearchRepository, times(0)).save(serveur);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurRepository.findAll().size();
        // set the field null
        serveur.setNom(null);

        // Create the Serveur, which fails.

        restServeurMockMvc.perform(post("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIpIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurRepository.findAll().size();
        // set the field null
        serveur.setAdresseIp(null);

        // Create the Serveur, which fails.

        restServeurMockMvc.perform(post("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurRepository.findAll().size();
        // set the field null
        serveur.setLogin(null);

        // Create the Serveur, which fails.

        restServeurMockMvc.perform(post("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotDePasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurRepository.findAll().size();
        // set the field null
        serveur.setMotDePasse(null);

        // Create the Serveur, which fails.

        restServeurMockMvc.perform(post("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepertoireDepotsIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurRepository.findAll().size();
        // set the field null
        serveur.setRepertoireDepots(null);

        // Create the Serveur, which fails.

        restServeurMockMvc.perform(post("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepertoireResultatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurRepository.findAll().size();
        // set the field null
        serveur.setRepertoireResultats(null);

        // Create the Serveur, which fails.

        restServeurMockMvc.perform(post("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepertoireParametresIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurRepository.findAll().size();
        // set the field null
        serveur.setRepertoireParametres(null);

        // Create the Serveur, which fails.

        restServeurMockMvc.perform(post("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = serveurRepository.findAll().size();
        // set the field null
        serveur.setEstActif(null);

        // Create the Serveur, which fails.

        restServeurMockMvc.perform(post("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServeurs() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList
        restServeurMockMvc.perform(get("/api/serveurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serveur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].adresseIp").value(hasItem(DEFAULT_ADRESSE_IP.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].motDePasse").value(hasItem(DEFAULT_MOT_DE_PASSE.toString())))
            .andExpect(jsonPath("$.[*].repertoireDepots").value(hasItem(DEFAULT_REPERTOIRE_DEPOTS.toString())))
            .andExpect(jsonPath("$.[*].repertoireResultats").value(hasItem(DEFAULT_REPERTOIRE_RESULTATS.toString())))
            .andExpect(jsonPath("$.[*].repertoireParametres").value(hasItem(DEFAULT_REPERTOIRE_PARAMETRES.toString())))
            .andExpect(jsonPath("$.[*].estActif").value(hasItem(DEFAULT_EST_ACTIF.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getServeur() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get the serveur
        restServeurMockMvc.perform(get("/api/serveurs/{id}", serveur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serveur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.adresseIp").value(DEFAULT_ADRESSE_IP.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.motDePasse").value(DEFAULT_MOT_DE_PASSE.toString()))
            .andExpect(jsonPath("$.repertoireDepots").value(DEFAULT_REPERTOIRE_DEPOTS.toString()))
            .andExpect(jsonPath("$.repertoireResultats").value(DEFAULT_REPERTOIRE_RESULTATS.toString()))
            .andExpect(jsonPath("$.repertoireParametres").value(DEFAULT_REPERTOIRE_PARAMETRES.toString()))
            .andExpect(jsonPath("$.estActif").value(DEFAULT_EST_ACTIF.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllServeursByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nom equals to DEFAULT_NOM
        defaultServeurShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the serveurList where nom equals to UPDATED_NOM
        defaultServeurShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllServeursByNomIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultServeurShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the serveurList where nom equals to UPDATED_NOM
        defaultServeurShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllServeursByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where nom is not null
        defaultServeurShouldBeFound("nom.specified=true");

        // Get all the serveurList where nom is null
        defaultServeurShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllServeursByAdresseIpIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where adresseIp equals to DEFAULT_ADRESSE_IP
        defaultServeurShouldBeFound("adresseIp.equals=" + DEFAULT_ADRESSE_IP);

        // Get all the serveurList where adresseIp equals to UPDATED_ADRESSE_IP
        defaultServeurShouldNotBeFound("adresseIp.equals=" + UPDATED_ADRESSE_IP);
    }

    @Test
    @Transactional
    public void getAllServeursByAdresseIpIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where adresseIp in DEFAULT_ADRESSE_IP or UPDATED_ADRESSE_IP
        defaultServeurShouldBeFound("adresseIp.in=" + DEFAULT_ADRESSE_IP + "," + UPDATED_ADRESSE_IP);

        // Get all the serveurList where adresseIp equals to UPDATED_ADRESSE_IP
        defaultServeurShouldNotBeFound("adresseIp.in=" + UPDATED_ADRESSE_IP);
    }

    @Test
    @Transactional
    public void getAllServeursByAdresseIpIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where adresseIp is not null
        defaultServeurShouldBeFound("adresseIp.specified=true");

        // Get all the serveurList where adresseIp is null
        defaultServeurShouldNotBeFound("adresseIp.specified=false");
    }

    @Test
    @Transactional
    public void getAllServeursByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where login equals to DEFAULT_LOGIN
        defaultServeurShouldBeFound("login.equals=" + DEFAULT_LOGIN);

        // Get all the serveurList where login equals to UPDATED_LOGIN
        defaultServeurShouldNotBeFound("login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllServeursByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where login in DEFAULT_LOGIN or UPDATED_LOGIN
        defaultServeurShouldBeFound("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN);

        // Get all the serveurList where login equals to UPDATED_LOGIN
        defaultServeurShouldNotBeFound("login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllServeursByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where login is not null
        defaultServeurShouldBeFound("login.specified=true");

        // Get all the serveurList where login is null
        defaultServeurShouldNotBeFound("login.specified=false");
    }

    @Test
    @Transactional
    public void getAllServeursByMotDePasseIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where motDePasse equals to DEFAULT_MOT_DE_PASSE
        defaultServeurShouldBeFound("motDePasse.equals=" + DEFAULT_MOT_DE_PASSE);

        // Get all the serveurList where motDePasse equals to UPDATED_MOT_DE_PASSE
        defaultServeurShouldNotBeFound("motDePasse.equals=" + UPDATED_MOT_DE_PASSE);
    }

    @Test
    @Transactional
    public void getAllServeursByMotDePasseIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where motDePasse in DEFAULT_MOT_DE_PASSE or UPDATED_MOT_DE_PASSE
        defaultServeurShouldBeFound("motDePasse.in=" + DEFAULT_MOT_DE_PASSE + "," + UPDATED_MOT_DE_PASSE);

        // Get all the serveurList where motDePasse equals to UPDATED_MOT_DE_PASSE
        defaultServeurShouldNotBeFound("motDePasse.in=" + UPDATED_MOT_DE_PASSE);
    }

    @Test
    @Transactional
    public void getAllServeursByMotDePasseIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where motDePasse is not null
        defaultServeurShouldBeFound("motDePasse.specified=true");

        // Get all the serveurList where motDePasse is null
        defaultServeurShouldNotBeFound("motDePasse.specified=false");
    }

    @Test
    @Transactional
    public void getAllServeursByRepertoireDepotsIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where repertoireDepots equals to DEFAULT_REPERTOIRE_DEPOTS
        defaultServeurShouldBeFound("repertoireDepots.equals=" + DEFAULT_REPERTOIRE_DEPOTS);

        // Get all the serveurList where repertoireDepots equals to UPDATED_REPERTOIRE_DEPOTS
        defaultServeurShouldNotBeFound("repertoireDepots.equals=" + UPDATED_REPERTOIRE_DEPOTS);
    }

    @Test
    @Transactional
    public void getAllServeursByRepertoireDepotsIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where repertoireDepots in DEFAULT_REPERTOIRE_DEPOTS or UPDATED_REPERTOIRE_DEPOTS
        defaultServeurShouldBeFound("repertoireDepots.in=" + DEFAULT_REPERTOIRE_DEPOTS + "," + UPDATED_REPERTOIRE_DEPOTS);

        // Get all the serveurList where repertoireDepots equals to UPDATED_REPERTOIRE_DEPOTS
        defaultServeurShouldNotBeFound("repertoireDepots.in=" + UPDATED_REPERTOIRE_DEPOTS);
    }

    @Test
    @Transactional
    public void getAllServeursByRepertoireDepotsIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where repertoireDepots is not null
        defaultServeurShouldBeFound("repertoireDepots.specified=true");

        // Get all the serveurList where repertoireDepots is null
        defaultServeurShouldNotBeFound("repertoireDepots.specified=false");
    }

    @Test
    @Transactional
    public void getAllServeursByRepertoireResultatsIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where repertoireResultats equals to DEFAULT_REPERTOIRE_RESULTATS
        defaultServeurShouldBeFound("repertoireResultats.equals=" + DEFAULT_REPERTOIRE_RESULTATS);

        // Get all the serveurList where repertoireResultats equals to UPDATED_REPERTOIRE_RESULTATS
        defaultServeurShouldNotBeFound("repertoireResultats.equals=" + UPDATED_REPERTOIRE_RESULTATS);
    }

    @Test
    @Transactional
    public void getAllServeursByRepertoireResultatsIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where repertoireResultats in DEFAULT_REPERTOIRE_RESULTATS or UPDATED_REPERTOIRE_RESULTATS
        defaultServeurShouldBeFound("repertoireResultats.in=" + DEFAULT_REPERTOIRE_RESULTATS + "," + UPDATED_REPERTOIRE_RESULTATS);

        // Get all the serveurList where repertoireResultats equals to UPDATED_REPERTOIRE_RESULTATS
        defaultServeurShouldNotBeFound("repertoireResultats.in=" + UPDATED_REPERTOIRE_RESULTATS);
    }

    @Test
    @Transactional
    public void getAllServeursByRepertoireResultatsIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where repertoireResultats is not null
        defaultServeurShouldBeFound("repertoireResultats.specified=true");

        // Get all the serveurList where repertoireResultats is null
        defaultServeurShouldNotBeFound("repertoireResultats.specified=false");
    }

    @Test
    @Transactional
    public void getAllServeursByRepertoireParametresIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where repertoireParametres equals to DEFAULT_REPERTOIRE_PARAMETRES
        defaultServeurShouldBeFound("repertoireParametres.equals=" + DEFAULT_REPERTOIRE_PARAMETRES);

        // Get all the serveurList where repertoireParametres equals to UPDATED_REPERTOIRE_PARAMETRES
        defaultServeurShouldNotBeFound("repertoireParametres.equals=" + UPDATED_REPERTOIRE_PARAMETRES);
    }

    @Test
    @Transactional
    public void getAllServeursByRepertoireParametresIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where repertoireParametres in DEFAULT_REPERTOIRE_PARAMETRES or UPDATED_REPERTOIRE_PARAMETRES
        defaultServeurShouldBeFound("repertoireParametres.in=" + DEFAULT_REPERTOIRE_PARAMETRES + "," + UPDATED_REPERTOIRE_PARAMETRES);

        // Get all the serveurList where repertoireParametres equals to UPDATED_REPERTOIRE_PARAMETRES
        defaultServeurShouldNotBeFound("repertoireParametres.in=" + UPDATED_REPERTOIRE_PARAMETRES);
    }

    @Test
    @Transactional
    public void getAllServeursByRepertoireParametresIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where repertoireParametres is not null
        defaultServeurShouldBeFound("repertoireParametres.specified=true");

        // Get all the serveurList where repertoireParametres is null
        defaultServeurShouldNotBeFound("repertoireParametres.specified=false");
    }

    @Test
    @Transactional
    public void getAllServeursByEstActifIsEqualToSomething() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where estActif equals to DEFAULT_EST_ACTIF
        defaultServeurShouldBeFound("estActif.equals=" + DEFAULT_EST_ACTIF);

        // Get all the serveurList where estActif equals to UPDATED_EST_ACTIF
        defaultServeurShouldNotBeFound("estActif.equals=" + UPDATED_EST_ACTIF);
    }

    @Test
    @Transactional
    public void getAllServeursByEstActifIsInShouldWork() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where estActif in DEFAULT_EST_ACTIF or UPDATED_EST_ACTIF
        defaultServeurShouldBeFound("estActif.in=" + DEFAULT_EST_ACTIF + "," + UPDATED_EST_ACTIF);

        // Get all the serveurList where estActif equals to UPDATED_EST_ACTIF
        defaultServeurShouldNotBeFound("estActif.in=" + UPDATED_EST_ACTIF);
    }

    @Test
    @Transactional
    public void getAllServeursByEstActifIsNullOrNotNull() throws Exception {
        // Initialize the database
        serveurRepository.saveAndFlush(serveur);

        // Get all the serveurList where estActif is not null
        defaultServeurShouldBeFound("estActif.specified=true");

        // Get all the serveurList where estActif is null
        defaultServeurShouldNotBeFound("estActif.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultServeurShouldBeFound(String filter) throws Exception {
        restServeurMockMvc.perform(get("/api/serveurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serveur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresseIp").value(hasItem(DEFAULT_ADRESSE_IP)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].motDePasse").value(hasItem(DEFAULT_MOT_DE_PASSE)))
            .andExpect(jsonPath("$.[*].repertoireDepots").value(hasItem(DEFAULT_REPERTOIRE_DEPOTS)))
            .andExpect(jsonPath("$.[*].repertoireResultats").value(hasItem(DEFAULT_REPERTOIRE_RESULTATS)))
            .andExpect(jsonPath("$.[*].repertoireParametres").value(hasItem(DEFAULT_REPERTOIRE_PARAMETRES)))
            .andExpect(jsonPath("$.[*].estActif").value(hasItem(DEFAULT_EST_ACTIF.booleanValue())));

        // Check, that the count call also returns 1
        restServeurMockMvc.perform(get("/api/serveurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultServeurShouldNotBeFound(String filter) throws Exception {
        restServeurMockMvc.perform(get("/api/serveurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServeurMockMvc.perform(get("/api/serveurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServeur() throws Exception {
        // Get the serveur
        restServeurMockMvc.perform(get("/api/serveurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServeur() throws Exception {
        // Initialize the database
        serveurService.save(serveur);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockServeurSearchRepository);

        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();

        // Update the serveur
        Serveur updatedServeur = serveurRepository.findById(serveur.getId()).get();
        // Disconnect from session so that the updates on updatedServeur are not directly saved in db
        em.detach(updatedServeur);
        updatedServeur
            .nom(UPDATED_NOM)
            .adresseIp(UPDATED_ADRESSE_IP)
            .login(UPDATED_LOGIN)
            .motDePasse(UPDATED_MOT_DE_PASSE)
            .repertoireDepots(UPDATED_REPERTOIRE_DEPOTS)
            .repertoireResultats(UPDATED_REPERTOIRE_RESULTATS)
            .repertoireParametres(UPDATED_REPERTOIRE_PARAMETRES)
            .estActif(UPDATED_EST_ACTIF);

        restServeurMockMvc.perform(put("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServeur)))
            .andExpect(status().isOk());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);
        Serveur testServeur = serveurList.get(serveurList.size() - 1);
        assertThat(testServeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testServeur.getAdresseIp()).isEqualTo(UPDATED_ADRESSE_IP);
        assertThat(testServeur.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testServeur.getMotDePasse()).isEqualTo(UPDATED_MOT_DE_PASSE);
        assertThat(testServeur.getRepertoireDepots()).isEqualTo(UPDATED_REPERTOIRE_DEPOTS);
        assertThat(testServeur.getRepertoireResultats()).isEqualTo(UPDATED_REPERTOIRE_RESULTATS);
        assertThat(testServeur.getRepertoireParametres()).isEqualTo(UPDATED_REPERTOIRE_PARAMETRES);
        assertThat(testServeur.isEstActif()).isEqualTo(UPDATED_EST_ACTIF);

        // Validate the Serveur in Elasticsearch
        verify(mockServeurSearchRepository, times(1)).save(testServeur);
    }

    @Test
    @Transactional
    public void updateNonExistingServeur() throws Exception {
        int databaseSizeBeforeUpdate = serveurRepository.findAll().size();

        // Create the Serveur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServeurMockMvc.perform(put("/api/serveurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serveur)))
            .andExpect(status().isBadRequest());

        // Validate the Serveur in the database
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Serveur in Elasticsearch
        verify(mockServeurSearchRepository, times(0)).save(serveur);
    }

    @Test
    @Transactional
    public void deleteServeur() throws Exception {
        // Initialize the database
        serveurService.save(serveur);

        int databaseSizeBeforeDelete = serveurRepository.findAll().size();

        // Delete the serveur
        restServeurMockMvc.perform(delete("/api/serveurs/{id}", serveur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Serveur> serveurList = serveurRepository.findAll();
        assertThat(serveurList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Serveur in Elasticsearch
        verify(mockServeurSearchRepository, times(1)).deleteById(serveur.getId());
    }

    @Test
    @Transactional
    public void searchServeur() throws Exception {
        // Initialize the database
        serveurService.save(serveur);
        when(mockServeurSearchRepository.search(queryStringQuery("id:" + serveur.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(serveur), PageRequest.of(0, 1), 1));
        // Search the serveur
        restServeurMockMvc.perform(get("/api/_search/serveurs?query=id:" + serveur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serveur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresseIp").value(hasItem(DEFAULT_ADRESSE_IP)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].motDePasse").value(hasItem(DEFAULT_MOT_DE_PASSE)))
            .andExpect(jsonPath("$.[*].repertoireDepots").value(hasItem(DEFAULT_REPERTOIRE_DEPOTS)))
            .andExpect(jsonPath("$.[*].repertoireResultats").value(hasItem(DEFAULT_REPERTOIRE_RESULTATS)))
            .andExpect(jsonPath("$.[*].repertoireParametres").value(hasItem(DEFAULT_REPERTOIRE_PARAMETRES)))
            .andExpect(jsonPath("$.[*].estActif").value(hasItem(DEFAULT_EST_ACTIF.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Serveur.class);
        Serveur serveur1 = new Serveur();
        serveur1.setId(1L);
        Serveur serveur2 = new Serveur();
        serveur2.setId(serveur1.getId());
        assertThat(serveur1).isEqualTo(serveur2);
        serveur2.setId(2L);
        assertThat(serveur1).isNotEqualTo(serveur2);
        serveur1.setId(null);
        assertThat(serveur1).isNotEqualTo(serveur2);
    }
}
