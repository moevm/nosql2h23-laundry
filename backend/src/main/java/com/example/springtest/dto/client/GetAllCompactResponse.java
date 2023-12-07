package com.example.springtest.dto.client;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCompactResponse {
    private List<String> clients;
}
