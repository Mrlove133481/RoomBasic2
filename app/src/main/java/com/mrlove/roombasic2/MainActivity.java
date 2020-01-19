package com.mrlove.roombasic2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.mrlove.roombasic2.databinding.ActivityMainBinding;
import com.mrlove.roombasic2.domain.Word;

import java.util.List;

public class MainActivity extends AppCompatActivity {
     MyViewModel myViewModel;
    //DatabindingBinding 由框架编译时生成，负责通知界面同步更新(命名方式：xml文件名 + Binding)；
     ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DataBindingUtil 将布局文件与Activity关联,生成DatabindingBinding实例binding；
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        //获取ViewModel实例
        myViewModel = new ViewModelProvider(this,new SavedStateViewModelFactory(getApplication(),this)).get(MyViewModel.class);
        //为布局文件设置源数据
        binding.setData(myViewModel);
        //为实现LiveData的数据设置观察者，以便当数据改变时通知UI更新数据
        myViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                //用StringBuilder保证在内存中只创建了一个对象，而String += ...，在每次+的时候实际都会创建一个新的对象。
                StringBuilder str = new StringBuilder();
                for (Word word: words
                ) {
                    str.append(word.getId()).append(":").append(word.getWord()).append("=").append(word.getChineseMeaning()).append("\n");
                }
                binding.textView2.setText(str.toString());
            }
        });
        //添加
        binding.buttoninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word word1 = new Word("hello","你好");
                Word word2 = new Word("world","世界");
                myViewModel.insertWords(word1,word2);

            }
        });
        //删除所有
        binding.buttonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewModel.clearWords();

            }
        });
        //更新
        binding.buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word word = new Word("haha","哈哈");
                word.setId(40);
                myViewModel.updateWords(word);
            }
        });
        //删除某一项
        binding.buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word word = new Word("haha","哈哈");
                word.setId(40);
                myViewModel.deleteWords(word);
            }
        });


    }
}
