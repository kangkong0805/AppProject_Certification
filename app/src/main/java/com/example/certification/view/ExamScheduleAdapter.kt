package com.example.certification.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.certification.databinding.ItemListBinding
import com.example.certification.jungchugi.data.Item

class ExamScheduleAdapter(val list: List<Item>): RecyclerView.Adapter<ExamScheduleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.title.text = "정보처리기능사"
            binding.schedule.text = "${item.docExamStartDt} ~ ${item.docExamEndDt}"
            binding.ghl.text = item.description
        }
    }

}