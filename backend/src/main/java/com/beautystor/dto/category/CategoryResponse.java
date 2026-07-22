package com.beautystor.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private long id;

    private String name;

    private String slug;

    private Long parentId;

    private Boolean active;
}
