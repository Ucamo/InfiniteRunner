package com.ranacalva.infiniterunner.actors;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ranacalva.infiniterunner.box2d.UserData;
import com.ranacalva.infiniterunner.utils.Constants;

public abstract class GameActor extends Actor {

    protected Body body;
    protected UserData userData;
    protected Rectangle screenRectangle;

    public GameActor(){

    }

    public GameActor(Body body){
        this.body=body;
        this.userData=(UserData)body.getUserData();
        screenRectangle=new Rectangle();
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if(body.getUserData()!=null){
            updateRectangle();
        } else {
            // This means the world destroyed the body (The enemy or runner went out of bounds
            remove();
        }
    }

    public abstract UserData getUserData();

    private void updateRectangle(){
        screenRectangle.x=transformToSceeen(body.getPosition().x-userData.getWidth()/2);
        screenRectangle.y = transformToSceeen(body.getPosition().y-userData.getHeight()/2);
        screenRectangle.height= transformToSceeen(userData.getHeight());
        screenRectangle.width= transformToSceeen(userData.getWidth());


    }

    protected float transformToSceeen(float n){
        return Constants.WORLD_TO_SCREEN*n;
    }
}
