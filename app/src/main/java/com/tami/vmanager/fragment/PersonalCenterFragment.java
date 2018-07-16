package com.tami.vmanager.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.activity.AccountSettingsActivity;
import com.tami.vmanager.activity.ClipPictureActivity;
import com.tami.vmanager.activity.MyCreateActivity;
import com.tami.vmanager.base.BaseFragment;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.UpdateUserIconRequest;
import com.tami.vmanager.entity.UpdateUserIconResponse;
import com.tami.vmanager.entity.UploadImageRequest;
import com.tami.vmanager.entity.UploadImageResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.GetImagePath;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.view.CircleImageView;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by why on 2018/6/12.
 * 我的
 */

public class PersonalCenterFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    private CircleImageView avatar_image;//我的头像
    private TextView full_name;//姓名
    private TextView position;//部门-职务
    private TextView my_creation;//我的创建
    private TextView account_settings;//帐号设置
    private BottomSheetDialog mBottomSheetDialog;

    public static final String HEAD_ICON_DIC = Environment
            .getExternalStorageDirectory()
            + File.separator + "TaMiExternal";
    protected final String TAG = getClass().getSimpleName();
    private File headIconFile = null;// 相册或者拍照保存的文件
    private File headClipFile = null;// 裁剪后的头像
    private String headFileNameStr = "headIcon.jpg";
    private String clipFileNameStr = "clipIcon.jpg";
    private Uri imageUri;
    private final String IMAGE_TYPE = "image/*";

    //权限相关
    public static final int REQUEST_EXTERNAL_STORAGE = 103;
    public static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int REQUEST_CAMERA = 104;
    public static final String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};

    private final int CHOOSE_PHOTO_REQUEST_CODE = 0X00;
    private final int TAKE_PHOTO_REQUEST_CODE = 0X01;
    private final int CLIP_PHOTO_BY_SELF_REQUEST_CODE = 0X02;

    private NetworkBroker networkBroker;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void initView() {
        avatar_image = findViewById(R.id.personal_center_avatar_image);
        full_name = findViewById(R.id.personal_center_full_name);
        position = findViewById(R.id.personal_center_position);
        my_creation = findViewById(R.id.personal_center_my_creation);
        account_settings = findViewById(R.id.personal_center_account_settings);
    }

    @Override
    public void initListener() {
        avatar_image.setOnClickListener(this);
        my_creation.setOnClickListener(this);
        account_settings.setOnClickListener(this);
    }

    @Override
    public void initData() {
        networkBroker = new NetworkBroker(getActivity());
        networkBroker.setCancelTag(getTAG());

        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            full_name.setText(item.getRealName());
            position.setText(item.getDepName() + "-" + item.getPositionName());
            if (!TextUtils.isEmpty(item.getIconUrl())) {
                Picasso.get().load(item.getIconUrl()).into(avatar_image);
            }
            //            else{
            //                String imageUrl = "http://img.tukuchina.cn/images/front/v/bd/c8/235563103124.jpg";
            //                Picasso.get().load(imageUrl).into(avatar_image);
            //            }
        }
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (mBottomSheetDialog != null && mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.dismiss();
            mBottomSheetDialog.setContentView(null);
            mBottomSheetDialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.personal_center_avatar_image:
                //头像
                avatarImage();
                break;
            case R.id.personal_center_my_creation:
                //我的创建
                myCreation();
                break;
            case R.id.personal_center_account_settings:
                //帐户设置
                accountSettings();
                break;
            case R.id.personal_menu_album:
                //相册
                mBottomSheetDialog.dismiss();
                xiangCe();
                break;
            case R.id.personal_menu_photograph:
                //拍照
                mBottomSheetDialog.dismiss();
                paiZhao();
                break;
            case R.id.personal_menu_cancel:
                //取消
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    @Override
    @AfterPermissionGranted(REQUEST_EXTERNAL_STORAGE)
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            //权限请求与判断
            if (EasyPermissions.hasPermissions(getContext(), PERMISSIONS_STORAGE)) {
                initHeadIconFile();
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.app_name), REQUEST_EXTERNAL_STORAGE, PERMISSIONS_STORAGE);
            }
        } else {
            initHeadIconFile();
        }
    }

    private void initHeadIconFile() {
        headIconFile = new File(HEAD_ICON_DIC);
        Log.e(TAG, "initHeadIconFile()---headIconFile.exists() : " + headIconFile.exists());
        if (!headIconFile.exists()) {
            boolean mkdirs = headIconFile.mkdirs();
            Log.e(TAG, "initHeadIconFile()---mkdirs : " + mkdirs);

        }
        headIconFile = new File(HEAD_ICON_DIC, headFileNameStr);
        headClipFile = new File(HEAD_ICON_DIC, clipFileNameStr);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult()---requestCode" + requestCode + ", resultCode : " + resultCode);
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST_CODE:
                Logger.d("拍照后返回.........");
                if (resultCode == getActivity().RESULT_OK) {
                    //调用自定义裁剪
                    clipPhotoBySelf(headIconFile.getAbsolutePath());
                }
                break;
            case CHOOSE_PHOTO_REQUEST_CODE:
                Logger.d("从相册选取照片后返回....");
                if (resultCode == getActivity().RESULT_OK) {

                    if (data != null) {
                        String filePath = "";
                        Uri originalUri = data.getData(); // 获得图片的uri
                        Logger.d("originalUri : " + originalUri);
                        if (originalUri != null) {
                            filePath = GetImagePath.getPath(getActivity(), originalUri);
                        }
                        Logger.d("filePath : " + filePath);

                        if (filePath != null && filePath.length() > 0) {
                            //调用自定义裁剪
                            clipPhotoBySelf(filePath);
                        }
                    }
                }
                break;
            case CLIP_PHOTO_BY_SELF_REQUEST_CODE:
                Logger.d("从自定义切图返回..........");
                if (resultCode == getActivity().RESULT_OK) {
                    Logger.d("onActivityResult()---headIconFile : " + headIconFile.getAbsolutePath());
                    Logger.d("onActivityResult()---headClipFile : " + headClipFile.getAbsolutePath());
                    addPicToGallery(headIconFile.getAbsolutePath());
                    addPicToGallery(headClipFile.getAbsolutePath());
                    avatar_image.setImageBitmap(BitmapFactory.decodeFile(headClipFile.getAbsolutePath()));
                    if (!TextUtils.isEmpty(headClipFile.getAbsolutePath())) {
                        uploadImage();
                    }
                } else {
                    Logger.d("onActivityResult()---resultCode : " + resultCode);
                }
                break;
        }
    }

    /**
     * 头像设置
     */
    private void avatarImage() {
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        LinearLayout linearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.show_menu_personal_pic, null);
        TextView album = linearLayout.findViewById(R.id.personal_menu_album);
        TextView photograph = linearLayout.findViewById(R.id.personal_menu_photograph);
        TextView cancel = linearLayout.findViewById(R.id.personal_menu_cancel);
        album.setOnClickListener(this);
        photograph.setOnClickListener(this);
        cancel.setOnClickListener(this);
        mBottomSheetDialog.setContentView(linearLayout);
        mBottomSheetDialog.show();
    }


    private void xiangCe() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), CHOOSE_PHOTO_REQUEST_CODE);
        } else {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType(IMAGE_TYPE);
                startActivityForResult(openAlbumIntent, CHOOSE_PHOTO_REQUEST_CODE);
            }
        }
    }

    @AfterPermissionGranted(REQUEST_CAMERA)
    private void paiZhao() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (EasyPermissions.hasPermissions(getContext(), PERMISSIONS_CAMERA)) {
                openCamera();
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                        REQUEST_CAMERA, PERMISSIONS_CAMERA);
            }
        } else {
            openCamera();
        }
    }

    /**
     * 打开系统摄像头拍照获取图片
     */
    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                imageUri = Uri.fromFile(headIconFile);
            } else {
                //FileProvider为7.0新增应用间共享文件,在7.0上暴露文件路径会报FileUriExposedException
                //为了适配7.0,所以需要使用FileProvider,具体使用百度一下即可
                imageUri = FileProvider.getUriForFile(getContext(),
                        "com.tami.vmanager.fileprovider", headIconFile);//通过FileProvider创建一个content类型的Uri
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
            Log.e(TAG, "openCamera()---intent" + intent);
        }
    }

    /**
     * 调用自定义切图方法
     *
     * @param filePath
     */
    protected void clipPhotoBySelf(String filePath) {
        Logger.d("通过自定义方式去剪辑这个照片:" + filePath);
        //进入裁剪页面,此处用的是自定义的裁剪页面而不是调用系统裁剪
        Intent intent = new Intent(getActivity(), ClipPictureActivity.class);
        intent.putExtra(ClipPictureActivity.IMAGE_PATH_ORIGINAL, filePath);
        intent.putExtra(ClipPictureActivity.IMAGE_PATH_AFTER_CROP,
                headClipFile.getAbsolutePath());
        startActivityForResult(intent, CLIP_PHOTO_BY_SELF_REQUEST_CODE);
    }

    /**
     * 把图像添加进系统相册
     *
     * @param imgPath 图像路径
     */
    private void addPicToGallery(String imgPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imgPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    /**
     * 我的创建
     */
    private void myCreation() {
        startActivity(new Intent(getActivity(), MyCreateActivity.class));
    }

    /**
     * 帐号设置
     */
    private void accountSettings() {
        startActivity(new Intent(getActivity(), AccountSettingsActivity.class));
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Logger.d("onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Logger.d("onPermissionsDenied:" + requestCode + ":" + perms.size());
    }

    /**
     * 更新头像
     *
     * @param imageUrl
     */
    public void updateUserIcon(String imageUrl) {
        UpdateUserIconRequest updateUserIconRequest = new UpdateUserIconRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            updateUserIconRequest.setUserId(String.valueOf(item.getId()));
        }
        updateUserIconRequest.setIconUrl(imageUrl);
        networkBroker.ask(updateUserIconRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                showToast(getString(R.string.replace_head_image, getString(R.string.failure)));
                return;
            }
            try {
                UpdateUserIconResponse response = (UpdateUserIconResponse) res;
                if (response.getCode() == 200) {
                    GlobaVariable.getInstance().item.setIconUrl(imageUrl);
                    showToast(getString(R.string.replace_head_image, getString(R.string.success)));
                } else {
                    showToast(getString(R.string.replace_head_image, getString(R.string.failure)));
                }
            } catch (Exception e) {
                showToast(getString(R.string.replace_head_image, getString(R.string.failure)));
                e.printStackTrace();
            }

        });
    }

    /**
     * 上传图片
     */
    private void uploadImage() {
        UploadImageRequest fmm = new UploadImageRequest();
        fmm.filePath = new String[]{headClipFile.getPath()};
        networkBroker.uploadImage(fmm, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                showToast(getString(R.string.replace_head_image, getString(R.string.failure)));
                return;
            }
            try {
                UploadImageResponse response = (UploadImageResponse) res;
                if (response.getCode() == 200) {
                    if (response.data != null) {
                        UploadImageResponse.Item item = response.data;
                        if (item.dataList != null && item.dataList.size() > 0) {
                            Logger.d("上传图片返回地址：" + item.dataList.get(0));
                            updateUserIcon(item.dataList.get(0));
                        } else {
                            showToast(getString(R.string.replace_head_image, getString(R.string.failure)));
                        }
                    }
                }
            } catch (Exception e) {
                showToast(getString(R.string.replace_head_image, getString(R.string.failure)));
                e.printStackTrace();
            }
        });
    }
}
