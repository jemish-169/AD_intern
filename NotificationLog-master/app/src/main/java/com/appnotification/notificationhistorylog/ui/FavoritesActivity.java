package com.appnotification.notificationhistorylog.ui;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appnotification.notificationhistorylog.R;


public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    static FavoriteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setTitle("Favorites");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.favorites_list);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FavoriteAdapter(this, true);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if(direction == ItemTouchHelper.LEFT) {
                    adapter.deleteFromFavorite(((FavoriteViewHolder) viewHolder).getAdapterPosition());
                }

            }
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null) {
                    final View foregroundView = ((FavoriteViewHolder) viewHolder).viewForeground;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((FavoriteViewHolder) viewHolder).viewForeground;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((FavoriteViewHolder) viewHolder).viewForeground;

                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);

            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                        int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((FavoriteViewHolder) viewHolder).viewForeground;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
                if(getSwipeDirs(recyclerView,viewHolder) == ItemTouchHelper.RIGHT){
                    ((FavoriteViewHolder) viewHolder).viewBackground_delete.setBackgroundColor(getResources().getColor(R.color.green));
                    ((FavoriteViewHolder) viewHolder).option_text.setText("ADD TO FAVORITES");
                }
            }


        }).attachToRecyclerView(recyclerView);



        if(adapter.getItemCount() == 0) {
            Toast.makeText(this, "No favorites yet.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
