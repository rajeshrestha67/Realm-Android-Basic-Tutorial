package com.rajesh.realmpractice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rajesh.realmpractice.R;
import com.rajesh.realmpractice.database.tables.DbBook;
import com.rajesh.realmpractice.utils.AppController;
import com.rajesh.realmpractice.utils.CustomClickListener_Callback;

import java.util.List;

public class BookAdapter extends BaseAdapter {


    private List<DbBook> bookList;
    private CustomClickListener_Callback customClickListener_callback;


    public BookAdapter(List<DbBook> bookList) {
        this.bookList = bookList;
    }

    public void setOnRecyclerItemClick(CustomClickListener_Callback customClickListener_callback) {
        this.customClickListener_callback = customClickListener_callback;
    }


    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) AppController.getmInstance().getMainContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_book_item, null);
        }
        TextView nameTV = convertView.findViewById(R.id.nameTV);
        TextView authorTV = convertView.findViewById(R.id.authorNameTV);
        TextView priceTV = convertView.findViewById(R.id.priceTV);
        TextView deleteTV = convertView.findViewById(R.id.deleteTV);

        DbBook book = bookList.get(position);
        nameTV.setText(book.getName());
        authorTV.setText(book.getAuthor());
        priceTV.setText(book.getPrice());

        deleteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customClickListener_callback.onClick(position);
            }
        });
        return convertView;
    }


    public void updateBookList(List<DbBook> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }
}
