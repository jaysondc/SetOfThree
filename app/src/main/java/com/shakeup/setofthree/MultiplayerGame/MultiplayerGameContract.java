package com.shakeup.setofthree.MultiplayerGame;

import com.shakeup.setofthree.SetGame.GameContract;

/**
 * Created by Jayson on 3/20/2017.
 */

public class MultiplayerGameContract extends GameContract {
    /**
     * Methods that need to be implemented by the Multiplayer Game View
     */
    interface View extends GameContract.View {

        void startFindSetCountdown();

        void setActivePlayer(int playerId);

        void onPlayerTimedOut();

        void setGameState(int gameState);

        void updatePlayerScore(int playerId, int playerScore);

    }

    /**
     * Methods that need to be implemented by the Multiplayer Game Presenter
     */
    interface UserActionsListener extends GameContract.UserActionsListener {

        void playerButtonClick(int playerId);

        void playerSuccess(int playerId);

        void playerButtonTimedOut();

    }
}
