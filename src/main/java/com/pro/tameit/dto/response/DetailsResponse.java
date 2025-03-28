package com.pro.tameit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailsResponse {
    private Long id;
    private String userName;
    private String email;
    private String imageUrl;
}
