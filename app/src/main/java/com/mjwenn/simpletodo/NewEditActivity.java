package com.mjwenn.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mjwenn.simpletodo.model.Todo;
import com.mjwenn.simpletodo.model.Todo_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Calendar;
import java.util.Locale;

public class NewEditActivity extends AppCompatActivity {

    private EditText etTask,
            etDesc;
    private Spinner spPriority;
    private DatePicker datePicker;

    Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.cancel_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Init compos
        etTask = (EditText) findViewById(R.id.ettask);
        etDesc = (EditText) findViewById(R.id.etdescription);
        spPriority = (Spinner) findViewById(R.id.sppriorite);
        datePicker = (DatePicker) findViewById(R.id.dtduedate);

        //Get task if edit
        Intent intent = getIntent();
        long idTask = intent.getLongExtra("idTask", 0);

            todo = SQLite.select()
                    .from(Todo.class)
                    .where(Todo_Table.ID.eq(idTask))
                    .querySingle();

            if (todo != null) {
                etTask.setText(todo.getTask());
                etDesc.setText(todo.getDescription());
                spPriority.setSelection(todo.getPrority());
            }else{
                Toast.makeText(this, "Task not loaded", Toast.LENGTH_LONG).show();
            }

    }

    private void setValues() {
        if (todo == null)
            todo = new Todo();

        todo.setTask(etTask.getText().toString());
        todo.setDescription(etDesc.getText().toString());
        todo.setPrority(spPriority.getSelectedItemPosition());
        todo.setDueDate(
                String.format(
                        Locale.US,
                        "%02d/%02d/%04d",
                        datePicker.getDayOfMonth(),
                        datePicker.getMonth() + 1,
                        datePicker.getYear()
                )
        );

        if (todo.getID() <= 0) {
            Calendar c = Calendar.getInstance();
            todo.setDateCreated(
                    String.format(
                            Locale.US,
                            "%04d-%02d-%02d",
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH) + 1,
                            c.get(Calendar.DAY_OF_MONTH)
                    )
            );
        }

        if (todo.getID() > 0) {
            if (todo.save())
                onFinish(todo.getID());
        } else {
            if(todo.insert() > 0)
                onFinish(todo.getID());
        }



    }

    void onFinish(long idTask){
        Intent data = new Intent();
        data.putExtra("idTask", todo.getID());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_save:
                setValues();
                return true;
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
