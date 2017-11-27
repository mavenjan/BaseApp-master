package com.nxt.net.baseapp.mvp.ui.fragment;import android.app.ProgressDialog;import android.content.DialogInterface;import android.content.Intent;import android.os.Bundle;import android.support.annotation.NonNull;import android.support.design.widget.TextInputLayout;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.EditText;import com.cauc.mavenj.app.Constant;import com.cauc.mavenj.utils.MjToastUtil;import com.cauc.mavenj.utils.SharePrefHelper;import com.jess.arms.di.component.AppComponent;import com.jess.arms.utils.ArmsUtils;import com.nxt.net.baseapp.R;import com.nxt.net.baseapp.app.C;import com.nxt.net.baseapp.app.Constants;import com.nxt.net.baseapp.base.BaseMjFragment;import com.nxt.net.baseapp.di.component.DaggerLoginComponent;import com.nxt.net.baseapp.di.module.LoginModule;import com.nxt.net.baseapp.mvp.contract.LoginContract;import com.nxt.net.baseapp.mvp.model.ResultDto;import com.nxt.net.baseapp.mvp.presenter.LoginPresenter;import com.nxt.net.baseapp.mvp.ui.activity.LoginActivity;import com.nxt.net.baseapp.utils.LoginCheck;import com.socks.library.KLog;import butterknife.BindView;import butterknife.OnClick;import butterknife.OnTextChanged;import static com.jess.arms.utils.Preconditions.checkNotNull;/** * @author Maven Jan * @time * @describe 描述 */public class LoginFragment extends BaseMjFragment<LoginPresenter> implements LoginContract.View {    @BindView(R.id.username)    EditText username;    @BindView(R.id.password)    EditText password;    @BindView(R.id.passwordWrapper)    TextInputLayout passwordWrapper;    @BindView(R.id.usernameWrapper)    TextInputLayout usernameWrapper;    @BindView(R.id.btnLogin)    Button btnLogin;    private int trueName = 0;    private int truePassWord = 0;    private ProgressDialog mProgressDialog;    /**     * 当注册后来后设置这个值     */    private String registerUsername = null;    @Override    public void introAnimate() {        super.introAnimate();        if (registerUsername == null) {            return;        }        username.setText(registerUsername);    }    public static LoginFragment newInstance() {        LoginFragment fragment = new LoginFragment();        return fragment;    }    @Override    public void setupFragmentComponent(AppComponent appComponent) {        DaggerLoginComponent //如找不到该类,请编译一下项目                .builder()                .appComponent(appComponent)                .loginModule(new LoginModule(this))                .build()                .inject(this);    }    @Override    public void initData(Bundle savedInstanceState) {        initOtherLoginBold();        btnLogin.setClickable(false);    }    /**     * 将其他登录方式加粗     */    private void initOtherLoginBold() {//        TextView tvOtherLogin = ButterKnife.findById(getRootView(), R.id.tvOtherLogin);//        TextPaint paint = tvOtherLogin.getPaint();//        paint.setFakeBoldText(true);    }    /**     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事     * <p>     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了     *     * @param data     */    @Override    public void setData(Object data) {    }    @OnClick({R.id.btnLogin, R.id.btnRegistered, R.id.tv_forget_password})    void onClick(View view) {        switch (view.getId()) {            case R.id.btnLogin:                usernameWrapper.setErrorEnabled(false);                usernameWrapper.setErrorEnabled(false);                showDialog();                mPresenter.requestLogin(username.getText().toString().trim(), password.getText().toString().trim());                break;            case R.id.btnRegistered:                setDeleted(true);                Log.e(TAG, "onClick: isornot----------->" + "进入");                LoginActivity.startFragment(C.fragment.REGISTER);                break;            case R.id.tv_forget_password:                setDeleted(true);                SharePrefHelper.put("forgetOrChange", 0);                LoginActivity.startFragment(C.fragment.CHANGEPASSWORDFRAGMENT);                break;            default:                break;        }    }    private void showDialog() {        mProgressDialog = new ProgressDialog(getActivity());        mProgressDialog.setCanceledOnTouchOutside(false);        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {            @Override            public void onCancel(DialogInterface dialog) {//				Log.d(TAG, "EMClient.getInstance().onCancel");//                progressShow = false;            }        });        mProgressDialog.setMessage(getString(R.string.Is_landing));        mProgressDialog.show();    }    private static ResultDto mResultDto;    @OnTextChanged(R.id.username)    public void usernameChanged(CharSequence text) {        usernameWrapper.setErrorEnabled(false);        mPresenter.checkUsername(text.toString());        if (LoginCheck.checkAccount(text.toString()) == null) {            trueName = 1;            mPresenter.checkUsernameUseAble(text.toString());        } else {            trueName = 0;        }        if (trueName == 1 && truePassWord == 1) {            btnLogin.setClickable(true);            btnLogin.setBackgroundResource(R.drawable.bt_shape);            btnLogin.setTextColor(getResources().getColor(R.color.colorPrimary));        } else {            btnLogin.setClickable(false);            btnLogin.setBackgroundResource(R.drawable.bt_shape_gray);            btnLogin.setTextColor(getResources().getColor(R.color.home_text_black_color));        }    }    @OnTextChanged(R.id.password)    public void passwordChanged(CharSequence text) {        passwordWrapper.setErrorEnabled(false);        mPresenter.checkPassword(text.toString());        if (LoginCheck.checkPassword(text.toString()) == null) {            truePassWord = 1;        } else {            truePassWord = 0;        }        if (trueName == 1 && truePassWord == 1) {            btnLogin.setClickable(true);            btnLogin.setBackgroundResource(R.drawable.bt_shape);            btnLogin.setTextColor(getResources().getColor(R.color.colorPrimary));        } else {            btnLogin.setClickable(false);            btnLogin.setBackgroundResource(R.drawable.bt_shape_gray);            btnLogin.setTextColor(getResources().getColor(R.color.home_text_black_color));        }    }    @Override    public void showLoading() {    }    @Override    public void hideLoading() {    }    @Override    public void showMessage(@NonNull String message) {        checkNotNull(message);        ArmsUtils.snackbarText(message);    }    @Override    public void launchActivity(@NonNull Intent intent) {        checkNotNull(intent);        ArmsUtils.startActivity(intent);    }    @Override    public void killMyself() {    }    @Override    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        return inflater.inflate(R.layout.fragment_login, container, false);    }    @Override    public void nameFormatError(String err) {        usernameWrapper.setError(err);    }    @Override    public void passwordFormatError(String err) {            passwordWrapper.setError(err);    }    @Override    public void callLoginSuccess() {        //已经登录        SharePrefHelper.put(Constants.IS_LOGIN, true);        MjToastUtil.showShort(getActivity(), "登陆成功");        mProgressDialog.dismiss();        LoginActivity myLoginActivity = (LoginActivity) getActivity();        myLoginActivity.callLoginSuccess();    }    @Override    public void callLoginFail(String failMassage) {    }}