package com.example.springtest.dto.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApproveOrderRequest {

    private List<String> orderIds;

}
