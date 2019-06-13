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
    List<DbBook> bookList = new ArrayList<>();
    BookAdapter adapter = new BookAdapter(new ArrayList<DbBook>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addDataBtn);
        bookListView = findViewById(R.id.listView);
        DBBookHandlers.getInstance().deleteAllBooks();
        //configureBookList();
        configureBulkBookList();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbBook book = new DbBook();
                book.setBookId((int) System.currentTimeMillis());
                book.setName("Game of thrones");
                book.setAuthor("Jane doe");
                book.setPrice("$400");
                DBBookHandlers.getInstance().saveBook(book, new DatabaseListener<String>() {
                    @Override
                    public void success(String response) {
                        List<DbBook> bookList = DBBookHandlers.getInstance().getBookList();
                        adapter.updateBookList(bookList);
                    }

                    @Override
                    public void failure(String response) {

                    }
                });
            }
        });
    }

    private void configureBookList() {
        List<DbBook> bookList = DBBookHandlers.getInstance().getBookList();
        adapter = new BookAdapter(bookList);
        bookListView.setAdapter(adapter);
    }

    private void configureBulkBookList() {
        adapter = new BookAdapter(bookList);
        bookListView.setAdapter(adapter);

        for (int i = 0; i < 2; i++) {
            DbBook book = new DbBook();
            book.setBookId((int) System.currentTimeMillis() + i);
            book.setName("Harry potter " + i);
            book.setAuthor("John doe");
            book.setPrice("$400");
            bookList.add(book);
        }

        DBBookHandlers.getInstance().saveBookList(bookList, new DatabaseListener<String>() {
            @Override
            public void success(String response) {
                bookList = DBBookHandlers.getInstance().getBookList();
                adapter.updateBookList(bookList);
            }

            @Override
            public void failure(String response) {

            }
        });


        adapter.setOnRecyclerItemClick(new CustomClickListener_Callback() {
            @Override
            public void onClick(int position) {
                Toast.makeText(MainActivity.this, "message: " + position, Toast.LENGTH_SHORT).show();

                DBBookHandlers.getInstance().deleteSpecificBook(bookList.get(position), new DatabaseListener<String>() {
                    @Override
                    public void success(String response) {
                        Toast.makeText(MainActivity.this, "message: " + response, Toast.LENGTH_SHORT).show();
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
