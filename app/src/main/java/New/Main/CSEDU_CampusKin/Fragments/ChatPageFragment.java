package New.Main.CSEDU_CampusKin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import New.Main.CSEDU_CampusKin.Adapters.RecentChatsRecyclerAdapter;
import New.Main.CSEDU_CampusKin.Adapters.SearchUserRecyclerAdapter;
import New.Main.CSEDU_CampusKin.Model.ChatRoomModel;
import New.Main.CSEDU_CampusKin.Model.UserModel;
import New.Main.CSEDU_CampusKin.R;
import New.Main.CSEDU_CampusKin.Utils.FirebaseUtils;


public class ChatPageFragment extends Fragment {

    RecyclerView recyclerView;
    RecentChatsRecyclerAdapter adapter;
    public ChatPageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.chat_screen_fragment, container, false);
        recyclerView = view.findViewById(R.id.chat_list_recycler_view);
        setUpRecyclerView();
        return  view;
    }

    void setUpRecyclerView()
    {
        Query query = FirebaseUtils.allChatRoomsCollectionReference()
                        .whereArrayContains("userIDs", FirebaseUtils.currentUserId())
                .orderBy("lastMessageTimestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatRoomModel> options = new FirestoreRecyclerOptions.Builder<ChatRoomModel>()
                .setQuery(query, ChatRoomModel.class).build();

        adapter = new RecentChatsRecyclerAdapter(options,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public void onStart()
    {
        super.onStart();
        if(adapter!=null) {
            setUpRecyclerView();
            adapter.startListening();
        }
    }
    @Override
    public void onStop()
    {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(adapter!=null) {
            setUpRecyclerView();
            adapter.notifyDataSetChanged();
        }
    }
}