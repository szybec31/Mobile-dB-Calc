package com.example.db_calc

import android.content.Intent
import kotlin.math.*
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.db_calc.databinding.MainActivityBinding
import com.example.db_calc.ui.theme.DB_CalcTheme
import kotlin.math.log10


class MainActivity : ComponentActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)

        var input_data: String= ""

        setContentView(binding.root)
        // Ustawienie pustych pol na dane
        binding.editTextId.text.clear()
        binding.Answer.text.clear()

        fun convert_unit_power(input_data: String,fromUnit:String,toUnit:String): Double{
            var x: Double = input_data.toDouble()

            if(fromUnit == "W/W" && toUnit == "dB"){
                var res: Double = 10* log10(x)
                return res
            }
            if(fromUnit == "dB" && toUnit == "W/W"){
                var res: Double = Math.pow(10.0, x / 10)
                return res
            }

            val toBaseUnit: Map<String, (Double) -> Double> = mapOf(
                "MW" to { it * 1000_000_000 }, // MW do mW
                "kW" to { it * 1000_000 }, // kW do mW
                "W" to { it * 1000 }, // W do mW
                "mW" to { it }, // mW do mW (tożsamość)
                "uW" to { it / 1000 }, // uW do mW
                "nW" to { it / 1_000_000}, // nW do mW
                "pW" to { it / 1_000_000_000}, // pW do mW

                "dBMW" to { Math.pow(10.0, it / 10)*1_000_000_000 }, // dBMW do mW
                "dBkW" to { Math.pow(10.0, it / 10)*1_000_000 }, // dBkW do mW
                "dBW" to { Math.pow(10.0, it / 10)*1_000 }, // dBW do mW
                "dBm" to { Math.pow(10.0, it / 10) }, // dBm do mW
                "dBuW" to { Math.pow(10.0, it / 10) / 1_000 }, // dBuW do mW
                "dBnW" to { Math.pow(10.0, it / 10) / 1_000_000 }, // dBnW do mW
                "dBpW" to { Math.pow(10.0, it / 10) / 1_000_000_000 } // dBpW do mW


            )

            val fromBaseUnit: Map<String, (Double) -> Double> = mapOf(

                "MW" to { it / 1000_000_000 }, // mW do MW
                "kW" to { it / 1000_000 }, //  mW do kW
                "W" to { it / 1000 }, // mW do W
                "mW" to { it }, // mW do mW (tożsamość)
                "uW" to { it * 1000 }, // uW do mW
                "nW" to { it * 1_000_000}, // nW do mW
                "pW" to { it * 1_000_000_000}, // pW do mW

                "dBMW" to { 10 * Math.log10(it) - 90 }, // mW do dBMW
                "dBkW" to { 10 * Math.log10(it) - 60 }, // mW do dBkW
                "dBW" to { 10 * Math.log10(it) - 30 }, // mW do dBW
                "dBm" to { 10 * Math.log10(it) }, // mW do dBm
                "dBuW" to { 10 * Math.log10(it) + 30 }, // mW do dBuW
                "dBnW" to { 10 * Math.log10(it) + 60 }, // mW do dBnW
                "dBpW" to { 10 * Math.log10(it) + 90 } // mW do dBnW
            )



            val baseValue = toBaseUnit[fromUnit]?.invoke(x)
                ?: throw IllegalArgumentException("Nieobsługiwana jednostka wejściowa: $fromUnit")
            return fromBaseUnit[toUnit]?.invoke(baseValue)
                ?: throw IllegalArgumentException("Nieobsługiwana jednostka wyjściowa: $toUnit")




        }

        fun convert_unit_voltage(input_data: String,fromUnit:String,toUnit:String): Double{
            var x: Double = input_data.toDouble()


            if(fromUnit == "V/V"){
                var res: Double = 20* log10(x)
                return res
            }
            if(fromUnit == "dB"){
                var res: Double = Math.pow(10.0, x / 20)
                return res
            }

            val toBaseUnit: Map<String, (Double) -> Double> = mapOf(
                "MV" to { it * 1000_000_000 }, // MW do mW
                "kV" to { it * 1000_000 }, // kW do mW
                "V" to { it * 1000 }, // W do mW
                "mV" to { it }, // mW do mW (tożsamość)
                "uV" to { it / 1000 }, // uW do mW
                "nV" to { it / 1_000_000}, // nW do mW
                "pV" to { it / 1_000_000_000}, // pW do mW

                "dBMV" to { Math.pow(10.0, it / 20)*1_000_000_000 }, // dBMW do mW
                "dBkV" to { Math.pow(10.0, it / 20)*1_000_000 }, // dBkW do mW
                "dBV" to { Math.pow(10.0, it / 20)*1_000 }, // dBW do mW
                "dBmV" to { Math.pow(10.0, it / 20) }, // dBm do mW
                "dBuV" to { Math.pow(10.0, it / 20) / 1_000 }, // dBuW do mW
                "dBnV" to { Math.pow(10.0, it / 20) / 1_000_000 }, // dBnW do mW
                "dBpV" to { Math.pow(10.0, it / 20) / 1_000_000_000 } // dBpW do mW


            )

            val fromBaseUnit: Map<String, (Double) -> Double> = mapOf(

                "MV" to { it / 1000_000_000 }, // mW do MW
                "kV" to { it / 1000_000 }, //  mW do kW
                "V" to { it / 1000 }, // mW do W
                "mV" to { it }, // mW do mW (tożsamość)
                "uV" to { it * 1000 }, // uW do mW
                "nV" to { it * 1_000_000}, // nW do mW
                "pV" to { it * 1_000_000_000}, // pW do mW

                "dBMV" to { 20 * Math.log10(it) - 180 }, // mW do dBMW
                "dBkV" to { 20 * Math.log10(it) - 120 }, // mW do dBkW
                "dBV" to { 20 * Math.log10(it) - 60 }, // mW do dBW
                "dBmV" to { 20 * Math.log10(it) }, // mW do dBm
                "dBuV" to { 20 * Math.log10(it) + 60 }, // mW do dBuW
                "dBnV" to { 20 * Math.log10(it) + 120 }, // mW do dBnW
                "dBpV" to { 20 * Math.log10(it) + 180 } // mW do dBnW
            )



            val baseValue = toBaseUnit[fromUnit]?.invoke(x)
                ?: throw IllegalArgumentException("Nieobsługiwana jednostka wejściowa: $fromUnit")
            return fromBaseUnit[toUnit]?.invoke(baseValue)
                ?: throw IllegalArgumentException("Nieobsługiwana jednostka wyjściowa: $toUnit")
        }

        val adapter = ArrayAdapter.createFromResource(this,R.array.Linear_Power_unites,R.layout.my_spinner_textview)
        adapter.setDropDownViewResource(R.layout.my_spinner_dropdown_view)

        val adapter1 = ArrayAdapter.createFromResource(this,R.array.dB_Power_unites,R.layout.my_spinner_textview)
        adapter1.setDropDownViewResource(R.layout.my_spinner_dropdown_view)

        val adapter2 = ArrayAdapter.createFromResource(this,R.array.Linear_Voltage_unites,R.layout.my_spinner_textview)
        adapter2.setDropDownViewResource(R.layout.my_spinner_dropdown_view)

        val adapter3 = ArrayAdapter.createFromResource(this,R.array.dB_Voltage_unites,R.layout.my_spinner_textview)
        adapter3.setDropDownViewResource(R.layout.my_spinner_dropdown_view)


        binding.spinner0.adapter = adapter
        binding.spinner1.adapter = adapter1

        // Przycisk do zamkniecia aplikacji
        binding.bExit.setOnClickListener{
            finishAffinity()  // Zamyka aplikację
            System.exit(0)  // Ostatecznie wyłącza aplikację
        }

        binding.binfo.setOnClickListener{
            val intent = Intent(this,Info_Activity::class.java)
            startActivity(intent)

        }


        fun handleState1() {
            // Obsługa stanu 1
            binding.switch1.text = "Power (W)"
            binding.switch2.text = "LIN --> dB"
            binding.spinner0.adapter = adapter
            binding.spinner1.adapter = adapter1

            binding.bminus.isEnabled = false
            binding.bminus.alpha = 0.5f // Ustaw przycisk jako "wyszarzony"
        }

        fun handleState2() {
            // Obsługa stanu 2
            binding.switch1.text = "Voltage (V)"
            binding.switch2.text = "LIN --> dB"
            binding.spinner0.adapter = adapter2
            binding.spinner1.adapter = adapter3

            binding.bminus.isEnabled = false
            binding.bminus.alpha = 0.5f // Ustaw przycisk jako "wyszarzony"
        }

        fun handleState3() {
            // Obsługa stanu 3
            binding.switch1.text = "Power (W)"
            binding.switch2.text = "dB --> LIN"
            binding.spinner0.adapter = adapter1
            binding.spinner1.adapter = adapter

            binding.bminus.isEnabled = true
            binding.bminus.alpha = 1f
        }

        fun handleState4() {
            // Obsługa stanu 4
            binding.switch1.text = "Voltage (V)"
            binding.switch2.text = "dB --> LIN"
            binding.spinner0.adapter = adapter3
            binding.spinner1.adapter = adapter2

            binding.bminus.isEnabled = true
            binding.bminus.alpha = 1f
        }

        fun updateState() {
            val switch1State = binding.switch1.isChecked
            val switch2State = binding.switch2.isChecked

            when {
                !switch1State && !switch2State -> {
                    // Stan 1: Switch1 = OFF, Switch2 = OFF
                    handleState1()
                }
                switch1State && !switch2State -> {
                    // Stan 2: Switch1 = ON, Switch2 = OFF
                    handleState2()
                }
                !switch1State && switch2State -> {
                    // Stan 3: Switch1 = OFF, Switch2 = ON
                    handleState3()
                }
                switch1State && switch2State -> {
                    // Stan 4: Switch1 = ON, Switch2 = ON
                    handleState4()
                }
            }
        }

        binding.bminus.isEnabled = false
        binding.bminus.alpha = 0.5f

        binding.switch1.setOnCheckedChangeListener { _, _ -> updateState()
            input_data = ""
            binding.editTextId.setText(input_data)
            binding.Answer.setText(input_data)}
        binding.switch2.setOnCheckedChangeListener { _, _ -> updateState()
            input_data = ""
            binding.editTextId.setText(input_data)
            binding.Answer.setText(input_data)}

        fun add_data_intput(x: Button, y: String){
            x.setOnClickListener()
            {
                input_data+=y
                binding.editTextId.setText(input_data)
            }
        }
        fun add_dot_data_intput(x: Button){
            x.setOnClickListener()
            {
                input_data+="."
                binding.editTextId.setText(input_data)
                binding.bdot.isEnabled = false
                binding.bdot.alpha = 0.5f
            }
        }
        fun clear_data_intput(x: Button){
            x.setOnClickListener()
            {
                input_data = ""
                binding.editTextId.setText(input_data)
                binding.Answer.setText(input_data)
                if(binding.switch2.text != "LIN --> dB"){
                    binding.bminus.isEnabled = true
                    binding.bminus.alpha = 1f
                }
                binding.bdot.isEnabled = true
                binding.bdot.alpha = 1f
            }
        }
        fun del_last_data_intput(x: Button){
            x.setOnClickListener()
            {
                input_data = input_data.dropLast(1)
                binding.editTextId.setText(input_data)
            }
        }
        fun add_minus_data_input(x: Button){
            x.setOnClickListener()
            {
                input_data = "-"+input_data
                binding.editTextId.setText(input_data)
                binding.bminus.isEnabled = false
                binding.bminus.alpha = 0.5f
            }
        }

        binding.editTextId.setOnTouchListener { _, _ -> true } // Blokuje reakcje na dotyk
        binding.editTextId.isCursorVisible = false

        binding.Answer.setOnTouchListener { _, _ -> true } // Blokuje reakcje na dotyk
        binding.Answer.isCursorVisible = false

        add_data_intput(binding.b0,"0")
        add_data_intput(binding.b1,"1")
        add_data_intput(binding.b2,"2")
        add_data_intput(binding.b3,"3")
        add_data_intput(binding.b4,"4")
        add_data_intput(binding.b5,"5")
        add_data_intput(binding.b6,"6")
        add_data_intput(binding.b7,"7")
        add_data_intput(binding.b8,"8")
        add_data_intput(binding.b9,"9")
        add_dot_data_intput(binding.bdot)
        clear_data_intput(binding.bC)
        del_last_data_intput(binding.bDel)
        add_minus_data_input(binding.bminus)

        binding.calculate.setOnClickListener(){
            if(input_data.isEmpty()){
                binding.Answer.setText("None")
                return@setOnClickListener}

            if(input_data == "." || input_data == "-"){
                binding.editTextId.setText("ERROR")
                return@setOnClickListener}

            var unit1: String = binding.spinner0.selectedItem.toString()
            var unit2: String = binding.spinner1.selectedItem.toString()

            var res: Double = 0.0

            if(binding.switch1.isChecked){
                if(unit1 == "V/V" && unit2 != "dB"){
                    binding.Answer.setText("ERROR")
                    return@setOnClickListener
                }
                if(unit1 == "dB" && unit2 != "V/V"){
                    binding.Answer.setText("ERROR")
                    return@setOnClickListener
                }
                if(unit1 != "V/V" && unit2 == "dB"){
                    binding.editTextId.setText("ERROR")
                    return@setOnClickListener
                }
                if(unit1 != "dB" && unit2 == "V/V"){
                    binding.editTextId.setText("ERROR")
                    return@setOnClickListener
                }
                res = convert_unit_voltage(input_data,unit1,unit2)
            }else{
                if(unit1 == "W/W" && unit2 != "dB"){
                    binding.Answer.setText("ERROR")
                    return@setOnClickListener
                }
                if(unit1 == "dB" && unit2 != "W/W"){
                    binding.Answer.setText("ERROR")
                    return@setOnClickListener
                }
                if(unit1 != "W/W" && unit2 == "dB"){
                    binding.editTextId.setText("ERROR")
                    return@setOnClickListener
                }
                if(unit1 != "dB" && unit2 == "W/W"){
                    binding.editTextId.setText("ERROR")
                    return@setOnClickListener
                }
                res = convert_unit_power(input_data,unit1,unit2)
            }

            // Zaokrąglij liczbę do dwóch znaczących
            fun Double.roundToSignificantFigures(n: Int): Double {
                if (this == 0.0) return 0.0
                val d = 10.0.pow(n - ceil(log10(abs(this))))
                return (this * d).roundToInt() / d
            }
            res = res.roundToSignificantFigures(2)
            var output_data: String = res.toString()
            binding.Answer.setText(output_data)

        }


    }
}
