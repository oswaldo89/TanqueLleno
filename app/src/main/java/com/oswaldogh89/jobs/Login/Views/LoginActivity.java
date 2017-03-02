package com.oswaldogh89.jobs.Login.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.oswaldogh89.jobs.Home.Views.HomeActivity;
import com.oswaldogh89.jobs.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.BtnLogin)
    Button BtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.BtnLogin)
    public void Login() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

}
