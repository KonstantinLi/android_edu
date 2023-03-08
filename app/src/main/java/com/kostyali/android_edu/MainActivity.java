package com.kostyali.android_edu;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private static final TransformationMethod NO_SHOW_PASSWORD_METHOD = PasswordTransformationMethod.getInstance();
    private static final TransformationMethod SHOW_PASSWORD_METHOD = HideReturnsTransformationMethod.getInstance();

    private final FormFragment formFragment = new FormFragment();
    private final CopyFragment copyFragment = new CopyFragment();

    private ClipboardManager clipboardManager;
    private TextView passwordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragment(formFragment, R.id.frame_form);
        setFragment(copyFragment, R.id.frame_copy);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        passwordTextView = findViewById(R.id.password);
        passwordTextView.setTransformationMethod(SHOW_PASSWORD_METHOD);

        setVisibleCopyFragment(false);
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClick(View view) {
        if (((RadioButton) view).isChecked()) {
            TextView passwordTextView = findViewById(R.id.password);

            switch (view.getId()) {
                case R.id.show:
                    passwordTextView.setTransformationMethod(SHOW_PASSWORD_METHOD);
                    break;
                case R.id.no_show:
                    passwordTextView.setTransformationMethod(NO_SHOW_PASSWORD_METHOD);
            }
        }
    }

    public void onPasswordButtonClick(View view) {
        String message = passwordTextView.getText().toString();

        if (message.isBlank())
            Toast.makeText(this, "Пароль відсутній", Toast.LENGTH_SHORT).show();
        else {
            EditText copyPasswordText = findViewById(R.id.copy_password);
            copyPasswordText.setText(message);
            setVisibleCopyFragment(true);
        }
    }

    public void onCopyButtonClick(View view) {
        String copyText = ((EditText) findViewById(R.id.copy_password)).getText().toString();
        ClipData clipData = ClipData.newPlainText("text", copyText);
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(this, "Пароль скопійований", Toast.LENGTH_SHORT).show();

        setVisibleCopyFragment(false);
    }

    private void setVisibleCopyFragment(boolean isVisible) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (isVisible)
            fragmentTransaction.show(copyFragment);
        else fragmentTransaction.hide(copyFragment);

        fragmentTransaction.commit();
    }

    private void setFragment(Fragment fragment, int id) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
