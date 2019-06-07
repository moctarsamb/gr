package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Monitor;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Flux;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operande;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Profil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ColonneRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ColonneSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ColonneService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ColonneCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ColonneQueryService;

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
 * Test class for the ColonneResource REST controller.
 *
 * @see ColonneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class ColonneResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    @Autowired
    private ColonneRepository colonneRepository;

    @Mock
    private ColonneRepository colonneRepositoryMock;

    @Mock
    private ColonneService colonneServiceMock;

    @Autowired
    private ColonneService colonneService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ColonneSearchRepositoryMockConfiguration
     */
    @Autowired
    private ColonneSearchRepository mockColonneSearchRepository;

    @Autowired
    private ColonneQueryService colonneQueryService;

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

    private MockMvc restColonneMockMvc;

    private Colonne colonne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColonneResource colonneResource = new ColonneResource(colonneService, colonneQueryService);
        this.restColonneMockMvc = MockMvcBuilders.standaloneSetup(colonneResource)
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
    public static Colonne createEntity(EntityManager em) {
        Colonne colonne = new Colonne()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .alias(DEFAULT_ALIAS);
        return colonne;
    }

    @Before
    public void initTest() {
        colonne = createEntity(em);
    }

    @Test
    @Transactional
    public void createColonne() throws Exception {
        int databaseSizeBeforeCreate = colonneRepository.findAll().size();

        // Create the Colonne
        restColonneMockMvc.perform(post("/api/colonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colonne)))
            .andExpect(status().isCreated());

        // Validate the Colonne in the database
        List<Colonne> colonneList = colonneRepository.findAll();
        assertThat(colonneList).hasSize(databaseSizeBeforeCreate + 1);
        Colonne testColonne = colonneList.get(colonneList.size() - 1);
        assertThat(testColonne.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testColonne.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testColonne.getAlias()).isEqualTo(DEFAULT_ALIAS);

        // Validate the Colonne in Elasticsearch
        verify(mockColonneSearchRepository, times(1)).save(testColonne);
    }

    @Test
    @Transactional
    public void createColonneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = colonneRepository.findAll().size();

        // Create the Colonne with an existing ID
        colonne.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColonneMockMvc.perform(post("/api/colonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colonne)))
            .andExpect(status().isBadRequest());

        // Validate the Colonne in the database
        List<Colonne> colonneList = colonneRepository.findAll();
        assertThat(colonneList).hasSize(databaseSizeBeforeCreate);

        // Validate the Colonne in Elasticsearch
        verify(mockColonneSearchRepository, times(0)).save(colonne);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = colonneRepository.findAll().size();
        // set the field null
        colonne.setLibelle(null);

        // Create the Colonne, which fails.

        restColonneMockMvc.perform(post("/api/colonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colonne)))
            .andExpect(status().isBadRequest());

        List<Colonne> colonneList = colonneRepository.findAll();
        assertThat(colonneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColonnes() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get all the colonneList
        restColonneMockMvc.perform(get("/api/colonnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colonne.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllColonnesWithEagerRelationshipsIsEnabled() throws Exception {
        ColonneResource colonneResource = new ColonneResource(colonneServiceMock, colonneQueryService);
        when(colonneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restColonneMockMvc = MockMvcBuilders.standaloneSetup(colonneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restColonneMockMvc.perform(get("/api/colonnes?eagerload=true"))
        .andExpect(status().isOk());

        verify(colonneServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllColonnesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ColonneResource colonneResource = new ColonneResource(colonneServiceMock, colonneQueryService);
            when(colonneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restColonneMockMvc = MockMvcBuilders.standaloneSetup(colonneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restColonneMockMvc.perform(get("/api/colonnes?eagerload=true"))
        .andExpect(status().isOk());

            verify(colonneServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getColonne() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get the colonne
        restColonneMockMvc.perform(get("/api/colonnes/{id}", colonne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(colonne.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS.toString()));
    }

    @Test
    @Transactional
    public void getAllColonnesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get all the colonneList where libelle equals to DEFAULT_LIBELLE
        defaultColonneShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the colonneList where libelle equals to UPDATED_LIBELLE
        defaultColonneShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllColonnesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get all the colonneList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultColonneShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the colonneList where libelle equals to UPDATED_LIBELLE
        defaultColonneShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllColonnesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get all the colonneList where libelle is not null
        defaultColonneShouldBeFound("libelle.specified=true");

        // Get all the colonneList where libelle is null
        defaultColonneShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    public void getAllColonnesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get all the colonneList where description equals to DEFAULT_DESCRIPTION
        defaultColonneShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the colonneList where description equals to UPDATED_DESCRIPTION
        defaultColonneShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllColonnesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get all the colonneList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultColonneShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the colonneList where description equals to UPDATED_DESCRIPTION
        defaultColonneShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllColonnesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get all the colonneList where description is not null
        defaultColonneShouldBeFound("description.specified=true");

        // Get all the colonneList where description is null
        defaultColonneShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllColonnesByAliasIsEqualToSomething() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get all the colonneList where alias equals to DEFAULT_ALIAS
        defaultColonneShouldBeFound("alias.equals=" + DEFAULT_ALIAS);

        // Get all the colonneList where alias equals to UPDATED_ALIAS
        defaultColonneShouldNotBeFound("alias.equals=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    public void getAllColonnesByAliasIsInShouldWork() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get all the colonneList where alias in DEFAULT_ALIAS or UPDATED_ALIAS
        defaultColonneShouldBeFound("alias.in=" + DEFAULT_ALIAS + "," + UPDATED_ALIAS);

        // Get all the colonneList where alias equals to UPDATED_ALIAS
        defaultColonneShouldNotBeFound("alias.in=" + UPDATED_ALIAS);
    }

    @Test
    @Transactional
    public void getAllColonnesByAliasIsNullOrNotNull() throws Exception {
        // Initialize the database
        colonneRepository.saveAndFlush(colonne);

        // Get all the colonneList where alias is not null
        defaultColonneShouldBeFound("alias.specified=true");

        // Get all the colonneList where alias is null
        defaultColonneShouldNotBeFound("alias.specified=false");
    }

    @Test
    @Transactional
    public void getAllColonnesByChampsRechercheIsEqualToSomething() throws Exception {
        // Initialize the database
        ChampsRecherche champsRecherche = ChampsRechercheResourceIntTest.createEntity(em);
        em.persist(champsRecherche);
        em.flush();
        colonne.addChampsRecherche(champsRecherche);
        colonneRepository.saveAndFlush(colonne);
        Long champsRechercheId = champsRecherche.getId();

        // Get all the colonneList where champsRecherche equals to champsRechercheId
        defaultColonneShouldBeFound("champsRechercheId.equals=" + champsRechercheId);

        // Get all the colonneList where champsRecherche equals to champsRechercheId + 1
        defaultColonneShouldNotBeFound("champsRechercheId.equals=" + (champsRechercheId + 1));
    }


    @Test
    @Transactional
    public void getAllColonnesByMonitorIsEqualToSomething() throws Exception {
        // Initialize the database
        Monitor monitor = MonitorResourceIntTest.createEntity(em);
        em.persist(monitor);
        em.flush();
        colonne.addMonitor(monitor);
        colonneRepository.saveAndFlush(colonne);
        Long monitorId = monitor.getId();

        // Get all the colonneList where monitor equals to monitorId
        defaultColonneShouldBeFound("monitorId.equals=" + monitorId);

        // Get all the colonneList where monitor equals to monitorId + 1
        defaultColonneShouldNotBeFound("monitorId.equals=" + (monitorId + 1));
    }


    @Test
    @Transactional
    public void getAllColonnesByTypeExtractionRequeteeIsEqualToSomething() throws Exception {
        // Initialize the database
        TypeExtraction typeExtractionRequetee = TypeExtractionResourceIntTest.createEntity(em);
        em.persist(typeExtractionRequetee);
        em.flush();
        colonne.addTypeExtractionRequetee(typeExtractionRequetee);
        colonneRepository.saveAndFlush(colonne);
        Long typeExtractionRequeteeId = typeExtractionRequetee.getId();

        // Get all the colonneList where typeExtractionRequetee equals to typeExtractionRequeteeId
        defaultColonneShouldBeFound("typeExtractionRequeteeId.equals=" + typeExtractionRequeteeId);

        // Get all the colonneList where typeExtractionRequetee equals to typeExtractionRequeteeId + 1
        defaultColonneShouldNotBeFound("typeExtractionRequeteeId.equals=" + (typeExtractionRequeteeId + 1));
    }


    @Test
    @Transactional
    public void getAllColonnesByFluxIsEqualToSomething() throws Exception {
        // Initialize the database
        Flux flux = FluxResourceIntTest.createEntity(em);
        em.persist(flux);
        em.flush();
        colonne.setFlux(flux);
        colonneRepository.saveAndFlush(colonne);
        Long fluxId = flux.getId();

        // Get all the colonneList where flux equals to fluxId
        defaultColonneShouldBeFound("fluxId.equals=" + fluxId);

        // Get all the colonneList where flux equals to fluxId + 1
        defaultColonneShouldNotBeFound("fluxId.equals=" + (fluxId + 1));
    }


    @Test
    @Transactional
    public void getAllColonnesByOperandeIsEqualToSomething() throws Exception {
        // Initialize the database
        Operande operande = OperandeResourceIntTest.createEntity(em);
        em.persist(operande);
        em.flush();
        colonne.addOperande(operande);
        colonneRepository.saveAndFlush(colonne);
        Long operandeId = operande.getId();

        // Get all the colonneList where operande equals to operandeId
        defaultColonneShouldBeFound("operandeId.equals=" + operandeId);

        // Get all the colonneList where operande equals to operandeId + 1
        defaultColonneShouldNotBeFound("operandeId.equals=" + (operandeId + 1));
    }


    @Test
    @Transactional
    public void getAllColonnesByProfilIsEqualToSomething() throws Exception {
        // Initialize the database
        Profil profil = ProfilResourceIntTest.createEntity(em);
        em.persist(profil);
        em.flush();
        colonne.addProfil(profil);
        colonneRepository.saveAndFlush(colonne);
        Long profilId = profil.getId();

        // Get all the colonneList where profil equals to profilId
        defaultColonneShouldBeFound("profilId.equals=" + profilId);

        // Get all the colonneList where profil equals to profilId + 1
        defaultColonneShouldNotBeFound("profilId.equals=" + (profilId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultColonneShouldBeFound(String filter) throws Exception {
        restColonneMockMvc.perform(get("/api/colonnes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colonne.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)));

        // Check, that the count call also returns 1
        restColonneMockMvc.perform(get("/api/colonnes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultColonneShouldNotBeFound(String filter) throws Exception {
        restColonneMockMvc.perform(get("/api/colonnes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restColonneMockMvc.perform(get("/api/colonnes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingColonne() throws Exception {
        // Get the colonne
        restColonneMockMvc.perform(get("/api/colonnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColonne() throws Exception {
        // Initialize the database
        colonneService.save(colonne);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockColonneSearchRepository);

        int databaseSizeBeforeUpdate = colonneRepository.findAll().size();

        // Update the colonne
        Colonne updatedColonne = colonneRepository.findById(colonne.getId()).get();
        // Disconnect from session so that the updates on updatedColonne are not directly saved in db
        em.detach(updatedColonne);
        updatedColonne
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .alias(UPDATED_ALIAS);

        restColonneMockMvc.perform(put("/api/colonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedColonne)))
            .andExpect(status().isOk());

        // Validate the Colonne in the database
        List<Colonne> colonneList = colonneRepository.findAll();
        assertThat(colonneList).hasSize(databaseSizeBeforeUpdate);
        Colonne testColonne = colonneList.get(colonneList.size() - 1);
        assertThat(testColonne.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testColonne.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testColonne.getAlias()).isEqualTo(UPDATED_ALIAS);

        // Validate the Colonne in Elasticsearch
        verify(mockColonneSearchRepository, times(1)).save(testColonne);
    }

    @Test
    @Transactional
    public void updateNonExistingColonne() throws Exception {
        int databaseSizeBeforeUpdate = colonneRepository.findAll().size();

        // Create the Colonne

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColonneMockMvc.perform(put("/api/colonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colonne)))
            .andExpect(status().isBadRequest());

        // Validate the Colonne in the database
        List<Colonne> colonneList = colonneRepository.findAll();
        assertThat(colonneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Colonne in Elasticsearch
        verify(mockColonneSearchRepository, times(0)).save(colonne);
    }

    @Test
    @Transactional
    public void deleteColonne() throws Exception {
        // Initialize the database
        colonneService.save(colonne);

        int databaseSizeBeforeDelete = colonneRepository.findAll().size();

        // Delete the colonne
        restColonneMockMvc.perform(delete("/api/colonnes/{id}", colonne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Colonne> colonneList = colonneRepository.findAll();
        assertThat(colonneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Colonne in Elasticsearch
        verify(mockColonneSearchRepository, times(1)).deleteById(colonne.getId());
    }

    @Test
    @Transactional
    public void searchColonne() throws Exception {
        // Initialize the database
        colonneService.save(colonne);
        when(mockColonneSearchRepository.search(queryStringQuery("id:" + colonne.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(colonne), PageRequest.of(0, 1), 1));
        // Search the colonne
        restColonneMockMvc.perform(get("/api/_search/colonnes?query=id:" + colonne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colonne.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Colonne.class);
        Colonne colonne1 = new Colonne();
        colonne1.setId(1L);
        Colonne colonne2 = new Colonne();
        colonne2.setId(colonne1.getId());
        assertThat(colonne1).isEqualTo(colonne2);
        colonne2.setId(2L);
        assertThat(colonne1).isNotEqualTo(colonne2);
        colonne1.setId(null);
        assertThat(colonne1).isNotEqualTo(colonne2);
    }
}
