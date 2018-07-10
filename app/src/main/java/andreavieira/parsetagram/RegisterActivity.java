package andreavieira.parsetagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private Button signupBtn;
    // Create the ParseUser
    ParseUser user = new ParseUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.username_et);
        passwordInput = findViewById(R.id.password_et);
        emailInput = findViewById(R.id.email_et);
        signupBtn = findViewById(R.id.signup_btn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                final String email = emailInput.getText().toString();
                register(username, password, email);
            }
        });
    }

    // REGISTERING
    private void register(String username, String password, String email) {
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("RegisterActivity", "Sign up successful!");

                    final Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("RegisterActivity", "Sign up failure.");
                    e.printStackTrace();
                }
            }
        });
    }
}
