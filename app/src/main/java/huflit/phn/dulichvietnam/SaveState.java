package huflit.phn.dulichvietnam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;


public class SaveState {
    private static final String TAG = SaveState.class.getName();
    SharedPreferences preferences;
    Context context;
    SharedPreferences.Editor editor;
    private final int  PRE_MODE = 1;
    private  static final String NAME ="login";
    private static final String KEY_LOGIN = "islogin";
    public  SaveState(@NonNull Context context)
    {
        this.context = context;
        preferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public void setKeyLogin(boolean islogin) {
        editor.putBoolean(KEY_LOGIN, islogin);
        editor.apply(); // Sử dụng apply() thay vì commit()
    }
    public boolean Check() {
        // Kiểm tra xem preferences có null hay không
        if (preferences != null) {
            // Truy cập vào SharedPreferences và trả về giá trị lưu trữ
            return preferences.getBoolean(KEY_LOGIN, false);
        } else {
            // Trường hợp preferences là null, trả về giá trị mặc định là false

            return false;
        }
    }

}
