package com.nithin.immutly.dto;

import com.nithin.immutly.entity.Product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class ProductDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    @Positive(message = "Price should be Positive")
    private Double price;

    @NotNull
    private Boolean availabilityStatus;
}
