package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article>{
    ArticleAdapter(Context context, List<Article> articles){
        super(context,0,articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.title=(TextView) convertView.findViewById(R.id.article_title);
            holder.sectionName=(TextView) convertView.findViewById(R.id.section);
            holder.date=(TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Article currArticle = getItem(position);

        if (!currArticle.getTite().isEmpty())
            holder.title.setText(currArticle.getTite());
        if (!currArticle.getSectionTitle().isEmpty())
            holder.sectionName.setText(currArticle.getSectionTitle());
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView sectionName;
        TextView date;
    }
}
