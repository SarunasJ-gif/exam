package com.dbg.api.games.repository;

import com.dbg.api.games.entity.Game;
import com.dbg.api.games.entity.Publisher;
import com.dbg.api.games.entity.User;
import com.dbg.api.games.repositoryMyBatis.GameRepositoryMyBatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class GameRepository {

  @Autowired
  private GameRepositoryMyBatis gameRepositoryMyBatis;

  public void addPublisher(Publisher publisher) {
    gameRepositoryMyBatis.addPublisher(publisher);
  }

  public Publisher getPublisherById(UUID id) {
    return gameRepositoryMyBatis.getPublisherById(id);
  }

  public void saveGame(Game game) {
    gameRepositoryMyBatis.addGame(game);
  }

  public List<Game> getGamesByPublisherId(UUID publisherId) {
    return gameRepositoryMyBatis.getAllGamesByPublisherId(publisherId);
  }

  public Game getGamesById(UUID id) {
    return gameRepositoryMyBatis.getGameById(id);
  }

  public void saveAllLikes(UUID gameId, UUID userId) {
    gameRepositoryMyBatis.saveLikes(gameId, userId);
  }

  public void removeLikes(UUID gameId, UUID userId) {
    gameRepositoryMyBatis.removeFromLikes(gameId, userId);
  }

  public UUID getGameIdFromLikes(UUID gameId, UUID userId) {
    return gameRepositoryMyBatis.getGameIdFromLikes(gameId, userId);
  }

  public void updateGameLikes(Game game) {
    gameRepositoryMyBatis.updateGameLikes(game);
  }

  public User getUserById(UUID id) {
    return gameRepositoryMyBatis.getUserById(id);
  }

  public List<Game> getTop3GamesByPublisherId(UUID publisherId) {
    return gameRepositoryMyBatis.getTop3GamesByPublisherId(publisherId);
  }

  public void saveUser(User user) {
    gameRepositoryMyBatis.saveUser(user);
  }

  public User getUserByName(String name) {
    return gameRepositoryMyBatis.getUserByName(name);
  }

  public void addColumnLikes() {
    gameRepositoryMyBatis.addColumnLikes();
  }

  public void addColumnPassword() {
    gameRepositoryMyBatis.addColumnPassword();
  }
}
