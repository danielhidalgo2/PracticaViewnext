package com.example.danielhidalgopractica1
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import org.joda.time.format.DateTimeFormat
import java.util.*

class DatePickerFragment(val listener : (year : Int, month : Int, day : Int ) -> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener  {

    private var calendario : Calendar= Calendar.getInstance()

    private var maxDate :String =""
    private var minDate: String = ""
    private var currentDate : String =""

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth,month,year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val local  = Locale("es", "ES")
        Locale.setDefault(local)
        val day = calendario.get(Calendar.DAY_OF_MONTH)
        val month = calendario.get(Calendar.MONTH)-1
        val year = calendario.get(Calendar.YEAR)

        val picker = DatePickerDialog(activity as Context,this,year,month+1,day )

        if(currentDate.isNotEmpty()){
            val fechaActualFormateada = DateTimeFormat.forPattern(Constants.defaultPatternFecha).parseDateTime(currentDate)
            picker.datePicker.updateDate(fechaActualFormateada.year,fechaActualFormateada.monthOfYear+1,fechaActualFormateada.dayOfMonth)
        }
        if(maxDate.isNotEmpty()){
            val fechaMaximaFormateada = DateTimeFormat.forPattern(Constants.defaultPatternFecha).parseDateTime(maxDate)
            picker.datePicker.maxDate=fechaMaximaFormateada.millis
        }else{
            picker.datePicker.maxDate=calendario.timeInMillis
        }

        if(minDate.isNotEmpty()){
            val fechaMinimaFormateada = DateTimeFormat.forPattern(Constants.defaultPatternFecha).parseDateTime(minDate)
            picker.datePicker.minDate=fechaMinimaFormateada.millis
        }

        return picker
    }

    fun setMaxDate(maxDateNueva : String){
        this.maxDate=maxDateNueva
    }

    fun setMinDate(minDateNueva:String){
        this.minDate=minDateNueva
    }

    fun setDate(dateNueva : String){
        this.currentDate=dateNueva
    }


}