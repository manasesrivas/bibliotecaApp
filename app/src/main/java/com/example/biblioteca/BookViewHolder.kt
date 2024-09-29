package com.example.biblioteca

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioteca.databinding.ItemBookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookViewHolder (view: View): RecyclerView.ViewHolder(view){
    private val binding = ItemBookBinding.bind(view)
    private val unVailable = R.drawable.iconamoon__unavailable_fill
    private val vailable = R.drawable.fluent__presence_available_16_regular
    private var retrofit: Retrofit = getRetrofit()
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create()).build()
//        return Retrofit.Builder().baseUrl("https://superheroapi.com/").addConverterFactory(GsonConverterFactory.create()).build()
    }


    fun bind(bookItemResponse: BookItemResponse, onItemSelected:(String)->Unit){
        val img = if(bookItemResponse.bookAvailable == "1") {
            vailable
        } else {
            unVailable
        }
        binding.tvBookTitle.text=bookItemResponse.bookTitle
        binding.tvAutor.text=bookItemResponse.bookAutor

        binding.ivBookDispobibleONo.setImageResource(img)
        itemView.setOnClickListener{
            onItemSelected(bookItemResponse.bookId)
        }
    }
}