package androidsupersquad.rocketfrenzy.MiniGame.AccGame;

import java.util.Random;

/**
 * Class used to update position of the rocket and to check for collision
 *
 * Created by Jimmy Chao (Lazer)
 */

public class Particle {
    /** Used for slowing down the rocket when hitting a boundary */
    private static final float COR = 0.6f;
    /** The rocket's x and y coordinates */
    public float mPosX, mPosY;
    /** The rocket's x and y velocity */
    private float mVelX, mVelY;
    /** Whether the game is running or not */
    private boolean isRunning = true;

    /**
     * Updates the position of the rocket
     *
     * @param sx x position to update to
     * @param sy y position to update to
     * @param sz z position to update to
     * @param timestamp The listener timestamp
     */
    public void updatePosition(float sx, float sy, float sz, long timestamp) {
        if (isRunning) {
            float dt = (System.nanoTime() - timestamp) / 60000000.0f;
            mVelX += -sx * dt;
            mVelY += -sy * dt;

            mPosX += mVelX * dt;
            mPosY += mVelY * dt;
        }
    }

    /**
     * Handles rocket colliding on the bounds of the screen
     *
     * @param mHorizontalBound The screen's horizontal bounds
     * @param mVerticalBound The screen's vertical bounds
     */
    public void resolveCollisionWithBounds(float mHorizontalBound, float mVerticalBound) {
        if (mPosX > mHorizontalBound) {
            mPosX = mHorizontalBound;
            mVelX = -mVelX * COR;
        } else if (mPosX < -mHorizontalBound) {
            mPosX = -mHorizontalBound;
            mVelX = -mVelX * COR;
        }
        if (mPosY > mVerticalBound) {
            mPosY = mVerticalBound;
            mVelY = -mVelY * COR;

        } else if (mPosY < -mVerticalBound) {
            mPosY = -mVerticalBound;
            mVelY = -mVelY * COR;
        }
    }

    /**
     * Shoots the particle in a random direction
     */
    public void throwParticle() {
        Random rand = new Random();
        int x = rand.nextInt(800) - 400;
        int y = rand.nextInt(800) - 400;
        mVelX = x;
        mVelY = y;
    }

    /**
     * Stops of particle, pauses the game
     */
    public void freeze() {
        mVelX = 0;
        mVelY = 0;
        isRunning = false;
    }

    /**
     * Unpauses the game
     */
    public void unFreeze() {
        isRunning = true;
    }
}
