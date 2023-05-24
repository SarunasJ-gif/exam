package com.dbg.api.games.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Publisher implements Serializable {

  private UUID id;
  private String title;

  public Publisher() {
    this.id = UUID.randomUUID();
  }
}
