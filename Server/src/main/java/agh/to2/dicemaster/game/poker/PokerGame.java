package agh.to2.dicemaster.game.poker;

import agh.to2.dicemaster.common.api.GameConfigDTO;
import agh.to2.dicemaster.common.api.GameDTO;
import agh.to2.dicemaster.game.model.Observer;
import agh.to2.dicemaster.game.model.Player;
import agh.to2.dicemaster.server.api.Game;
import agh.to2.dicemaster.server.api.GameParticipant;


import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PokerGame extends Game {

    private PokerGameManager pokerGameManager;
    private List<Player> players = new LinkedList<>();
    private List<GameParticipant> observers = new LinkedList<>();

    public PokerGame(int id, GameConfigDTO gameConfigDTO) {
        super(id, gameConfigDTO);
    }

    public List<Player> getPlayerList() {
        return players;
    }

    public void removeParticipants(Collection<GameParticipant> participantsToRemove) {
        players.removeAll(participantsToRemove);
        observers.removeAll(participantsToRemove);

    }

    public PokerGameManager getPokerGameManager() {
        return pokerGameManager;
    }

    public void setPokerGameManager(PokerGameManager pokerGameManager) {
        this.pokerGameManager = pokerGameManager;
    }

    @Override
    public void addObserver(GameParticipant gameParticipant) {
        PokerPlayerEventHandler pokerPlayerEventHandler = new PokerPlayerEventHandler(pokerGameManager, gameParticipant);
        observers.add(gameParticipant);
        gameParticipant.registerPlayerEventHandler(pokerPlayerEventHandler);
    }

    @Override
    public void addPlayer(GameParticipant gameParticipant) {
        PokerPlayerEventHandler pokerPlayerEventHandler = new PokerPlayerEventHandler(pokerGameManager, gameParticipant);
        Player player = new Player(gameParticipant);
        players.add(player);
        player.registerPlayerEventHandler(pokerPlayerEventHandler);
        if (getGameConfigDTO().getMaxPlayers() == players.size()) {
            pokerGameManager.onGameStart();
        }
    }

    @Override
    public List<GameParticipant> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    @Override
    public List<GameParticipant> getObservers() {
        return Collections.unmodifiableList(observers);
    }

    @Override
    public GameDTO getGameDTO() {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(getId());
        gameDTO.setGameConfig(getGameConfigDTO());
        List<String> observerList = observers
                .stream()
                .map(GameParticipant::getId)
                .collect(Collectors.toList());

        return gameDTO;
    }
}
