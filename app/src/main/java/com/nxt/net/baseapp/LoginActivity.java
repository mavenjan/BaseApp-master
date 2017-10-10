package com.nxt.net.baseapp;import android.content.Intent;import android.databinding.DataBindingUtil;import android.os.Bundle;import android.os.Handler;import android.preference.PreferenceManager;import android.support.v7.app.AppCompatActivity;import android.view.Menu;import android.view.MenuInflater;import android.view.MenuItem;import android.view.View;import com.cauc.mavenj.databinding.ActivityLoginBinding;/** * Created by Jan Maven on 2017/9/30. * Email:cyjiang_11@163.com * Description: */public class LoginActivity extends AppCompatActivity {    public static final String PREF_KEY_FIRST_START = "com.nxt.net.baseapp.PREF_KEY_FIRST_START";    public static final int REQUEST_CODE_INTRO = 1;    private ActivityLoginBinding binding;    private boolean loggedIn = false;    private Handler loginHandler = new Handler();    private Runnable loginRunnable = new Runnable() {        @Override        public void run() {            binding.fakeLogin.setText(R.string.label_fake_login_success);            loggedIn = true;            startActivity(new Intent(LoginActivity.this, MainActivity.class));        }    };    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);        setSupportActionBar(binding.toolbar);        binding.fakeUsername.setEnabled(!loggedIn);        binding.fakePassword.setEnabled(!loggedIn);        binding.fakeLogin.setEnabled(!loggedIn);        binding.fakeLogin.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                binding.fakeUsername.setEnabled(false);                binding.fakePassword.setEnabled(false);                binding.fakeLogin.setEnabled(false);                binding.fakeLogin.setText(R.string.label_fake_login_loading);                loginHandler.postDelayed(loginRunnable, 2000);            }        });        boolean firstStart = PreferenceManager.getDefaultSharedPreferences(this)                .getBoolean(PREF_KEY_FIRST_START, true);        if (firstStart) {            Intent intent = new Intent(this, GuideActivity.class);            startActivityForResult(intent, REQUEST_CODE_INTRO);        }    }    @Override    protected void onActivityResult(int requestCode, int resultCode, Intent data) {        super.onActivityResult(requestCode, resultCode, data);        if (requestCode == REQUEST_CODE_INTRO) {            if (resultCode == RESULT_OK) {                PreferenceManager.getDefaultSharedPreferences(this).edit()                        .putBoolean(PREF_KEY_FIRST_START, false)                        .apply();            } else {                PreferenceManager.getDefaultSharedPreferences(this).edit()                        .putBoolean(PREF_KEY_FIRST_START, true)                        .apply();                //User cancelled the intro so we'll finish this activity too.                finish();            }        }    }    @Override    public boolean onCreateOptionsMenu(Menu menu) {        MenuInflater inflater = getMenuInflater();        inflater.inflate(R.menu.menu_reset_tag, menu);        return true;    }    @Override    public boolean onOptionsItemSelected(MenuItem item) {        if (item.getItemId() == R.id.menu_item_reset_first_start) {            PreferenceManager.getDefaultSharedPreferences(this).edit()                    .putBoolean(PREF_KEY_FIRST_START, true)                    .apply();            return true;        }        return super.onOptionsItemSelected(item);    }    @Override    public void onDestroy() {        loginHandler.removeCallbacks(loginRunnable);        super.onDestroy();    }}