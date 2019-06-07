package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.GestionrequisitionsfrontendgatewayApp;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Valeur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operande;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ValeurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ValeurSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ValeurService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.ExceptionTranslator;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ValeurCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ValeurQueryService;

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
 * Test class for the ValeurResource REST controller.
 *
 * @see ValeurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GestionrequisitionsfrontendgatewayApp.class)
public class ValeurResourceIntTest {

    private static final String DEFAULT_VALEUR = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR = "BBBBBBBBBB";

    @Autowired
    private ValeurRepository valeurRepository;

    @Autowired
    private ValeurService valeurService;

    /**
     * This repository is mocked in the sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search test package.
     *
     * @see sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ValeurSearchRepositoryMockConfiguration
     */
    @Autowired
    private ValeurSearchRepository mockValeurSearchRepository;

    @Autowired
    private ValeurQueryService valeurQueryService;

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

    private MockMvc restValeurMockMvc;

    private Valeur valeur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValeurResource valeurResource = new ValeurResource(valeurService, valeurQueryService);
        this.restValeurMockMvc = MockMvcBuilders.standaloneSetup(valeurResource)
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
    public static Valeur createEntity(EntityManager em) {
        Valeur valeur = new Valeur()
            .valeur(DEFAULT_VALEUR);
        return valeur;
    }

    @Before
    public void initTest() {
        valeur = createEntity(em);
    }

    @Test
    @Transactional
    public void createValeur() throws Exception {
        int databaseSizeBeforeCreate = valeurRepository.findAll().size();

        // Create the Valeur
        restValeurMockMvc.perform(post("/api/valeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeur)))
            .andExpect(status().isCreated());

        // Validate the Valeur in the database
        List<Valeur> valeurList = valeurRepository.findAll();
        assertThat(valeurList).hasSize(databaseSizeBeforeCreate + 1);
        Valeur testValeur = valeurList.get(valeurList.size() - 1);
        assertThat(testValeur.getValeur()).isEqualTo(DEFAULT_VALEUR);

        // Validate the Valeur in Elasticsearch
        verify(mockValeurSearchRepository, times(1)).save(testValeur);
    }

    @Test
    @Transactional
    public void createValeurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valeurRepository.findAll().size();

        // Create the Valeur with an existing ID
        valeur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValeurMockMvc.perform(post("/api/valeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeur)))
            .andExpect(status().isBadRequest());

        // Validate the Valeur in the database
        List<Valeur> valeurList = valeurRepository.findAll();
        assertThat(valeurList).hasSize(databaseSizeBeforeCreate);

        // Validate the Valeur in Elasticsearch
        verify(mockValeurSearchRepository, times(0)).save(valeur);
    }

    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = valeurRepository.findAll().size();
        // set the field null
        valeur.setValeur(null);

        // Create the Valeur, which fails.

        restValeurMockMvc.perform(post("/api/valeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeur)))
            .andExpect(status().isBadRequest());

        List<Valeur> valeurList = valeurRepository.findAll();
        assertThat(valeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValeurs() throws Exception {
        // Initialize the database
        valeurRepository.saveAndFlush(valeur);

        // Get all the valeurList
        restValeurMockMvc.perform(get("/api/valeurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())));
    }
    
    @Test
    @Transactional
    public void getValeur() throws Exception {
        // Initialize the database
        valeurRepository.saveAndFlush(valeur);

        // Get the valeur
        restValeurMockMvc.perform(get("/api/valeurs/{id}", valeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valeur.getId().intValue()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.toString()));
    }

    @Test
    @Transactional
    public void getAllValeursByValeurIsEqualToSomething() throws Exception {
        // Initialize the database
        valeurRepository.saveAndFlush(valeur);

        // Get all the valeurList where valeur equals to DEFAULT_VALEUR
        defaultValeurShouldBeFound("valeur.equals=" + DEFAULT_VALEUR);

        // Get all the valeurList where valeur equals to UPDATED_VALEUR
        defaultValeurShouldNotBeFound("valeur.equals=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllValeursByValeurIsInShouldWork() throws Exception {
        // Initialize the database
        valeurRepository.saveAndFlush(valeur);

        // Get all the valeurList where valeur in DEFAULT_VALEUR or UPDATED_VALEUR
        defaultValeurShouldBeFound("valeur.in=" + DEFAULT_VALEUR + "," + UPDATED_VALEUR);

        // Get all the valeurList where valeur equals to UPDATED_VALEUR
        defaultValeurShouldNotBeFound("valeur.in=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllValeursByValeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        valeurRepository.saveAndFlush(valeur);

        // Get all the valeurList where valeur is not null
        defaultValeurShouldBeFound("valeur.specified=true");

        // Get all the valeurList where valeur is null
        defaultValeurShouldNotBeFound("valeur.specified=false");
    }

    @Test
    @Transactional
    public void getAllValeursByOperandeIsEqualToSomething() throws Exception {
        // Initialize the database
        Operande operande = OperandeResourceIntTest.createEntity(em);
        em.persist(operande);
        em.flush();
        valeur.addOperande(operande);
        valeurRepository.saveAndFlush(valeur);
        Long operandeId = operande.getId();

        // Get all the valeurList where operande equals to operandeId
        defaultValeurShouldBeFound("operandeId.equals=" + operandeId);

        // Get all the valeurList where operande equals to operandeId + 1
        defaultValeurShouldNotBeFound("operandeId.equals=" + (operandeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultValeurShouldBeFound(String filter) throws Exception {
        restValeurMockMvc.perform(get("/api/valeurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR)));

        // Check, that the count call also returns 1
        restValeurMockMvc.perform(get("/api/valeurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultValeurShouldNotBeFound(String filter) throws Exception {
        restValeurMockMvc.perform(get("/api/valeurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restValeurMockMvc.perform(get("/api/valeurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingValeur() throws Exception {
        // Get the valeur
        restValeurMockMvc.perform(get("/api/valeurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValeur() throws Exception {
        // Initialize the database
        valeurService.save(valeur);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockValeurSearchRepository);

        int databaseSizeBeforeUpdate = valeurRepository.findAll().size();

        // Update the valeur
        Valeur updatedValeur = valeurRepository.findById(valeur.getId()).get();
        // Disconnect from session so that the updates on updatedValeur are not directly saved in db
        em.detach(updatedValeur);
        updatedValeur
            .valeur(UPDATED_VALEUR);

        restValeurMockMvc.perform(put("/api/valeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValeur)))
            .andExpect(status().isOk());

        // Validate the Valeur in the database
        List<Valeur> valeurList = valeurRepository.findAll();
        assertThat(valeurList).hasSize(databaseSizeBeforeUpdate);
        Valeur testValeur = valeurList.get(valeurList.size() - 1);
        assertThat(testValeur.getValeur()).isEqualTo(UPDATED_VALEUR);

        // Validate the Valeur in Elasticsearch
        verify(mockValeurSearchRepository, times(1)).save(testValeur);
    }

    @Test
    @Transactional
    public void updateNonExistingValeur() throws Exception {
        int databaseSizeBeforeUpdate = valeurRepository.findAll().size();

        // Create the Valeur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValeurMockMvc.perform(put("/api/valeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeur)))
            .andExpect(status().isBadRequest());

        // Validate the Valeur in the database
        List<Valeur> valeurList = valeurRepository.findAll();
        assertThat(valeurList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Valeur in Elasticsearch
        verify(mockValeurSearchRepository, times(0)).save(valeur);
    }

    @Test
    @Transactional
    public void deleteValeur() throws Exception {
        // Initialize the database
        valeurService.save(valeur);

        int databaseSizeBeforeDelete = valeurRepository.findAll().size();

        // Delete the valeur
        restValeurMockMvc.perform(delete("/api/valeurs/{id}", valeur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Valeur> valeurList = valeurRepository.findAll();
        assertThat(valeurList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Valeur in Elasticsearch
        verify(mockValeurSearchRepository, times(1)).deleteById(valeur.getId());
    }

    @Test
    @Transactional
    public void searchValeur() throws Exception {
        // Initialize the database
        valeurService.save(valeur);
        when(mockValeurSearchRepository.search(queryStringQuery("id:" + valeur.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(valeur), PageRequest.of(0, 1), 1));
        // Search the valeur
        restValeurMockMvc.perform(get("/api/_search/valeurs?query=id:" + valeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Valeur.class);
        Valeur valeur1 = new Valeur();
        valeur1.setId(1L);
        Valeur valeur2 = new Valeur();
        valeur2.setId(valeur1.getId());
        assertThat(valeur1).isEqualTo(valeur2);
        valeur2.setId(2L);
        assertThat(valeur1).isNotEqualTo(valeur2);
        valeur1.setId(null);
        assertThat(valeur1).isNotEqualTo(valeur2);
    }
}
