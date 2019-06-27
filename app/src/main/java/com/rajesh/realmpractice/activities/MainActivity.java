package com.rajesh.realmpractice.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.rajesh.realmpractice.R;
import com.rajesh.realmpractice.adapters.BookAdapter;
import com.rajesh.realmpractice.database.handlers.DBBookHandlers;
import com.rajesh.realmpractice.database.tables.DbBook;
import com.rajesh.realmpractice.utils.CustomClickListener_Callback;
import com.rajesh.realmpractice.utils.DatabaseListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView bookListView;
    private Button addButton;
    List<DbBook> dbBookList = new ArrayList<>();
    BookAdapter adapter = new BookAdapter(new ArrayList<DbBook>());
    private static int published = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addDataBtn);
        bookListView = findViewById(R.id.listView);
        configureBookList();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbBook dbBook = new DbBook();
                dbBook.setBookId((int) System.currentTimeMillis());
                dbBook.setName("Game of thrones");
                dbBook.setAuthor("Jane doe");
                dbBook.setPrice("$400");
                published = (published + 1);
                dbBook.setPublishYear(String.valueOf(published));
                DBBookHandlers.getInstance().saveBook(dbBook, new DatabaseListener<String>() {
                    @Override
                    public void success(String response) {
                        List<DbBook> dbBookList = DBBookHandlers.getInstance().getBookList();
                        adapter.updateBookList(dbBookList);
                    }

                    @Override
                    public void failure(String response) {
                        Toast.makeText(MainActivity.this, "message: " + response, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void configureBookList() {
        adapter = new BookAdapter(dbBookList);
        bookListView.setAdapter(adapter);

        DBBookHandlers.getInstance().saveBookList(dbBookList, new DatabaseListener<String>() {
            @Override
            public void success(String response) {
                dbBookList = DBBookHandlers.getInstance().getBookList();
                adapter.updateBookList(dbBookList);
            }

            @Override
            public void failure(String response) {

            }
        });


        adapter.setOnRecyclerItemClick(new CustomClickListener_Callback() {
            @Override
            public void onClick(int position) {
                DBBookHandlers.getInstance().deleteSpecificBook(dbBookList.get(position), new DatabaseListener<String>() {
                    @Override
                    public void success(String response) {
                        Toast.makeText(MainActivity.this, "message: " + response, Toast.LENGTH_SHORT).show();
                        dbBookList = DBBookHandlers.getInstance().getBookList();
                        adapter.updateBookList(dbBookList);
                    }

                    @Override
                    public void failure(String response) {
                        Toast.makeText(MainActivity.this, "message: " + response, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
