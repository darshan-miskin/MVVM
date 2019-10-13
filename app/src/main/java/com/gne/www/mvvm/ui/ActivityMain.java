package com.gne.www.mvvm.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gne.www.mvvm.R;
import com.gne.www.mvvm.util.MyResponse;
import com.gne.www.mvvm.viewmodel.ListViewModel;
import com.gne.www.mvvm.vo.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.gne.www.mvvm.ui.ActivityEdit.EXTRA_ID;
import static com.gne.www.mvvm.ui.ActivityEdit.EXTRA_PRODUCT_NAME;
import static com.gne.www.mvvm.ui.ActivityEdit.EXTRA_QUANTITY;
import static com.gne.www.mvvm.ui.ActivityEdit.EXTRA_SUPPLIER_NAME;

public class ActivityMain extends AppCompatActivity {

    public static final int CODE_EDIT=101;
    public static final int CODE_ADD=102;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private TextView textView;

    private ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout=findViewById(R.id.swipe_refresh);
        recyclerView=findViewById(R.id.recycler_main);
        fab=findViewById(R.id.fab);
        progressBar=findViewById(R.id.progressbar);
        textView=findViewById(R.id.txt_message);

        final AdapterRecycler adapterRecycler=new AdapterRecycler();
        recyclerView.setAdapter(adapterRecycler);

        adapterRecycler.setOnItemClickListener(new AdapterRecycler.OnItemClickListener() {
            @Override
            public void OnItemClick(Item item, int position) {
                Intent intent=new Intent(ActivityMain.this, ActivityEdit.class);
                intent.putExtra(ActivityEdit.EXTRA_ITEM,item);
                intent.putExtra(ActivityEdit.EXTRA_POSITION,position);
                startActivityForResult(intent,CODE_EDIT);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityMain.this,ActivityEdit.class);
                startActivityForResult(intent,CODE_ADD);
            }
        });

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        listViewModel.getItemsLiveData().observe(this, new Observer<MyResponse<List<Item>>>() {
            @Override
            public void onChanged(MyResponse<List<Item>> items) {
                switch (items.getStatus()){
                    case SUCCESS:{
                        progressBar.setVisibility(View.GONE);
                        adapterRecycler.submitList(items.getData());
                        break;
                    }
                    case ERROR:{

                    }
                    case NULL:{

                    }
                    case FAILURE:{

                    }
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listViewModel.refreshItemsLiveData();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==CODE_EDIT && resultCode==RESULT_OK){

            String pname=intent.getStringExtra(EXTRA_PRODUCT_NAME);
            String sname=intent.getStringExtra(EXTRA_SUPPLIER_NAME);
            String quan=intent.getStringExtra(EXTRA_QUANTITY);
            String id=intent.getStringExtra(EXTRA_ID);

            Item item=new Item();
            item.setId(id);
            item.setProduct_name(pname);
            item.setSupplier(sname);
            item.setQuantity(Integer.parseInt(quan));
//            listViewModel.patchItem(item);
        }
    }
}
