package com.kostyali.android_edu;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final TransformationMethod NO_SHOW_PASSWORD_METHOD = PasswordTransformationMethod.getInstance();
    private static final TransformationMethod SHOW_PASSWORD_METHOD = HideReturnsTransformationMethod.getInstance();
    private TextView passwordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordTextView = findViewById(R.id.password);
        passwordTextView.setTransformationMethod(SHOW_PASSWORD_METHOD);
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClick(View view) {
        if (((RadioButton) view).isChecked())
            switch (view.getId()) {
                case R.id.show:
                    passwordTextView.setTransformationMethod(SHOW_PASSWORD_METHOD);
                    break;
                case R.id.no_show:
                    passwordTextView.setTransformationMethod(NO_SHOW_PASSWORD_METHOD);
            }
    }

    public void onButtonClick(View view) {
        String message = passwordTextView.getText().toString();

        if (message.isBlank())
            Toast.makeText(this, "Пароль відсутній", Toast.LENGTH_SHORT).show();
        else showAlertDialog(message);
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Скопійований пароль")
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Закрити", (dialogInterface, i) -> dialogInterface.cancel())
                .show();
    }
}
