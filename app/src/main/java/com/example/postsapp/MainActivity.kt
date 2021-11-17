package com.example.postsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.postsapp.adapter.PostAdapter
import com.example.postsapp.databinding.ActivityMainBinding
import com.example.postsapp.utils.NetworkStatus
import com.example.postsapp.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter

    private val postViewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerview()
        setUpViewModel()
        setClickListener()
    }

    private fun setClickListener() {
        binding.retryButton.setOnClickListener {
           postViewModel.getPosts()
        }
    }

    private fun setUpViewModel() {
        postViewModel.getPosts()
        postViewModel.status.observe(this, {
            when(it) {
                is NetworkStatus.Success -> {
                    binding.apply {
                        progressBar.isVisible = false
                        recyclerView.isVisible = true
                        textError.isVisible = false
                        retryButton.isVisible = false
                        errorImage.isVisible = false

                    }
                }
                is NetworkStatus.Loading -> {
                    binding.apply {
                        progressBar.isVisible = true
                        recyclerView.isVisible = false
                        textError.isVisible = false
                        retryButton.isVisible = false
                        errorImage.isVisible = false

                    }
                }
                is NetworkStatus.Error -> {
                    binding.apply {
                        progressBar.isVisible = false
                        recyclerView.isVisible = false
                        textError.isVisible = true
                        retryButton.isVisible = true
                        errorImage.isVisible = true

                    }
                }
            }
        })
        postViewModel.postList.observe(this, {
            postAdapter.submitList(it)
        })
    }

    private fun setUpRecyclerview() {
        postAdapter = PostAdapter()
        binding.recyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }


}