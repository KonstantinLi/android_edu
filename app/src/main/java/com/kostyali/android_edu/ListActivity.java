package com.kostyali.android_edu;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kostyali.android_edu.model.PasswordEntry;
import com.kostyali.android_edu.model.PasswordReaderDBHelper;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.password_list);
        listView.setOnItemClickListener(this::onPasswordItemClick);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        showPasswords();
    }

    private void onPasswordItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String password = (String) adapterView.getAdapter().getItem(i);
        ClipData clipData = ClipData.newPlainText("text", password);
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(this, "Пароль скопійований", Toast.LENGTH_SHORT).show();
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showPasswords() {
        SQLiteDatabase db = new PasswordReaderDBHelper(this).getReadableDatabase();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.password_item, R.id.password_item, getPasswordsFromDB(db));
        listView.setAdapter(adapter);

        db.close();
    }

    private List<String> getPasswordsFromDB(SQLiteDatabase db) {
        Cursor cursor = db.query(PasswordEntry.TABLE_NAME, null, null, null, null, null, null);

        List<String> passwords = new ArrayList<>();
        while (cursor.moveToNext()) {
            String password = cursor.getString(cursor.getColumnIndexOrThrow(PasswordEntry.COLUMN_VALUE));
            passwords.add(password);
        }

        cursor.close();

        return passwords;
    }
}