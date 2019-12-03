package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BmtChangCi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BmtChangCi}.
 */
public interface BmtChangCiService {

    /**
     * Save a bmtChangCi.
     *
     * @param bmtChangCi the entity to save.
     * @return the persisted entity.
     */
    BmtChangCi save(BmtChangCi bmtChangCi);

    /**
     * Get all the bmtChangCis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BmtChangCi> findAll(Pageable pageable);


    /**
     * Get the "id" bmtChangCi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BmtChangCi> findOne(Long id);

    /**
     * Delete the "id" bmtChangCi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
