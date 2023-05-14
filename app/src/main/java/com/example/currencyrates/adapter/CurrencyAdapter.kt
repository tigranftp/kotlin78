package com.example.currencyrates.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyrates.MAIN
import com.example.currencyrates.POS
import com.example.currencyrates.R
import com.example.currencyrates.databinding.CurrencyLayoutBinding
import com.example.currencyrates.model.CurrencyModel


class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.TaskViewHolder>() {


    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CurrencyLayoutBinding.bind(view)
    }


    private lateinit var navController: NavController
    private var currencyList = ArrayList<CurrencyModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.currency_layout, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.currencyName.text = currencyList[position].Name
        holder.binding.currencyRate.text = currencyList[position].Rate.toString()
        holder.binding.card.setOnClickListener{
            POS = position
            MAIN.navController.navigate(R.id.action_mainFragment_to_currencyFragment)
        }
//        holder.binding.card.setOnClickListener {
//            POS = position
//            MAIN.navController.navigate(R.id.action_mainFragment_to_changeFragment)
//        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<CurrencyModel>) {
        currencyList = list
        notifyDataSetChanged()
    }
}