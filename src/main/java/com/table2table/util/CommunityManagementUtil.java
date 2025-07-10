package com.table2table.util;

import com.table2table.dto.CommunityDto;
import com.table2table.dto.CreateCommunityRequest;
import com.table2table.model.Community;

public class CommunityManagementUtil {
    public static CommunityDto toDto(Community c) {
        CommunityDto dto = new CommunityDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setLocation(c.getLocation());
        dto.setTotalFlats(c.getTotalFlats());
        return dto;
    }

    public static Community toEntity(CreateCommunityRequest request) {
        Community c = new Community();
        c.setName(request.getName());
        c.setLocation(request.getLocation());
        c.setTotalFlats(request.getTotalFlats());
        return c;
    }
}
