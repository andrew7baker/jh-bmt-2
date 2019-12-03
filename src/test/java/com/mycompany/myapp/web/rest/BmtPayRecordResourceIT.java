package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.BmtApp;
import com.mycompany.myapp.domain.BmtPayRecord;
import com.mycompany.myapp.repository.BmtPayRecordRepository;
import com.mycompany.myapp.service.BmtPayRecordService;
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
 * Integration tests for the {@link BmtPayRecordResource} REST controller.
 */
@SpringBootTest(classes = BmtApp.class)
public class BmtPayRecordResourceIT {

    private static final Instant DEFAULT_PAY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PAY_PERSON_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAY_PERSON_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_PERSON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAY_PERSON_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PAY_AMOUNT = 1L;
    private static final Long UPDATED_PAY_AMOUNT = 2L;

    @Autowired
    private BmtPayRecordRepository bmtPayRecordRepository;

    @Autowired
    private BmtPayRecordService bmtPayRecordService;

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

    private MockMvc restBmtPayRecordMockMvc;

    private BmtPayRecord bmtPayRecord;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BmtPayRecordResource bmtPayRecordResource = new BmtPayRecordResource(bmtPayRecordService);
        this.restBmtPayRecordMockMvc = MockMvcBuilders.standaloneSetup(bmtPayRecordResource)
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
    public static BmtPayRecord createEntity(EntityManager em) {
        BmtPayRecord bmtPayRecord = new BmtPayRecord()
            .payTime(DEFAULT_PAY_TIME)
            .payPersonId(DEFAULT_PAY_PERSON_ID)
            .payPersonName(DEFAULT_PAY_PERSON_NAME)
            .payAmount(DEFAULT_PAY_AMOUNT);
        return bmtPayRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BmtPayRecord createUpdatedEntity(EntityManager em) {
        BmtPayRecord bmtPayRecord = new BmtPayRecord()
            .payTime(UPDATED_PAY_TIME)
            .payPersonId(UPDATED_PAY_PERSON_ID)
            .payPersonName(UPDATED_PAY_PERSON_NAME)
            .payAmount(UPDATED_PAY_AMOUNT);
        return bmtPayRecord;
    }

    @BeforeEach
    public void initTest() {
        bmtPayRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createBmtPayRecord() throws Exception {
        int databaseSizeBeforeCreate = bmtPayRecordRepository.findAll().size();

        // Create the BmtPayRecord
        restBmtPayRecordMockMvc.perform(post("/api/bmt-pay-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bmtPayRecord)))
            .andExpect(status().isCreated());

        // Validate the BmtPayRecord in the database
        List<BmtPayRecord> bmtPayRecordList = bmtPayRecordRepository.findAll();
        assertThat(bmtPayRecordList).hasSize(databaseSizeBeforeCreate + 1);
        BmtPayRecord testBmtPayRecord = bmtPayRecordList.get(bmtPayRecordList.size() - 1);
        assertThat(testBmtPayRecord.getPayTime()).isEqualTo(DEFAULT_PAY_TIME);
        assertThat(testBmtPayRecord.getPayPersonId()).isEqualTo(DEFAULT_PAY_PERSON_ID);
        assertThat(testBmtPayRecord.getPayPersonName()).isEqualTo(DEFAULT_PAY_PERSON_NAME);
        assertThat(testBmtPayRecord.getPayAmount()).isEqualTo(DEFAULT_PAY_AMOUNT);
    }

    @Test
    @Transactional
    public void createBmtPayRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bmtPayRecordRepository.findAll().size();

        // Create the BmtPayRecord with an existing ID
        bmtPayRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBmtPayRecordMockMvc.perform(post("/api/bmt-pay-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bmtPayRecord)))
            .andExpect(status().isBadRequest());

        // Validate the BmtPayRecord in the database
        List<BmtPayRecord> bmtPayRecordList = bmtPayRecordRepository.findAll();
        assertThat(bmtPayRecordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBmtPayRecords() throws Exception {
        // Initialize the database
        bmtPayRecordRepository.saveAndFlush(bmtPayRecord);

        // Get all the bmtPayRecordList
        restBmtPayRecordMockMvc.perform(get("/api/bmt-pay-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bmtPayRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].payTime").value(hasItem(DEFAULT_PAY_TIME.toString())))
            .andExpect(jsonPath("$.[*].payPersonId").value(hasItem(DEFAULT_PAY_PERSON_ID)))
            .andExpect(jsonPath("$.[*].payPersonName").value(hasItem(DEFAULT_PAY_PERSON_NAME)))
            .andExpect(jsonPath("$.[*].payAmount").value(hasItem(DEFAULT_PAY_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getBmtPayRecord() throws Exception {
        // Initialize the database
        bmtPayRecordRepository.saveAndFlush(bmtPayRecord);

        // Get the bmtPayRecord
        restBmtPayRecordMockMvc.perform(get("/api/bmt-pay-records/{id}", bmtPayRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bmtPayRecord.getId().intValue()))
            .andExpect(jsonPath("$.payTime").value(DEFAULT_PAY_TIME.toString()))
            .andExpect(jsonPath("$.payPersonId").value(DEFAULT_PAY_PERSON_ID))
            .andExpect(jsonPath("$.payPersonName").value(DEFAULT_PAY_PERSON_NAME))
            .andExpect(jsonPath("$.payAmount").value(DEFAULT_PAY_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBmtPayRecord() throws Exception {
        // Get the bmtPayRecord
        restBmtPayRecordMockMvc.perform(get("/api/bmt-pay-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBmtPayRecord() throws Exception {
        // Initialize the database
        bmtPayRecordService.save(bmtPayRecord);

        int databaseSizeBeforeUpdate = bmtPayRecordRepository.findAll().size();

        // Update the bmtPayRecord
        BmtPayRecord updatedBmtPayRecord = bmtPayRecordRepository.findById(bmtPayRecord.getId()).get();
        // Disconnect from session so that the updates on updatedBmtPayRecord are not directly saved in db
        em.detach(updatedBmtPayRecord);
        updatedBmtPayRecord
            .payTime(UPDATED_PAY_TIME)
            .payPersonId(UPDATED_PAY_PERSON_ID)
            .payPersonName(UPDATED_PAY_PERSON_NAME)
            .payAmount(UPDATED_PAY_AMOUNT);

        restBmtPayRecordMockMvc.perform(put("/api/bmt-pay-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBmtPayRecord)))
            .andExpect(status().isOk());

        // Validate the BmtPayRecord in the database
        List<BmtPayRecord> bmtPayRecordList = bmtPayRecordRepository.findAll();
        assertThat(bmtPayRecordList).hasSize(databaseSizeBeforeUpdate);
        BmtPayRecord testBmtPayRecord = bmtPayRecordList.get(bmtPayRecordList.size() - 1);
        assertThat(testBmtPayRecord.getPayTime()).isEqualTo(UPDATED_PAY_TIME);
        assertThat(testBmtPayRecord.getPayPersonId()).isEqualTo(UPDATED_PAY_PERSON_ID);
        assertThat(testBmtPayRecord.getPayPersonName()).isEqualTo(UPDATED_PAY_PERSON_NAME);
        assertThat(testBmtPayRecord.getPayAmount()).isEqualTo(UPDATED_PAY_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingBmtPayRecord() throws Exception {
        int databaseSizeBeforeUpdate = bmtPayRecordRepository.findAll().size();

        // Create the BmtPayRecord

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBmtPayRecordMockMvc.perform(put("/api/bmt-pay-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bmtPayRecord)))
            .andExpect(status().isBadRequest());

        // Validate the BmtPayRecord in the database
        List<BmtPayRecord> bmtPayRecordList = bmtPayRecordRepository.findAll();
        assertThat(bmtPayRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBmtPayRecord() throws Exception {
        // Initialize the database
        bmtPayRecordService.save(bmtPayRecord);

        int databaseSizeBeforeDelete = bmtPayRecordRepository.findAll().size();

        // Delete the bmtPayRecord
        restBmtPayRecordMockMvc.perform(delete("/api/bmt-pay-records/{id}", bmtPayRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BmtPayRecord> bmtPayRecordList = bmtPayRecordRepository.findAll();
        assertThat(bmtPayRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
