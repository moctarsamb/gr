package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Requisition;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Provenance;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Structure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Utilisateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.RequisitionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.RequisitionSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.RequisitionService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.RequisitionCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.RequisitionQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.StatusEvolution;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.EtatRequisition;
/**
 * Test class for the RequisitionResource REST controller.
 *
 * @see RequisitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class RequisitionResourceIntTest {

    private static final Integer DEFAULT_NUMERO_ARRIVEE_DEMANDE = 1;
    private static final Integer UPDATED_NUMERO_ARRIVEE_DEMANDE = 2;

    private static final Integer DEFAULT_NUMERO_PV = 1;
    private static final Integer UPDATED_NUMERO_PV = 2;

    private static final LocalDate DEFAULT_DATE_SAISIE_PV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SAISIE_PV = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_DATE_ARRIVEE_DEMANDE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ARRIVEE_DEMANDE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_SAISIE_DEMANDE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_SAISIE_DEMANDE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE = false;
    private static final Boolean UPDATED_ENVOI_RESULTAT_AUTOMATIQUE = true;

    private static final Instant DEFAULT_DATE_REPONSE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_REPONSE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_RENVOIE_RESULTAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_RENVOIE_RESULTAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StatusEvolution DEFAULT_STATUS = StatusEvolution.ENCOURS;
    private static final StatusEvolution UPDATED_STATUS = StatusEvolution.TERMINE;

    private static final EtatRequisition DEFAULT_ETAT = EtatRequisition.ERREUR;
    private static final EtatRequisition UPDATED_ETAT = EtatRequisition.SUCCES;

    @Autowired
    private RequisitionRepository requisitionRepository;

    @Autowired
    private RequisitionService requisitionService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.RequisitionSearchRepositoryMockConfiguration
     */
    @Autowired
    private RequisitionSearchRepository mockRequisitionSearchRepository;

    @Autowired
    private RequisitionQueryService requisitionQueryService;

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

    private MockMvc restRequisitionMockMvc;

    private Requisition requisition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequisitionResource requisitionResource = new RequisitionResource(requisitionService, requisitionQueryService);
        this.restRequisitionMockMvc = MockMvcBuilders.standaloneSetup(requisitionResource)
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
    public static Requisition createEntity(EntityManager em) {
        Requisition requisition = new Requisition()
            .numeroArriveeDemande(DEFAULT_NUMERO_ARRIVEE_DEMANDE)
            .numeroPv(DEFAULT_NUMERO_PV)
            .dateSaisiePv(DEFAULT_DATE_SAISIE_PV)
            .dateArriveeDemande(DEFAULT_DATE_ARRIVEE_DEMANDE)
            .dateSaisieDemande(DEFAULT_DATE_SAISIE_DEMANDE)
            .envoiResultatAutomatique(DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE)
            .dateReponse(DEFAULT_DATE_REPONSE)
            .dateRenvoieResultat(DEFAULT_DATE_RENVOIE_RESULTAT)
            .status(DEFAULT_STATUS)
            .etat(DEFAULT_ETAT);
        return requisition;
    }

    @Before
    public void initTest() {
        requisition = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequisition() throws Exception {
        int databaseSizeBeforeCreate = requisitionRepository.findAll().size();

        // Create the Requisition
        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisition)))
            .andExpect(status().isCreated());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeCreate + 1);
        Requisition testRequisition = requisitionList.get(requisitionList.size() - 1);
        assertThat(testRequisition.getNumeroArriveeDemande()).isEqualTo(DEFAULT_NUMERO_ARRIVEE_DEMANDE);
        assertThat(testRequisition.getNumeroPv()).isEqualTo(DEFAULT_NUMERO_PV);
        assertThat(testRequisition.getDateSaisiePv()).isEqualTo(DEFAULT_DATE_SAISIE_PV);
        assertThat(testRequisition.getDateArriveeDemande()).isEqualTo(DEFAULT_DATE_ARRIVEE_DEMANDE);
        assertThat(testRequisition.getDateSaisieDemande()).isEqualTo(DEFAULT_DATE_SAISIE_DEMANDE);
        assertThat(testRequisition.isEnvoiResultatAutomatique()).isEqualTo(DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE);
        assertThat(testRequisition.getDateReponse()).isEqualTo(DEFAULT_DATE_REPONSE);
        assertThat(testRequisition.getDateRenvoieResultat()).isEqualTo(DEFAULT_DATE_RENVOIE_RESULTAT);
        assertThat(testRequisition.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRequisition.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the Requisition in Elasticsearch
        verify(mockRequisitionSearchRepository, times(1)).save(testRequisition);
    }

    @Test
    @Transactional
    public void createRequisitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requisitionRepository.findAll().size();

        // Create the Requisition with an existing ID
        requisition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisition)))
            .andExpect(status().isBadRequest());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Requisition in Elasticsearch
        verify(mockRequisitionSearchRepository, times(0)).save(requisition);
    }

    @Test
    @Transactional
    public void checkNumeroArriveeDemandeIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setNumeroArriveeDemande(null);

        // Create the Requisition, which fails.

        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisition)))
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroPvIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setNumeroPv(null);

        // Create the Requisition, which fails.

        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisition)))
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateSaisiePvIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setDateSaisiePv(null);

        // Create the Requisition, which fails.

        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisition)))
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateArriveeDemandeIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setDateArriveeDemande(null);

        // Create the Requisition, which fails.

        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisition)))
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateSaisieDemandeIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setDateSaisieDemande(null);

        // Create the Requisition, which fails.

        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisition)))
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnvoiResultatAutomatiqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setEnvoiResultatAutomatique(null);

        // Create the Requisition, which fails.

        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisition)))
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitionRepository.findAll().size();
        // set the field null
        requisition.setStatus(null);

        // Create the Requisition, which fails.

        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisition)))
            .andExpect(status().isBadRequest());

        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRequisitions() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList
        restRequisitionMockMvc.perform(get("/api/requisitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroArriveeDemande").value(hasItem(DEFAULT_NUMERO_ARRIVEE_DEMANDE)))
            .andExpect(jsonPath("$.[*].numeroPv").value(hasItem(DEFAULT_NUMERO_PV)))
            .andExpect(jsonPath("$.[*].dateSaisiePv").value(hasItem(DEFAULT_DATE_SAISIE_PV.toString())))
            .andExpect(jsonPath("$.[*].dateArriveeDemande").value(hasItem(DEFAULT_DATE_ARRIVEE_DEMANDE.toString())))
            .andExpect(jsonPath("$.[*].dateSaisieDemande").value(hasItem(DEFAULT_DATE_SAISIE_DEMANDE.toString())))
            .andExpect(jsonPath("$.[*].envoiResultatAutomatique").value(hasItem(DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateReponse").value(hasItem(DEFAULT_DATE_REPONSE.toString())))
            .andExpect(jsonPath("$.[*].dateRenvoieResultat").value(hasItem(DEFAULT_DATE_RENVOIE_RESULTAT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get the requisition
        restRequisitionMockMvc.perform(get("/api/requisitions/{id}", requisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requisition.getId().intValue()))
            .andExpect(jsonPath("$.numeroArriveeDemande").value(DEFAULT_NUMERO_ARRIVEE_DEMANDE))
            .andExpect(jsonPath("$.numeroPv").value(DEFAULT_NUMERO_PV))
            .andExpect(jsonPath("$.dateSaisiePv").value(DEFAULT_DATE_SAISIE_PV.toString()))
            .andExpect(jsonPath("$.dateArriveeDemande").value(DEFAULT_DATE_ARRIVEE_DEMANDE.toString()))
            .andExpect(jsonPath("$.dateSaisieDemande").value(DEFAULT_DATE_SAISIE_DEMANDE.toString()))
            .andExpect(jsonPath("$.envoiResultatAutomatique").value(DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE.booleanValue()))
            .andExpect(jsonPath("$.dateReponse").value(DEFAULT_DATE_REPONSE.toString()))
            .andExpect(jsonPath("$.dateRenvoieResultat").value(DEFAULT_DATE_RENVOIE_RESULTAT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getAllRequisitionsByNumeroArriveeDemandeIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where numeroArriveeDemande equals to DEFAULT_NUMERO_ARRIVEE_DEMANDE
        defaultRequisitionShouldBeFound("numeroArriveeDemande.equals=" + DEFAULT_NUMERO_ARRIVEE_DEMANDE);

        // Get all the requisitionList where numeroArriveeDemande equals to UPDATED_NUMERO_ARRIVEE_DEMANDE
        defaultRequisitionShouldNotBeFound("numeroArriveeDemande.equals=" + UPDATED_NUMERO_ARRIVEE_DEMANDE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByNumeroArriveeDemandeIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where numeroArriveeDemande in DEFAULT_NUMERO_ARRIVEE_DEMANDE or UPDATED_NUMERO_ARRIVEE_DEMANDE
        defaultRequisitionShouldBeFound("numeroArriveeDemande.in=" + DEFAULT_NUMERO_ARRIVEE_DEMANDE + "," + UPDATED_NUMERO_ARRIVEE_DEMANDE);

        // Get all the requisitionList where numeroArriveeDemande equals to UPDATED_NUMERO_ARRIVEE_DEMANDE
        defaultRequisitionShouldNotBeFound("numeroArriveeDemande.in=" + UPDATED_NUMERO_ARRIVEE_DEMANDE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByNumeroArriveeDemandeIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where numeroArriveeDemande is not null
        defaultRequisitionShouldBeFound("numeroArriveeDemande.specified=true");

        // Get all the requisitionList where numeroArriveeDemande is null
        defaultRequisitionShouldNotBeFound("numeroArriveeDemande.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByNumeroArriveeDemandeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where numeroArriveeDemande greater than or equals to DEFAULT_NUMERO_ARRIVEE_DEMANDE
        defaultRequisitionShouldBeFound("numeroArriveeDemande.greaterOrEqualThan=" + DEFAULT_NUMERO_ARRIVEE_DEMANDE);

        // Get all the requisitionList where numeroArriveeDemande greater than or equals to UPDATED_NUMERO_ARRIVEE_DEMANDE
        defaultRequisitionShouldNotBeFound("numeroArriveeDemande.greaterOrEqualThan=" + UPDATED_NUMERO_ARRIVEE_DEMANDE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByNumeroArriveeDemandeIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where numeroArriveeDemande less than or equals to DEFAULT_NUMERO_ARRIVEE_DEMANDE
        defaultRequisitionShouldNotBeFound("numeroArriveeDemande.lessThan=" + DEFAULT_NUMERO_ARRIVEE_DEMANDE);

        // Get all the requisitionList where numeroArriveeDemande less than or equals to UPDATED_NUMERO_ARRIVEE_DEMANDE
        defaultRequisitionShouldBeFound("numeroArriveeDemande.lessThan=" + UPDATED_NUMERO_ARRIVEE_DEMANDE);
    }


    @Test
    @Transactional
    public void getAllRequisitionsByNumeroPvIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where numeroPv equals to DEFAULT_NUMERO_PV
        defaultRequisitionShouldBeFound("numeroPv.equals=" + DEFAULT_NUMERO_PV);

        // Get all the requisitionList where numeroPv equals to UPDATED_NUMERO_PV
        defaultRequisitionShouldNotBeFound("numeroPv.equals=" + UPDATED_NUMERO_PV);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByNumeroPvIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where numeroPv in DEFAULT_NUMERO_PV or UPDATED_NUMERO_PV
        defaultRequisitionShouldBeFound("numeroPv.in=" + DEFAULT_NUMERO_PV + "," + UPDATED_NUMERO_PV);

        // Get all the requisitionList where numeroPv equals to UPDATED_NUMERO_PV
        defaultRequisitionShouldNotBeFound("numeroPv.in=" + UPDATED_NUMERO_PV);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByNumeroPvIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where numeroPv is not null
        defaultRequisitionShouldBeFound("numeroPv.specified=true");

        // Get all the requisitionList where numeroPv is null
        defaultRequisitionShouldNotBeFound("numeroPv.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByNumeroPvIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where numeroPv greater than or equals to DEFAULT_NUMERO_PV
        defaultRequisitionShouldBeFound("numeroPv.greaterOrEqualThan=" + DEFAULT_NUMERO_PV);

        // Get all the requisitionList where numeroPv greater than or equals to UPDATED_NUMERO_PV
        defaultRequisitionShouldNotBeFound("numeroPv.greaterOrEqualThan=" + UPDATED_NUMERO_PV);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByNumeroPvIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where numeroPv less than or equals to DEFAULT_NUMERO_PV
        defaultRequisitionShouldNotBeFound("numeroPv.lessThan=" + DEFAULT_NUMERO_PV);

        // Get all the requisitionList where numeroPv less than or equals to UPDATED_NUMERO_PV
        defaultRequisitionShouldBeFound("numeroPv.lessThan=" + UPDATED_NUMERO_PV);
    }


    @Test
    @Transactional
    public void getAllRequisitionsByDateSaisiePvIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateSaisiePv equals to DEFAULT_DATE_SAISIE_PV
        defaultRequisitionShouldBeFound("dateSaisiePv.equals=" + DEFAULT_DATE_SAISIE_PV);

        // Get all the requisitionList where dateSaisiePv equals to UPDATED_DATE_SAISIE_PV
        defaultRequisitionShouldNotBeFound("dateSaisiePv.equals=" + UPDATED_DATE_SAISIE_PV);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateSaisiePvIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateSaisiePv in DEFAULT_DATE_SAISIE_PV or UPDATED_DATE_SAISIE_PV
        defaultRequisitionShouldBeFound("dateSaisiePv.in=" + DEFAULT_DATE_SAISIE_PV + "," + UPDATED_DATE_SAISIE_PV);

        // Get all the requisitionList where dateSaisiePv equals to UPDATED_DATE_SAISIE_PV
        defaultRequisitionShouldNotBeFound("dateSaisiePv.in=" + UPDATED_DATE_SAISIE_PV);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateSaisiePvIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateSaisiePv is not null
        defaultRequisitionShouldBeFound("dateSaisiePv.specified=true");

        // Get all the requisitionList where dateSaisiePv is null
        defaultRequisitionShouldNotBeFound("dateSaisiePv.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateSaisiePvIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateSaisiePv greater than or equals to DEFAULT_DATE_SAISIE_PV
        defaultRequisitionShouldBeFound("dateSaisiePv.greaterOrEqualThan=" + DEFAULT_DATE_SAISIE_PV);

        // Get all the requisitionList where dateSaisiePv greater than or equals to UPDATED_DATE_SAISIE_PV
        defaultRequisitionShouldNotBeFound("dateSaisiePv.greaterOrEqualThan=" + UPDATED_DATE_SAISIE_PV);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateSaisiePvIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateSaisiePv less than or equals to DEFAULT_DATE_SAISIE_PV
        defaultRequisitionShouldNotBeFound("dateSaisiePv.lessThan=" + DEFAULT_DATE_SAISIE_PV);

        // Get all the requisitionList where dateSaisiePv less than or equals to UPDATED_DATE_SAISIE_PV
        defaultRequisitionShouldBeFound("dateSaisiePv.lessThan=" + UPDATED_DATE_SAISIE_PV);
    }


    @Test
    @Transactional
    public void getAllRequisitionsByDateArriveeDemandeIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateArriveeDemande equals to DEFAULT_DATE_ARRIVEE_DEMANDE
        defaultRequisitionShouldBeFound("dateArriveeDemande.equals=" + DEFAULT_DATE_ARRIVEE_DEMANDE);

        // Get all the requisitionList where dateArriveeDemande equals to UPDATED_DATE_ARRIVEE_DEMANDE
        defaultRequisitionShouldNotBeFound("dateArriveeDemande.equals=" + UPDATED_DATE_ARRIVEE_DEMANDE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateArriveeDemandeIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateArriveeDemande in DEFAULT_DATE_ARRIVEE_DEMANDE or UPDATED_DATE_ARRIVEE_DEMANDE
        defaultRequisitionShouldBeFound("dateArriveeDemande.in=" + DEFAULT_DATE_ARRIVEE_DEMANDE + "," + UPDATED_DATE_ARRIVEE_DEMANDE);

        // Get all the requisitionList where dateArriveeDemande equals to UPDATED_DATE_ARRIVEE_DEMANDE
        defaultRequisitionShouldNotBeFound("dateArriveeDemande.in=" + UPDATED_DATE_ARRIVEE_DEMANDE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateArriveeDemandeIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateArriveeDemande is not null
        defaultRequisitionShouldBeFound("dateArriveeDemande.specified=true");

        // Get all the requisitionList where dateArriveeDemande is null
        defaultRequisitionShouldNotBeFound("dateArriveeDemande.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateSaisieDemandeIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateSaisieDemande equals to DEFAULT_DATE_SAISIE_DEMANDE
        defaultRequisitionShouldBeFound("dateSaisieDemande.equals=" + DEFAULT_DATE_SAISIE_DEMANDE);

        // Get all the requisitionList where dateSaisieDemande equals to UPDATED_DATE_SAISIE_DEMANDE
        defaultRequisitionShouldNotBeFound("dateSaisieDemande.equals=" + UPDATED_DATE_SAISIE_DEMANDE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateSaisieDemandeIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateSaisieDemande in DEFAULT_DATE_SAISIE_DEMANDE or UPDATED_DATE_SAISIE_DEMANDE
        defaultRequisitionShouldBeFound("dateSaisieDemande.in=" + DEFAULT_DATE_SAISIE_DEMANDE + "," + UPDATED_DATE_SAISIE_DEMANDE);

        // Get all the requisitionList where dateSaisieDemande equals to UPDATED_DATE_SAISIE_DEMANDE
        defaultRequisitionShouldNotBeFound("dateSaisieDemande.in=" + UPDATED_DATE_SAISIE_DEMANDE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateSaisieDemandeIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateSaisieDemande is not null
        defaultRequisitionShouldBeFound("dateSaisieDemande.specified=true");

        // Get all the requisitionList where dateSaisieDemande is null
        defaultRequisitionShouldNotBeFound("dateSaisieDemande.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByEnvoiResultatAutomatiqueIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where envoiResultatAutomatique equals to DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE
        defaultRequisitionShouldBeFound("envoiResultatAutomatique.equals=" + DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE);

        // Get all the requisitionList where envoiResultatAutomatique equals to UPDATED_ENVOI_RESULTAT_AUTOMATIQUE
        defaultRequisitionShouldNotBeFound("envoiResultatAutomatique.equals=" + UPDATED_ENVOI_RESULTAT_AUTOMATIQUE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByEnvoiResultatAutomatiqueIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where envoiResultatAutomatique in DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE or UPDATED_ENVOI_RESULTAT_AUTOMATIQUE
        defaultRequisitionShouldBeFound("envoiResultatAutomatique.in=" + DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE + "," + UPDATED_ENVOI_RESULTAT_AUTOMATIQUE);

        // Get all the requisitionList where envoiResultatAutomatique equals to UPDATED_ENVOI_RESULTAT_AUTOMATIQUE
        defaultRequisitionShouldNotBeFound("envoiResultatAutomatique.in=" + UPDATED_ENVOI_RESULTAT_AUTOMATIQUE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByEnvoiResultatAutomatiqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where envoiResultatAutomatique is not null
        defaultRequisitionShouldBeFound("envoiResultatAutomatique.specified=true");

        // Get all the requisitionList where envoiResultatAutomatique is null
        defaultRequisitionShouldNotBeFound("envoiResultatAutomatique.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateReponseIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateReponse equals to DEFAULT_DATE_REPONSE
        defaultRequisitionShouldBeFound("dateReponse.equals=" + DEFAULT_DATE_REPONSE);

        // Get all the requisitionList where dateReponse equals to UPDATED_DATE_REPONSE
        defaultRequisitionShouldNotBeFound("dateReponse.equals=" + UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateReponseIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateReponse in DEFAULT_DATE_REPONSE or UPDATED_DATE_REPONSE
        defaultRequisitionShouldBeFound("dateReponse.in=" + DEFAULT_DATE_REPONSE + "," + UPDATED_DATE_REPONSE);

        // Get all the requisitionList where dateReponse equals to UPDATED_DATE_REPONSE
        defaultRequisitionShouldNotBeFound("dateReponse.in=" + UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateReponseIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateReponse is not null
        defaultRequisitionShouldBeFound("dateReponse.specified=true");

        // Get all the requisitionList where dateReponse is null
        defaultRequisitionShouldNotBeFound("dateReponse.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateRenvoieResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateRenvoieResultat equals to DEFAULT_DATE_RENVOIE_RESULTAT
        defaultRequisitionShouldBeFound("dateRenvoieResultat.equals=" + DEFAULT_DATE_RENVOIE_RESULTAT);

        // Get all the requisitionList where dateRenvoieResultat equals to UPDATED_DATE_RENVOIE_RESULTAT
        defaultRequisitionShouldNotBeFound("dateRenvoieResultat.equals=" + UPDATED_DATE_RENVOIE_RESULTAT);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateRenvoieResultatIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateRenvoieResultat in DEFAULT_DATE_RENVOIE_RESULTAT or UPDATED_DATE_RENVOIE_RESULTAT
        defaultRequisitionShouldBeFound("dateRenvoieResultat.in=" + DEFAULT_DATE_RENVOIE_RESULTAT + "," + UPDATED_DATE_RENVOIE_RESULTAT);

        // Get all the requisitionList where dateRenvoieResultat equals to UPDATED_DATE_RENVOIE_RESULTAT
        defaultRequisitionShouldNotBeFound("dateRenvoieResultat.in=" + UPDATED_DATE_RENVOIE_RESULTAT);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByDateRenvoieResultatIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where dateRenvoieResultat is not null
        defaultRequisitionShouldBeFound("dateRenvoieResultat.specified=true");

        // Get all the requisitionList where dateRenvoieResultat is null
        defaultRequisitionShouldNotBeFound("dateRenvoieResultat.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where status equals to DEFAULT_STATUS
        defaultRequisitionShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the requisitionList where status equals to UPDATED_STATUS
        defaultRequisitionShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRequisitionShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the requisitionList where status equals to UPDATED_STATUS
        defaultRequisitionShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where status is not null
        defaultRequisitionShouldBeFound("status.specified=true");

        // Get all the requisitionList where status is null
        defaultRequisitionShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where etat equals to DEFAULT_ETAT
        defaultRequisitionShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the requisitionList where etat equals to UPDATED_ETAT
        defaultRequisitionShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultRequisitionShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the requisitionList where etat equals to UPDATED_ETAT
        defaultRequisitionShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where etat is not null
        defaultRequisitionShouldBeFound("etat.specified=true");

        // Get all the requisitionList where etat is null
        defaultRequisitionShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByChampsRechercheIsEqualToSomething() throws Exception {
        // Initialize the database
        ChampsRecherche champsRecherche = ChampsRechercheResourceIntTest.createEntity(em);
        em.persist(champsRecherche);
        em.flush();
        requisition.addChampsRecherche(champsRecherche);
        requisitionRepository.saveAndFlush(requisition);
        Long champsRechercheId = champsRecherche.getId();

        // Get all the requisitionList where champsRecherche equals to champsRechercheId
        defaultRequisitionShouldBeFound("champsRechercheId.equals=" + champsRechercheId);

        // Get all the requisitionList where champsRecherche equals to champsRechercheId + 1
        defaultRequisitionShouldNotBeFound("champsRechercheId.equals=" + (champsRechercheId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionsByProvenanceIsEqualToSomething() throws Exception {
        // Initialize the database
        Provenance provenance = ProvenanceResourceIntTest.createEntity(em);
        em.persist(provenance);
        em.flush();
        requisition.setProvenance(provenance);
        requisitionRepository.saveAndFlush(requisition);
        Long provenanceId = provenance.getId();

        // Get all the requisitionList where provenance equals to provenanceId
        defaultRequisitionShouldBeFound("provenanceId.equals=" + provenanceId);

        // Get all the requisitionList where provenance equals to provenanceId + 1
        defaultRequisitionShouldNotBeFound("provenanceId.equals=" + (provenanceId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionsByStructureIsEqualToSomething() throws Exception {
        // Initialize the database
        Structure structure = StructureResourceIntTest.createEntity(em);
        em.persist(structure);
        em.flush();
        requisition.setStructure(structure);
        requisitionRepository.saveAndFlush(requisition);
        Long structureId = structure.getId();

        // Get all the requisitionList where structure equals to structureId
        defaultRequisitionShouldBeFound("structureId.equals=" + structureId);

        // Get all the requisitionList where structure equals to structureId + 1
        defaultRequisitionShouldNotBeFound("structureId.equals=" + (structureId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionsByUtilisateurIsEqualToSomething() throws Exception {
        // Initialize the database
        Utilisateur utilisateur = UtilisateurResourceIntTest.createEntity(em);
        em.persist(utilisateur);
        em.flush();
        requisition.setUtilisateur(utilisateur);
        requisitionRepository.saveAndFlush(requisition);
        Long utilisateurId = utilisateur.getId();

        // Get all the requisitionList where utilisateur equals to utilisateurId
        defaultRequisitionShouldBeFound("utilisateurId.equals=" + utilisateurId);

        // Get all the requisitionList where utilisateur equals to utilisateurId + 1
        defaultRequisitionShouldNotBeFound("utilisateurId.equals=" + (utilisateurId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRequisitionShouldBeFound(String filter) throws Exception {
        restRequisitionMockMvc.perform(get("/api/requisitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroArriveeDemande").value(hasItem(DEFAULT_NUMERO_ARRIVEE_DEMANDE)))
            .andExpect(jsonPath("$.[*].numeroPv").value(hasItem(DEFAULT_NUMERO_PV)))
            .andExpect(jsonPath("$.[*].dateSaisiePv").value(hasItem(DEFAULT_DATE_SAISIE_PV.toString())))
            .andExpect(jsonPath("$.[*].dateArriveeDemande").value(hasItem(DEFAULT_DATE_ARRIVEE_DEMANDE.toString())))
            .andExpect(jsonPath("$.[*].dateSaisieDemande").value(hasItem(DEFAULT_DATE_SAISIE_DEMANDE.toString())))
            .andExpect(jsonPath("$.[*].envoiResultatAutomatique").value(hasItem(DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateReponse").value(hasItem(DEFAULT_DATE_REPONSE.toString())))
            .andExpect(jsonPath("$.[*].dateRenvoieResultat").value(hasItem(DEFAULT_DATE_RENVOIE_RESULTAT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));

        // Check, that the count call also returns 1
        restRequisitionMockMvc.perform(get("/api/requisitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRequisitionShouldNotBeFound(String filter) throws Exception {
        restRequisitionMockMvc.perform(get("/api/requisitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRequisitionMockMvc.perform(get("/api/requisitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRequisition() throws Exception {
        // Get the requisition
        restRequisitionMockMvc.perform(get("/api/requisitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequisition() throws Exception {
        // Initialize the database
        requisitionService.save(requisition);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockRequisitionSearchRepository);

        int databaseSizeBeforeUpdate = requisitionRepository.findAll().size();

        // Update the requisition
        Requisition updatedRequisition = requisitionRepository.findById(requisition.getId()).get();
        // Disconnect from session so that the updates on updatedRequisition are not directly saved in db
        em.detach(updatedRequisition);
        updatedRequisition
            .numeroArriveeDemande(UPDATED_NUMERO_ARRIVEE_DEMANDE)
            .numeroPv(UPDATED_NUMERO_PV)
            .dateSaisiePv(UPDATED_DATE_SAISIE_PV)
            .dateArriveeDemande(UPDATED_DATE_ARRIVEE_DEMANDE)
            .dateSaisieDemande(UPDATED_DATE_SAISIE_DEMANDE)
            .envoiResultatAutomatique(UPDATED_ENVOI_RESULTAT_AUTOMATIQUE)
            .dateReponse(UPDATED_DATE_REPONSE)
            .dateRenvoieResultat(UPDATED_DATE_RENVOIE_RESULTAT)
            .status(UPDATED_STATUS)
            .etat(UPDATED_ETAT);

        restRequisitionMockMvc.perform(put("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRequisition)))
            .andExpect(status().isOk());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeUpdate);
        Requisition testRequisition = requisitionList.get(requisitionList.size() - 1);
        assertThat(testRequisition.getNumeroArriveeDemande()).isEqualTo(UPDATED_NUMERO_ARRIVEE_DEMANDE);
        assertThat(testRequisition.getNumeroPv()).isEqualTo(UPDATED_NUMERO_PV);
        assertThat(testRequisition.getDateSaisiePv()).isEqualTo(UPDATED_DATE_SAISIE_PV);
        assertThat(testRequisition.getDateArriveeDemande()).isEqualTo(UPDATED_DATE_ARRIVEE_DEMANDE);
        assertThat(testRequisition.getDateSaisieDemande()).isEqualTo(UPDATED_DATE_SAISIE_DEMANDE);
        assertThat(testRequisition.isEnvoiResultatAutomatique()).isEqualTo(UPDATED_ENVOI_RESULTAT_AUTOMATIQUE);
        assertThat(testRequisition.getDateReponse()).isEqualTo(UPDATED_DATE_REPONSE);
        assertThat(testRequisition.getDateRenvoieResultat()).isEqualTo(UPDATED_DATE_RENVOIE_RESULTAT);
        assertThat(testRequisition.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequisition.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the Requisition in Elasticsearch
        verify(mockRequisitionSearchRepository, times(1)).save(testRequisition);
    }

    @Test
    @Transactional
    public void updateNonExistingRequisition() throws Exception {
        int databaseSizeBeforeUpdate = requisitionRepository.findAll().size();

        // Create the Requisition

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequisitionMockMvc.perform(put("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisition)))
            .andExpect(status().isBadRequest());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Requisition in Elasticsearch
        verify(mockRequisitionSearchRepository, times(0)).save(requisition);
    }

    @Test
    @Transactional
    public void deleteRequisition() throws Exception {
        // Initialize the database
        requisitionService.save(requisition);

        int databaseSizeBeforeDelete = requisitionRepository.findAll().size();

        // Delete the requisition
        restRequisitionMockMvc.perform(delete("/api/requisitions/{id}", requisition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Requisition in Elasticsearch
        verify(mockRequisitionSearchRepository, times(1)).deleteById(requisition.getId());
    }

    @Test
    @Transactional
    public void searchRequisition() throws Exception {
        // Initialize the database
        requisitionService.save(requisition);
        when(mockRequisitionSearchRepository.search(queryStringQuery("id:" + requisition.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(requisition), PageRequest.of(0, 1), 1));
        // Search the requisition
        restRequisitionMockMvc.perform(get("/api/_search/requisitions?query=id:" + requisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroArriveeDemande").value(hasItem(DEFAULT_NUMERO_ARRIVEE_DEMANDE)))
            .andExpect(jsonPath("$.[*].numeroPv").value(hasItem(DEFAULT_NUMERO_PV)))
            .andExpect(jsonPath("$.[*].dateSaisiePv").value(hasItem(DEFAULT_DATE_SAISIE_PV.toString())))
            .andExpect(jsonPath("$.[*].dateArriveeDemande").value(hasItem(DEFAULT_DATE_ARRIVEE_DEMANDE.toString())))
            .andExpect(jsonPath("$.[*].dateSaisieDemande").value(hasItem(DEFAULT_DATE_SAISIE_DEMANDE.toString())))
            .andExpect(jsonPath("$.[*].envoiResultatAutomatique").value(hasItem(DEFAULT_ENVOI_RESULTAT_AUTOMATIQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateReponse").value(hasItem(DEFAULT_DATE_REPONSE.toString())))
            .andExpect(jsonPath("$.[*].dateRenvoieResultat").value(hasItem(DEFAULT_DATE_RENVOIE_RESULTAT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Requisition.class);
        Requisition requisition1 = new Requisition();
        requisition1.setId(1L);
        Requisition requisition2 = new Requisition();
        requisition2.setId(requisition1.getId());
        assertThat(requisition1).isEqualTo(requisition2);
        requisition2.setId(2L);
        assertThat(requisition1).isNotEqualTo(requisition2);
        requisition1.setId(null);
        assertThat(requisition1).isNotEqualTo(requisition2);
    }
}
