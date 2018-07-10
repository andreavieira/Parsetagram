package andreavieira.parsetagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    // Fields and Buttons
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Details pertaining to Login
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.username_et);
        passwordInput = findViewById(R.id.password_et);
        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                login(username, password);
            }
        });

        // Details pertaining to Register, enter Register activity
        registerBtn = findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // LOGGING IN
    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successful!");

                    final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.e("LoginActivity", "Login failure.");
                    e.printStackTrace();
                }
            }
        });
    }
}
