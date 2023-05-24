package com.dbg.api.games.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;



@AllArgsConstructor
@Data
@Builder
public class Game {

  private UUID id;
  private String title;
  private UUID publisherId;
  private int salesCount;
  private UUID userId;
  private int likes;

  public Game() {
    this.id = UUID.randomUUID();
  }

  public void addLike() {
    this.likes += 1;
  }

  public void removeLike() {
    if (this.likes != 0) {
      this.likes -= 1;
    }
  }
}
