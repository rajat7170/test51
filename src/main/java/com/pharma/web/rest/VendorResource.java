package com.pharma.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pharma.service.VendorService;
import com.pharma.web.rest.util.HeaderUtil;
import com.pharma.web.rest.util.PaginationUtil;
import com.pharma.service.dto.VendorDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Vendor.
 */
@RestController
@RequestMapping("/api")
public class VendorResource {

    private final Logger log = LoggerFactory.getLogger(VendorResource.class);

    private static final String ENTITY_NAME = "vendor";

    private final VendorService vendorService;

    public VendorResource(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    /**
     * POST  /vendors : Create a new vendor.
     *
     * @param vendorDTO the vendorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vendorDTO, or with status 400 (Bad Request) if the vendor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vendors")
    @Timed
    public ResponseEntity<VendorDTO> createVendor(@Valid @RequestBody VendorDTO vendorDTO) throws URISyntaxException {
        log.debug("REST request to save Vendor : {}", vendorDTO);
        if (vendorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vendor cannot already have an ID")).body(null);
        }
        VendorDTO result = vendorService.save(vendorDTO);
        return ResponseEntity.created(new URI("/api/vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vendors : Updates an existing vendor.
     *
     * @param vendorDTO the vendorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vendorDTO,
     * or with status 400 (Bad Request) if the vendorDTO is not valid,
     * or with status 500 (Internal Server Error) if the vendorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vendors")
    @Timed
    public ResponseEntity<VendorDTO> updateVendor(@Valid @RequestBody VendorDTO vendorDTO) throws URISyntaxException {
        log.debug("REST request to update Vendor : {}", vendorDTO);
        if (vendorDTO.getId() == null) {
            return createVendor(vendorDTO);
        }
        VendorDTO result = vendorService.save(vendorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vendorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vendors : get all the vendors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vendors in body
     */
    @GetMapping("/vendors")
    @Timed
    public ResponseEntity<List<VendorDTO>> getAllVendors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Vendors");
        Page<VendorDTO> page = vendorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vendors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vendors/:id : get the "id" vendor.
     *
     * @param id the id of the vendorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vendorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vendors/{id}")
    @Timed
    public ResponseEntity<VendorDTO> getVendor(@PathVariable String id) {
        log.debug("REST request to get Vendor : {}", id);
        VendorDTO vendorDTO = vendorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vendorDTO));
    }

    /**
     * DELETE  /vendors/:id : delete the "id" vendor.
     *
     * @param id the id of the vendorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vendors/{id}")
    @Timed
    public ResponseEntity<Void> deleteVendor(@PathVariable String id) {
        log.debug("REST request to delete Vendor : {}", id);
        vendorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
