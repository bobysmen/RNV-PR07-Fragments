package es.iessaladillo.pedrojoya.pr05.ui.profile;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.databinding.FragmentProfileBinding;
import es.iessaladillo.pedrojoya.pr05.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr05.ui.mainViewCard.FragmentViewCard;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static es.iessaladillo.pedrojoya.pr05.data.local.Database.getInstance;
import static es.iessaladillo.pedrojoya.pr05.utils.IntentsUtils.existAppToOpen;
import static es.iessaladillo.pedrojoya.pr05.utils.IntentsUtils.intentAdress;
import static es.iessaladillo.pedrojoya.pr05.utils.IntentsUtils.intentCallPhone;
import static es.iessaladillo.pedrojoya.pr05.utils.IntentsUtils.intentEmail;
import static es.iessaladillo.pedrojoya.pr05.utils.IntentsUtils.intentWebSearch;
import static es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils.isValidEmail;
import static es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils.isValidPhone;
import static es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils.isValidUrl;

@SuppressWarnings("WeakerAccess")
public class FragmentProfile extends Fragment {

    FragmentProfileBinding b;
    FragmentProfileViewModel viewModel;
    MainActivityViewModel viewModelActivity;
    FragmentProfile.editAvatarListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentProfileBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelActivity = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        viewModel = ViewModelProviders.of(requireActivity()).get(FragmentProfileViewModel.class);
        setupView();
        if((viewModelActivity.getUser().getValue()!=null) && !viewModelActivity.isNewUser()){
            //For work with userActual
            viewModelActivity.setUserActual(viewModelActivity.getUser().getValue());
            fillFieldsProfile();
        }else{
            viewModelActivity.setNewUser(false);
        }

        //OnFocusListener
        b.include.txtName.setOnFocusChangeListener((v, hasFocus) -> setLblBold(b.include.lblName, hasFocus));
        b.include.txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> setLblBold(b.include.lblPhonenumber, hasFocus));
        b.include.txtEmail.setOnFocusChangeListener((v, hasFocus) -> setLblBold(b.include.lblEmail, hasFocus));
        b.include.txtAddress.setOnFocusChangeListener((v, hasFocus) -> setLblBold(b.include.lblAddress, hasFocus));
        b.include.txtWeb.setOnFocusChangeListener((v, hasFocus) -> setLblBold(b.include.lblWeb, hasFocus));
        //OnClickListener
        b.include.imgEmail.setOnClickListener(v -> {
            //Check out if exist app
            if (existAppToOpen(getContext(), intentEmail(String.valueOf(b.include.txtEmail.getText())))) {
                startActivity(intentEmail(String.valueOf(b.include.txtEmail.getText())));
            }
        });

        b.include.imgPhonenumber.setOnClickListener(v -> {
            if(existAppToOpen(getContext(),intentCallPhone(String.valueOf(b.include.txtPhonenumber.getText())))){
                startActivity(intentCallPhone(String.valueOf(b.include.txtPhonenumber.getText())));
            }
        });

        b.include.imgAddress.setOnClickListener(v -> {
            if(existAppToOpen(getContext(), intentAdress(String.valueOf(b.include.txtAddress.getText())))){
                startActivity(intentAdress(String.valueOf(b.include.txtAddress.getText())));
            }
        });

        b.include.imgWeb.setOnClickListener(v -> {
            if(existAppToOpen(getContext(), intentWebSearch(String.valueOf(b.include.txtWeb.getText())))){
                startActivity(intentWebSearch(String.valueOf(b.include.txtWeb.getText())));
            }
        });

        b.imgAvatar.setOnClickListener(v -> {
            viewModelActivity.setAvatar(viewModelActivity.getUserActual().getAvatar());
            listener.editAvatar();
        });
        b.lblAvatar.setOnClickListener(v -> {
            viewModelActivity.setAvatar(viewModelActivity.getUserActual().getAvatar());
            listener.editAvatar();
        });
        //OnEditorActionListener IME
        b.include.txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId==EditorInfo.IME_ACTION_DONE){
                save();
                InputMethodManager imm =
                        (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });

        //TextChangedListener
        b.include.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkName();
            }
        });

        b.include.txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEmail();
            }
        });

        b.include.txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPhoneNumber();
            }
        });

        b.include.txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAddress();
            }
        });

        b.include.txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkWeb();
            }
        });
    }

    private void fillFieldsProfile() {
        b.imgAvatar.setImageResource(viewModelActivity.getUserActual().getAvatar().getImageResId());
        b.lblAvatar.setText(viewModelActivity.getUserActual().getAvatar().getName());
        b.include.txtName.setText(viewModelActivity.getUserActual().getName());
        b.include.txtEmail.setText(viewModelActivity.getUserActual().getEmail());
        b.include.txtPhonenumber.setText(viewModelActivity.getUserActual().getPhoneNumber());
        b.include.txtAddress.setText(viewModelActivity.getUserActual().getAddress());
        b.include.txtWeb.setText(viewModelActivity.getUserActual().getWeb());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (editAvatarListener) context;
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

    //Function for Validate
    private boolean checkName() {
        if(TextUtils.isEmpty(b.include.txtName.getText())){
            b.include.txtName.setError(getString(R.string.main_invalid_data));
            b.include.lblName.setEnabled(false);
            return false;
        }else{
            b.include.lblName.setEnabled(true);
            return true;
        }
    }

    private boolean checkEmail() {
        if(!isValidEmail(String.valueOf(b.include.txtEmail.getText()))){
            setDisableIcon(b.include.txtEmail, b.include.lblEmail, b.include.imgEmail);
            return false;
        }else{
            setEnableIcon(b.include.lblEmail, b.include.imgEmail);
            return true;
        }
    }

    private boolean checkPhoneNumber() {
        if(!isValidPhone(String.valueOf(b.include.txtPhonenumber.getText()))){
            setDisableIcon(b.include.txtPhonenumber, b.include.lblPhonenumber, b.include.imgPhonenumber);
            return false;
        }else{
            setEnableIcon(b.include.lblPhonenumber, b.include.imgPhonenumber);
            return true;
        }
    }

    private boolean checkAddress() {
        if(TextUtils.isEmpty(b.include.txtAddress.getText())){
            setDisableIcon(b.include.txtAddress, b.include.lblAddress, b.include.imgAddress);
            return false;
        }else{
            setEnableIcon(b.include.lblAddress, b.include.imgAddress);
            return true;
        }
    }

    private boolean checkWeb() {
        if(!isValidUrl(String.valueOf(b.include.txtWeb.getText()))){
            setDisableIcon(b.include.txtWeb, b.include.lblWeb, b.include.imgWeb);
            return false;
        }else{
            setEnableIcon(b.include.lblWeb, b.include.imgWeb);
            return true;
        }
    }

        private void setEnableIcon(TextView lbl, ImageView img) {
        lbl.setEnabled(true);
        img.setEnabled(true);
    }

    private void setDisableIcon(EditText txt, TextView lbl, ImageView img) {
        txt.setError(getString(R.string.main_invalid_data));
        lbl.setEnabled(false);
        img.setEnabled(false);
    }


    //Function Label Bold
    private void setLblBold(TextView lbl, boolean hasFocus){
        if(hasFocus){
            lbl.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        }else{
            lbl.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        }
    }

        private void setupView() {
        //Set default Avatar
        if (viewModel.getAvatar()==null) {
            viewModel.setAvatar(getInstance().getDefaultAvatar());
        }
        b.imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
        b.imgAvatar.setTag(viewModel.getAvatar().getImageResId());
        b.lblAvatar.setText(viewModel.getAvatar().getName());

    }


    private void save() {
        boolean isCheckName, isCheckEmail, isCheckPhoneNumber, isCheckAddress, isCheckWeb;
        isCheckName=checkName();
        isCheckEmail=checkEmail();
        isCheckPhoneNumber=checkPhoneNumber();
        isCheckAddress=checkAddress();
        isCheckWeb=checkWeb();
        if(isCheckName && isCheckEmail && isCheckPhoneNumber && isCheckAddress && isCheckWeb){
            Snackbar.make(b.include.lblName,R.string.main_saved_succesfully, Snackbar.LENGTH_LONG).show();
            Toast.makeText(requireActivity(),"AAAAAAAAAA",Toast.LENGTH_SHORT).show();
            //intentUserToCard();
        }else{
            Snackbar.make(b.include.lblName, R.string.main_error_saving, Snackbar.LENGTH_LONG).show();
            Toast.makeText(requireActivity(),"OOOOOOO",Toast.LENGTH_SHORT).show();
        }
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface editAvatarListener {
        void editAvatar();
    }

}
