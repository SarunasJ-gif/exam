package com.dbg.api.games.controller;

import com.dbg.api.games.dto.ErrorDto;
import com.dbg.api.games.dto.GameDto;
import com.dbg.api.games.dto.GameResponse;
import com.dbg.api.games.dto.PublisherDto;
import com.dbg.api.games.entity.User;
import com.dbg.api.games.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/game")
@Validated
public class GameController {

  @Autowired
  private GameService gameService;

  @PostMapping("/publisher")
  public ResponseEntity<PublisherDto> savePublisher(@RequestBody @Valid PublisherDto publisherDto) {
    PublisherDto addedPublisher = gameService.addPublisher(publisherDto);
    return new ResponseEntity<>(addedPublisher, HttpStatus.OK);
  }

  @PostMapping()
  public ResponseEntity<GameResponse> saveGame(@RequestBody @Valid GameDto gameDto) {
    GameResponse addedGame = gameService.addGame(gameDto);
    return new ResponseEntity<>(addedGame, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<GameResponse> getGameById(@PathVariable UUID id) {
    GameResponse gameResponse = gameService.getGameById(id);
    if (gameResponse != null) {
      return new ResponseEntity<>(gameResponse, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/publisher/{publisherId}")
  public Iterable<GameResponse> getAllGamesByPublisher(@PathVariable UUID publisherId) {
    return gameService.getAllGamesByPublisherId(publisherId);
  }

  @PutMapping("/{gameId}/user/{userId}/addLike")
  public ResponseEntity<?> addLike(@PathVariable UUID gameId, @PathVariable UUID userId) {
    Optional<User> optionalUser = gameService.getUserById(userId);
    if (optionalUser.isEmpty()) {
      ErrorDto error = new ErrorDto("non-existant userId");
      return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    GameResponse gameResponse = gameService.addLikeToGame(gameId, userId);
    return new ResponseEntity<>(gameResponse, HttpStatus.OK);
  }

  @PutMapping("/{gameId}/user/{userId}/removeLike")
  public ResponseEntity<?> removeLike(@PathVariable UUID gameId, @PathVariable UUID userId) {
    Optional<User> optionalUser = gameService.getUserById(userId);
    if (optionalUser.isEmpty()) {
      ErrorDto error = new ErrorDto("non-existant userId");
      return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    GameResponse gameResponse = gameService.removeLikeFromGame(gameId, userId);
    return new ResponseEntity<>(gameResponse, HttpStatus.OK);
  }

  @GetMapping("/top3/publisher/{publisherId}")
  public Iterable<GameResponse> getTop3GamesByPublisherId(@PathVariable UUID publisherId) {
    return gameService.getTop3Games(publisherId);
  }
}
