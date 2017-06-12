package com.pharma.service.impl;

import com.pharma.service.VendorService;
import com.pharma.domain.Vendor;
import com.pharma.repository.VendorRepository;
import com.pharma.service.dto.VendorDTO;
import com.pharma.service.mapper.VendorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Vendor.
 */
@Service
public class VendorServiceImpl implements VendorService{

    private final Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    /**
     * Save a vendor.
     *
     * @param vendorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VendorDTO save(VendorDTO vendorDTO) {
        log.debug("Request to save Vendor : {}", vendorDTO);
        Vendor vendor = vendorMapper.toEntity(vendorDTO);
        vendor = vendorRepository.save(vendor);
        return vendorMapper.toDto(vendor);
    }

    /**
     *  Get all the vendors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<VendorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vendors");
        return vendorRepository.findAll(pageable)
            .map(vendorMapper::toDto);
    }

    /**
     *  Get one vendor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public VendorDTO findOne(String id) {
        log.debug("Request to get Vendor : {}", id);
        Vendor vendor = vendorRepository.findOne(id);
        return vendorMapper.toDto(vendor);
    }

    /**
     *  Delete the  vendor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Vendor : {}", id);
        vendorRepository.delete(id);
    }
}
