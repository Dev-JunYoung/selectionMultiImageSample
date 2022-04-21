package com.charlezz.selectionsample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;

public class LookUpViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    ImageView checkBox;

    public LookUpViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        checkBox = itemView.findViewById(R.id.checkbox);
    }
    //4
    //. 추상클래스인 ItemDetails 에는 getPosition 과 getSelectionKey 라는 메소드를 구현하게끔 되어있는데,
    // 위와 같이 adapter 의 position 과 item id를 리턴시켜줍니다. (리턴 한 다음->SelectionTracker 만들기)
    public ItemDetailsLookup.ItemDetails<Long> getItemDetails(){
        return new ItemDetailsLookup.ItemDetails<Long>() {
            @Override
            public int getPosition() {
                return getAdapterPosition();
            }

            @Nullable
            @Override
            public Long getSelectionKey() {
                return getItemId();
            }

            @Override
            public boolean inSelectionHotspot(@NonNull MotionEvent e) {
                return true;
            }
        };
    }

    public void setPhoto(Photo photo) {
        Glide.with(image).load(photo.getPath()).transition(DrawableTransitionOptions.withCrossFade()).into(image);
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        if(selectionTracker!=null && selectionTracker.isSelected((long) getAdapterPosition())){
            checkBox.setImageResource(android.R.drawable.checkbox_on_background);
        }else{
            checkBox.setImageResource(android.R.drawable.checkbox_off_background);
        }
    }
}