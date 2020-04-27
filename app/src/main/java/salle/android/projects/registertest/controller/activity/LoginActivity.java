package salle.android.projects.registertest.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.model.User;
import salle.android.projects.registertest.model.UserToken;
import salle.android.projects.registertest.restapi.callback.UserCallback;
import salle.android.projects.registertest.restapi.manager.UserManager;
import salle.android.projects.registertest.utils.Session;

public class LoginActivity extends AppCompatActivity implements UserCallback {

    private EditText etLogin;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvToRegister;

    @Override
    public void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews () {
        etLogin = (EditText) findViewById(R.id.login_user);
        etPassword = (EditText) findViewById(R.id.login_password);
        tvToRegister = (TextView) findViewById(R.id.login_to_register);
        tvToRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin = (Button) findViewById(R.id.login_btn_action);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doLogin(etLogin.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    private void doLogin(String username, String userpassword) {
        UserManager.getInstance(getApplicationContext())
                .loginAttempt(username, userpassword, LoginActivity.this); }

    @Override
    public void onLoginSuccess(UserToken userToken) {
        Session.getInstance().setUserToken(userToken);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onLoginFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Login failed " + throwable.getMessage(), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRegisterSuccess() {

    }
    @Override
    public void onRegisterFailure(Throwable throwable) {

    }
    @Override
    public void onUserInfoReceived(User userData) {

    }
    @Override
    public void onFailure(Throwable throwable) {

    }
}