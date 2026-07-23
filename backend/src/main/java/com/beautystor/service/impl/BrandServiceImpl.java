package com.beautystor.service.impl;

import com.beautystor.dto.brand.CreateBrandRequest;
import com.beautystor.dto.brand.UpdateBrandRequest;
import com.beautystor.dto.brand.BrandResponse;
import com.beautystor.entity.Brand;
import com.beautystor.repository.BrandRepository;
import com.beautystor.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public BrandResponse create(CreateBrandRequest request) {
        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setSlug(request.getSlug());
        brand.setLogoUrl(request.getLogoUrl());

        Brand savedBrand = brandRepository.save(brand);

        return mapToBrandResponse(savedBrand);
    }

    @Override
    public List<BrandResponse> getAll() {
        return brandRepository.findAll()
                .stream()
                .map(this::mapToBrandResponse)
                .toList();
    }

    @Override
    public BrandResponse getById(long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand with ID " + id + " not found"));

        return mapToBrandResponse(brand);
    }

    @Override
    public BrandResponse update(long id, UpdateBrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand with ID " + id + " not found"));

        brand.setName(request.getName());
        brand.setSlug(request.getSlug());
        brand.setLogoUrl(request.getLogoUrl());

        Brand updatedBrand = brandRepository.save(brand);

        return mapToBrandResponse(updatedBrand);
    }

    @Override
    public void delete(long id) {
        if (!brandRepository.existsById(id)) {
            throw new IllegalArgumentException("Brand with ID " + id + " not found");
        }
        brandRepository.deleteById(id);
    }

    private BrandResponse mapToBrandResponse(Brand brand) {
        BrandResponse response = new BrandResponse();
        response.setId(brand.getId());
        response.setName(brand.getName());
        response.setSlug(brand.getSlug());
        response.setLogoUrl(brand.getLogoUrl());
        return response;
    }
}
