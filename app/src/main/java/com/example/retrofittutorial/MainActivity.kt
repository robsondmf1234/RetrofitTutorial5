package com.example.retrofittutorial

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofittutorial.databinding.ActivityMainBinding
import com.example.retrofittutorial.model.Post
import com.example.retrofittutorial.repository.Repository
import retrofit2.Response

const val TAG = "Response"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        setLoadingLayout(true)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                setLoadingLayout(false)
                setVisibilityTrue()
                setLogsSuccess(response)
                setValuesOnTextViews(response)
            } else {
                setLoadingLayout(false)
                setLogError(response)
                setErrorValues(response)
            }
        })

    }

    private fun setLoadingLayout(visibility:Boolean) {
        binding.loading.root.isVisible = visibility
    }

    private fun setLogError(response: Response<Post>) {
        Log.d(TAG, response.errorBody().toString())
    }

    private fun setLogsSuccess(response: Response<Post>) {
        Log.d(TAG, response.body()?.userId.toString())
        Log.d(TAG, response.body()?.id.toString())
        Log.d(TAG, response.body()?.title.toString())
        Log.d(TAG, response.body()?.body.toString())
    }

    private fun setErrorValues(response: Response<Post>) {
        binding.error.textViewError.isVisible = true
        binding.error.textViewError.text = response.code().toString()
    }

    private fun setValuesOnTextViews(response: Response<Post>) {
        binding.success.txtUserId.text = response.body()?.userId.toString()
        binding.success.txtId.text = response.body()?.id.toString()
        binding.success.txtTitle.text = response.body()?.title.toString()
        binding.success.txtBody.text = response.body()?.body.toString()
    }

    private fun setVisibilityTrue() {
        binding.success.txtUserId.isVisible = true
        binding.success.txtId.isVisible = true
        binding.success.txtTitle.isVisible = true
        binding.success.txtBody.isVisible = true
    }
}