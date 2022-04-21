package com.charlezz.selectionsample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQ_CODE_PERMISSION = 0;

    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;

    SelectionTracker<Long> selectionTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_CODE_PERMISSION );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_CODE_PERMISSION){
            for(int grantResult :grantResults){
                if(grantResult == PackageManager.PERMISSION_DENIED){
                    finish();
                    return;
                }
            }
            setupUI();
        }
    }

    private void setupUI(){
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this);
        recyclerView.setAdapter(photoAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        setupSelectionTracker();
        photoAdapter.setSelectionTracker(selectionTracker);
    }
    //5 SelectionTracker 만들기
    //Warning : SelectionTracker 는 반드시 RecyclerView 에
    // Adapter 가 참조된 이후에 만들어야 합니다.
    // 그렇지 않으면 IllegalArgumentException 이 발생됩니다.
/*selectionId : 선택내용 대한 Id를 지정합니다.
recyclerView : 선택내용을 추적할 RecyclerView 를 지정합니다.
keyProvider : 캐시를 위한 선택되는 아이템의 Key 제공자
itemDetailsLookup : RecyclerView 아이템의 대한 정보
storage : saved state 에서 키를 저장하기 위한 전략*/
    private void setupSelectionTracker(){
        selectionTracker = new SelectionTracker.Builder<>(
                "selection_id",
                recyclerView,
                new StableIdKeyProvider(recyclerView),
                new PhotoDetailsLookUp(recyclerView),
                StorageStrategy.createLongStorage())
                //어떠한 선택을 할지 결정할 수 있게 합니다. Selection 라이브러리에서
                // 제공하는 SelectionPredicates.createSelectionAnything()을 쓸 수도 있고,
                // 자신만의 SelectionPredicate 를 만들 수도 있습니다.
                .withSelectionPredicate(SelectionPredicates.<Long>createSelectAnything())
                .build();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }
}
