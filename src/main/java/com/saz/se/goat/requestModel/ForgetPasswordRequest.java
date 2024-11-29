package com.saz.se.goat.requestModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordRequest
{
    private String email;
    private String password;
}


