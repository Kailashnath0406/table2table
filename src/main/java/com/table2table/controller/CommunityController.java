package com.table2table.controller;

import com.table2table.dto.CommunityDto;
import com.table2table.dto.CreateCommunityRequest;
import com.table2table.dto.UpdateCommunityRequest;
import com.table2table.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
@EnableMethodSecurity
public class CommunityController {

    private final CommunityService communityService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create")
    public ResponseEntity<CommunityDto> createCommunity(@RequestBody CreateCommunityRequest request) {
        return ResponseEntity.ok(communityService.createCommunity(request));
    }

    @GetMapping
    public ResponseEntity<List<CommunityDto>> getAllCommunities() {
        return ResponseEntity.ok(communityService.getAllCommunities());
    }

    @GetMapping("communityById/{id}")
    public ResponseEntity<CommunityDto> getCommunityById(@PathVariable Long id) {
        return ResponseEntity.ok(communityService.getCommunityById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public ResponseEntity<CommunityDto> updateCommunity(@PathVariable Long id,
                                                        @RequestBody UpdateCommunityRequest request) {
        return ResponseEntity.ok(communityService.updateCommunity(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id) {
        communityService.deleteCommunity(id);
        return ResponseEntity.noContent().build();
    }
}

