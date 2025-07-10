package com.table2table.service;

import com.table2table.dto.CommunityDto;
import com.table2table.dto.CreateCommunityRequest;
import com.table2table.dto.UpdateCommunityRequest;

import java.util.List;

public interface CommunityService {
    CommunityDto createCommunity(CreateCommunityRequest request);
    List<CommunityDto> getAllCommunities();
    CommunityDto getCommunityById(Long id);
    CommunityDto updateCommunity(Long id, UpdateCommunityRequest request);
    void deleteCommunity(Long id);
}