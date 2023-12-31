package New.Main.CSEDU_CampusKin.Utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.firestore.auth.User;

import New.Main.CSEDU_CampusKin.Model.UserModel;

public class AndroidUtil {

    public static String notificationType;

    public static void setNotificationType(String type)
    {
        notificationType = type;
    }

    public static String getNotificationType()
    {
        return notificationType;
    }
    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void passUserModelAsIntent(Intent intent, UserModel model){
        intent.putExtra("userID", model.getUserID());
        intent.putExtra("username", model.getUsername());
        intent.putExtra("batch", model.getBatch());
        intent.putExtra("FCMToken", model.getFCMToken());
    }

    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setUserID(intent.getStringExtra("userID"));
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setBatch(intent.getStringExtra("batch"));
        userModel.setFCMToken(intent.getStringExtra("FCMToken"));
        return userModel;
    }
}
