package com.shakeup.setofthree.mainmenu;

/**
 * Created by Jayson on 3/2/2017.
 */

/**
 * This specifies the contract between the view and the presenter.
 */

public class MainMenuSinglePlayerContract {

    interface SinglePlayerView {

        void openNormal();

        void openTimeAttack(long timeAttackLength);

        void openPractice();

        void openPreviousFragment();

    }

    interface UserActionsListener {

        void onNormalClick();

        void onTimeAttackClick();

        void onPracticeClick();

        void onBackClick();

    }
}
