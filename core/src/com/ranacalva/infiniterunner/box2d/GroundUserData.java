package com.ranacalva.infiniterunner.box2d;


import com.ranacalva.infiniterunner.enums.UserDataType;

public class GroundUserData extends UserData {

    public GroundUserData(){
        super();
        userDataType= UserDataType.GROUND;
    }
}
