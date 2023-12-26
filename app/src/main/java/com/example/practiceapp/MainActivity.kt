package com.example.practiceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.practiceapp.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuote()
        binding.button2.setOnClickListener{
            getQuote()
        }
    }
    private fun getQuote(){
        setInProgress(true)

        GlobalScope.launch {
            try{
                val response =  RetrofitInstance.quoteApi.getRandomQuote()
                runOnUiThread{
                    setInProgress(false)
                    response.body()?.first()?.let{
                        setUI(it)
                    }
                }
            }
            catch (e:Exception){
                runOnUiThread{
                    setInProgress(false)
                    Toast.makeText(applicationContext,"make sure internet is on",Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    private fun setUI(quote:QuoteModelItem){
        binding.quoteText.text = quote.q
    }
    private fun  setInProgress(inProgress : Boolean){
        if(inProgress){
            binding.progressBar.visibility = View.VISIBLE
            binding.button2.visibility = View.GONE
        }else{
            binding.progressBar.visibility = View.GONE
            binding.button2.visibility = View.VISIBLE
        }
    }
}