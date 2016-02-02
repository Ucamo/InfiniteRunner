package com.ranacalva.infiniterunner.box2d;


import com.ranacalva.infiniterunner.enums.UserDataType;

public class GroundUserData extends UserData {

    public GroundUserData(float width, float height){
        super(width,height);
        userDataType= UserDataType.GROUND;
    }
}
