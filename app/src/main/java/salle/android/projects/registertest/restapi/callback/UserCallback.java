package salle.android.projects.registertest.restapi.callback;

import salle.android.projects.registertest.model.User;
import salle.android.projects.registertest.model.UserToken;

public interface UserCallback extends FailureCallback {
    void onLoginSuccess(UserToken userToken);
    void onLoginFailure(Throwable throwable);
    void onRegisterSuccess();
    void onRegisterFailure(Throwable throwable);
    void onUserInfoReceived(User userData);
}
