package com.gm.tweak.application.service;

import java.util.List;

import com.gm.tweak.domain.game.Drawing;
import com.gm.tweak.domain.game.Game;
import com.gm.tweak.domain.game.GameId;
import com.gm.tweak.domain.game.GameRepository;
import com.gm.tweak.domain.game.Player;
import com.gm.tweak.domain.game.Word;
import com.gm.tweak.domain.game.factory.GameBuilder;
import com.gm.tweak.domain.user.event.PlayerWonDomainEvent;
import com.gm.tweak.exception.GameCreationException;

public class GameService implements DefaultGameService {

	private GameRepository gameRepository;

	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	public Game create(Drawing drawing, Player gameCreator) throws GameCreationException {

		GameId gameId = gameRepository.nextGameId();

		Game game = new GameBuilder().withGameId(gameId).withDrawing(drawing).withPlayerCreator(gameCreator)
				.withDomainEvent(new PlayerWonDomainEvent()).build();

		gameRepository.save(game);

		return game;
	}

	public void tryWord(GameId gameId, Player diviner, Word word) {
		Game game = gameRepository.findById(gameId);
		game.tryWord(diviner, word);
	}

	public List<Game> findAllGames() {
		return gameRepository.findAll();
	}

}