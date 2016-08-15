package com.histler.appbase.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;


public class HeaderViewHolder extends BaseViewHolder {
    @Bind(android.R.id.text1)
    public TextView title;

    public HeaderViewHolder(View itemView) {
        super(itemView);
    }

    public void initValue(String text) {
        title.setText(text);
    }
}
