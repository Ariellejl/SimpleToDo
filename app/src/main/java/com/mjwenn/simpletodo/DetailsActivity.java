package com.mjwenn.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mjwenn.simpletodo.model.Todo;
import com.mjwenn.simpletodo.model.Todo_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

public class DetailsActivity extends AppCompatActivity {

    public static int EDIT_TASK = 123;
    Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        showTask(intent);


    }

    void showTask(Intent data) {

        long idTask = data.getLongExtra("idTask", 0);

            todo = SQLite.select()
                    .from(Todo.class)
                    .where(Todo_Table.ID.eq(idTask))
                    .querySingle();

            if (todo != null) {
                ((TextView) findViewById(R.id.tvtask_details)).setText(todo.getTask());
                ((TextView) findViewById(R.id.tvdesc_details)).setText(todo.getDescription());
                ((TextView) findViewById(R.id.tvdate_details)).setText(todo.getDueDate());
                ((TextView) findViewById(R.id.tvpriority_details)).setText(getResources().getStringArray(R.array.priority_level)[todo.getPrority()]);
            }else{
                Toast.makeText(this, "Task not loaded", Toast.LENGTH_LONG).show();
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TASK) {
            if (resultCode == RESULT_OK) {
                showTask(data);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_edit:
                Intent intent = new Intent(this, NewEditActivity.class);
                intent.putExtra("idTask", todo.getID());
                startActivityForResult(intent, EDIT_TASK);
                return true;
            case R.id.action_delete:
                if (SQLite.delete().from(Todo.class).where(Todo_Table.ID.eq(todo.getID())).executeUpdateDelete() > 0){
                    setResult(RESULT_OK);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
