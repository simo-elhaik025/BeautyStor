package com.beautystor.service;

import com.beautystor.dto.brand.CreateBrandRequest;
import com.beautystor.dto.brand.UpdateBrandRequest;
import com.beautystor.dto.brand.BrandResponse;

import java.util.List;

public interface BrandService {
    BrandResponse create(CreateBrandRequest request);
    List<BrandResponse> getAll();
    BrandResponse getById(long id);
    BrandResponse update(long id, UpdateBrandRequest request);
    void delete(long id);
}
