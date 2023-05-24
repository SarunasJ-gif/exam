package com.dbg.api.games.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublisherDto implements Serializable {

  private UUID id;
  @Size(min = 3, message = "Title must have at least 3 characters")
  private String title;
}
