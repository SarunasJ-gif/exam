package com.dbg.api.games.service;

import com.dbg.api.games.dto.*;
import com.dbg.api.games.entity.Game;
import com.dbg.api.games.entity.Publisher;
import com.dbg.api.games.entity.User;
import com.dbg.api.games.exception.GameNotExistException;
import com.dbg.api.games.mapper.GameConverter;
import com.dbg.api.games.mapper.PublisherConverter;
import com.dbg.api.games.mapper.UserConverter;
import com.dbg.api.games.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {

  @Autowired
  private GameRepository gameRepository;

  public PublisherDto addPublisher(PublisherDto publisherDto) {
    Publisher publisher = PublisherConverter.converterToPublisher(publisherDto);
    gameRepository.addPublisher(publisher);
    return PublisherConverter.converterToPublisherDto(publisher);
  }

  public GameResponse addGame(GameDto gameDto) {
    gameRepository.addColumnLikes();
    Game game = GameConverter.convertToGame(gameDto);
    gameRepository.saveGame(game);
    GameResponse gameResponse = GameConverter.convertToGameResponse(game);
    gameResponse.setPublisher(getPublisherTitle(game.getPublisherId()));
    return gameResponse;
  }

  public GameResponse getGameById(UUID id) {
    gameRepository.addColumnLikes();
    Optional<Game> optionalGame = Optional.ofNullable(gameRepository.getGamesById(id));
    if (optionalGame.isEmpty()) {
      return null;
    }
    Game game = optionalGame.get();
    GameResponse gameResponse = GameConverter.convertToGameResponse(game);
    gameResponse.setPublisher(getPublisherTitle(game.getPublisherId()));
    return gameResponse;
  }



  public List<GameResponse> getAllGamesByPublisherId(UUID publisherId) {
    gameRepository.addColumnLikes();
    List<Game> games = gameRepository.getGamesByPublisherId(publisherId);
    List<GameResponse> gameResponses = new ArrayList<>();
    for (Game game : games) {
      GameResponse gameResponse = GameConverter.convertToGameResponse(game);
      gameResponse.setPublisher(getPublisherTitle(game.getPublisherId()));
      gameResponses.add(gameResponse);
    }
    return gameResponses;
  }

  public GameResponse addLikeToGame(UUID gameId, UUID userId) {
    gameRepository.addColumnLikes();
    Optional<Game> optionalGame = Optional.ofNullable(gameRepository.getGamesById(gameId));
    if (optionalGame.isEmpty()) {
      throw new GameNotExistException("Such game doesn't exist");
    }
    UUID idForGame = getGameId(gameId, userId);
    if (idForGame != null) {
      return GameConverter.convertToGameResponse(optionalGame.get());
    }
    gameRepository.saveAllLikes(userId, gameId);
    Game game = optionalGame.get();
    game.addLike();
    gameRepository.updateGameLikes(game);
    GameResponse gameResponse = GameConverter.convertToGameResponse(game);
    gameResponse.setPublisher(getPublisherTitle(game.getPublisherId()));
    return gameResponse;
  }



  public GameResponse removeLikeFromGame(UUID gameId, UUID userId) {
    gameRepository.addColumnLikes();
    Optional<Game> optionalGame = Optional.ofNullable(gameRepository.getGamesById(gameId));
    if (optionalGame.isEmpty()) {
      throw new GameNotExistException("Such game doesn't exist");
    }
    UUID idForGame = getGameId(gameId, userId);
    if (idForGame == null) {
      return GameConverter.convertToGameResponse(optionalGame.get());
    }
    gameRepository.removeLikes(gameId, userId);
    Game game = optionalGame.get();
    game.removeLike();
    gameRepository.updateGameLikes(game);
    GameResponse gameResponse = GameConverter.convertToGameResponse(game);
    gameResponse.setPublisher(getPublisherTitle(game.getPublisherId()));
    return gameResponse;
  }

  public Optional<User> getUserById(UUID id) {
    return Optional.ofNullable(gameRepository.getUserById(id));

  }

  private String getPublisherTitle(UUID id) {
    Optional<Publisher> optionalPublisher = Optional.ofNullable(gameRepository.getPublisherById(id));
    if (optionalPublisher.isPresent()) {
      return optionalPublisher.get().getTitle();
    }
    return "";
  }

  private UUID getGameId(UUID gameId, UUID userId) {
    gameRepository.addColumnLikes();
    return gameRepository.getGameIdFromLikes(gameId, userId);
  }


  public Iterable<GameResponse> getTop3Games(UUID publisherId) {
    gameRepository.addColumnLikes();
    List<Game> games = gameRepository.getTop3GamesByPublisherId(publisherId);
    List<GameResponse> gameResponses = new ArrayList<>();
    for (Game game : games) {
      GameResponse gameResponse = GameConverter.convertToGameResponse(game);
      gameResponse.setPublisher(getPublisherTitle(game.getPublisherId()));
      gameResponses.add(gameResponse);
    }
    return gameResponses;
  }

  public UserDto saveUser(UserRegister userRegister) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = passwordEncoder.encode(userRegister.getPassword());
    User user = UserConverter.convertToUserFromRegister(userRegister);
    user.setPassword(hashedPassword);
    gameRepository.saveUser(user);
    return UserConverter.convertToUserDto(user);
  }

  public User getUserByName(String name) {
    return gameRepository.getUserByName(name);
  }

  public void addColumnPassword() {
    gameRepository.addColumnPassword();
  }

}
