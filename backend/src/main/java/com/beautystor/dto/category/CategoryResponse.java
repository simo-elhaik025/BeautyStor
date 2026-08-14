package com.beautystor.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représentation d'une catégorie.")
public class CategoryResponse {

    private long id;

    private String name;

    private String slug;

    private Long parentId;

    private Boolean active;
}
