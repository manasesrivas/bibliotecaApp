package com.example.biblioteca

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.biblioteca.DetailBookActivity.Companion.EXTRA_ID
import com.example.biblioteca.databinding.ActivityListBooksBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListBooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBooksBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: BookAdapter

    private lateinit var dialog: Dialog
    private lateinit var btnSaveBook: AppCompatButton



    private lateinit var etTitle: EditText
    private lateinit var etAutor: EditText
    private lateinit var etEditorial: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUi()




        adapter = BookAdapter { navigateToDetail(it) }
        binding.rvBook.adapter = adapter
        binding.rvBook.setHasFixedSize(true)
        binding.rvBook.layoutManager = LinearLayoutManager(this)

    }

    override fun onRestart() {
        super.onRestart()
        getBooks("")
    }

    private fun getBooks(query: String?) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<BookDataResponse> =
                retrofit.create(ApiService::class.java).getBooks(query)
            if (myResponse.isSuccessful) {
                val response: BookDataResponse? = myResponse.body()
                if (response != null) {
                    runOnUiThread {
                        binding.progressBar.isVisible = false
                        adapter.updateList(response.books)

                    }
                }
            } else {
                Log.i("response", "no funciona")
            }
        }
    }

    private fun showDialogAddBook() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_book)
        btnSaveBook = dialog.findViewById(R.id.btnSave)
        etTitle = dialog.findViewById(R.id.etTitulo)
        etAutor = dialog.findViewById(R.id.etAutor)
        etEditorial = dialog.findViewById(R.id.etEditorial)
        btnSaveBook.setOnClickListener {

            if (etTitle.text.isEmpty() ||
                etAutor.text.isEmpty() ||
                etEditorial.text.isEmpty()
            ) {
                Toast.makeText(dialog.context, "porfavor completa los campos", Toast.LENGTH_LONG)
                    .show()
            } else {
                binding.progressBar.isVisible = true
                CoroutineScope(Dispatchers.IO).launch {
                    retrofit.create(ApiService::class.java).addBook(
                        etTitle.text.toString(),
                        etAutor.text.toString(),
                        etEditorial.text.toString()
                    )
                }
                dialog.hide()
            }


        }
        dialog.show()
    }

    private fun initUi() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?):Boolean{
                getBooks(query)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {getBooks("")}
                return false
            }
        })
        getBooks("")
        binding.fabAddBook.setOnClickListener {
            showDialogAddBook()
        }
    }

    private fun navigateToDetail(id: String) {
        val intent = Intent(this, DetailBookActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }



    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create()).build()
//        return Retrofit.Builder().baseUrl("https://superheroapi.com/").addConverterFactory(GsonConverterFactory.create()).build()
    }


}