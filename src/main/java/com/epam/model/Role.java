package com.epam.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Role {

  @Id
  private String id;

  @Indexed(unique = true)
  private String name;

  public Role(String name) {
    this.name = name;
  }

}