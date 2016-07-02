package pl.konradcygal.expressexample;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;

import java.util.ArrayList;

import pl.konradcygal.expressexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private AHBottomNavigationViewPager viewPager;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initBottomNavigation();
    }

    private void initBottomNavigation() {
        AHBottomNavigation bottomNavigation = binding.bottomNavigation;
        viewPager = binding.viewPager;
        AHBottomNavigationItem derstandard = new AHBottomNavigationItem(R.string.derstandard,
            android.R.drawable.ic_dialog_info, R.color.bottomColorDerstandard);
        AHBottomNavigationItem diepresse = new AHBottomNavigationItem(R.string.diepresse,
            android.R.drawable.ic_input_get, R.color.bottomColorDiepresse);
        AHBottomNavigationItem messages = new AHBottomNavigationItem(R.string.messages,
            android.R.drawable.ic_dialog_email, R.color.bottomColorMessages);

        ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
        bottomNavigationItems.add(derstandard);
        bottomNavigationItems.add(diepresse);
        bottomNavigationItems.add(messages);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setColored(true);

        viewPager.setOffscreenPageLimit(3);
        BottomNavigationAdapter adapter = new BottomNavigationAdapter(
            getSupportFragmentManager(),
            getFragments()
        );
        viewPager.setAdapter(adapter);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected) {
                    hideSoftKeyboard();
                }
                viewPager.setCurrentItem(position, false);
                return true;
            }
        });
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(WebViewFragment.newInstance("http://mobile.derstandard.at"));
        fragments.add(WebViewFragment.newInstance("http://diepresse.com"));
        fragments.add(MessagesFragment.newInstance());
        return fragments;
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
            Activity.INPUT_METHOD_SERVICE
        );
        if (getCurrentFocus() != null) {
            IBinder iBinder = getCurrentFocus().getWindowToken();
            inputMethodManager.hideSoftInputFromWindow(iBinder, 0);
        }
    }
}
