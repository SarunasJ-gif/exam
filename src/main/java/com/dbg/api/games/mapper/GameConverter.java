package com.dbg.api.games.mapper;

import com.dbg.api.games.entity.Game;
import com.dbg.api.games.dto.GameDto;
import com.dbg.api.games.dto.GameResponse;

public class GameConverter {

  public static Game convertToGame(GameDto gameDto) {
    Game game = new Game();
    game.setTitle(gameDto.getTitle());
    game.setSalesCount(gameDto.getSalesCount());
    game.setPublisherId(gameDto.getPublisherId());
    game.setUserId(gameDto.getUserId());
    return game;
  }

  public static GameDto convertToGameDto(Game game) {
    GameDto gameDto = new GameDto();
    gameDto.setId(game.getId());
    gameDto.setTitle(game.getTitle());
    gameDto.setSalesCount(game.getSalesCount());
    return gameDto;
  }

  public static GameResponse convertToGameResponse(Game game) {
    GameResponse gameResponse = new GameResponse();
    gameResponse.setId(game.getId());
    gameResponse.setTitle(game.getTitle());
    gameResponse.setSalesCount(game.getSalesCount());
    gameResponse.setLikes(game.getLikes());
    return gameResponse;
  }

}
