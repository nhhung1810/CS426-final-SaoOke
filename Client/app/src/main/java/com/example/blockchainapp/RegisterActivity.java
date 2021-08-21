package com.example.blockchainapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    public String username;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        handleAlreadyHaveAccountButton();
        initializeTypingNotifications();



        username = new String();
        password = new String();

    }

    private void initializeTypingNotifications() {
        EditText edt_username = findViewById(R.id.edt_username);
        EditText edt_password = findViewById(R.id.edt_password);
        EditText edt_confirmPassword = findViewById(R.id.edt_confirmPassword);

        TextView tv_notiUsername = findViewById(R.id.tv_notiUsername);
        TextView tv_notiPassword = findViewById(R.id.tv_notiPassword);
        TextView tv_notiConfirmPassword = findViewById(R.id.tv_notiConfirmPassword);

        // handle EditText of username
        // dev note: when pasting a sequence of char with invalid ones, the whole current will be delete
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = 0; i < source.length(); ++i) {
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
        SpannableString ss = new SpannableString("Already have an account? Log in");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
//                startActivity(new Intent(this, LogInActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan, 25, 31, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        TextView textView = findViewById(R.id.tv_login);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
    }
}