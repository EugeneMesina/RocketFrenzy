package androidsupersquad.rocketfrenzy.MiniGame.AccGame;

/**
 * Created by Jimmy Chao on 3/15/2017.
  * 012677182
 */

public class Particle {
    private static final float COR = 0.7f;
    public float mPosX, mPosY;
    private float mVelX, mVelY;
//Changes the position of the ball
    public void updatePosition(float sx, float sy, float sz, long timestamp){
        float dt = (System.nanoTime() - timestamp)/100000000.0f;
        mVelX += -sx * dt;
        mVelY += -sy * dt;

        mPosX += mVelX * dt;
        mPosY += mVelY * dt;

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


}
