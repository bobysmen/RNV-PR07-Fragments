package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.ui.mainViewCard.FragmentViewCard;
import es.iessaladillo.pedrojoya.pr05.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(
                    getSupportFragmentManager(), R.id.flContent, new FragmentViewCard(), FragmentViewCard.class.getSimpleName());
        }
    }
}
