package Controller;

import Model.Player;
import Model.PlayerHandler;
import java.io.Serializable;
public class RoundController implements  Serializable{
    int currentPlayerId = 0;
    int playerCount = 0;
    int currentRound = 0;
    boolean isOver = false;
    final int MAX_ROUND = 6;
    PlayerHandler playerHandler;
    int lastPassedIndex = 0;
    Player[] passRoundPlayerList;

    RoundController(Player[] playerList){
        currentPlayerId = 0;
        this.playerCount = playerList.length;
        playerHandler= new PlayerHandler();
        passRoundPlayerList = new Player[playerCount];
    }

    /**
     * Will be called after ending a turn
     */
    public void endTurn(Player[] playerList) { //Skip Turn Clicked

        currentPlayerId++;
        if(currentPlayerId >= playerCount) {
            currentPlayerId = 0;
        }
        if(!isRoundOver()){
        while(playerList[currentPlayerId].isRoundPassed()){
            currentPlayerId++;
            if(currentPlayerId >= playerCount) {
                currentPlayerId = 0;
            }
            }
        }
    }

    /**
     * Round is over, update resources and pass to next round
     */
    private void endRound(Player[] playerList) { //Pass Round Clicked
        for(int i = 0; i < passRoundPlayerList.length; i++){
            playerList[i] = passRoundPlayerList[i];
        }

        lastPassedIndex = 0;
        System.out.println("Round Over");
        for(int i = 0; i < playerCount; i++){
            playerHandler.updateResources(playerList[i]);
            System.out.println("\t"+playerList[i].getPriestNum());
            playerList[i].setRoundPassed(false);
        }
        currentPlayerId = 0;
        currentRound++;
        System.out.println("Round is : "+ currentRound);
        if(currentRound >= MAX_ROUND) {
            System.out.println("GAME OVER");
            isOver = true;
         //   System.exit(1);
        }
    }

    public void passRound(Player[] playerList) {
        System.out.println(playerList[currentPlayerId]);
        playerHandler.passRound(playerList[currentPlayerId]);
        passRoundPlayerList[lastPassedIndex] = playerList[currentPlayerId];
        lastPassedIndex++;
        if(isRoundOver()) {
            endRound(playerList);
        }else
            endTurn(playerList);
    }
    public boolean isOver(){
        return isOver;
    }
    public boolean isRoundOver() {
        return lastPassedIndex == playerCount;
    }
    public int getCurrentPlayerId(){
        return currentPlayerId;
    }
    public int getCurrentRound(){
        return currentRound;
    }
    public int getMAX_ROUND() {
        return MAX_ROUND;
    }
}
