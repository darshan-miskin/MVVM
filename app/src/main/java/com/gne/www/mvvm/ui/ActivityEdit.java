package com.gne.www.mvvm.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gne.www.mvvm.R;
import com.gne.www.mvvm.util.MyResponse;
import com.gne.www.mvvm.viewmodel.UpdateViewModel;
import com.gne.www.mvvm.vo.Item;

public class ActivityEdit extends AppCompatActivity {

    public static final String EXTRA_ITEM="current_item";
    public static final String EXTRA_POSITION="item_position";
    public static final String EXTRA_PRODUCT_NAME="product_name";
    public static final String EXTRA_SUPPLIER_NAME="supplier_name";
    public static final String EXTRA_QUANTITY="quantity";
    public static final String EXTRA_ID="id";

    private static final String errorMessage="Cannot be blank";

    private Item item;
    private UpdateViewModel updateViewModel;
    private int itemPosition=-1;

    private EditText edtProductName, edtSupplierName, edtQuantity;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        item =(Item) getIntent().getSerializableExtra(EXTRA_ITEM);
        itemPosition=getIntent().getIntExtra(EXTRA_POSITION,-1);

        String title="";
        if(item!=null)
            title="Edit Item";
        else
            title="Add New Item";

        getSupportActionBar().setTitle(title);


        edtProductName=findViewById(R.id.edt_product_name);
        edtSupplierName=findViewById(R.id.edt_supplier_name);
        edtQuantity=findViewById(R.id.edt_quantity);
        btnSave=findViewById(R.id.btn_save);


        if(item!=null){
            edtProductName.setText(item.getProduct_name());
            edtSupplierName.setText(item.getSupplier());
            edtQuantity.setText(Integer.toString(item.getQuantity()));
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pname=edtProductName.getText().toString().trim();
                String sname=edtSupplierName.getText().toString().trim();
                String quan=edtQuantity.getText().toString().trim();
                if(pname.isEmpty()){
                    edtProductName.setError(errorMessage);
                    edtProductName.requestFocus();
                    return;
                }
                if(sname.isEmpty()){
                    edtSupplierName.setError(errorMessage);
                    edtSupplierName.requestFocus();
                    return;
                }
                if(quan.isEmpty()){
                    edtQuantity.setError(errorMessage);
                    edtQuantity.requestFocus();
                    return;
                }

                Item item=new Item();
                item.setProduct_name(pname);
                item.setSupplier(sname);
                item.setQuantity(Integer.parseInt(quan));
                item.setId(itemPosition!=-1?ActivityEdit.this.item.getId():Integer.toString((int)Math.random()));

                if(itemPosition!=-1) {
                    updateViewModel.patchItem(item, itemPosition);
                }
                else
                    updateViewModel.putItem(item);

            }
        });


        updateViewModel=ViewModelProviders.of(ActivityEdit.this).get(UpdateViewModel.class);

        updateViewModel.getIsUpdating().observe(ActivityEdit.this, new Observer<MyResponse<Boolean>>() {
            @Override
            public void onChanged(MyResponse<Boolean> aBoolean) {
                String message="";
                switch (aBoolean.getStatus()){
                    case SUCCESS:{
                        message="Item updated successfully";
                        break;
                    }
                    case ERROR:{
                        message=aBoolean.getErrorMessage();
                        break;
                    }
                    case NULL:{

                    }
                    case FAILURE:{
                        message="Something went wrong with updating";
                        break;
                    }
                }
                if(!aBoolean.getData()) {
                    new AlertDialog.Builder(ActivityEdit.this)
                            .setMessage(message)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();

                }
                else
                    Toast.makeText(ActivityEdit.this,"Updating item. Please wait",Toast.LENGTH_SHORT).show();
            }
        });

        updateViewModel.getIsAdding().observe(ActivityEdit.this, new Observer<MyResponse<Boolean>>() {
            @Override
            public void onChanged(MyResponse<Boolean> aBoolean) {
                String message="";
                switch (aBoolean.getStatus()){
                    case SUCCESS:{
                        message="Item added successfully";
                        break;
                    }
                    case ERROR:{
                        message=aBoolean.getErrorMessage();
                        break;
                    }
                    case NULL:{

                    }
                    case FAILURE:{
                        message="Something went wrong while adding";
                        break;
                    }
                }
                if(!aBoolean.getData()) {
                    new AlertDialog.Builder(ActivityEdit.this)
                            .setMessage(message)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();

                }
                else
                    Toast.makeText(ActivityEdit.this,"Adding item. Please wait",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
