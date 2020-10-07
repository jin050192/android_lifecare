package com.example.lifecare.Chating;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;

    //Firebase Database 관리 객체참조변수
    FirebaseDatabase firebaseDatabase;

    //'chat'노드의 참조객체 참조변수
    DatabaseReference chatRef;
    //키저장
    String keysss;
    String[] keys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        listView = findViewById(R.id.listview);

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        chatRef = firebaseDatabase.getInstance().getReference();


        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chat'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것으 듣는 리스너 추가
        chatRef.addChildEventListener(new ChildEventListener() {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                keysss += dataSnapshot.getKey()+",";
                //null 이붙은 부분 제거
                String ssss = keysss.replace("null","");
                //규칙 을 주어서 , 마다 배열로 나눔
                keys = ssss.split(",");

                ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.listitem, keys);
                ListView listView = (ListView) findViewById(R.id.listview);
                listView.setAdapter(itemsAdapter);

                listView.setOnItemClickListener(ChatListActivity.this);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*상단바 숨기기*/
        getSupportActionBar().hide();

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String ke = keys[position];
        System.out.println("==========================="+ke);
        Intent intent = new Intent(getApplicationContext(),AdminChatActivity.class);
        intent.putExtra("ke",ke);
        startActivity(intent);
    }
}