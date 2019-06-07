package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Utilisateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.User;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.EnvoiResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Notification;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Profil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Requisition;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.DroitAcces;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.UtilisateurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.UtilisateurSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.UtilisateurService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.UtilisateurCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.UtilisateurQueryService;

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
 * Test class for the UtilisateurResource REST controller.
 *
 * @see UtilisateurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class UtilisateurResourceIntTest {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EST_ACTIF = false;
    private static final Boolean UPDATED_EST_ACTIF = true;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.UtilisateurSearchRepositoryMockConfiguration
     */
    @Autowired
    private UtilisateurSearchRepository mockUtilisateurSearchRepository;

    @Autowired
    private UtilisateurQueryService utilisateurQueryService;

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

    private MockMvc restUtilisateurMockMvc;

    private Utilisateur utilisateur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UtilisateurResource utilisateurResource = new UtilisateurResource(utilisateurService, utilisateurQueryService);
        this.restUtilisateurMockMvc = MockMvcBuilders.standaloneSetup(utilisateurResource)
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
    public static Utilisateur createEntity(EntityManager em) {
        Utilisateur utilisateur = new Utilisateur()
            .matricule(DEFAULT_MATRICULE)
            .username(DEFAULT_USERNAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .estActif(DEFAULT_EST_ACTIF);
        return utilisateur;
    }

    @Before
    public void initTest() {
        utilisateur = createEntity(em);
    }

    @Test
    @Transactional
    public void createUtilisateur() throws Exception {
        int databaseSizeBeforeCreate = utilisateurRepository.findAll().size();

        // Create the Utilisateur
        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isCreated());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeCreate + 1);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testUtilisateur.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUtilisateur.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUtilisateur.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUtilisateur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUtilisateur.isEstActif()).isEqualTo(DEFAULT_EST_ACTIF);

        // Validate the Utilisateur in Elasticsearch
        verify(mockUtilisateurSearchRepository, times(1)).save(testUtilisateur);
    }

    @Test
    @Transactional
    public void createUtilisateurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = utilisateurRepository.findAll().size();

        // Create the Utilisateur with an existing ID
        utilisateur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeCreate);

        // Validate the Utilisateur in Elasticsearch
        verify(mockUtilisateurSearchRepository, times(0)).save(utilisateur);
    }

    @Test
    @Transactional
    public void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setMatricule(null);

        // Create the Utilisateur, which fails.

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setUsername(null);

        // Create the Utilisateur, which fails.

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setFirstName(null);

        // Create the Utilisateur, which fails.

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setLastName(null);

        // Create the Utilisateur, which fails.

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setEmail(null);

        // Create the Utilisateur, which fails.

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setEstActif(null);

        // Create the Utilisateur, which fails.

        restUtilisateurMockMvc.perform(post("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUtilisateurs() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList
        restUtilisateurMockMvc.perform(get("/api/utilisateurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].estActif").value(hasItem(DEFAULT_EST_ACTIF.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get the utilisateur
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/{id}", utilisateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(utilisateur.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.estActif").value(DEFAULT_EST_ACTIF.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllUtilisateursByMatriculeIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where matricule equals to DEFAULT_MATRICULE
        defaultUtilisateurShouldBeFound("matricule.equals=" + DEFAULT_MATRICULE);

        // Get all the utilisateurList where matricule equals to UPDATED_MATRICULE
        defaultUtilisateurShouldNotBeFound("matricule.equals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByMatriculeIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where matricule in DEFAULT_MATRICULE or UPDATED_MATRICULE
        defaultUtilisateurShouldBeFound("matricule.in=" + DEFAULT_MATRICULE + "," + UPDATED_MATRICULE);

        // Get all the utilisateurList where matricule equals to UPDATED_MATRICULE
        defaultUtilisateurShouldNotBeFound("matricule.in=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByMatriculeIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where matricule is not null
        defaultUtilisateurShouldBeFound("matricule.specified=true");

        // Get all the utilisateurList where matricule is null
        defaultUtilisateurShouldNotBeFound("matricule.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where username equals to DEFAULT_USERNAME
        defaultUtilisateurShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the utilisateurList where username equals to UPDATED_USERNAME
        defaultUtilisateurShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultUtilisateurShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the utilisateurList where username equals to UPDATED_USERNAME
        defaultUtilisateurShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where username is not null
        defaultUtilisateurShouldBeFound("username.specified=true");

        // Get all the utilisateurList where username is null
        defaultUtilisateurShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where firstName equals to DEFAULT_FIRST_NAME
        defaultUtilisateurShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the utilisateurList where firstName equals to UPDATED_FIRST_NAME
        defaultUtilisateurShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultUtilisateurShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the utilisateurList where firstName equals to UPDATED_FIRST_NAME
        defaultUtilisateurShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where firstName is not null
        defaultUtilisateurShouldBeFound("firstName.specified=true");

        // Get all the utilisateurList where firstName is null
        defaultUtilisateurShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where lastName equals to DEFAULT_LAST_NAME
        defaultUtilisateurShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the utilisateurList where lastName equals to UPDATED_LAST_NAME
        defaultUtilisateurShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultUtilisateurShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the utilisateurList where lastName equals to UPDATED_LAST_NAME
        defaultUtilisateurShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where lastName is not null
        defaultUtilisateurShouldBeFound("lastName.specified=true");

        // Get all the utilisateurList where lastName is null
        defaultUtilisateurShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where email equals to DEFAULT_EMAIL
        defaultUtilisateurShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the utilisateurList where email equals to UPDATED_EMAIL
        defaultUtilisateurShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultUtilisateurShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the utilisateurList where email equals to UPDATED_EMAIL
        defaultUtilisateurShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where email is not null
        defaultUtilisateurShouldBeFound("email.specified=true");

        // Get all the utilisateurList where email is null
        defaultUtilisateurShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByEstActifIsEqualToSomething() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where estActif equals to DEFAULT_EST_ACTIF
        defaultUtilisateurShouldBeFound("estActif.equals=" + DEFAULT_EST_ACTIF);

        // Get all the utilisateurList where estActif equals to UPDATED_EST_ACTIF
        defaultUtilisateurShouldNotBeFound("estActif.equals=" + UPDATED_EST_ACTIF);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByEstActifIsInShouldWork() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where estActif in DEFAULT_EST_ACTIF or UPDATED_EST_ACTIF
        defaultUtilisateurShouldBeFound("estActif.in=" + DEFAULT_EST_ACTIF + "," + UPDATED_EST_ACTIF);

        // Get all the utilisateurList where estActif equals to UPDATED_EST_ACTIF
        defaultUtilisateurShouldNotBeFound("estActif.in=" + UPDATED_EST_ACTIF);
    }

    @Test
    @Transactional
    public void getAllUtilisateursByEstActifIsNullOrNotNull() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList where estActif is not null
        defaultUtilisateurShouldBeFound("estActif.specified=true");

        // Get all the utilisateurList where estActif is null
        defaultUtilisateurShouldNotBeFound("estActif.specified=false");
    }

    @Test
    @Transactional
    public void getAllUtilisateursByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        utilisateur.setUser(user);
        utilisateurRepository.saveAndFlush(utilisateur);
        String userId = user.getId();

        // Get all the utilisateurList where user equals to userId
        defaultUtilisateurShouldBeFound("userId.equals=" + userId);

        // Get all the utilisateurList where user equals to userId + 1
        defaultUtilisateurShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllUtilisateursByEnvoiResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        EnvoiResultat envoiResultat = EnvoiResultatResourceIntTest.createEntity(em);
        em.persist(envoiResultat);
        em.flush();
        utilisateur.addEnvoiResultat(envoiResultat);
        utilisateurRepository.saveAndFlush(utilisateur);
        Long envoiResultatId = envoiResultat.getId();

        // Get all the utilisateurList where envoiResultat equals to envoiResultatId
        defaultUtilisateurShouldBeFound("envoiResultatId.equals=" + envoiResultatId);

        // Get all the utilisateurList where envoiResultat equals to envoiResultatId + 1
        defaultUtilisateurShouldNotBeFound("envoiResultatId.equals=" + (envoiResultatId + 1));
    }


    @Test
    @Transactional
    public void getAllUtilisateursByNotificationIsEqualToSomething() throws Exception {
        // Initialize the database
        Notification notification = NotificationResourceIntTest.createEntity(em);
        em.persist(notification);
        em.flush();
        utilisateur.addNotification(notification);
        utilisateurRepository.saveAndFlush(utilisateur);
        Long notificationId = notification.getId();

        // Get all the utilisateurList where notification equals to notificationId
        defaultUtilisateurShouldBeFound("notificationId.equals=" + notificationId);

        // Get all the utilisateurList where notification equals to notificationId + 1
        defaultUtilisateurShouldNotBeFound("notificationId.equals=" + (notificationId + 1));
    }


    @Test
    @Transactional
    public void getAllUtilisateursByProfilAdministreIsEqualToSomething() throws Exception {
        // Initialize the database
        Profil profilAdministre = ProfilResourceIntTest.createEntity(em);
        em.persist(profilAdministre);
        em.flush();
        utilisateur.addProfilAdministre(profilAdministre);
        utilisateurRepository.saveAndFlush(utilisateur);
        Long profilAdministreId = profilAdministre.getId();

        // Get all the utilisateurList where profilAdministre equals to profilAdministreId
        defaultUtilisateurShouldBeFound("profilAdministreId.equals=" + profilAdministreId);

        // Get all the utilisateurList where profilAdministre equals to profilAdministreId + 1
        defaultUtilisateurShouldNotBeFound("profilAdministreId.equals=" + (profilAdministreId + 1));
    }


    @Test
    @Transactional
    public void getAllUtilisateursByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        utilisateur.addRequisition(requisition);
        utilisateurRepository.saveAndFlush(utilisateur);
        Long requisitionId = requisition.getId();

        // Get all the utilisateurList where requisition equals to requisitionId
        defaultUtilisateurShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the utilisateurList where requisition equals to requisitionId + 1
        defaultUtilisateurShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }


    @Test
    @Transactional
    public void getAllUtilisateursByDroitAccesIsEqualToSomething() throws Exception {
        // Initialize the database
        DroitAcces droitAcces = DroitAccesResourceIntTest.createEntity(em);
        em.persist(droitAcces);
        em.flush();
        utilisateur.setDroitAcces(droitAcces);
        utilisateurRepository.saveAndFlush(utilisateur);
        Long droitAccesId = droitAcces.getId();

        // Get all the utilisateurList where droitAcces equals to droitAccesId
        defaultUtilisateurShouldBeFound("droitAccesId.equals=" + droitAccesId);

        // Get all the utilisateurList where droitAcces equals to droitAccesId + 1
        defaultUtilisateurShouldNotBeFound("droitAccesId.equals=" + (droitAccesId + 1));
    }


    @Test
    @Transactional
    public void getAllUtilisateursByFilialeIsEqualToSomething() throws Exception {
        // Initialize the database
        Filiale filiale = FilialeResourceIntTest.createEntity(em);
        em.persist(filiale);
        em.flush();
        utilisateur.setFiliale(filiale);
        utilisateurRepository.saveAndFlush(utilisateur);
        Long filialeId = filiale.getId();

        // Get all the utilisateurList where filiale equals to filialeId
        defaultUtilisateurShouldBeFound("filialeId.equals=" + filialeId);

        // Get all the utilisateurList where filiale equals to filialeId + 1
        defaultUtilisateurShouldNotBeFound("filialeId.equals=" + (filialeId + 1));
    }


    @Test
    @Transactional
    public void getAllUtilisateursByProfilIsEqualToSomething() throws Exception {
        // Initialize the database
        Profil profil = ProfilResourceIntTest.createEntity(em);
        em.persist(profil);
        em.flush();
        utilisateur.setProfil(profil);
        utilisateurRepository.saveAndFlush(utilisateur);
        Long profilId = profil.getId();

        // Get all the utilisateurList where profil equals to profilId
        defaultUtilisateurShouldBeFound("profilId.equals=" + profilId);

        // Get all the utilisateurList where profil equals to profilId + 1
        defaultUtilisateurShouldNotBeFound("profilId.equals=" + (profilId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUtilisateurShouldBeFound(String filter) throws Exception {
        restUtilisateurMockMvc.perform(get("/api/utilisateurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].estActif").value(hasItem(DEFAULT_EST_ACTIF.booleanValue())));

        // Check, that the count call also returns 1
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUtilisateurShouldNotBeFound(String filter) throws Exception {
        restUtilisateurMockMvc.perform(get("/api/utilisateurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUtilisateur() throws Exception {
        // Get the utilisateur
        restUtilisateurMockMvc.perform(get("/api/utilisateurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUtilisateur() throws Exception {
        // Initialize the database
        utilisateurService.save(utilisateur);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUtilisateurSearchRepository);

        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Update the utilisateur
        Utilisateur updatedUtilisateur = utilisateurRepository.findById(utilisateur.getId()).get();
        // Disconnect from session so that the updates on updatedUtilisateur are not directly saved in db
        em.detach(updatedUtilisateur);
        updatedUtilisateur
            .matricule(UPDATED_MATRICULE)
            .username(UPDATED_USERNAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .estActif(UPDATED_EST_ACTIF);

        restUtilisateurMockMvc.perform(put("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUtilisateur)))
            .andExpect(status().isOk());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testUtilisateur.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUtilisateur.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUtilisateur.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUtilisateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUtilisateur.isEstActif()).isEqualTo(UPDATED_EST_ACTIF);

        // Validate the Utilisateur in Elasticsearch
        verify(mockUtilisateurSearchRepository, times(1)).save(testUtilisateur);
    }

    @Test
    @Transactional
    public void updateNonExistingUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Create the Utilisateur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUtilisateurMockMvc.perform(put("/api/utilisateurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Utilisateur in Elasticsearch
        verify(mockUtilisateurSearchRepository, times(0)).save(utilisateur);
    }

    @Test
    @Transactional
    public void deleteUtilisateur() throws Exception {
        // Initialize the database
        utilisateurService.save(utilisateur);

        int databaseSizeBeforeDelete = utilisateurRepository.findAll().size();

        // Delete the utilisateur
        restUtilisateurMockMvc.perform(delete("/api/utilisateurs/{id}", utilisateur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Utilisateur in Elasticsearch
        verify(mockUtilisateurSearchRepository, times(1)).deleteById(utilisateur.getId());
    }

    @Test
    @Transactional
    public void searchUtilisateur() throws Exception {
        // Initialize the database
        utilisateurService.save(utilisateur);
        when(mockUtilisateurSearchRepository.search(queryStringQuery("id:" + utilisateur.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(utilisateur), PageRequest.of(0, 1), 1));
        // Search the utilisateur
        restUtilisateurMockMvc.perform(get("/api/_search/utilisateurs?query=id:" + utilisateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].estActif").value(hasItem(DEFAULT_EST_ACTIF.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utilisateur.class);
        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setId(1L);
        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setId(utilisateur1.getId());
        assertThat(utilisateur1).isEqualTo(utilisateur2);
        utilisateur2.setId(2L);
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);
        utilisateur1.setId(null);
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);
    }
}
