package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BmtApp;
import com.mycompany.myapp.domain.BmtChangCi;
import com.mycompany.myapp.repository.BmtChangCiRepository;
import com.mycompany.myapp.service.BmtChangCiService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BmtChangCiResource} REST controller.
 */
@SpringBootTest(classes = BmtApp.class)
public class BmtChangCiResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEEK_DAY = "AAAAAAAAAA";
    private static final String UPDATED_WEEK_DAY = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIME_BEGIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_BEGIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TIME_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private BmtChangCiRepository bmtChangCiRepository;

    @Autowired
    private BmtChangCiService bmtChangCiService;

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

    private MockMvc restBmtChangCiMockMvc;

    private BmtChangCi bmtChangCi;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BmtChangCiResource bmtChangCiResource = new BmtChangCiResource(bmtChangCiService);
        this.restBmtChangCiMockMvc = MockMvcBuilders.standaloneSetup(bmtChangCiResource)
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
    public static BmtChangCi createEntity(EntityManager em) {
        BmtChangCi bmtChangCi = new BmtChangCi()
            .name(DEFAULT_NAME)
            .weekDay(DEFAULT_WEEK_DAY)
            .timeBegin(DEFAULT_TIME_BEGIN)
            .timeEnd(DEFAULT_TIME_END)
            .owner(DEFAULT_OWNER)
            .userId(DEFAULT_USER_ID);
        return bmtChangCi;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BmtChangCi createUpdatedEntity(EntityManager em) {
        BmtChangCi bmtChangCi = new BmtChangCi()
            .name(UPDATED_NAME)
            .weekDay(UPDATED_WEEK_DAY)
            .timeBegin(UPDATED_TIME_BEGIN)
            .timeEnd(UPDATED_TIME_END)
            .owner(UPDATED_OWNER)
            .userId(UPDATED_USER_ID);
        return bmtChangCi;
    }

    @BeforeEach
    public void initTest() {
        bmtChangCi = createEntity(em);
    }

    @Test
    @Transactional
    public void createBmtChangCi() throws Exception {
        int databaseSizeBeforeCreate = bmtChangCiRepository.findAll().size();

        // Create the BmtChangCi
        restBmtChangCiMockMvc.perform(post("/api/bmt-chang-cis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bmtChangCi)))
            .andExpect(status().isCreated());

        // Validate the BmtChangCi in the database
        List<BmtChangCi> bmtChangCiList = bmtChangCiRepository.findAll();
        assertThat(bmtChangCiList).hasSize(databaseSizeBeforeCreate + 1);
        BmtChangCi testBmtChangCi = bmtChangCiList.get(bmtChangCiList.size() - 1);
        assertThat(testBmtChangCi.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBmtChangCi.getWeekDay()).isEqualTo(DEFAULT_WEEK_DAY);
        assertThat(testBmtChangCi.getTimeBegin()).isEqualTo(DEFAULT_TIME_BEGIN);
        assertThat(testBmtChangCi.getTimeEnd()).isEqualTo(DEFAULT_TIME_END);
        assertThat(testBmtChangCi.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testBmtChangCi.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createBmtChangCiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bmtChangCiRepository.findAll().size();

        // Create the BmtChangCi with an existing ID
        bmtChangCi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBmtChangCiMockMvc.perform(post("/api/bmt-chang-cis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bmtChangCi)))
            .andExpect(status().isBadRequest());

        // Validate the BmtChangCi in the database
        List<BmtChangCi> bmtChangCiList = bmtChangCiRepository.findAll();
        assertThat(bmtChangCiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBmtChangCis() throws Exception {
        // Initialize the database
        bmtChangCiRepository.saveAndFlush(bmtChangCi);

        // Get all the bmtChangCiList
        restBmtChangCiMockMvc.perform(get("/api/bmt-chang-cis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bmtChangCi.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].weekDay").value(hasItem(DEFAULT_WEEK_DAY)))
            .andExpect(jsonPath("$.[*].timeBegin").value(hasItem(DEFAULT_TIME_BEGIN.toString())))
            .andExpect(jsonPath("$.[*].timeEnd").value(hasItem(DEFAULT_TIME_END.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getBmtChangCi() throws Exception {
        // Initialize the database
        bmtChangCiRepository.saveAndFlush(bmtChangCi);

        // Get the bmtChangCi
        restBmtChangCiMockMvc.perform(get("/api/bmt-chang-cis/{id}", bmtChangCi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bmtChangCi.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.weekDay").value(DEFAULT_WEEK_DAY))
            .andExpect(jsonPath("$.timeBegin").value(DEFAULT_TIME_BEGIN.toString()))
            .andExpect(jsonPath("$.timeEnd").value(DEFAULT_TIME_END.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBmtChangCi() throws Exception {
        // Get the bmtChangCi
        restBmtChangCiMockMvc.perform(get("/api/bmt-chang-cis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBmtChangCi() throws Exception {
        // Initialize the database
        bmtChangCiService.save(bmtChangCi);

        int databaseSizeBeforeUpdate = bmtChangCiRepository.findAll().size();

        // Update the bmtChangCi
        BmtChangCi updatedBmtChangCi = bmtChangCiRepository.findById(bmtChangCi.getId()).get();
        // Disconnect from session so that the updates on updatedBmtChangCi are not directly saved in db
        em.detach(updatedBmtChangCi);
        updatedBmtChangCi
            .name(UPDATED_NAME)
            .weekDay(UPDATED_WEEK_DAY)
            .timeBegin(UPDATED_TIME_BEGIN)
            .timeEnd(UPDATED_TIME_END)
            .owner(UPDATED_OWNER)
            .userId(UPDATED_USER_ID);

        restBmtChangCiMockMvc.perform(put("/api/bmt-chang-cis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBmtChangCi)))
            .andExpect(status().isOk());

        // Validate the BmtChangCi in the database
        List<BmtChangCi> bmtChangCiList = bmtChangCiRepository.findAll();
        assertThat(bmtChangCiList).hasSize(databaseSizeBeforeUpdate);
        BmtChangCi testBmtChangCi = bmtChangCiList.get(bmtChangCiList.size() - 1);
        assertThat(testBmtChangCi.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBmtChangCi.getWeekDay()).isEqualTo(UPDATED_WEEK_DAY);
        assertThat(testBmtChangCi.getTimeBegin()).isEqualTo(UPDATED_TIME_BEGIN);
        assertThat(testBmtChangCi.getTimeEnd()).isEqualTo(UPDATED_TIME_END);
        assertThat(testBmtChangCi.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testBmtChangCi.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBmtChangCi() throws Exception {
        int databaseSizeBeforeUpdate = bmtChangCiRepository.findAll().size();

        // Create the BmtChangCi

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBmtChangCiMockMvc.perform(put("/api/bmt-chang-cis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bmtChangCi)))
            .andExpect(status().isBadRequest());

        // Validate the BmtChangCi in the database
        List<BmtChangCi> bmtChangCiList = bmtChangCiRepository.findAll();
        assertThat(bmtChangCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBmtChangCi() throws Exception {
        // Initialize the database
        bmtChangCiService.save(bmtChangCi);

        int databaseSizeBeforeDelete = bmtChangCiRepository.findAll().size();

        // Delete the bmtChangCi
        restBmtChangCiMockMvc.perform(delete("/api/bmt-chang-cis/{id}", bmtChangCi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BmtChangCi> bmtChangCiList = bmtChangCiRepository.findAll();
        assertThat(bmtChangCiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
