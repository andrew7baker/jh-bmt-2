package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BmtChangCiService;
import com.mycompany.myapp.domain.BmtChangCi;
import com.mycompany.myapp.repository.BmtChangCiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BmtChangCi}.
 */
@Service
@Transactional
public class BmtChangCiServiceImpl implements BmtChangCiService {

    private final Logger log = LoggerFactory.getLogger(BmtChangCiServiceImpl.class);

    private final BmtChangCiRepository bmtChangCiRepository;

    public BmtChangCiServiceImpl(BmtChangCiRepository bmtChangCiRepository) {
        this.bmtChangCiRepository = bmtChangCiRepository;
    }

    /**
     * Save a bmtChangCi.
     *
     * @param bmtChangCi the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BmtChangCi save(BmtChangCi bmtChangCi) {
        log.debug("Request to save BmtChangCi : {}", bmtChangCi);
        return bmtChangCiRepository.save(bmtChangCi);
    }

    /**
     * Get all the bmtChangCis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BmtChangCi> findAll(Pageable pageable) {
        log.debug("Request to get all BmtChangCis");
        return bmtChangCiRepository.findAll(pageable);
    }


    /**
     * Get one bmtChangCi by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BmtChangCi> findOne(Long id) {
        log.debug("Request to get BmtChangCi : {}", id);
        return bmtChangCiRepository.findById(id);
    }

    /**
     * Delete the bmtChangCi by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BmtChangCi : {}", id);
        bmtChangCiRepository.deleteById(id);
    }
}
