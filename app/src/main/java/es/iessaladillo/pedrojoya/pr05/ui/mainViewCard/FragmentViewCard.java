package es.iessaladillo.pedrojoya.pr05.ui.mainViewCard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.DataBaseUser;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.databinding.FragmentCardBinding;
import es.iessaladillo.pedrojoya.pr05.ui.main.MainActivityViewModel;

public class FragmentViewCard extends Fragment {

    private FragmentCardBinding b;
    private FragmentViewCardViewModel viewModel;
    private MainActivityViewModel viewModelActivity;
    private FragmentViewCardAdapter listAdapter;
    private FragmentViewCard.addNewUserListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentCardBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelActivity = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        viewModel = ViewModelProviders.of(requireActivity(), new FragmentViewCardViewModelFactory(new DataBaseUser()))
                .get(FragmentViewCardViewModel.class);
        setupView();
        observeUsers();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (addNewUserListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " must implement FragmentViewCard.addNewUserListener");
        }
    }

    @Override
    public void onDetach() {
        listener=null;
        super.onDetach();
    }

    private void setupView() {
        listAdapter = new FragmentViewCardAdapter(position -> deleteUser(listAdapter.getItem(position)), position -> editUser(listAdapter.getItem(position)));
        b.lstUsers.setHasFixedSize(true);
        b.lstUsers.setLayoutManager(new GridLayoutManager(getContext(),
                getResources().getInteger(R.integer.main_lstUsers_columns)));
        b.lstUsers.setItemAnimator(new DefaultItemAnimator());
        b.lstUsers.setAdapter(listAdapter);

        b.lblEmptyView.setOnClickListener(v -> listener.addNewUser());

        b.fab.setOnClickListener(v -> listener.addNewUser());
    }

    private void editUser(User user) {
        viewModelActivity.setUser(user);
    }

    private void observeUsers() {
        viewModel.getUsers().observe(this, users -> {
            listAdapter.submitList(users);
            b.lblEmptyView.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

        private void deleteUser(User user) {
        viewModel.deleteUser(user);
    }

    public interface addNewUserListener {
        void addNewUser();
    }

    //    public static final int RC_EDIT = 1;
//    public static final int RC_ADD = 2;
//
//    private FragmentCardBinding b;
//    private FragmentViewCardViewModel viewModel;
//    private FragmentViewCardAdapter listAdapter;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        b = DataBindingUtil.setContentView(this, R.layout.fragment_card);
//        viewModel = ViewModelProviders.of(this, new FragmentViewCardViewModelFactory(new DataBaseUser()))
//                .get(FragmentViewCardViewModel.class);
//        setupView();
//        observeUsers();
//    }
//
//    private void setupView() {
//        listAdapter = new FragmentViewCardAdapter(position -> deleteUser(listAdapter.getItem(position)), position -> editIntentUser(listAdapter.getItem(position)));
//        b.lstUsers.setHasFixedSize(true);
//        b.lstUsers.setLayoutManager(new GridLayoutManager(getContext(),
//                getResources().getInteger(R.integer.main_lstUsers_columns)));
//        b.lstUsers.setItemAnimator(new DefaultItemAnimator());
//        b.lstUsers.setAdapter(listAdapter);
//
//        b.lblEmptyView.setOnClickListener(v -> addIntentUser());
//
//        b.fab.setOnClickListener(v -> addIntentUser());
//    }
//
//    private void addIntentUser() {
//        FragmentProfile.startForResult(FragmentViewCard.this, RC_ADD);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(resultCode==RESULT_OK && requestCode==RC_EDIT){
//            if(data!=null && data.hasExtra(FragmentProfile.EXTRA_USER_TO_CARD)){
//                editUser(data.getParcelableExtra(FragmentProfile.EXTRA_USER_TO_CARD));
//            }
//        }
//        if(resultCode==RESULT_OK && requestCode==RC_ADD){
//            if(data!=null && data.hasExtra(FragmentProfile.EXTRA_USER_TO_CARD)){
//                viewModel.addUser(data.getParcelableExtra(FragmentProfile.EXTRA_USER_TO_CARD));
//            }
//        }
//    }
//
//    private void editIntentUser(User user) {
//        FragmentProfile.startForResult(FragmentViewCard.this, RC_EDIT, user);
//        //Save user to delete if return intents is ok. use Viewmodel cause destroy change orientation
//        viewModel.setUser(user);
//    }
//
//
//    private void observeUsers() {
//        viewModel.getUsers().observe(this, users -> {
//            listAdapter.submitList(users);
//            b.lblEmptyView.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
//        });
//    }
//
//    private void deleteUser(User user) {
//        viewModel.deleteUser(user);
//    }
//
//    private void editUser (User user){
//        viewModel.addUser(user);
//        deleteUser(viewModel.getUser());
//    }



}
