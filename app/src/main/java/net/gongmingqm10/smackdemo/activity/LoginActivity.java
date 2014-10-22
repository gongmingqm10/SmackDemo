package net.gongmingqm10.smackdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.gongmingqm10.smackdemo.R;
import net.gongmingqm10.smackdemo.xmpp.XMPPManager;

import org.jivesoftware.smack.SmackAndroid;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends ActionBarActivity {

    @InjectView(R.id.username)
    EditText usernameEdit;
    @InjectView(R.id.password)
    EditText passwordEdit;
    @InjectView(R.id.loginBtn)
    Button loginBtn;
    @InjectView(R.id.friend)
    EditText friendEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SmackAndroid.init(this);
        ButterKnife.inject(this);
        init();

    }

    private void init() {

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                String friend = friendEdit.getText().toString().trim();
                XMPPManager.getInstance().connect(username, password);
                startMainActivity(friend);
            }
        });

    }

    private void startMainActivity(String friend) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("friend", friend);
        startActivity(intent);
    }
}

