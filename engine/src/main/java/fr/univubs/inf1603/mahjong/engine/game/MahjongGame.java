package fr.univubs.inf1603.mahjong.engine.game;

import fr.univubs.inf1603.mahjong.engine.rule.GameRule;
import fr.univubs.inf1603.mahjong.engine.rule.Wind;
import java.beans.PropertyChangeSupport;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.UUID;

/**
 * Cette classe permet de représenter une partie de Mahjong
 *
 * @author COGOLUEGNES Charles
 */
public class MahjongGame implements Game {

    private Wind[] playerWind;
    private GameRule rule;
    private MahjongBoard board;
    private Move lastPlayedMove;

    private Duration stealingTime;
    private Duration playingTime;

    private ArrayList<Move> registeredMoves;
    private UUID uuid;
    
    private int[] playerPoints;
    
    /**
     * This is the full constructor of MahjongGame, allowing to initialize all of its fields
     * @param rule Rules of this game
     * @param board Current state of the board
     * @param lastPlayedMove The last played move of this game
     * @param stealingTime The time players have to decide if they can steal a discarded tile
     * @param playingTime This players have to decide what to discard
     * @param uuid This game's UUID
     * @throws GameException
     */
    public MahjongGame(GameRule rule, MahjongBoard board, Move lastPlayedMove, Duration stealingTime, Duration playingTime,int[] playerPoints,UUID uuid) throws GameException{
        this.rule = rule;
        this.board = board;
        this.lastPlayedMove = lastPlayedMove;
        this.stealingTime = stealingTime;
        this.playingTime = playingTime;
        this.uuid = uuid;
        this.playerPoints = playerPoints;
    }
    
    @Deprecated
    public MahjongGame(GameRule rule) throws GameException {
        this.rule = rule;
        this.board = null;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public void launchGame() {
        throw new UnsupportedOperationException("not implemented yet");
//        this.playerWind = this.rule.getPlayersOrder();
        //      this.board = this.rule.initBoard();
    }

    @Override
    public void registerMove(Move move) throws GameException {
        throw new UnsupportedOperationException("not immplemented yes");
    }

    @Override
    public Board getBoard(Wind wind) throws GameException {
        if(wind == null){
            return this.board;
        }
        return this.board.getViewFromWind(wind);
    }
    
    public Board getBoard(){
        return this.board;
    }
    
    


    @Override
    public ArrayList<Move> getPossibleMoves() {
        EnumMap<Wind, Collection<Move>> res = this.rule.getBoardRule().findValidMoves(this.board, this.lastPlayedMove);
        ArrayList<Move> ret = new ArrayList<>();
        for(Collection c : res.values()){
            ret.addAll(c);
        }
        return ret;
    }

    @Override
    public MahjongGame clone() {
        throw new UnsupportedOperationException("not implemented yet");
//        return new MahjongGameGame();
    }

    @Override
    public Duration getStealingTime() {
        return this.stealingTime;
    }

    @Override
    public Duration getPlayingTime() {
        return this.playingTime;
    }

    @Override
    public Move getLastPlayedMove() {
        return this.lastPlayedMove;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @Override
    public GameRule getRule() {
        return this.rule;
    }

    @Override
    public Wind getCurrentwind() throws GameException {
        return this.board.getCurrentWind();
    }

    
    @Override
    public int getPlayerPoints(int player) {
        return this.playerPoints[player];
    }

    
    @Override
    public int getPlayerPoints(Wind wind) {
        int indexOfWind=0;
        do{
          indexOfWind++;  
        }while(this.playerWind[indexOfWind] != wind);
        return this.playerPoints[indexOfWind];
    }

    @Override
    public int[] getAllPlayerPoints() {
        return this.playerPoints;
    }

    @Override
    public Wind getPlayerWind(int player) throws GameException {
        if(player<0 || player > 3)throw new GameException("The player you are asking the wind from doesn't exist. There is only 4 player describs by 0 1 2 3 .");
        Wind wind = this.playerWind[player];
        if(wind == null) throw new GameException("The wind of a player cannot be null.");
        return wind;
    }

    @Override
    public int getPlayerFromWind(Wind wind) throws GameException {
        int player = -1;
        for(int i=0; i<4; i++){
            if(this.playerWind[i] == wind) player = i;
        }
        if(player == -1)throw new GameException("The wind has to be in the playerWind.");
        return player;
    }

    @Override
    public Wind[] getPlayerWinds() throws GameException {
        return this.playerWind;
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Wind wind) throws GameException {
        return new ArrayList<>(this.rule.getBoardRule().findValidMoves(this.board, this.lastPlayedMove).get(wind));
    }

    @Override
    public ArrayList<Move> getPossibleMoves(int player) throws GameException {
        return getPossibleMoves(playerWind[player]);
    }

}
