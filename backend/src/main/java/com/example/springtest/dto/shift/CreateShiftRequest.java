package com.example.springtest.dto.shift;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShiftRequest {

    private String employeeId;

}
