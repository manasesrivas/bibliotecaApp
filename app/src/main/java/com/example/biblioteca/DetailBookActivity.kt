package com.example.biblioteca

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.biblioteca.databinding.ActivityDetailBookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBookBinding
    private  lateinit var id: String
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(EXTRA_ID).orEmpty()
        getBookInformation(id)
    }

    private fun getBookInformation(id: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse =
                getRetrofit().create(ApiService::class.java).getBook(id)
            if (myResponse.isSuccessful) {

                if (myResponse.body() != null) {
                    runOnUiThread {
                        createUI(myResponse.body()!!)
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }
    }

    private fun createUI(book: BookShowResponse) {

        binding.etTitulo.setText(book.book.bookTitle)
        binding.etAutor.setText(book.book.bookAutor)
        binding.etEditorial.setText(book.book.bookEditorial)

        binding.btnSave.setOnClickListener {
            updatebook()
            onBackPressed()
        }

        binding.btnDelete.setOnClickListener{
            deletebook()
            onBackPressed()
        }
    }



    private fun deletebook(){
        CoroutineScope(Dispatchers.IO).launch {
            getRetrofit().create(ApiService::class.java).deleteBook(id)
        }
    }

    private fun updatebook(){
        CoroutineScope(Dispatchers.IO).launch {
            getRetrofit().create(ApiService::class.java).updateBook(
                id, binding.etTitulo.text.toString(), binding.etAutor.text.toString(),
                binding.etEditorial.text.toString()
            )
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }


}