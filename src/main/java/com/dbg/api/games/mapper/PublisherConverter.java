package com.dbg.api.games.mapper;

import com.dbg.api.games.entity.Publisher;
import com.dbg.api.games.dto.PublisherDto;

public class PublisherConverter {

  public static PublisherDto converterToPublisherDto(Publisher publisher) {
    PublisherDto publisherDto = new PublisherDto();
    publisherDto.setId(publisher.getId());
    publisherDto.setTitle(publisher.getTitle());
    return publisherDto;
  }

  public static Publisher converterToPublisher(PublisherDto publisherDto) {
    Publisher publisher = new Publisher();
    publisher.setTitle(publisherDto.getTitle());
    return publisher;
  }
}
