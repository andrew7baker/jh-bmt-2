package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BmtPayRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BmtPayRecord}.
 */
public interface BmtPayRecordService {

    /**
     * Save a bmtPayRecord.
     *
     * @param bmtPayRecord the entity to save.
     * @return the persisted entity.
     */
    BmtPayRecord save(BmtPayRecord bmtPayRecord);

    /**
     * Get all the bmtPayRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BmtPayRecord> findAll(Pageable pageable);


    /**
     * Get the "id" bmtPayRecord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BmtPayRecord> findOne(Long id);

    /**
     * Delete the "id" bmtPayRecord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
