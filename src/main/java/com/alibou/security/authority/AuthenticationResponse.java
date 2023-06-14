package com.alibou.security.authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  private String token;

  private String refreshToken;

  private String responseText;

  public AuthenticationResponse(String responseText) {
    this.responseText = responseText;
  }
}
