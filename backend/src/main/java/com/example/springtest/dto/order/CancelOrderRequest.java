package com.example.springtest.dto.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelOrderRequest {
    private List<String> orderIds;
}
