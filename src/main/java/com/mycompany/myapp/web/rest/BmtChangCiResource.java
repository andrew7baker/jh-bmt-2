package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BmtChangCi;
import com.mycompany.myapp.service.BmtChangCiService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BmtChangCi}.
 */
@RestController
@RequestMapping("/api")
public class BmtChangCiResource {

    private final Logger log = LoggerFactory.getLogger(BmtChangCiResource.class);

    private static final String ENTITY_NAME = "bmtBmtChangCi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BmtChangCiService bmtChangCiService;

    public BmtChangCiResource(BmtChangCiService bmtChangCiService) {
        this.bmtChangCiService = bmtChangCiService;
    }

    /**
     * {@code POST  /bmt-chang-cis} : Create a new bmtChangCi.
     *
     * @param bmtChangCi the bmtChangCi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bmtChangCi, or with status {@code 400 (Bad Request)} if the bmtChangCi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bmt-chang-cis")
    public ResponseEntity<BmtChangCi> createBmtChangCi(@RequestBody BmtChangCi bmtChangCi) throws URISyntaxException {
        log.debug("REST request to save BmtChangCi : {}", bmtChangCi);
        if (bmtChangCi.getId() != null) {
            throw new BadRequestAlertException("A new bmtChangCi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BmtChangCi result = bmtChangCiService.save(bmtChangCi);
        return ResponseEntity.created(new URI("/api/bmt-chang-cis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bmt-chang-cis} : Updates an existing bmtChangCi.
     *
     * @param bmtChangCi the bmtChangCi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bmtChangCi,
     * or with status {@code 400 (Bad Request)} if the bmtChangCi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bmtChangCi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bmt-chang-cis")
    public ResponseEntity<BmtChangCi> updateBmtChangCi(@RequestBody BmtChangCi bmtChangCi) throws URISyntaxException {
        log.debug("REST request to update BmtChangCi : {}", bmtChangCi);
        if (bmtChangCi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BmtChangCi result = bmtChangCiService.save(bmtChangCi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bmtChangCi.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bmt-chang-cis} : get all the bmtChangCis.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bmtChangCis in body.
     */
    @GetMapping("/bmt-chang-cis")
    public ResponseEntity<List<BmtChangCi>> getAllBmtChangCis(Pageable pageable) {
        log.debug("REST request to get a page of BmtChangCis");
        Page<BmtChangCi> page = bmtChangCiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bmt-chang-cis/:id} : get the "id" bmtChangCi.
     *
     * @param id the id of the bmtChangCi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bmtChangCi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bmt-chang-cis/{id}")
    public ResponseEntity<BmtChangCi> getBmtChangCi(@PathVariable Long id) {
        log.debug("REST request to get BmtChangCi : {}", id);
        Optional<BmtChangCi> bmtChangCi = bmtChangCiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bmtChangCi);
    }

    /**
     * {@code DELETE  /bmt-chang-cis/:id} : delete the "id" bmtChangCi.
     *
     * @param id the id of the bmtChangCi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bmt-chang-cis/{id}")
    public ResponseEntity<Void> deleteBmtChangCi(@PathVariable Long id) {
        log.debug("REST request to delete BmtChangCi : {}", id);
        bmtChangCiService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
