package sanpham;

import static java.lang.reflect.Array.getInt;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String SHARED_PREF_NAME = "your_shared_pref_name";
    private static SharedPreferencesManager instance;
    private final SharedPreferences sharedPreferences;
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_PRO_ID = "pro_id";



    public boolean isLogin = false;

    private SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context.getApplicationContext());
        }
        return instance;
    }

    // Lưu một giá trị vào SharedPreferences
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // Lấy giá trị từ SharedPreferences
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Lấy giá trị từ SharedPreferences
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Xóa một giá trị khỏi SharedPreferences
    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    // Kiểm tra xem một key có tồn tại trong SharedPreferences hay không
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }
    public void savePhoneNumber(String phoneNumber) {
        saveString(KEY_PHONE_NUMBER, phoneNumber);
    }

    // Lấy số điện thoại từ SharedPreferences
    public String getPhoneNumber() {
        return getString(KEY_PHONE_NUMBER, "");
    }
    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getProId() {
        return sharedPreferences.getInt(KEY_PRO_ID, 0); // 0 là giá trị mặc định nếu không tìm thấy pro_id trong SharedPreferences
    }
}

