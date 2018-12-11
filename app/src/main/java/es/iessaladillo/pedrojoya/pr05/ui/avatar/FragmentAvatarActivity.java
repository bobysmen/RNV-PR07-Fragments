package es.iessaladillo.pedrojoya.pr05.ui.avatar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.utils.ResourcesUtils;

public class FragmentAvatarActivity extends Fragment {

    public static final String EXTRA_AVATAR_FROM_MAIN ="EXTRA_AVATAR_FROM_MAIN";
    public static final String EXTRA_AVATAR_TO_MAIN="EXTRA_AVATAR_TO_MAIN";
    public final long CAT1_ID=1;
    public final long CAT2_ID=2;
    public final long CAT3_ID=3;
    public final long CAT4_ID=4;
    public final long CAT5_ID=5;
    public final long CAT6_ID=6;

    private Avatar avatarIn;
    private ImageView imgAvatar1;
    private ImageView imgAvatar2;
    private ImageView imgAvatar3;
    private ImageView imgAvatar4;
    private ImageView imgAvatar5;
    private ImageView imgAvatar6;

    private List<Avatar> avatares;
    private TextView lblAvatar1;
    private TextView lblAvatar2;
    private TextView lblAvatar3;
    private TextView lblAvatar4;
    private TextView lblAvatar5;
    private TextView lblAvatar6;

    private FragmentAvatarActivityViewModel viewModel;

    @VisibleForTesting
    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_avatar, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(FragmentAvatarActivityViewModel.class);
        setupView();
//        getIntentData();

        imgAvatar1.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar1.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        imgAvatar2.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar2.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        imgAvatar3.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar3.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        imgAvatar4.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar4.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        imgAvatar5.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar5.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        imgAvatar6.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar6.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        lblAvatar1.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar1.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        lblAvatar2.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar2.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        lblAvatar3.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar3.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        lblAvatar4.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar4.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        lblAvatar5.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar5.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });

        lblAvatar6.setOnClickListener(v -> {
            allImageColorDefault();
            imgAvatar6.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        });
    }

    private void allImageColorDefault(){
        imgAvatar1.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_not_selected_image_alpha));
        imgAvatar2.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_not_selected_image_alpha));
        imgAvatar3.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_not_selected_image_alpha));
        imgAvatar4.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_not_selected_image_alpha));
        imgAvatar5.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_not_selected_image_alpha));
        imgAvatar6.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_not_selected_image_alpha));
    }

    private void setupView() {
        imgAvatar1=ActivityCompat.requireViewById(getActivity(), R.id.imgAvatar1);
        imgAvatar2=ActivityCompat.requireViewById(getActivity(), R.id.imgAvatar2);
        imgAvatar3=ActivityCompat.requireViewById(getActivity(), R.id.imgAvatar3);
        imgAvatar4=ActivityCompat.requireViewById(getActivity(), R.id.imgAvatar4);
        imgAvatar5=ActivityCompat.requireViewById(getActivity(), R.id.imgAvatar5);
        imgAvatar6=ActivityCompat.requireViewById(getActivity(), R.id.imgAvatar6);

        lblAvatar1=ActivityCompat.requireViewById(getActivity(), R.id.lblAvatar1);
        lblAvatar2=ActivityCompat.requireViewById(getActivity(), R.id.lblAvatar2);
        lblAvatar3=ActivityCompat.requireViewById(getActivity(), R.id.lblAvatar3);
        lblAvatar4=ActivityCompat.requireViewById(getActivity(), R.id.lblAvatar4);
        lblAvatar5=ActivityCompat.requireViewById(getActivity(), R.id.lblAvatar5);
        lblAvatar6=ActivityCompat.requireViewById(getActivity(), R.id.lblAvatar6);


        avatares=Database.getInstance().queryAvatars();
        imgAvatar1.setImageResource(avatares.get(0).getImageResId());
        lblAvatar1.setText(avatares.get(0).getName());
        imgAvatar2.setImageResource(avatares.get(1).getImageResId());
        lblAvatar2.setText(avatares.get(1).getName());
        imgAvatar3.setImageResource(avatares.get(2).getImageResId());
        lblAvatar3.setText(avatares.get(2).getName());
        imgAvatar4.setImageResource(avatares.get(3).getImageResId());
        lblAvatar4.setText(avatares.get(3).getName());
        imgAvatar5.setImageResource(avatares.get(4).getImageResId());
        lblAvatar5.setText(avatares.get(4).getName());
        imgAvatar6.setImageResource(avatares.get(5).getImageResId());
        lblAvatar6.setText(avatares.get(5).getName());
    }

//    private void getIntentData() {
//        Intent intent = getIntent();
//        if(intent!=null && intent.hasExtra(EXTRA_AVATAR_FROM_MAIN)){
//            avatarIn = intent.getParcelableExtra(EXTRA_AVATAR_FROM_MAIN);
//            viewModel.setAvatar(avatarIn);
//            setColorSelectAvatar(viewModel.getAvatar().getId());
//        }
//    }

    private void setColorSelectAvatar(long id) {
        if(id==CAT1_ID){
            imgAvatar1.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        }else if(id==CAT2_ID){
            imgAvatar2.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        }else if(id==CAT3_ID){
            imgAvatar3.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        }else if(id==CAT4_ID){
            imgAvatar4.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        }else if(id==CAT5_ID){
            imgAvatar5.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        }else if(id==CAT6_ID){
            imgAvatar6.setAlpha(ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha));
        }
    }


    public static void startForResult(Activity activity, int requestCode,
                                      Avatar avatar) {
        Intent intent = new Intent(activity, FragmentAvatarActivity.class);
        intent.putExtra(EXTRA_AVATAR_FROM_MAIN, avatar);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            if(imgAvatar1.getAlpha()==ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha)){
//                intentAvatarToMain(CAT1_ID);
            }else if(imgAvatar2.getAlpha()==ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha)){
//                intentAvatarToMain(CAT2_ID);
            }else if(imgAvatar3.getAlpha()==ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha)){
//                intentAvatarToMain(CAT3_ID);
            }else if(imgAvatar4.getAlpha()==ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha)){
//                intentAvatarToMain(CAT4_ID);
            }else if(imgAvatar5.getAlpha()==ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha)){
//                intentAvatarToMain(CAT5_ID);
            }else if(imgAvatar6.getAlpha()==ResourcesUtils.getFloat(getContext(), R.dimen.avatar_selected_image_alpha)){
//                intentAvatarToMain(CAT6_ID);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
