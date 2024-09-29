package com.example.biblioteca

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BookAdapter (var bookList: List<BookItemResponse> = emptyList(),
    private val onItemSelected: (String) -> Unit) : RecyclerView.Adapter<BookViewHolder>() {

        fun updateList(list: List<BookItemResponse>){
            bookList = list
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        )
    }

    override fun getItemCount()=bookList.size


    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position], onItemSelected)
    }

}