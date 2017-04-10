package commmadondo.github.everyonevotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText editTextVote;
    EditText editTextAllVotes;
    Button buttonVote;
    DatabaseReference myDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up widget links to layout
        editTextVote = (EditText) findViewById(R.id.editTextVote);
        editTextAllVotes = (EditText) findViewById(R.id.editTextAllVotes);
        buttonVote = (Button) findViewById(R.id.buttonSaveVote);

        //Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myDbRef = database.getReference("Everyone Votes");

        buttonVote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "saving vote: ");        // debugging log

                // ---- Get a new database key for the vote
                String key = myDbRef.child("Favorite Pet Votes").push().getKey();

                // ---- set up the vote
                String voteText = editTextVote.getText().toString();
                Integer voteNum = 0;
                if (voteText.compareToIgnoreCase("DOG")==0) {
                    voteNum = 1;
                } else if (voteText.compareToIgnoreCase("CAT")==0) {
                    voteNum = 2;
                }
                Vote vote = new Vote(voteText, voteNum);

                // ---- write the vote to Firebase
                myDbRef.child("Favorite Pet Votes").child(key).setValue(vote);
            }
        });

        myDbRef.child("Favorite Pet Votes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("CIS3334", "Starting onDataChange()");        // debugging log
                editTextAllVotes.setText("");           // clear out the all votes text box
                // loop through all the votes returned
                for (DataSnapshot voteDataSnapshot : dataSnapshot.getChildren()) {
                    Vote vote = voteDataSnapshot.getValue(Vote.class);          // get the current vote from the data set returned
                    editTextAllVotes.append("\n" + vote.toString());            // display the vote in the edit text widget
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("CIS3334", "onCancelled: ");
            }
        });

    }
}
