package com.tkteam.reading;

/**
 * User: khiemvx
 * Date: 6/15/15
 * Time: 11:12 PM
 */
public class ApplicationStateHolder {
    // ------------------------------ FIELDS ------------------------------
    private static ApplicationStateHolder instance;
    MyActivity myActivity;

// -------------------------- STATIC METHODS --------------------------

    public ApplicationStateHolder(MyActivity myActivity) {
        this.myActivity = myActivity;
    }

    public static ApplicationStateHolder getInstance() {
        return instance;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public static ApplicationStateHolder getInstance(MyActivity myActivity) {
        if (instance == null) {
            instance = new ApplicationStateHolder(myActivity);
        } else {
            instance.myActivity = myActivity;
        }
        return instance;
    }

// --------------------- GETTER / SETTER METHODS ---------------------


    public MyActivity getMyActivity() {
        return myActivity;
    }

}
