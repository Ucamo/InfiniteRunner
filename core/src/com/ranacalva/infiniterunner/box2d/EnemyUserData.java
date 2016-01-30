package com.ranacalva.infiniterunner.box2d;


import com.badlogic.gdx.math.Vector2;
import com.ranacalva.infiniterunner.enums.UserDataType;
import com.ranacalva.infiniterunner.utils.Constants;

public class EnemyUserData extends UserData {

    private Vector2 linearVelicity;

    public EnemyUserData(float width, float height){
        super(width,height);
        userDataType = UserDataType.ENEMY;
        linearVelicity= Constants.ENEMY_LINEAR_VELOCITY;
    }

    public void setLinearVelicity(Vector2 linearVelicity){
        this.linearVelicity=linearVelicity;
    }

    public Vector2 getLinearVelicity(){
        return linearVelicity;
    }
}
