package com.mjwenn.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mjwenn.simpletodo.model.Todo;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int NEW_TASK = 123, DETAILS_TASK = 124;

    TaskAdapter itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(
                        "idTask",
                        ((Todo)adapterView.getItemAtPosition(i)).getID());
                startActivityForResult(intent, DETAILS_TASK);
            }
        });

        loadData();

    }

    void loadData(){

        List<Todo> todos = SQLite.select().from(Todo.class).queryList();

        //Toast.makeText(this, todos.get(0).getTask() + " tasks", Toast.LENGTH_LONG).show();

        itemsAdapter = new TaskAdapter(
                this,
                R.layout.adapter_task,
                todos
        );

        lvItems.setAdapter(itemsAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TASK){
            if (resultCode == RESULT_OK){
                loadData();
            }
        }else if(requestCode == DETAILS_TASK){
            if (resultCode == RESULT_OK){
                loadData();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(this, NewEditActivity.class);
                startActivityForResult(intent, NEW_TASK);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
