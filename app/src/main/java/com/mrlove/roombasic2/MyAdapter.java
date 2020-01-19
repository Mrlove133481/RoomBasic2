package com.mrlove.roombasic2;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mrlove.roombasic2.databinding.CellNormalBinding;
import com.mrlove.roombasic2.domain.Word;

import java.util.ArrayList;
import java.util.List;

//RecyclerView内容管理器类
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static final String TAG = "mrLove" ;
    private List<Word> allWords = new ArrayList<>();

    //返回所有的数据
    void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    //当适配器创建的时候调用
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //RecyclerView中调用Databinding，绑定布局，获取binding实例
        CellNormalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cell_normal, parent, false);
        //把binding作为参数，返回一个自定义的MyViewHolder
        return new MyViewHolder(binding);
    }
    //当调用ViewHolder是响应
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //获取当前位置的一行数据
        Word word = allWords.get(position);
        //获取一个binding
        CellNormalBinding binding = holder.getBinding();
        //设置数据
        binding.textNumber.setText(String.valueOf(position + 1)); //让页面显示从1开始，而不用显示word.getId数据库中实际的位置
        binding.textEnglish.setText(word.getWord());
        binding.textChinese.setText(word.getChineseMeaning());
    }

    @Override
    public int getItemCount() {
        //返回一个数据的容量
        return allWords.size();
    }
    //把onCreateViewHolder方法中的binding存起来，可以下次再用。这样做的好处就是不必每次都到布局文件中去拿到你的View，提高了效率。
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CellNormalBinding binding;

        MyViewHolder(CellNormalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        CellNormalBinding getBinding() {
            return binding;
        }

    }
}
