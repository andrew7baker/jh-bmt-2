package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BmtPayRecord;
import com.mycompany.myapp.service.BmtPayRecordService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.BmtPayRecord}.
 */
@RestController
@RequestMapping("/api")
public class BmtPayRecordResource {

    private final Logger log = LoggerFactory.getLogger(BmtPayRecordResource.class);

    private static final String ENTITY_NAME = "bmtBmtPayRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BmtPayRecordService bmtPayRecordService;

    public BmtPayRecordResource(BmtPayRecordService bmtPayRecordService) {
        this.bmtPayRecordService = bmtPayRecordService;
    }

    /**
     * {@code POST  /bmt-pay-records} : Create a new bmtPayRecord.
     *
     * @param bmtPayRecord the bmtPayRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bmtPayRecord, or with status {@code 400 (Bad Request)} if the bmtPayRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bmt-pay-records")
    public ResponseEntity<BmtPayRecord> createBmtPayRecord(@RequestBody BmtPayRecord bmtPayRecord) throws URISyntaxException {
        log.debug("REST request to save BmtPayRecord : {}", bmtPayRecord);
        if (bmtPayRecord.getId() != null) {
            throw new BadRequestAlertException("A new bmtPayRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BmtPayRecord result = bmtPayRecordService.save(bmtPayRecord);
        return ResponseEntity.created(new URI("/api/bmt-pay-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bmt-pay-records} : Updates an existing bmtPayRecord.
     *
     * @param bmtPayRecord the bmtPayRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bmtPayRecord,
     * or with status {@code 400 (Bad Request)} if the bmtPayRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bmtPayRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bmt-pay-records")
    public ResponseEntity<BmtPayRecord> updateBmtPayRecord(@RequestBody BmtPayRecord bmtPayRecord) throws URISyntaxException {
        log.debug("REST request to update BmtPayRecord : {}", bmtPayRecord);
        if (bmtPayRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BmtPayRecord result = bmtPayRecordService.save(bmtPayRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bmtPayRecord.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bmt-pay-records} : get all the bmtPayRecords.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bmtPayRecords in body.
     */
    @GetMapping("/bmt-pay-records")
    public ResponseEntity<List<BmtPayRecord>> getAllBmtPayRecords(Pageable pageable) {
        log.debug("REST request to get a page of BmtPayRecords");
        Page<BmtPayRecord> page = bmtPayRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bmt-pay-records/:id} : get the "id" bmtPayRecord.
     *
     * @param id the id of the bmtPayRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bmtPayRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bmt-pay-records/{id}")
    public ResponseEntity<BmtPayRecord> getBmtPayRecord(@PathVariable Long id) {
        log.debug("REST request to get BmtPayRecord : {}", id);
        Optional<BmtPayRecord> bmtPayRecord = bmtPayRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bmtPayRecord);
    }

    /**
     * {@code DELETE  /bmt-pay-records/:id} : delete the "id" bmtPayRecord.
     *
     * @param id the id of the bmtPayRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bmt-pay-records/{id}")
    public ResponseEntity<Void> deleteBmtPayRecord(@PathVariable Long id) {
        log.debug("REST request to delete BmtPayRecord : {}", id);
        bmtPayRecordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
