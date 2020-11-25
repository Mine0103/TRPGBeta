package com.mine.trpgbeta.hunting;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mine.trpgbeta.R;
import com.mine.trpgbeta.variable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class chatting1 extends AppCompatActivity {
    variable var1 = (variable)getApplication();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final EditText chatContent = findViewById(R.id.chat_content);
        Button chatSend = findViewById(R.id.chat_send);

        final ListView chatListView = findViewById(R.id.listview);

        db.collection("chatting").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                final ArrayList<HashMap<String, Object>> dataList = new ArrayList();
                for(QueryDocumentSnapshot doc : value) {
                    HashMap<String, Object> mapData = new HashMap<>();
                    mapData.put("chat_content", doc.get("chat_content"));
                    Timestamp time = (Timestamp) doc.get("create_at");
                    mapData.put("create_at", new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(time.toDate()));

                    dataList.add(mapData);
                }
                SimpleAdapter adapter = new SimpleAdapter(
                        getApplicationContext(),
                        dataList,
                        android.R.layout.simple_list_item_2,
                        new String[]{"chat_content", "create_at"},
                        new int[]{android.R.id.text1, android.R.id.text2});

                BaseAdapter adapter1 = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return dataList.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return null;
                    }

                    @Override
                    public long getItemId(int position) {
                        return 0;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View item = getLayoutInflater().inflate(R.layout.item_chatting, null);
                        TextView content = findViewById(R.id.chatting);
                        content.setText(dataList.get(position).get("chat_content").toString());
                        return item;
                    }
                };

                chatListView.setAdapter(adapter);
            }
        });

        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> chatMap = new HashMap<>();
                chatMap.put("chat_content", var1.getName()+"\n"+chatContent.getText().toString());
                chatMap.put("create_at", new Date());
                db.collection("chatting").add(chatMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        chatContent.setText("");
                    }
                });
            }
        });
    }
}