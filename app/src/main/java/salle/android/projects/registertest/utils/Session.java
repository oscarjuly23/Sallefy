package salle.android.projects.registertest.utils;

import android.content.Context;

import salle.android.projects.registertest.model.User;
import salle.android.projects.registertest.model.UserRegister;
import salle.android.projects.registertest.model.UserToken;

public class Session {

    public static Session sSession;
    private static Object mutex = new Object();

    private Context mContext;

    private UserRegister mUserRegister;
    private User mUser;
    private UserToken mUserToken;

    public static Session getInstance(Context context) {
        Session result = sSession;
        if (result == null) {
            synchronized (mutex) {
                result = sSession;
                if (result == null)
                    sSession = result = new Session();
            }
        }
        return result;
    }

    private Session() {}

    public Session(Context context) {
        this.mContext = context;
        this.mUserRegister = null;
        this.mUserToken = null;
    }

    public void resetValues() {
        mUserRegister = null;
        mUserToken = null;
    }

    public UserRegister getUserRegister() {
        return mUserRegister;
    }

    public void setUserRegister(UserRegister userRegister) {
        mUserRegister = userRegister;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public UserToken getUserToken() {
        return mUserToken;
    }

    public void setUserToken(UserToken userToken) {
        this.mUserToken = userToken;
    }
}
