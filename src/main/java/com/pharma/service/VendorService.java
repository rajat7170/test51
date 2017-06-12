package com.pharma.service;

import com.pharma.service.dto.VendorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Vendor.
 */
public interface VendorService {

    /**
     * Save a vendor.
     *
     * @param vendorDTO the entity to save
     * @return the persisted entity
     */
    VendorDTO save(VendorDTO vendorDTO);

    /**
     *  Get all the vendors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VendorDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" vendor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VendorDTO findOne(String id);

    /**
     *  Delete the "id" vendor.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
