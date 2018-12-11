package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.ui.avatar.FragmentAvatarActivity;
import es.iessaladillo.pedrojoya.pr05.ui.mainViewCard.FragmentViewCard;
import es.iessaladillo.pedrojoya.pr05.ui.mainViewCard.FragmentViewCardAdapter;
import es.iessaladillo.pedrojoya.pr05.ui.mainViewCard.OnUserClickListenerEdit;
import es.iessaladillo.pedrojoya.pr05.ui.profile.FragmentProfile;
import es.iessaladillo.pedrojoya.pr05.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity implements FragmentViewCard.addNewUserListener, FragmentProfile.editAvatarListener {

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

        updateUser();

    }

    public void updateUser(){
        viewModel.getUser().observe(this, user -> FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent, new FragmentProfile(),
                FragmentProfile.class.getSimpleName(), FragmentProfile.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
    }

    @Override
    public void addNewUser() {
        viewModel.setNewUser(true);
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent, new FragmentProfile(),
                FragmentProfile.class.getSimpleName(), FragmentProfile.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    @Override
    public void editAvatar() {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent, new FragmentAvatarActivity(),
                FragmentAvatarActivity.class.getSimpleName(), FragmentAvatarActivity.class.getSimpleName(), FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
//            save();
            if (getSupportFragmentManager().findFragmentByTag(FragmentAvatarActivity.class.getSimpleName())!=null) {
                viewModel.getUserActual().setAvatar(viewModel.getAvatar().getValue());
                getSupportFragmentManager().popBackStack();
            }
            if(getSupportFragmentManager().findFragmentByTag(FragmentProfile.class.getSimpleName())!=null){

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
