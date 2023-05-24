package com.dbg.api.games.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class User {

  private UUID id;
  private String username;
  private String password;

  public User() {
    this.id = UUID.randomUUID();
  }
}
