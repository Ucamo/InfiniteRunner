package com.ranacalva.infiniterunner.box2d;


import com.ranacalva.infiniterunner.enums.UserDataType;

public abstract class UserData {

    protected UserDataType userDataType;

    public UserData(){

    }

    public UserDataType getUserDataType(){
        return userDataType;
    }
}
