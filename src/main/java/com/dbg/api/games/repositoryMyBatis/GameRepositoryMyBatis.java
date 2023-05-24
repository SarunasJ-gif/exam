package com.dbg.api.games.repositoryMyBatis;

import com.dbg.api.games.entity.Game;
import com.dbg.api.games.entity.Publisher;
import com.dbg.api.games.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Mapper
public interface GameRepositoryMyBatis {

  @Insert("INSERT INTO PUBLISHERS (ID, TITLE) VALUES (#{id}, #{title})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void addPublisher(@RequestBody Publisher publisher);

  @Select("SELECT p.id, p.title FROM PUBLISHERS p WHERE ID = #{id}")
  Publisher getPublisherById(@Param("id") UUID id);

  @Insert("INSERT INTO GAMES (ID, TITLE, PUBLISHER_ID, SALES_COUNT, USER_ID) VALUES (#{id}, #{title}, #{publisherId}, #{salesCount}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void addGame(@RequestBody Game game);

  @Select("SELECT g.id, g.title, g.publisher_id AS publisherId, g.sales_count AS salesCount, likes FROM GAMES g WHERE ID = #{id}")
  Game getGameById(@Param("id") UUID id);

  @Select("SELECT g.id, g.title, g.publisher_id AS publisherId, g.sales_count AS salesCount, likes FROM GAMES g WHERE publisher_id = #{publisherId}")
  List<Game> getAllGamesByPublisherId(@Param("publisherId") UUID publisherId);

  @Insert("INSERT INTO LIKES (USER_ID, GAME_ID) SELECT #{userId}, #{gameId} " +
    "WHERE NOT EXISTS ( SELECT #{userId} FROM LIKES WHERE user_id = #{userId} " +
    "AND game_id = #{gameId})")
  void saveLikes(@Param("userId") UUID userId, @Param("gameId") UUID gameId);

  @Delete("DELETE FROM LIKES WHERE game_id = #{gameId} AND user_id = #{userId}")
  void removeFromLikes(@Param("gameId") UUID gameId, @Param("userId") UUID userId);

  @Select("SELECT l.game_id FROM LIKES l WHERE l.GAME_ID = #{gameId} AND l.USER_ID = #{userId} ")
  UUID getGameIdFromLikes(@Param("gameId") UUID gameId, @Param("userId") UUID userId);

  @Update("UPDATE GAMES g SET likes = #{likes} WHERE g.ID = #{id}")
  void updateGameLikes(@RequestBody Game game);

  @Select("SELECT s.ID, s.NAME FROM USERS s WHERE s.ID = #{id}")
  User getUserById(@Param("id") UUID id);

  @Select("SELECT g.id, g.title, g.publisher_id AS publisherId, g.sales_count AS salesCount, likes " +
    "FROM GAMES g WHERE g.PUBLISHER_ID = #{publisherId} " +
    "ORDER BY g.LIKES DESC LIMIT 3")
  List<Game> getTop3GamesByPublisherId(@Param("publisherId") UUID publisherId);

  @Insert("INSERT INTO USERS (ID, NAME, PASSWORD) VALUES (#{id}, #{username}, #{password})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void saveUser(@RequestBody User user);

  @Select("SELECT s.ID, s.NAME, s.PASSWORD FROM USERS s WHERE s.NAME = #{name}")
  User getUserByName(@Param("name") String name);

  @Update("ALTER TABLE GAMES ADD COLUMN IF NOT EXISTS LIKES INT")
  void addColumnLikes();

  @Update("ALTER TABLE USERS ADD COLUMN IF NOT EXISTS PASSWORD VARCHAR(255)")
  void addColumnPassword();
}



