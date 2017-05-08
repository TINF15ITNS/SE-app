package it15ns.friendscom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText text_email;
    private EditText text_password;
    private EditText text_password2;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get Items
        text_email = (EditText)findViewById(R.id.email);
        text_password = (EditText)findViewById(R.id.password);
        text_password2 = (EditText)findViewById(R.id.password2);
        btn_register = (Button)findViewById(R.id.register_button);

        //register button action listener
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call GRPC Method to register
            }
        });
    }
}
