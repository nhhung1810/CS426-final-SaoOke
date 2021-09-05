package com.example.blockchainapp.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blockchainapp.Account.RSAKey;
import com.example.blockchainapp.Constants;
import com.example.blockchainapp.MainActivity;
import com.example.blockchainapp.R;
import com.example.blockchainapp.Utils.RetrofitUtils;
import com.example.blockchainapp.Account.UserAccount;
import com.example.blockchainapp.Account.UserKey;

import java.security.KeyPair;
import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity {
    public UserKey user;
    public UserAccount account;
    private EditText edt_username;
    private EditText edt_password;
    // private EditText edt_confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handleAlreadyHaveAccountButton();
        initializeTypingNotifications();

    }

    private void initializeTypingNotifications() {
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        // edt_confirmPassword = findViewById(R.id.edt_confirmPassword);

        TextView tv_notiUsername = findViewById(R.id.tv_notiUsername);
        TextView tv_notiPassword = findViewById(R.id.tv_notiPassword);
        // TextView tv_notiConfirmPassword = findViewById(R.id.tv_notiConfirmPassword);

        // handle EditText of username
        InputFilter filter = new InputFilter() {
            // dev note: behaviour of param source is kinda weird sometimes :/ gotta research it later
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.length() + dest.length() > 20)
                    return "";
                for (int i = start; i < end; ++i) {
                    char ch = source.charAt(i);
                    if (!(('A' <= ch && ch <= 'Z') || ('a' <= ch && ch <= 'z')
                            || ('0' <= ch && ch <= '9') || ch == '_' || ch == '.'))
                        return "";
                }
                return null;
            }
        };

        edt_username.setFilters(new InputFilter[] {filter});

        edt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String txt = new String();
                txt = edt_username.getText().toString();
                if (!hasFocus) {
                    if (txt.isEmpty()) return;

                }
                else {
                    tv_notiUsername.setText("");
                }
            }
        });

    }

    private boolean checkUsername(String text) {
        return false;
    }

    private void handleAlreadyHaveAccountButton() {
        SpannableString ss = new SpannableString("Don't have an account yet? Sign up");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan, 27, 34, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        TextView textView = findViewById(R.id.tv_login);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
    }

    private boolean CheckValidCredential() {
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void HandleLogin(View view) {
        if (!CheckValidCredential()) return;
        account = new UserAccount(edt_username.getText().toString(), edt_password.getText().toString());
        KeyPair kp = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            SecurityManager.HashMethod hashMethod = SecurityManager.getAppropriateHash();
            String hashedPassword = SecurityManager.getHashedPassword( hashMethod, account.getPassword() );
            hashedPassword = hashedPassword.replaceAll("[^a-zA-Z0-9]", "");
            kp = RSAKey.parseKey(getApplicationContext(), account.getUsername() + "-" + hashedPassword);

            Constants.USERNAME = account.getUsername();
            System.out.println(Constants.USERNAME);
            Constants.PRIVATE_KEY = kp.getPrivate();
            Constants.PUBLIC_KEY = kp.getPublic();
            Constants.SESSION_ACTIVE = true;

            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Successfully registered!");
            builder.setMessage("Your private key saved: " + kp.getPrivate()
                    + " | Your public key saved: " + kp.getPublic());

            RetrofitUtils.GetBalance();
            RetrofitUtils.LoadCampaignNames(Constants.USERNAME);
            RetrofitUtils.LoadAllCampaigns();
            RetrofitUtils.GetRealPublicKey();

            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
            builder.show();


        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.toString());
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        /*
        Call<UserKey> keyCall =  RetrofitUtils.blockchainInterface.ExecutePostLogin(account);
        Log.d("Test", "Login called");
        keyCall.enqueue(new Callback<UserKey>() {
            @Override
            public void onResponse(Call<UserKey> call, Response<UserKey> response) {
                if (response.code() == 200) {
                    user = response.body();
                    Constants.PRIVATE_KEY = user.getPrivateKey();
                    Constants.PUBLIC_KEY = user.getPublicKey();
                    Constants.SESSION_ACTIVE = true;

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Successfully registered!");
                    builder.setMessage("Your private key is: " + user.getPrivateKey()
                            + " | Your public key is: " + user.getPublicKey());
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    builder.show();


                }
                else {

                    try {
                        JSONObject jObj = new JSONObject(response.body().toString());
                        Toast.makeText(LoginActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }

                    try {
                        Toast.makeText(LoginActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserKey> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

         */


    }

}