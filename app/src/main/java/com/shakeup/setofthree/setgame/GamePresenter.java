package com.shakeup.setofthree.setgame;

import android.support.annotation.NonNull;

import com.shakeup.setgamelibrary.SetGame;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jayson on 3/2/2017.
 * <p>
 * Handles all UI actions generated by the GameFragment class. The contract
 * between interactions is specified in GameContract.java
 */

public class GamePresenter implements GameContract.UserActionsListener {

    protected SetGame mSetGame;
    // ArrayList holding the current valid locations of sets
    protected ArrayList<SetGame.Triplet> mSetLocations;
    private GameContract.View mGameView;


    // Supply a default constructor
    public GamePresenter() {
    }

    /**
     * Public constructor used to set up the presenter. Requires a reference to the calling View.
     *
     * @param gameView A reference to the calling View
     */
    public GamePresenter(
            @NonNull GameContract.View gameView) {
        mGameView = checkNotNull(gameView, "gameView cannot be null!");
    }

    /**
     * Public method for setting a gameview to be used by subclasses
     *
     * @param gameView
     */
    public void setGameView(GameContract.View gameView) {
        mGameView = gameView;
    }

    /**
     * Create a new instance of a SET Game and pass the data back to the
     * game fragment to be displayed and interacted with
     */
    @Override
    public void initGame(SetGame existingGame) {
        // Initialize the SetGame object
        // Reuse the received game if it exists
        if (existingGame != null) {
            mSetGame = existingGame;
        } else {
            mSetGame = new SetGame(1);
        }

        // Get the location of valid sets on the board
        mSetLocations = mSetGame.getLocationOfSets();

        // Initialize the game display
        mGameView.displayGame(mSetGame.getSetHand());
    }

    /**
     * Handles SET claims thrown from the GameFragment
     * Calls the GamePresenter success and failure handlers when appropriate.
     * This also
     *
     * @param indexOne   Index of the first card in the set.
     * @param indexTwo   Index of the second card in the set.
     * @param indexThree Index of the third card in the set.
     */
    @Override
    public void onSubmitSet(int indexOne, int indexTwo, int indexThree) {

        // Resolve set claim and obtain result
        boolean result = mSetGame.claimSet(indexOne, indexTwo, indexThree);

        if (!result) {
            // Call presenter method to handle failure
            this.onSetFailure();
        } else {
            // Update the set hand
            mGameView.updateSetHand(
                    mSetGame.getIsOverflow(),
                    mSetGame.getDeckSize());

            // Call presenter method to handle success
            this.onSetSuccess();

            // Call the GameOver method if the game is over,
            if (mSetGame.getIsGameOver()) {
                this.onGameOver();
            }

            // Get the new SetLocations array
            mSetLocations = mSetGame.getLocationOfSets();
        }

    }

    /**
     * Subclasses should override this method to perform additional actions to react
     * to SetFailure before the view is affected.
     */
    @Override
    public void onSetFailure() {
        mGameView.onSetFailure();
    }

    /**
     * Subclasses should override this method to perform additional actions to react
     * to SetSuccess before the view is affected.
     */
    @Override
    public void onSetSuccess() {
        mGameView.onSetSuccess();
    }

    /**
     * Subclasses should override this method to perform additional actions to react
     * to GameOver before the view is affected.
     */
    @Override
    public void onGameOver() {
        mGameView.showGameOver();
    }

    /**
     * Handle clicks thrown from the board and react to them. This can be overridden
     * to perform additional actions whenever a card on the board is clicked
     */
    @Override
    public void onSetCardClick() {
        mGameView.onSetCardClicked();
    }

    /**
     * Highlight a valid set on the board
     */
    public void highlightValidSet() {
        // If there are any sets left on the board
        if (mSetGame.getNumAvailableSets() > 0) {
            // Get a random set
            SetGame.Triplet randomSet = mSetGame.getRandomSet();

            // Highlight each index
            mGameView.highlightCard(randomSet.getFirst());
            mGameView.highlightCard(randomSet.getSecond());
            mGameView.highlightCard(randomSet.getThird());
        }
    }

    /**
     * Highlight a single card from a valid set as a hint
     */
    public void onShowHintClick() {
        // If there are any sets left on the board
        if (mSetGame.getNumAvailableSets() > 0) {
            // Get a random set
            SetGame.Triplet randomSet = mSetGame.getRandomSet();

            // Highlight the first index
            mGameView.highlightCard(randomSet.getFirst());
        }
    }

    /**
     * Get a reference to the SetGame for testing
     *
     * @return Reference to the current SetGame
     */
    public SetGame getSetGame() {
        return mSetGame;
    }

    /**
     * Manually set an instance of the SetGame object.
     *
     * @param game SetGame instance to be used.
     */
    public void setSetGame(SetGame game) {
        mSetGame = game;
    }

    /**
     * Get the location of all available sets in the current board
     *
     * @return An ArrayLilst of Triplets containing the indexes of
     * all available sets
     */
    public ArrayList<SetGame.Triplet> getSetLocations() {
        return mSetGame.getLocationOfSets();
    }
}
