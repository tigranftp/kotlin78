package com.example.currencyrates

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.currencyrates.adapter.CurrencyAdapter
import com.example.currencyrates.db.RoomRepository
import com.example.currencyrates.db.initRoomRepo
import com.example.currencyrates.model.CurrencyModel
import com.example.currencyrates.retrofit.Currency
import com.example.currencyrates.retrofit.CurrencyAPI
import com.example.currencyrates.retrofit.Repository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.Provider.Service
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    var adapter = CurrencyAdapter()
    var curList = ArrayList<CurrencyModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MAIN = this
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupActionBarWithNavController(navController)


        initRoomRepo(applicationContext)
        val roomRepo = RoomRepository()
        val repo = Repository()

        var currency = roomRepo.getCurrencyRates()
        if (currency == null) {
            CoroutineScope(Dispatchers.IO).launch {

                currency = repo.getCurrencyRates()
                runOnUiThread {
                    initList(currency!!)
                }
                roomRepo.writeCurrencyRates(currency!!)
            }
            return
        }
        initList(currency!!)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initList(currency: Currency) {
        curList.add(CurrencyModel("EUR", currency.rates.EUR))
        curList.add(CurrencyModel("CAD", currency.rates.CAD))
        curList.add(CurrencyModel("GBP", currency.rates.GBP))
        adapter.setList(curList)
    }


}