package New.Main.CSEDU_CampusKin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import New.Main.CSEDU_CampusKin.Activity.SeeeReviewActivity;
import New.Main.CSEDU_CampusKin.ChatActivity;
import New.Main.CSEDU_CampusKin.Model.WorkReview;
import New.Main.CSEDU_CampusKin.NavigationActivity;
import New.Main.CSEDU_CampusKin.R;
import New.Main.CSEDU_CampusKin.Utils.AndroidUtil;
import New.Main.CSEDU_CampusKin.Utils.FirebaseUtils;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchReviewRecyclerAdapter extends FirestoreRecyclerAdapter<WorkReview, SearchReviewRecyclerAdapter.WorkReviewViewHolder> {
    Context context;
    private Activity activity;

    public SearchReviewRecyclerAdapter(@NonNull FirestoreRecyclerOptions<WorkReview> options, Context context, Activity activity) {
        super(options);
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull SearchReviewRecyclerAdapter.WorkReviewViewHolder holder, int position, @NonNull WorkReview model) {


            holder.prof_name_text.setText(model.getBossName());
            holder.workPlace_text.setText("Workplace : " + model.getNameOfWorkPlace());
            holder.workPlace_review.setText("Review on work place : " + model.getReviewOnWorkPlace());
            holder.prof_review.setText("Review on Prof/Boss : " + model.getReviewOnBoss());
            holder.profLinkedIn.setText("LinkedIn of Prof/Boss : " + model.getBossLinkedin());
            if(model.getWorkingStatus()!=null)
             holder.workingStatus.setText(model.getWorkingStatus());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDateStart, formattedDateEnd;
            formattedDateStart = dateFormat.format(model.getStarted());
            if(model.getEnded()!= null) {
                formattedDateEnd = dateFormat.format(model.getEnded());
                holder.endingDate.setText(formattedDateEnd);
            }
            holder.startingDate.setText(formattedDateStart);
    }

    @NonNull
    @Override
    public SearchReviewRecyclerAdapter.WorkReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_review_recycler_row, parent, false);
        return new SearchReviewRecyclerAdapter.WorkReviewViewHolder(view);
    }

    class WorkReviewViewHolder extends RecyclerView.ViewHolder {
        TextView prof_name_text;
        TextView workPlace_text;
        TextView workPlace_review;
        TextView prof_review;
        TextView profLinkedIn;
        TextView workingStatus;
        TextView startingDate;
        TextView endingDate;

        public WorkReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            prof_name_text = itemView.findViewById(R.id.prof_name_text);
            workPlace_text = itemView.findViewById(R.id.workPlace_text);
            workPlace_review = itemView.findViewById(R.id.workPlace_review);
            prof_review = itemView.findViewById(R.id.Prof_review);
            profLinkedIn = itemView.findViewById(R.id.Prof_linkedIn);
            workingStatus = itemView.findViewById(R.id.working_status);
            startingDate = itemView.findViewById(R.id.working_start);
            endingDate = itemView.findViewById(R.id.working_end);
        }
    }
}
