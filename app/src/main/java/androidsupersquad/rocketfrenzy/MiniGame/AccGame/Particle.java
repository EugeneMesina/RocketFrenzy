package androidsupersquad.rocketfrenzy.MiniGame.AccGame;

import java.util.Random;

/**
 * Created by Jimmy Chao on 3/15/2017.
  * 012677182
 */

public class Particle {
    private static final float COR = 0.6f;
    public float mPosX, mPosY;
    private float mVelX, mVelY;
    private boolean isRunning = true;
//Changes the position of the ball
    public void updatePosition(float sx, float sy, float sz, long timestamp){
        if(isRunning) {
            float dt = (System.nanoTime() - timestamp) / 60000000.0f;
            mVelX += -sx * dt;
            mVelY += -sy * dt;

            mPosX += mVelX * dt;
            mPosY += mVelY * dt;
        }
    }
//Resolves collision on the bounds
    public void resolveCollisionWithBounds(float mHorizontalBound, float mVerticalBound){
        if(mPosX > mHorizontalBound)
        {
            mPosX = mHorizontalBound;
            mVelX = -mVelX * COR;
        } else if (mPosX<-mHorizontalBound){
            mPosX = -mHorizontalBound;
            mVelX = -mVelX* COR;
        }
        if(mPosY > mVerticalBound)
        {
            mPosY = mVerticalBound;
            mVelY = -mVelY * COR;

        } else if (mPosY< -mVerticalBound){
            mPosY = -mVerticalBound;
            mVelY = -mVelY * COR;
        }
    }

    /**
     * Shoots the particle in a random direction
     */
    public void throwParticle()
    {
        Random rand = new Random();
        int x = rand.nextInt(800) - 400;
        int y = rand.nextInt(800) - 400;
        mVelX=x;
        mVelY=y;
    }

    /**
     * Stops of particle, pauses the game
     */
    public void freeze()
    {
        mVelX = 0;
        mVelY = 0;
        isRunning = false;
    }

    /**
     * Unpauses the game
     */
    public void unFreeze()
    {
        isRunning = true;
    }
}
