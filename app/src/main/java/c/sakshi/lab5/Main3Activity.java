package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {

    int noteid = -1;
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", noteid);

        if (noteid != -1) {
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }
    }

    public void onClick(View view) {

        EditText editText = (EditText) findViewById(R.id.editText);
        String content = editText.getText().toString();

        Context context = getApplicationContext();
        sqLiteDatabase = openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);
        dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTE_" + (Main2Activity.notes.size() + 1);
            dbHelper.saveNotes(username, date, title, content);
        } else {
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNotes(username, date, title, content);
        }

        goToActivity2();
    }

    public void goToActivity2() {

        Intent intent = new Intent(this, c.sakshi.lab5.Main2Activity.class);
        startActivity(intent);
    }
}