package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BmtPayRecordService;
import com.mycompany.myapp.domain.BmtPayRecord;
import com.mycompany.myapp.repository.BmtPayRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BmtPayRecord}.
 */
@Service
@Transactional
public class BmtPayRecordServiceImpl implements BmtPayRecordService {

    private final Logger log = LoggerFactory.getLogger(BmtPayRecordServiceImpl.class);

    private final BmtPayRecordRepository bmtPayRecordRepository;

    public BmtPayRecordServiceImpl(BmtPayRecordRepository bmtPayRecordRepository) {
        this.bmtPayRecordRepository = bmtPayRecordRepository;
    }

    /**
     * Save a bmtPayRecord.
     *
     * @param bmtPayRecord the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BmtPayRecord save(BmtPayRecord bmtPayRecord) {
        log.debug("Request to save BmtPayRecord : {}", bmtPayRecord);
        return bmtPayRecordRepository.save(bmtPayRecord);
    }

    /**
     * Get all the bmtPayRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BmtPayRecord> findAll(Pageable pageable) {
        log.debug("Request to get all BmtPayRecords");
        return bmtPayRecordRepository.findAll(pageable);
    }


    /**
     * Get one bmtPayRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BmtPayRecord> findOne(Long id) {
        log.debug("Request to get BmtPayRecord : {}", id);
        return bmtPayRecordRepository.findById(id);
    }

    /**
     * Delete the bmtPayRecord by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BmtPayRecord : {}", id);
        bmtPayRecordRepository.deleteById(id);
    }
}
