package com.epam.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

  private Integer code;
  private String type;
  private String message;

}
