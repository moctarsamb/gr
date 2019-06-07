package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Resultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Environnement;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Requisition;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ChampsRechercheRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ChampsRechercheSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ChampsRechercheService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ChampsRechercheCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ChampsRechercheQueryService;

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
import java.time.ZoneId;
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
 * Test class for the ChampsRechercheResource REST controller.
 *
 * @see ChampsRechercheResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class ChampsRechercheResourceIntTest {

    private static final String DEFAULT_CHAMPS = "AAAAAAAAAA";
    private static final String UPDATED_CHAMPS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT_EXTRACTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT_EXTRACTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN_EXTRACTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN_EXTRACTION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ChampsRechercheRepository champsRechercheRepository;

    @Autowired
    private ChampsRechercheService champsRechercheService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ChampsRechercheSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChampsRechercheSearchRepository mockChampsRechercheSearchRepository;

    @Autowired
    private ChampsRechercheQueryService champsRechercheQueryService;

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

    private MockMvc restChampsRechercheMockMvc;

    private ChampsRecherche champsRecherche;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChampsRechercheResource champsRechercheResource = new ChampsRechercheResource(champsRechercheService, champsRechercheQueryService);
        this.restChampsRechercheMockMvc = MockMvcBuilders.standaloneSetup(champsRechercheResource)
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
    public static ChampsRecherche createEntity(EntityManager em) {
        ChampsRecherche champsRecherche = new ChampsRecherche()
            .champs(DEFAULT_CHAMPS)
            .dateDebutExtraction(DEFAULT_DATE_DEBUT_EXTRACTION)
            .dateFinExtraction(DEFAULT_DATE_FIN_EXTRACTION);
        return champsRecherche;
    }

    @Before
    public void initTest() {
        champsRecherche = createEntity(em);
    }

    @Test
    @Transactional
    public void createChampsRecherche() throws Exception {
        int databaseSizeBeforeCreate = champsRechercheRepository.findAll().size();

        // Create the ChampsRecherche
        restChampsRechercheMockMvc.perform(post("/api/champs-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champsRecherche)))
            .andExpect(status().isCreated());

        // Validate the ChampsRecherche in the database
        List<ChampsRecherche> champsRechercheList = champsRechercheRepository.findAll();
        assertThat(champsRechercheList).hasSize(databaseSizeBeforeCreate + 1);
        ChampsRecherche testChampsRecherche = champsRechercheList.get(champsRechercheList.size() - 1);
        assertThat(testChampsRecherche.getChamps()).isEqualTo(DEFAULT_CHAMPS);
        assertThat(testChampsRecherche.getDateDebutExtraction()).isEqualTo(DEFAULT_DATE_DEBUT_EXTRACTION);
        assertThat(testChampsRecherche.getDateFinExtraction()).isEqualTo(DEFAULT_DATE_FIN_EXTRACTION);

        // Validate the ChampsRecherche in Elasticsearch
        verify(mockChampsRechercheSearchRepository, times(1)).save(testChampsRecherche);
    }

    @Test
    @Transactional
    public void createChampsRechercheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = champsRechercheRepository.findAll().size();

        // Create the ChampsRecherche with an existing ID
        champsRecherche.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChampsRechercheMockMvc.perform(post("/api/champs-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champsRecherche)))
            .andExpect(status().isBadRequest());

        // Validate the ChampsRecherche in the database
        List<ChampsRecherche> champsRechercheList = champsRechercheRepository.findAll();
        assertThat(champsRechercheList).hasSize(databaseSizeBeforeCreate);

        // Validate the ChampsRecherche in Elasticsearch
        verify(mockChampsRechercheSearchRepository, times(0)).save(champsRecherche);
    }

    @Test
    @Transactional
    public void checkChampsIsRequired() throws Exception {
        int databaseSizeBeforeTest = champsRechercheRepository.findAll().size();
        // set the field null
        champsRecherche.setChamps(null);

        // Create the ChampsRecherche, which fails.

        restChampsRechercheMockMvc.perform(post("/api/champs-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champsRecherche)))
            .andExpect(status().isBadRequest());

        List<ChampsRecherche> champsRechercheList = champsRechercheRepository.findAll();
        assertThat(champsRechercheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutExtractionIsRequired() throws Exception {
        int databaseSizeBeforeTest = champsRechercheRepository.findAll().size();
        // set the field null
        champsRecherche.setDateDebutExtraction(null);

        // Create the ChampsRecherche, which fails.

        restChampsRechercheMockMvc.perform(post("/api/champs-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champsRecherche)))
            .andExpect(status().isBadRequest());

        List<ChampsRecherche> champsRechercheList = champsRechercheRepository.findAll();
        assertThat(champsRechercheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinExtractionIsRequired() throws Exception {
        int databaseSizeBeforeTest = champsRechercheRepository.findAll().size();
        // set the field null
        champsRecherche.setDateFinExtraction(null);

        // Create the ChampsRecherche, which fails.

        restChampsRechercheMockMvc.perform(post("/api/champs-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champsRecherche)))
            .andExpect(status().isBadRequest());

        List<ChampsRecherche> champsRechercheList = champsRechercheRepository.findAll();
        assertThat(champsRechercheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChampsRecherches() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList
        restChampsRechercheMockMvc.perform(get("/api/champs-recherches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(champsRecherche.getId().intValue())))
            .andExpect(jsonPath("$.[*].champs").value(hasItem(DEFAULT_CHAMPS.toString())))
            .andExpect(jsonPath("$.[*].dateDebutExtraction").value(hasItem(DEFAULT_DATE_DEBUT_EXTRACTION.toString())))
            .andExpect(jsonPath("$.[*].dateFinExtraction").value(hasItem(DEFAULT_DATE_FIN_EXTRACTION.toString())));
    }
    
    @Test
    @Transactional
    public void getChampsRecherche() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get the champsRecherche
        restChampsRechercheMockMvc.perform(get("/api/champs-recherches/{id}", champsRecherche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(champsRecherche.getId().intValue()))
            .andExpect(jsonPath("$.champs").value(DEFAULT_CHAMPS.toString()))
            .andExpect(jsonPath("$.dateDebutExtraction").value(DEFAULT_DATE_DEBUT_EXTRACTION.toString()))
            .andExpect(jsonPath("$.dateFinExtraction").value(DEFAULT_DATE_FIN_EXTRACTION.toString()));
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByChampsIsEqualToSomething() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where champs equals to DEFAULT_CHAMPS
        defaultChampsRechercheShouldBeFound("champs.equals=" + DEFAULT_CHAMPS);

        // Get all the champsRechercheList where champs equals to UPDATED_CHAMPS
        defaultChampsRechercheShouldNotBeFound("champs.equals=" + UPDATED_CHAMPS);
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByChampsIsInShouldWork() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where champs in DEFAULT_CHAMPS or UPDATED_CHAMPS
        defaultChampsRechercheShouldBeFound("champs.in=" + DEFAULT_CHAMPS + "," + UPDATED_CHAMPS);

        // Get all the champsRechercheList where champs equals to UPDATED_CHAMPS
        defaultChampsRechercheShouldNotBeFound("champs.in=" + UPDATED_CHAMPS);
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByChampsIsNullOrNotNull() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where champs is not null
        defaultChampsRechercheShouldBeFound("champs.specified=true");

        // Get all the champsRechercheList where champs is null
        defaultChampsRechercheShouldNotBeFound("champs.specified=false");
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByDateDebutExtractionIsEqualToSomething() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where dateDebutExtraction equals to DEFAULT_DATE_DEBUT_EXTRACTION
        defaultChampsRechercheShouldBeFound("dateDebutExtraction.equals=" + DEFAULT_DATE_DEBUT_EXTRACTION);

        // Get all the champsRechercheList where dateDebutExtraction equals to UPDATED_DATE_DEBUT_EXTRACTION
        defaultChampsRechercheShouldNotBeFound("dateDebutExtraction.equals=" + UPDATED_DATE_DEBUT_EXTRACTION);
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByDateDebutExtractionIsInShouldWork() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where dateDebutExtraction in DEFAULT_DATE_DEBUT_EXTRACTION or UPDATED_DATE_DEBUT_EXTRACTION
        defaultChampsRechercheShouldBeFound("dateDebutExtraction.in=" + DEFAULT_DATE_DEBUT_EXTRACTION + "," + UPDATED_DATE_DEBUT_EXTRACTION);

        // Get all the champsRechercheList where dateDebutExtraction equals to UPDATED_DATE_DEBUT_EXTRACTION
        defaultChampsRechercheShouldNotBeFound("dateDebutExtraction.in=" + UPDATED_DATE_DEBUT_EXTRACTION);
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByDateDebutExtractionIsNullOrNotNull() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where dateDebutExtraction is not null
        defaultChampsRechercheShouldBeFound("dateDebutExtraction.specified=true");

        // Get all the champsRechercheList where dateDebutExtraction is null
        defaultChampsRechercheShouldNotBeFound("dateDebutExtraction.specified=false");
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByDateDebutExtractionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where dateDebutExtraction greater than or equals to DEFAULT_DATE_DEBUT_EXTRACTION
        defaultChampsRechercheShouldBeFound("dateDebutExtraction.greaterOrEqualThan=" + DEFAULT_DATE_DEBUT_EXTRACTION);

        // Get all the champsRechercheList where dateDebutExtraction greater than or equals to UPDATED_DATE_DEBUT_EXTRACTION
        defaultChampsRechercheShouldNotBeFound("dateDebutExtraction.greaterOrEqualThan=" + UPDATED_DATE_DEBUT_EXTRACTION);
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByDateDebutExtractionIsLessThanSomething() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where dateDebutExtraction less than or equals to DEFAULT_DATE_DEBUT_EXTRACTION
        defaultChampsRechercheShouldNotBeFound("dateDebutExtraction.lessThan=" + DEFAULT_DATE_DEBUT_EXTRACTION);

        // Get all the champsRechercheList where dateDebutExtraction less than or equals to UPDATED_DATE_DEBUT_EXTRACTION
        defaultChampsRechercheShouldBeFound("dateDebutExtraction.lessThan=" + UPDATED_DATE_DEBUT_EXTRACTION);
    }


    @Test
    @Transactional
    public void getAllChampsRecherchesByDateFinExtractionIsEqualToSomething() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where dateFinExtraction equals to DEFAULT_DATE_FIN_EXTRACTION
        defaultChampsRechercheShouldBeFound("dateFinExtraction.equals=" + DEFAULT_DATE_FIN_EXTRACTION);

        // Get all the champsRechercheList where dateFinExtraction equals to UPDATED_DATE_FIN_EXTRACTION
        defaultChampsRechercheShouldNotBeFound("dateFinExtraction.equals=" + UPDATED_DATE_FIN_EXTRACTION);
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByDateFinExtractionIsInShouldWork() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where dateFinExtraction in DEFAULT_DATE_FIN_EXTRACTION or UPDATED_DATE_FIN_EXTRACTION
        defaultChampsRechercheShouldBeFound("dateFinExtraction.in=" + DEFAULT_DATE_FIN_EXTRACTION + "," + UPDATED_DATE_FIN_EXTRACTION);

        // Get all the champsRechercheList where dateFinExtraction equals to UPDATED_DATE_FIN_EXTRACTION
        defaultChampsRechercheShouldNotBeFound("dateFinExtraction.in=" + UPDATED_DATE_FIN_EXTRACTION);
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByDateFinExtractionIsNullOrNotNull() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where dateFinExtraction is not null
        defaultChampsRechercheShouldBeFound("dateFinExtraction.specified=true");

        // Get all the champsRechercheList where dateFinExtraction is null
        defaultChampsRechercheShouldNotBeFound("dateFinExtraction.specified=false");
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByDateFinExtractionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where dateFinExtraction greater than or equals to DEFAULT_DATE_FIN_EXTRACTION
        defaultChampsRechercheShouldBeFound("dateFinExtraction.greaterOrEqualThan=" + DEFAULT_DATE_FIN_EXTRACTION);

        // Get all the champsRechercheList where dateFinExtraction greater than or equals to UPDATED_DATE_FIN_EXTRACTION
        defaultChampsRechercheShouldNotBeFound("dateFinExtraction.greaterOrEqualThan=" + UPDATED_DATE_FIN_EXTRACTION);
    }

    @Test
    @Transactional
    public void getAllChampsRecherchesByDateFinExtractionIsLessThanSomething() throws Exception {
        // Initialize the database
        champsRechercheRepository.saveAndFlush(champsRecherche);

        // Get all the champsRechercheList where dateFinExtraction less than or equals to DEFAULT_DATE_FIN_EXTRACTION
        defaultChampsRechercheShouldNotBeFound("dateFinExtraction.lessThan=" + DEFAULT_DATE_FIN_EXTRACTION);

        // Get all the champsRechercheList where dateFinExtraction less than or equals to UPDATED_DATE_FIN_EXTRACTION
        defaultChampsRechercheShouldBeFound("dateFinExtraction.lessThan=" + UPDATED_DATE_FIN_EXTRACTION);
    }


    @Test
    @Transactional
    public void getAllChampsRecherchesByResultatIsEqualToSomething() throws Exception {
        // Initialize the database
        Resultat resultat = ResultatResourceIntTest.createEntity(em);
        em.persist(resultat);
        em.flush();
        champsRecherche.setResultat(resultat);
        resultat.setChampsRecherche(champsRecherche);
        champsRechercheRepository.saveAndFlush(champsRecherche);
        Long resultatId = resultat.getId();

        // Get all the champsRechercheList where resultat equals to resultatId
        defaultChampsRechercheShouldBeFound("resultatId.equals=" + resultatId);

        // Get all the champsRechercheList where resultat equals to resultatId + 1
        defaultChampsRechercheShouldNotBeFound("resultatId.equals=" + (resultatId + 1));
    }


    @Test
    @Transactional
    public void getAllChampsRecherchesByColonneIsEqualToSomething() throws Exception {
        // Initialize the database
        Colonne colonne = ColonneResourceIntTest.createEntity(em);
        em.persist(colonne);
        em.flush();
        champsRecherche.setColonne(colonne);
        champsRechercheRepository.saveAndFlush(champsRecherche);
        Long colonneId = colonne.getId();

        // Get all the champsRechercheList where colonne equals to colonneId
        defaultChampsRechercheShouldBeFound("colonneId.equals=" + colonneId);

        // Get all the champsRechercheList where colonne equals to colonneId + 1
        defaultChampsRechercheShouldNotBeFound("colonneId.equals=" + (colonneId + 1));
    }


    @Test
    @Transactional
    public void getAllChampsRecherchesByEnvironnementIsEqualToSomething() throws Exception {
        // Initialize the database
        Environnement environnement = EnvironnementResourceIntTest.createEntity(em);
        em.persist(environnement);
        em.flush();
        champsRecherche.setEnvironnement(environnement);
        champsRechercheRepository.saveAndFlush(champsRecherche);
        Long environnementId = environnement.getId();

        // Get all the champsRechercheList where environnement equals to environnementId
        defaultChampsRechercheShouldBeFound("environnementId.equals=" + environnementId);

        // Get all the champsRechercheList where environnement equals to environnementId + 1
        defaultChampsRechercheShouldNotBeFound("environnementId.equals=" + (environnementId + 1));
    }


    @Test
    @Transactional
    public void getAllChampsRecherchesByFilialeIsEqualToSomething() throws Exception {
        // Initialize the database
        Filiale filiale = FilialeResourceIntTest.createEntity(em);
        em.persist(filiale);
        em.flush();
        champsRecherche.setFiliale(filiale);
        champsRechercheRepository.saveAndFlush(champsRecherche);
        Long filialeId = filiale.getId();

        // Get all the champsRechercheList where filiale equals to filialeId
        defaultChampsRechercheShouldBeFound("filialeId.equals=" + filialeId);

        // Get all the champsRechercheList where filiale equals to filialeId + 1
        defaultChampsRechercheShouldNotBeFound("filialeId.equals=" + (filialeId + 1));
    }


    @Test
    @Transactional
    public void getAllChampsRecherchesByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        champsRecherche.setRequisition(requisition);
        champsRechercheRepository.saveAndFlush(champsRecherche);
        Long requisitionId = requisition.getId();

        // Get all the champsRechercheList where requisition equals to requisitionId
        defaultChampsRechercheShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the champsRechercheList where requisition equals to requisitionId + 1
        defaultChampsRechercheShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }


    @Test
    @Transactional
    public void getAllChampsRecherchesByTypeExtractionIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeExtraction typeExtraction = TypeExtractionResourceIntTest.createEntity(em);
        em.persist(typeExtraction);
        em.flush();
        champsRecherche.setTypeExtraction(typeExtraction);
        champsRechercheRepository.saveAndFlush(champsRecherche);
        Long typeExtractionId = typeExtraction.getId();

        // Get all the champsRechercheList where typeExtraction equals to typeExtractionId
        defaultChampsRechercheShouldBeFound("typeExtractionId.equals=" + typeExtractionId);

        // Get all the champsRechercheList where typeExtraction equals to typeExtractionId + 1
        defaultChampsRechercheShouldNotBeFound("typeExtractionId.equals=" + (typeExtractionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultChampsRechercheShouldBeFound(String filter) throws Exception {
        restChampsRechercheMockMvc.perform(get("/api/champs-recherches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(champsRecherche.getId().intValue())))
            .andExpect(jsonPath("$.[*].champs").value(hasItem(DEFAULT_CHAMPS)))
            .andExpect(jsonPath("$.[*].dateDebutExtraction").value(hasItem(DEFAULT_DATE_DEBUT_EXTRACTION.toString())))
            .andExpect(jsonPath("$.[*].dateFinExtraction").value(hasItem(DEFAULT_DATE_FIN_EXTRACTION.toString())));

        // Check, that the count call also returns 1
        restChampsRechercheMockMvc.perform(get("/api/champs-recherches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultChampsRechercheShouldNotBeFound(String filter) throws Exception {
        restChampsRechercheMockMvc.perform(get("/api/champs-recherches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChampsRechercheMockMvc.perform(get("/api/champs-recherches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingChampsRecherche() throws Exception {
        // Get the champsRecherche
        restChampsRechercheMockMvc.perform(get("/api/champs-recherches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChampsRecherche() throws Exception {
        // Initialize the database
        champsRechercheService.save(champsRecherche);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockChampsRechercheSearchRepository);

        int databaseSizeBeforeUpdate = champsRechercheRepository.findAll().size();

        // Update the champsRecherche
        ChampsRecherche updatedChampsRecherche = champsRechercheRepository.findById(champsRecherche.getId()).get();
        // Disconnect from session so that the updates on updatedChampsRecherche are not directly saved in db
        em.detach(updatedChampsRecherche);
        updatedChampsRecherche
            .champs(UPDATED_CHAMPS)
            .dateDebutExtraction(UPDATED_DATE_DEBUT_EXTRACTION)
            .dateFinExtraction(UPDATED_DATE_FIN_EXTRACTION);

        restChampsRechercheMockMvc.perform(put("/api/champs-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChampsRecherche)))
            .andExpect(status().isOk());

        // Validate the ChampsRecherche in the database
        List<ChampsRecherche> champsRechercheList = champsRechercheRepository.findAll();
        assertThat(champsRechercheList).hasSize(databaseSizeBeforeUpdate);
        ChampsRecherche testChampsRecherche = champsRechercheList.get(champsRechercheList.size() - 1);
        assertThat(testChampsRecherche.getChamps()).isEqualTo(UPDATED_CHAMPS);
        assertThat(testChampsRecherche.getDateDebutExtraction()).isEqualTo(UPDATED_DATE_DEBUT_EXTRACTION);
        assertThat(testChampsRecherche.getDateFinExtraction()).isEqualTo(UPDATED_DATE_FIN_EXTRACTION);

        // Validate the ChampsRecherche in Elasticsearch
        verify(mockChampsRechercheSearchRepository, times(1)).save(testChampsRecherche);
    }

    @Test
    @Transactional
    public void updateNonExistingChampsRecherche() throws Exception {
        int databaseSizeBeforeUpdate = champsRechercheRepository.findAll().size();

        // Create the ChampsRecherche

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChampsRechercheMockMvc.perform(put("/api/champs-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champsRecherche)))
            .andExpect(status().isBadRequest());

        // Validate the ChampsRecherche in the database
        List<ChampsRecherche> champsRechercheList = champsRechercheRepository.findAll();
        assertThat(champsRechercheList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ChampsRecherche in Elasticsearch
        verify(mockChampsRechercheSearchRepository, times(0)).save(champsRecherche);
    }

    @Test
    @Transactional
    public void deleteChampsRecherche() throws Exception {
        // Initialize the database
        champsRechercheService.save(champsRecherche);

        int databaseSizeBeforeDelete = champsRechercheRepository.findAll().size();

        // Delete the champsRecherche
        restChampsRechercheMockMvc.perform(delete("/api/champs-recherches/{id}", champsRecherche.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChampsRecherche> champsRechercheList = champsRechercheRepository.findAll();
        assertThat(champsRechercheList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ChampsRecherche in Elasticsearch
        verify(mockChampsRechercheSearchRepository, times(1)).deleteById(champsRecherche.getId());
    }

    @Test
    @Transactional
    public void searchChampsRecherche() throws Exception {
        // Initialize the database
        champsRechercheService.save(champsRecherche);
        when(mockChampsRechercheSearchRepository.search(queryStringQuery("id:" + champsRecherche.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(champsRecherche), PageRequest.of(0, 1), 1));
        // Search the champsRecherche
        restChampsRechercheMockMvc.perform(get("/api/_search/champs-recherches?query=id:" + champsRecherche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(champsRecherche.getId().intValue())))
            .andExpect(jsonPath("$.[*].champs").value(hasItem(DEFAULT_CHAMPS)))
            .andExpect(jsonPath("$.[*].dateDebutExtraction").value(hasItem(DEFAULT_DATE_DEBUT_EXTRACTION.toString())))
            .andExpect(jsonPath("$.[*].dateFinExtraction").value(hasItem(DEFAULT_DATE_FIN_EXTRACTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChampsRecherche.class);
        ChampsRecherche champsRecherche1 = new ChampsRecherche();
        champsRecherche1.setId(1L);
        ChampsRecherche champsRecherche2 = new ChampsRecherche();
        champsRecherche2.setId(champsRecherche1.getId());
        assertThat(champsRecherche1).isEqualTo(champsRecherche2);
        champsRecherche2.setId(2L);
        assertThat(champsRecherche1).isNotEqualTo(champsRecherche2);
        champsRecherche1.setId(null);
        assertThat(champsRecherche1).isNotEqualTo(champsRecherche2);
    }
}
