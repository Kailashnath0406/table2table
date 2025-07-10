package com.table2table.dto;

import lombok.Data;

@Data
public class UpdateCommunityRequest {
    private String name;
    private String location;
    private Integer totalFlats;
}
