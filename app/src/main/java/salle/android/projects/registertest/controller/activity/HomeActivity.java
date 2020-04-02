package salle.android.projects.registertest.controller.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eightbitlab.bottomnavigationbar.BottomBarItem;
import com.eightbitlab.bottomnavigationbar.BottomNavigationBar;

import salle.android.projects.registertest.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView textView = ((TextView) findViewById(R.id.textView));
        showContent(0, textView);
        setupBottomBar(textView);
    }

    private void setupBottomBar(@NonNull final TextView textView) {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_bar);

        BottomBarItem home = new BottomBarItem(R.drawable.ic_home_black_24dp, R.string.Home);
        BottomBarItem search = new BottomBarItem(R.drawable.ic_search_24px, R.string.Search);
        BottomBarItem library = new BottomBarItem(R.drawable.ic_view_list_24px, R.string.Library);
        BottomBarItem profile = new BottomBarItem(R.drawable.ic_account_circle_24px, R.string.Profile);

        bottomNavigationBar
                .addTab(home)
                .addTab(search)
                .addTab(library)
                .addTab(profile);

        bottomNavigationBar.setOnSelectListener(new BottomNavigationBar.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                showContent(position, textView);
            }
        });

        //only for translucent system navbar
        if (shouldAddNavigationBarPadding()) {
            //if your bottom bar has fixed height
            //you'll need to increase its height as well
            bottomNavigationBar.setPadding(0, 0, 0, getSystemNavigationBarHeight());
        }
    }

    private boolean shouldAddNavigationBarPadding() {
        return atLeastKitkat() && isInPortrait() && hasSystemNavigationBar();
    }

    private boolean isInPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private boolean atLeastKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    void showContent(int position, @NonNull TextView textView) {
        textView.setText("Tab " + position + " selected");
    }

    private int getSystemNavigationBarHeight() {
        Resources res = getResources();

        int id = res.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = 0;

        if (id > 0) {
            height = res.getDimensionPixelSize(id);
        }

        return height;
    }

    private boolean hasSystemNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        } else {
            boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !hasMenuKey && !hasBackKey;
        }
    }
}