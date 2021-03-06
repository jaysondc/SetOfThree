package com.shakeup.setofthree.timeattackgame;

import android.support.annotation.NonNull;

import com.shakeup.setgamelibrary.SetGame;
import com.shakeup.setofthree.setgame.GamePresenter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Jayson on 3/29/2017.
 * <p>
 * Presenter for Time Attack game mode
 */

public class TimeAttackGamePresenter extends GamePresenter
        implements TimeAttackGameContract.UserActionsListener {

    // Hints given per game
    private final int HINTS_AVAILABLE = 3;

    private final String LOG_TAG = getClass().getSimpleName();
    long mPlayerScore = 0;
    boolean mScoreUploaded = false;
    private TimeAttackGameContract.View mTimeAttackGameView;
    private int mHintsAvailable = HINTS_AVAILABLE;

    // Supply a default constructor
    public TimeAttackGamePresenter() {
    }

    /**
     * Public constructor used to set up the presenter. Requires a reference to the calling View.
     *
     * @param timeAttackGameView A reference to the calling View
     */
    public TimeAttackGamePresenter(
            @NonNull TimeAttackGameContract.View timeAttackGameView) {
        mTimeAttackGameView =
                checkNotNull(timeAttackGameView, "timeAttackGameView cannot be null!");

        // Set our the view reference in our superclass
        setGameView(mTimeAttackGameView);
    }

    /**
     * Initialize the timer and scoreboard for a new game. Provide arguments to restore
     * a previous game.
     */
    @Override
    public void initGame(SetGame existingGame, long timerLength, long playerScore) {
        super.initGame(existingGame);

        // Set our deck to endless mode
        mSetGame.setEndlessMode(true);
        // Enable clicks
        mTimeAttackGameView.setGameClickable(true);
        // Start a time attack counter
        // Timer is actually started in the fragments onResume method
        // Set the score
        mPlayerScore = playerScore;
        mTimeAttackGameView.updateScore(playerScore);
        // Show hints available
        mTimeAttackGameView.updateHintButton(mHintsAvailable);
    }

    @Override
    public void onFindSetSuccess() {
        mPlayerScore++;
        mTimeAttackGameView.updateScore(mPlayerScore);
    }

    @Override
    public void onFindSetFailure() {

    }

    /*
     * Once the timer ends, let the presenter know the game is over
     */
    @Override
    public void onTimeUp() {
        onGameOver();
    }

    @Override
    public long getPlayerScore() {
        return mPlayerScore;
    }

    /*
         * Show game over, upload score, save locally
         */
    @Override
    public void onGameOver() {
        super.onGameOver();

        mTimeAttackGameView.setGameClickable(false);
        if (!mIsDebug) {
            mTimeAttackGameView.uploadScore(mPlayerScore);
        }
        mTimeAttackGameView.saveLocalScore(mPlayerScore, mScoreUploaded);
    }

    /**
     * Set our member flag to show whether or not the score was
     * successfully uploaded
     *
     * @param uploaded Whether or not the score was uploaded
     */
    @Override
    public void onScoreUploaded(boolean uploaded) {
        mScoreUploaded = uploaded;
    }

    @Override
    public void onPauseClicked() {
        mTimeAttackGameView.pauseGame();
    }

    /**
     * Shows the user a hint if any are left to use.
     */
    @Override
    public void onHintClicked() {
        if (mHintsAvailable > 0) {
            if (showHint()) {
                mHintsAvailable--;
            }
        }
        mTimeAttackGameView.updateHintButton(mHintsAvailable);
    }

    @Override
    public void onPauseResultResume() {
        mTimeAttackGameView.resumeGame();
    }

    @Override
    public void onPauseResultMainMenu() {
        mTimeAttackGameView.openMainMenu();
    }

    @Override
    public void onPauseResultRestart() {
        mTimeAttackGameView.restartGame();
    }
}
