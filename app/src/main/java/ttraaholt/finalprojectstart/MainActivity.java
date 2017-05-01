package ttraaholt.finalprojectstart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText etAddNote;
    TextView tvNotes;
    ListView lvNotes;
    Button buttonAddNote;
    Button buttonDeleteNote;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNotes = (TextView) findViewById(R.id.textViewNotes);
        lvNotes = (ListView) findViewById(R.id.listViewNotes);

        userAuthentication();
        setupAddButton();

    }

    private void userAuthentication() {

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() { //initialized mAuthListener
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //track the user when they sign in or out using the firebaseAuth
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Log.d("CSS3334","onAuthStateChanged - User NOT is signed in");
                    Intent signInIntent = new Intent(getBaseContext(), SecondActivity.class);
                    startActivity(signInIntent);

                }
            }
        };

    }

    private void setupAddButton() {
        // Set up the button to add a new fish using a seperate activity
        buttonAddNote = (Button) findViewById(R.id.buttonAdd);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Start up the add fish activity with an intent
                tvNotes.setText(etAddNote.getText().toString());

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
