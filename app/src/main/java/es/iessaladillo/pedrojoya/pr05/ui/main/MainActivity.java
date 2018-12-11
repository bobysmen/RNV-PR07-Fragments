package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.ui.mainViewCard.FragmentViewCard;
import es.iessaladillo.pedrojoya.pr05.ui.mainViewCard.FragmentViewCardAdapter;
import es.iessaladillo.pedrojoya.pr05.ui.mainViewCard.OnUserClickListenerEdit;
import es.iessaladillo.pedrojoya.pr05.ui.profile.FragmentProfile;
import es.iessaladillo.pedrojoya.pr05.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity implements FragmentViewCard.addNewUserListener {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        if (getSupportFragmentManager().findFragmentByTag(FragmentViewCard.class.getSimpleName()) == null) {
            FragmentUtils.replaceFragment(
                    getSupportFragmentManager(), R.id.flContent, new FragmentViewCard(), FragmentViewCard.class.getSimpleName());
        }


    }

    @Override
    public void addNewUser() {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent, new FragmentProfile(),
                FragmentProfile.class.getSimpleName(), FragmentProfile.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
