package huflit.phn.dulichvietnam;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class View_chinh extends FragmentStatePagerAdapter{

    public View_chinh(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new trangchu();
            case 1:
                return new hanhtrinh();
            case 2:
                return new thongbao();
            case 3:
                return new caidat();
            default:
                return new trangchu();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
