package New.Main.CSEDU_CampusKin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.media.MediaCodec;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Firebase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.EventListener;
import java.util.Queue;

import New.Main.CSEDU_CampusKin.Adapters.SearchUserRecyclerAdapter;
import New.Main.CSEDU_CampusKin.Model.UserModel;
import New.Main.CSEDU_CampusKin.Utils.FirebaseUtils;

public class SearchUserActivity extends AppCompatActivity {

    EditText searchInput;
    ImageButton backButton;
    ImageButton searchButton;
    RecyclerView[] searchUserRecyclerView = new RecyclerView[7];

    SearchUserRecyclerAdapter adapter, adapter1, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_user_screen);

        searchInput = findViewById(R.id.searchInput);
        backButton = findViewById(R.id.back_button);
        searchButton = findViewById(R.id.search_button);
        searchUserRecyclerView[0] = findViewById(R.id.recycler_view0);
        searchUserRecyclerView[1] = findViewById(R.id.recycler_view1);
        searchUserRecyclerView[2] = findViewById(R.id.recycler_view2);
        searchUserRecyclerView[3] = findViewById(R.id.recycler_view3);
        searchUserRecyclerView[4] = findViewById(R.id.recycler_view4);
        searchUserRecyclerView[5] = findViewById(R.id.recycler_view5);
        searchUserRecyclerView[6] = findViewById(R.id.recycler_view6);

        searchInput.requestFocus();

        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        searchButton.setOnClickListener(view -> {
            String searchTerm = searchInput.getText().toString();
            if (searchTerm.isEmpty() || searchTerm.length() < 1) {
                searchInput.setError("Invalid Kin Name");
                return;
            }
            String[] field = {"username", "batch", "works", "workEnv", "fieldOfInt", "gender", "registrationNo"};

            for(int i=0; i<7; i++)
            {
                setUpSearchRecyclerView(searchTerm, searchUserRecyclerView[i], field[i]);
            }
        });
    }

    void setUpSearchRecyclerView(String searchTerm, RecyclerView recyclerView, String field) {
        CollectionReference userCollection = FirebaseUtils.allUserCollectionReference();

        Query query = userCollection.orderBy(field).startAt(searchTerm).endAt(searchTerm + "\uf8ff");

        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class).build();

        adapter = new SearchUserRecyclerAdapter(options, getApplicationContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        System.out.println(recyclerView.getAdapter().getItemCount() + " users found ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }

}