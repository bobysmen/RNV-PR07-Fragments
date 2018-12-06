package es.iessaladillo.pedrojoya.pr05.ui.profile;

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

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.databinding.FragmentProfileBinding;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentProfileBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FragmentProfileViewModel.class);
        setupView();
        //for change orientation
//        if (savedInstanceState==null) {
//            getIntentData();
//        }

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

//        b.imgAvatar.setOnClickListener(v -> {
//            FragmentAvatarActivity.startForResult(FragmentProfile.this, RC_IMG_AVATAR, viewModel.getAvatar());
//        });
//        b.lblAvatar.setOnClickListener(v -> {
//            FragmentAvatarActivity.startForResult(FragmentProfile.this, RC_IMG_AVATAR, viewModel.getAvatar());
//        });
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

    //No se si funcionara
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            //intentUserToCard();
        }else{
            Snackbar.make(b.include.lblName, R.string.main_error_saving, Snackbar.LENGTH_LONG).show();
        }
    }

    //    public final int RC_IMG_AVATAR=1;
//    public static final String EXTRA_USER_FROM_CARD="EXTRA_USER_FROM_CARD";
//    public static final String EXTRA_USER_TO_CARD="EXTRA_USER_TO_CARD";
//
//    private TextView lblName;
//    private EditText txtName;
//    private TextView lblPhoneNumber;
//    private EditText txtPhoneNumber;
//    private TextView lblEmail;
//    private EditText txtEmail;
//    private TextView lblAddress;
//    private EditText txtAddress;
//    private TextView lblWeb;
//    private EditText txtWeb;
//    private ImageView imgEmail;
//    private ImageView imgPhoneNumber;
//    private ImageView imgAddress;
//    private ImageView imgWeb;
//    private ImageView imgAvatar;
//    private TextView lblAvatar;
//
//    private User userIn;
//
//    private FragmentProfileViewModel viewModel;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_profile);
//        viewModel = ViewModelProviders.of(this).get(FragmentProfileViewModel.class);
//        setupView();
//        //for change orientation
//        if (savedInstanceState==null) {
//            getIntentData();
//        }
//
//        //OnFocusListener
//        txtName.setOnFocusChangeListener((v, hasFocus) -> setLblBold(lblName, hasFocus));
//        txtPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> setLblBold(lblPhoneNumber, hasFocus));
//        txtEmail.setOnFocusChangeListener((v, hasFocus) -> setLblBold(lblEmail, hasFocus));
//        txtAddress.setOnFocusChangeListener((v, hasFocus) -> setLblBold(lblAddress, hasFocus));
//        txtWeb.setOnFocusChangeListener((v, hasFocus) -> setLblBold(lblWeb, hasFocus));
//        //OnClickListener
//        imgEmail.setOnClickListener(v -> {
//            //Check out if exist app
//            if (existAppToOpen(this, intentEmail(String.valueOf(txtEmail.getText())))) {
//                startActivity(intentEmail(String.valueOf(txtEmail.getText())));
//            }
//        });
//
//        imgPhoneNumber.setOnClickListener(v -> {
//            if(existAppToOpen(this,intentCallPhone(String.valueOf(txtPhoneNumber.getText())))){
//                startActivity(intentCallPhone(String.valueOf(txtPhoneNumber.getText())));
//            }
//        });
//
//        imgAddress.setOnClickListener(v -> {
//            if(existAppToOpen(this, intentAdress(String.valueOf(txtAddress.getText())))){
//                startActivity(intentAdress(String.valueOf(txtAddress.getText())));
//            }
//        });
//
//        imgWeb.setOnClickListener(v -> {
//            if(existAppToOpen(this, intentWebSearch(String.valueOf(txtWeb.getText())))){
//                startActivity(intentWebSearch(String.valueOf(txtWeb.getText())));
//            }
//        });
//
//        imgAvatar.setOnClickListener(v -> {
//            FragmentAvatarActivity.startForResult(FragmentProfile.this, RC_IMG_AVATAR, viewModel.getAvatar());
//        });
//        lblAvatar.setOnClickListener(v -> {
//            FragmentAvatarActivity.startForResult(FragmentProfile.this, RC_IMG_AVATAR, viewModel.getAvatar());
//        });
//        //OnEditorActionListener IME
//        txtWeb.setOnEditorActionListener((v, actionId, event) -> {
//            if(actionId==EditorInfo.IME_ACTION_DONE){
//                save();
//                InputMethodManager imm =
//                        (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                return true;
//            }
//            return false;
//        });
//
//        //TextChangedListener
//        txtName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                checkName();
//            }
//        });
//
//        txtEmail.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                checkEmail();
//            }
//        });
//
//        txtPhoneNumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                checkPhoneNumber();
//            }
//        });
//
//        txtAddress.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                checkAddress();
//            }
//        });
//
//        txtWeb.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                checkWeb();
//            }
//        });
//    }
//
//    //Reception user of intent of the mainViewCard
//    private void getIntentData() {
//        Intent intent = getIntent();
//        if(intent!=null && intent.hasExtra(EXTRA_USER_FROM_CARD)){
//            userIn = intent.getParcelableExtra(EXTRA_USER_FROM_CARD);
//            //Save in viewModel for in the edit save ID user
//            viewModel.setUser(userIn);
//            viewModel.setAvatar(userIn.getAvatar());
//            fillFieldsProfile(userIn);
//        }
//    }
//
//    private void fillFieldsProfile(User userIn) {
//        txtName.setText(userIn.getName());
//        txtEmail.setText(userIn.getEmail());
//        txtPhoneNumber.setText(userIn.getPhoneNumber());
//        txtAddress.setText(userIn.getAddress());
//        txtWeb.setText(userIn.getWeb());
//        imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
//        lblAvatar.setText(viewModel.getAvatar().getName());
//    }
//
//    private void intentUserToCard(){
//        Intent intent = new Intent();
//        User userOut;
//        //if viewmodel not null then has execute getIntentData() and then has execute startForResult with user
//        if (viewModel.getUser()!=null) {
//            userOut = new User(
//                    viewModel.getUser().getId(), txtName.getText().toString(), txtEmail.getText().toString(), txtPhoneNumber.getText().toString(), txtAddress.getText().toString(), txtWeb.getText().toString(), viewModel.getAvatar());
//        //startForResult without user
//        }else{
//            //put id -1 to know that i dont know user id. In dataBaseUser change id for list size.
//            userOut = new User(
//                    -1, txtName.getText().toString(), txtEmail.getText().toString(), txtPhoneNumber.getText().toString(), txtAddress.getText().toString(), txtWeb.getText().toString(), viewModel.getAvatar());
//        }
//        intent.putExtra(EXTRA_USER_TO_CARD, userOut);
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//
//    //startForResult of the MainCardView with user
//    public static void startForResult(Activity activity, int requestCode, User user){
//        Intent intent = new Intent(activity, FragmentProfile.class);
//        intent.putExtra(EXTRA_USER_FROM_CARD, user);
//        activity.startActivityForResult(intent, requestCode);
//    }
//
//    //starForResultof the MainCardView without user
//    public static void startForResult(Activity activity, int requestCode){
//        Intent intent = new Intent(activity, FragmentProfile.class);
//        activity.startActivityForResult(intent, requestCode);
//    }
//
//    //Recepcion datos
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(resultCode==RESULT_OK && requestCode==RC_IMG_AVATAR){
//            if(data!=null && data.hasExtra(FragmentAvatarActivity.EXTRA_AVATAR_TO_MAIN)){
//                viewModel.setAvatar(data.getParcelableExtra(FragmentAvatarActivity.EXTRA_AVATAR_TO_MAIN));
//                imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
//                lblAvatar.setText(viewModel.getAvatar().getName());
//            }
//        }
//    }
//
//    //Function for Validate
//    private boolean checkName() {
//        if(TextUtils.isEmpty(txtName.getText())){
//            txtName.setError(getString(R.string.main_invalid_data));
//            lblName.setEnabled(false);
//            return false;
//        }else{
//            lblName.setEnabled(true);
//            return true;
//        }
//    }
//
//    private boolean checkEmail() {
//        if(!isValidEmail(String.valueOf(txtEmail.getText()))){
//            setDisableIcon(txtEmail, lblEmail, imgEmail);
//            return false;
//        }else{
//            setEnableIcon(lblEmail, imgEmail);
//            return true;
//        }
//    }
//
//    private boolean checkPhoneNumber() {
//        if(!isValidPhone(String.valueOf(txtPhoneNumber.getText()))){
//            setDisableIcon(txtPhoneNumber, lblPhoneNumber, imgPhoneNumber);
//            return false;
//        }else{
//            setEnableIcon(lblPhoneNumber, imgPhoneNumber);
//            return true;
//        }
//    }
//
//    private boolean checkAddress() {
//        if(TextUtils.isEmpty(txtAddress.getText())){
//            setDisableIcon(txtAddress, lblAddress, imgAddress);
//            return false;
//        }else{
//            setEnableIcon(lblAddress, imgAddress);
//            return true;
//        }
//    }
//
//    private boolean checkWeb() {
//        if(!isValidUrl(String.valueOf(txtWeb.getText()))){
//            setDisableIcon(txtWeb, lblWeb, imgWeb);
//            return false;
//        }else{
//            setEnableIcon(lblWeb, imgWeb);
//            return true;
//        }
//    }
//
//    private void setEnableIcon(TextView lbl, ImageView img) {
//        lbl.setEnabled(true);
//        img.setEnabled(true);
//    }
//
//    private void setDisableIcon(EditText txt, TextView lbl, ImageView img) {
//        txt.setError(getString(R.string.main_invalid_data));
//        lbl.setEnabled(false);
//        img.setEnabled(false);
//    }
//
//
//    //Function Label Bold
//    private void setLblBold(TextView lbl, boolean hasFocus){
//        if(hasFocus){
//            lbl.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
//        }else{
//            lbl.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
//        }
//    }
//
//    private void setupView() {
//        imgAvatar=ActivityCompat.requireViewById(this, R.id.imgAvatar);
//        lblAvatar=ActivityCompat.requireViewById(this, R.id.lblAvatar);
//        lblName=ActivityCompat.requireViewById(this,R.id.lblName);
//        txtName=ActivityCompat.requireViewById(this, R.id.txtName);
//        lblPhoneNumber=ActivityCompat.requireViewById(this, R.id.lblPhonenumber);
//        txtPhoneNumber=ActivityCompat.requireViewById(this, R.id.txtPhonenumber);
//        imgPhoneNumber=ActivityCompat.requireViewById(this, R.id.imgPhonenumber);
//        lblEmail=ActivityCompat.requireViewById(this, R.id.lblEmail);
//        txtEmail=ActivityCompat.requireViewById(this, R.id.txtEmail);
//        imgEmail=ActivityCompat.requireViewById(this, R.id.imgEmail);
//        lblAddress=ActivityCompat.requireViewById(this, R.id.lblAddress);
//        txtAddress=ActivityCompat.requireViewById(this, R.id.txtAddress);
//        imgAddress=ActivityCompat.requireViewById(this, R.id.imgAddress);
//        lblWeb=ActivityCompat.requireViewById(this, R.id.lblWeb);
//        txtWeb=ActivityCompat.requireViewById(this, R.id.txtWeb);
//        imgWeb=ActivityCompat.requireViewById(this, R.id.imgWeb);
//        //Set default Avatar
//        if (viewModel.getAvatar()==null) {
//            viewModel.setAvatar(getInstance().getDefaultAvatar());
//        }
//        imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
//        imgAvatar.setTag(viewModel.getAvatar().getImageResId());
//        lblAvatar.setText(viewModel.getAvatar().getName());
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.mnuSave) {
//            save();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void save() {
//        boolean isCheckName, isCheckEmail, isCheckPhoneNumber, isCheckAddress, isCheckWeb;
//        isCheckName=checkName();
//        isCheckEmail=checkEmail();
//        isCheckPhoneNumber=checkPhoneNumber();
//        isCheckAddress=checkAddress();
//        isCheckWeb=checkWeb();
//        if(isCheckName && isCheckEmail && isCheckPhoneNumber && isCheckAddress && isCheckWeb){
//            Snackbar.make(lblName,R.string.main_saved_succesfully, Snackbar.LENGTH_LONG).show();
//            intentUserToCard();
//        }else{
//            Snackbar.make(lblName, R.string.main_error_saving, Snackbar.LENGTH_LONG).show();
//        }
//    }

}
