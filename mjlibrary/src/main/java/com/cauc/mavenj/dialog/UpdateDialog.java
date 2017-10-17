package com.cauc.mavenj.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cauc.mavenj.service.DownloadService;
import com.cauc.mavenj.utils.NetWorkUtils;
import com.cauc.mavenj.R;

/**
 * @author qiang_xi on 2016/10/7 13:04.
 * 非强制更新对话框
 */

public class UpdateDialog extends Dialog {
    private Context context;
    private View view;
    private TextView updateTitle;
    private TextView updateTime;
    private TextView updateVersion;
    private TextView updateSize;
    private TextView updateDesc;
    private LinearLayout updateDescLayout;
    private TextView updateNetworkState;
    private Button update;
    private Button noUpdate;

    private String mDownloadUrl;
    private String mTitle;
    private String mAppTime;
    private String mVersionName;
    private float mAppSize;
    private String mAppDesc;
    private String mAppName;
    private String mFilePath;
    private String mFileName;
    private int mIconResId;
    private boolean isShowProgress = false;
    private long timeRange;

    /**
     * 兼容v4版本fragment
     */
    private Fragment mCompatFragmentCallback;
    /**
     * 兼容3.0的fragment
     */
    private android.app.Fragment mFragmentCallback;

    /**
     * 权限请求码
     */
    public static final int UPDATE_DIALOG_PERMISSION_REQUEST_CODE = 0;

    /**
     * 在activity中动态请求权限使用这个构造方法
     */
    public UpdateDialog(Context context) {
        super(context);
        setDialogTheme();
        setCanceledOnTouchOutside(false);
        this.context = context;
    }

    /**
     * 在v4包的Fragment中动态请求权限使用这个构造方法
     */
    public UpdateDialog(Context context, @NonNull Fragment fragment) {
        this(context);
        mCompatFragmentCallback = fragment;
    }

    /**
     * 在app包的Fragment中动态请求权限使用这个构造方法
     */
    public UpdateDialog(Context context, @NonNull android.app.Fragment fragment) {
        this(context);
        mFragmentCallback = fragment;
    }

    /**
     * set dialog theme(设置对话框主题)
     */
    private void setDialogTheme() {
        // android:windowNoTitle
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // android:backgroundDimEnabled默认是true的
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // android:windowBackground
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(context).inflate(R.layout.mj_checkupdatelibrary_update_dialog_layout, null);
        setContentView(view);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        //更新
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission_group.STORAGE);
                if (context.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.M
                        || Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                        || permissionStatus == PackageManager.PERMISSION_GRANTED) {
                    download();
                } else {
                    if (mCompatFragmentCallback != null) {
                        mCompatFragmentCallback.requestPermissions(new String[]{Manifest.permission.
                                WRITE_EXTERNAL_STORAGE}, UPDATE_DIALOG_PERMISSION_REQUEST_CODE);
                    } else if (mFragmentCallback != null) {
                        mFragmentCallback.requestPermissions(new String[]{Manifest.permission.
                                WRITE_EXTERNAL_STORAGE}, UPDATE_DIALOG_PERMISSION_REQUEST_CODE);
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.
                                WRITE_EXTERNAL_STORAGE}, UPDATE_DIALOG_PERMISSION_REQUEST_CODE);
                    }
                }
            }
        });
        //不更新
        noUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private int  time = 1000;
    public void download() {
        //防抖动,两次点击间隔小于1s都return;
        if (System.currentTimeMillis() - timeRange < time) {
            return;
        }
        timeRange = System.currentTimeMillis();
        setNetWorkState();
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("downloadUrl", mDownloadUrl);
        intent.putExtra("filePath", mFilePath);
        intent.putExtra("fileName", mFileName);
        intent.putExtra("iconResId", mIconResId);
        intent.putExtra("isShowProgress", isShowProgress);
        intent.putExtra("appName", mAppName);
        context.startService(intent);
        dismiss();
        Toast.makeText(context, "正在后台为您下载...", Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        //标题
        if (TextUtils.isEmpty(mTitle)) {
            updateTitle.setVisibility(View.GONE);
        } else {
            updateTitle.setText(mTitle);
        }
        //发布时间
        if (TextUtils.isEmpty(mAppTime)) {
            updateTime.setVisibility(View.GONE);
        } else {
            updateTime.setText("发布时间:" + mAppTime);
        }
        //新版版本名,eg:2.2.1
        if (TextUtils.isEmpty(mVersionName)) {
            updateVersion.setVisibility(View.GONE);
        } else {
            updateVersion.setText("版本:" + mVersionName);
        }
        //新版本app大小
        if (mAppSize == 0) {
            updateSize.setVisibility(View.GONE);
        } else {
            updateSize.setText("大小:" + mAppSize + "M");
        }
        //更新日志
        if (TextUtils.isEmpty(mAppDesc)) {
            updateDescLayout.setVisibility(View.GONE);
        } else {
            updateDesc.setText(mAppDesc);
            updateDesc.setMovementMethod(ScrollingMovementMethod.getInstance());
        }
        setNetWorkState();
    }

    /**
     * 设置网络状态
     */
    private void setNetWorkState() {
        if (NetWorkUtils.isWifiConnection(context)) {
            updateNetworkState.setText("当前为WiFi网络环境,可放心下载.");
            updateNetworkState.setTextColor(Color.parseColor("#629755"));
        } else if (NetWorkUtils.isMobileConnection(context)) {
            updateNetworkState.setText("当前为移动网络环境,下载将会消耗流量!");
            updateNetworkState.setTextColor(Color.parseColor("#BAA029"));
        } else if (!NetWorkUtils.isNetworkAvailable(context)) {
            updateNetworkState.setText("当前无网络连接,请打开网络后重试!");
            updateNetworkState.setTextColor(Color.RED);
        } else {
            updateNetworkState.setVisibility(View.GONE);
        }
    }

    private void initView() {
        updateTitle = (TextView) view.findViewById(R.id.updateTitle);
        updateTime = (TextView) view.findViewById(R.id.updateTime);
        updateVersion = (TextView) view.findViewById(R.id.updateVersion);
        updateSize = (TextView) view.findViewById(R.id.updateSize);
        updateDesc = (TextView) view.findViewById(R.id.updateDesc);
        updateDescLayout = (LinearLayout) view.findViewById(R.id.updateDescLayout);
        updateNetworkState = (TextView) view.findViewById(R.id.updateNetworkState);
        update = (Button) view.findViewById(R.id.update);
        noUpdate = (Button) view.findViewById(R.id.noUpdate);
    }

    /**
     * 设置文件下载地址
     */
    public UpdateDialog setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
        return this;
    }

    /**
     * 设置dialog显示标题
     */
    public UpdateDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 设置发布时间
     */
    public UpdateDialog setReleaseTime(String releaseTime) {
        this.mAppTime = releaseTime;
        return this;
    }

    /**
     * 设置版本名,如2.2.1
     */
    public UpdateDialog setVersionName(String versionName) {
        this.mVersionName = versionName;
        return this;
    }

    /**
     * 设置更新日志,需要自己分好段落
     */
    public UpdateDialog setUpdateDesc(String updateDesc) {
        this.mAppDesc = updateDesc;
        return this;
    }

    /**
     * 设置软件大小
     */
    public UpdateDialog setAppSize(float appSize) {
        this.mAppSize = appSize;
        return this;
    }

    /**
     * 设置文件存储路径
     */
    public UpdateDialog setFilePath(String filePath) {
        this.mFilePath = filePath;
        return this;
    }

    /**
     * 设置下载文件名
     */
    public UpdateDialog setFileName(String fileName) {
        this.mFileName = fileName;
        return this;
    }

    /**
     * 设置图标资源id,该图标用来显示在通知栏中
     */
    public UpdateDialog setIconResId(int iconResId) {
        this.mIconResId = iconResId;
        return this;
    }

    /**
     * 是否在通知栏显示下载进度(true:显示,false:不显示,默认不显示)
     */
    public UpdateDialog setShowProgress(boolean isShowProgress) {
        this.isShowProgress = isShowProgress;
        return this;
    }

    /**
     * 设置软件名称
     */
    public UpdateDialog setAppName(String appName) {
        this.mAppName = appName;
        return this;
    }
}
