package com.ccsw.tutorial.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.game.model.Game;

@ExtendWith(MockitoExtension.class)
public class GameTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    public static final Long EXISTS_GAME_ID = 1L;
    public static final Long NOT_EXISTS_GAME_ID = 0L;

    @Test
    public void getExistsGameIdShouldReturnGame() {
        Game game = mock(Game.class);
        when(game.getId()).thenReturn(EXISTS_GAME_ID);
        when(gameRepository.findById(EXISTS_GAME_ID)).thenReturn(Optional.of(game));

        Game gameResponse = gameService.get(EXISTS_GAME_ID);

        assertNotNull(gameResponse);

        assertEquals(EXISTS_GAME_ID, gameResponse.getId());
    }

    @Test
    public void getNotExistsGameIdShouldReturnNull() {
          when(gameRepository.findById(NOT_EXISTS_GAME_ID)).thenReturn(Optional.empty());

          Game game = gameService.get(NOT_EXISTS_GAME_ID);

          assertNull(game);
    }

}