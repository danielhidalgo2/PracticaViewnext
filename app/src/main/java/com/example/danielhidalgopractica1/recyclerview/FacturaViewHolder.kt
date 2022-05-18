package com.example.practica1danielhidalgo.recyclerview
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.danielhidalgopractica1.Constants
import com.example.danielhidalgopractica1.concatenar
import com.example.danielhidalgopractica1.concatenarSinEspacios
import com.example.danielhidalgopractica1.databinding.ItemFacturaBinding
import com.example.danielhidalgopractica1.model.Factura
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*

class FacturaViewHolder(view: View, private val facturaClickListener: FacturaAdapter.OnFacturaListener) : RecyclerView.ViewHolder(view) {
    private val binding = ItemFacturaBinding.bind(view)



    fun bind(factura: Factura) {
        binding.importeFactura.text=concatenar(factura.importeOrdenacion.toString(), Constants.divisa)

        if(factura.descEstado!=Constants.opcion1) {
            binding.estadoFactura.visibility=View.VISIBLE
            binding.estadoFactura.text=factura.descEstado}
        else{
            binding.estadoFactura.visibility=View.GONE
        }

        binding.fecha.text=getFactura(factura.fecha)


        binding.factura.setOnClickListener{facturaClickListener.onFacturaClick()}

    }


    private fun getFactura(fecha : String): String{

        val local  = Locale(Constants.idioma, Constants.pais)
        val fechaNueva = DateTimeFormat.forPattern(Constants.defaultPatternFecha).parseDateTime(fecha)
        var format : DateTimeFormatter = DateTimeFormat.forPattern(Constants.tresPrimerasLetrasMesPattern).withLocale(local)
        var mes = format.print(fechaNueva)
        mes= concatenarSinEspacios(mes[0].uppercase(),mes.substring(1,3))

        format = DateTimeFormat.forPattern(Constants.diaPattern)
        val dia =format.print(fechaNueva)

        format = DateTimeFormat.forPattern(Constants.anyoPattern)
        val anyo = format.print(fechaNueva)

        return(concatenar(dia,mes,anyo))
    }
}