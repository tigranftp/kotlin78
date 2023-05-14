package com.example.currencyrates.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyrates.MAIN
import com.example.currencyrates.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var recycler: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        initialize()
        return binding.root
    }



    private fun initialize() {
        recycler = binding.tasksRecyclerView
        recycler.adapter = MAIN.adapter
    }


}