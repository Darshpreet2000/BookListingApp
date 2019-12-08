package com.example.booklistingapk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.booklistingapk.BookClass;
import com.example.booklistingapk.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class BookAdapter extends ArrayAdapter<BookClass> {
    private Context context;
    public BookAdapter(@NonNull Context context,  @NonNull List<BookClass> objects) {
        super(context, 0, objects);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list, parent, false);
        }
        BookClass currentBook=getItem(position);
        TextView title=(TextView) listItemView.findViewById(R.id.Titles);
        TextView publisher=(TextView) listItemView.findViewById(R.id.publisher);
        //TextView description=(TextView) listItemView.findViewById(R.id.description);
        TextView date=(TextView) listItemView.findViewById(R.id.date);
        title.setText(currentBook.gettitle());
        ImageView imageView=(ImageView) listItemView.findViewById(R.id.imag);
        Picasso.get().setLoggingEnabled(true);
        String check=currentBook.getImg();
        Log.v("Adapter",check);
        Picasso.get().load(currentBook.getImg()).into(imageView);
        publisher.setText(currentBook.getPublisher());
        date.setText(currentBook.getdate());
        return listItemView;
    }
}