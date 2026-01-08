package com.shoppingcart.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(
        name = "addresses",
        indexes = {
                @Index(name = "idx_address_pincode", columnList = "pincode"),
                @Index(name = "idx_address_city", columnList = "city")
        }
)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 255)
    @Column(name = "street", nullable = false, length = 255)
    private String street;

    @Size(max = 100)
    @Column(name = "building_name", length = 100)
    private String buildingName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @NotBlank
    @Size(max = 100)
    @Column(name = "state", nullable = false, length = 100)
    private String state;

    @NotBlank
    @Size(max = 100)
    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @NotBlank
    @Pattern(
            regexp = "^[0-9]{6}$",
            message = "Pincode must be exactly 6 digits"
    )
    @Column(name = "pincode", nullable = false, length = 6)
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
