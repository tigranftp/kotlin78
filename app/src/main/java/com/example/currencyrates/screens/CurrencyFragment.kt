package com.example.currencyrates.screens

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.androidplot.xy.*
import com.example.currencyrates.MAIN
import com.example.currencyrates.POS
import com.example.currencyrates.R
import com.example.currencyrates.databinding.FragmentCurrencyBinding
import com.example.currencyrates.db.RoomRepository
import com.example.currencyrates.retrofit.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.time.LocalDate
import java.util.*

class CurrencyFragment : Fragment() {
    lateinit var binding: FragmentCurrencyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val curName =  MAIN.curList[POS].Name
        binding.curName.text = curName + " Graph"


        binding.plot.clear();

        CoroutineScope(Dispatchers.IO).launch {

            val rr = RoomRepository()
            var currencyBT = rr.getCurrencyRatesTimes()
            if (currencyBT == null) {
                val repo = Repository()
                currencyBT = repo.getCurrencyRatesForInterval(LocalDate.now().minusDays(20),LocalDate.now())
                rr.writeCurrencyRatesTimes(currencyBT)
            }


            var domainLabels = ArrayList<Number>();
            var series1Number = ArrayList<Float>()
            for (entry in currencyBT.rates.entries.iterator()) {
                domainLabels.add(entry.key.split('-').last().toInt())
                when (curName) {
                    "EUR" -> series1Number.add(entry.value.EUR)
                    "CAD"  -> series1Number.add(entry.value.CAD)
                    "GBP" -> series1Number.add(entry.value.GBP)
                }
            }
            val series1: XYSeries = SimpleXYSeries(
                series1Number.toList(), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, curName
            );

            val series1Format = LineAndPointFormatter(Color.BLUE,Color.BLACK,null,null)
            series1Format.interpolationParams = CatmullRomInterpolator.Params(100,
                CatmullRomInterpolator.Type.Centripetal)
            binding.plot.addSeries(series1,series1Format)

            val decimalFormat = DecimalFormat("#.###")
            binding.plot.graph.getLineLabelStyle(XYGraphWidget.Edge.LEFT).format = decimalFormat

            binding.plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
                override fun format(
                    obj: Any?,
                    toAppendTo: StringBuffer,
                    pos: FieldPosition
                ): StringBuffer {
                    val i = Math.round((obj as Number).toFloat())
                    return toAppendTo.append(domainLabels[i])
                }

                override fun parseObject(source: String?, pos: ParsePosition): Any? {
                    return null
                }


            }

            binding.plot.redraw();

        }

    }
}