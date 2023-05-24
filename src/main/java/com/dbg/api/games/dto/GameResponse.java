package com.dbg.api.games.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public class GameResponse implements Serializable {

    private UUID id;
    private String title;
    private String publisher;
    private int salesCount;
    private int likes;
  }

