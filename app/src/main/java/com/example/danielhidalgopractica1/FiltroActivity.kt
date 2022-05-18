package com.example.danielhidalgopractica1
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.danielhidalgopractica1.databinding.ActivityFiltrosBinding
import com.example.danielhidalgopractica1.model.WrapperFiltro
import com.google.gson.Gson
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


class FiltroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFiltrosBinding
    var valorSeekBar: Int =0
    private lateinit var fecha1: DateTime
    private lateinit var fecha2: DateTime



    lateinit var wrapperFiltro: WrapperFiltro


    override fun onCreate(savedInstanceState: Bundle?) {
        wrapperFiltro =
            Gson().fromJson(intent.getStringExtra(Constants.wrapper).toString(), WrapperFiltro::class.java)

        super.onCreate(savedInstanceState)
        binding = ActivityFiltrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
    }


    private fun bind() {

        bindeoConDatosWrapper()

        binding.importe.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                valorSeekBar = progress
                binding.valorImporte.text =concatenar(valorSeekBar.toString(),"€")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                wrapperFiltro.setImporteFiltro(valorSeekBar)
            }
        })

        binding.fechaDesde.setOnClickListener {
            showDateDialog(binding.fechaDesde.id)
        }

        binding.fechaHasta.setOnClickListener {
            showDateDialog(binding.fechaHasta.id)
        }


        binding.botonCerrar.setOnClickListener {
            recogerYValidarCheckBox()
            finish()
        }

        binding.botonEliminarFiltros.setOnClickListener {

            wrapperFiltro.setImporteFiltro(0)
            wrapperFiltro.setEstadosFiltro(ArrayList<String>())
            wrapperFiltro.setFecha_desde("")
            wrapperFiltro.setFecha_hasta("")
            binding.valorImporte.text=Constants.valorIniciarSlider
            binding.importe.progress=0
            bind()

        }

        binding.botonFiltrar.setOnClickListener {
            recogerYValidarCheckBox()

            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra(Constants.wrapperFiltro, Gson().toJson(wrapperFiltro).toString())

            setResult(RESULT_OK,intent)

            finish()
        }
    }


    private fun onDateSelected(day: Int, month: Int, year: Int, id: Int) {
        val ff = DateTimeFormat.forPattern(Constants.defaultPatternFecha)
        if (id == R.id.fechaDesde) {
            fecha1 = DateTimeFormat.forPattern(Constants.defaultPatternFecha).parseDateTime("$day/${month + 1}/$year")

            binding.fechaDesde.text = concatenarSinEspacios(day.toString(),"/",(month + 1).toString(),"/",year.toString())

            wrapperFiltro.setFecha_desde(ff.print(fecha1))


        } else {
            fecha2 = DateTimeFormat.forPattern(Constants.defaultPatternFecha).parseDateTime("$day/${month + 1}/$year")

            println(fecha2)
            binding.fechaHasta.text = concatenarSinEspacios(day.toString(),"/",(month + 1).toString(),"/",year.toString())

            wrapperFiltro.setFecha_hasta(ff.print(fecha2))

        }
    }

    private fun recogerYValidarCheckBox() {


        if (binding.opcion1.isChecked) {
            wrapperFiltro.introducirEstadoFiltro(Constants.opcion1)
        }else{
            wrapperFiltro.eliminarEstadoFiltro(Constants.opcion1)
        }

        if (binding.opcion2.isChecked) {
            wrapperFiltro.introducirEstadoFiltro(Constants.opcion2)
        }else{
            wrapperFiltro.eliminarEstadoFiltro(Constants.opcion2)
        }

        if (binding.opcion3.isChecked) {
            wrapperFiltro.introducirEstadoFiltro(Constants.opcion3)
        }else{
            wrapperFiltro.eliminarEstadoFiltro(Constants.opcion3)
        }

        if (binding.opcion4.isChecked) {
            wrapperFiltro.introducirEstadoFiltro(Constants.opcion4)
        }else{
            wrapperFiltro.eliminarEstadoFiltro(Constants.opcion4)
        }

        if (binding.opcion5.isChecked) {
            wrapperFiltro.introducirEstadoFiltro(Constants.opcion5)

        }else{
            wrapperFiltro.eliminarEstadoFiltro(Constants.opcion5)
        }


    }

    private fun pintarCheckBox(datosPrevios:List<String>){

        binding.opcion1.isChecked = datosPrevios.contains(Constants.opcion1)
        binding.opcion2.isChecked = datosPrevios.contains(Constants.opcion2)
        binding.opcion3.isChecked = datosPrevios.contains(Constants.opcion3)
        binding.opcion4.isChecked = datosPrevios.contains(Constants.opcion4)
        binding.opcion5.isChecked = datosPrevios.contains(Constants.opcion5)
    }

    private fun bindeoConDatosWrapper(){

        binding.importe.progress=wrapperFiltro.getImporteFiltro()
        binding.importe.max = wrapperFiltro.getImporteMaximo()
        binding.maximo.text = concatenar(wrapperFiltro.importeMaximo.toString(),"€")
        binding.valorImporte.text = wrapperFiltro.getImporteFiltro().toString()

        pintarCheckBox(wrapperFiltro.estadosFiltro)

        if(wrapperFiltro.getFecha_desde().isEmpty()){
            binding.fechaDesde.text=getString(R.string.dia_mes_año)

        }else{
            binding.fechaDesde.text=wrapperFiltro.getFecha_desde()
        }

        if(wrapperFiltro.getFecha_hasta().isEmpty()){
            binding.fechaHasta.text=getString(R.string.dia_mes_año)
        }else{
            binding.fechaHasta.text=wrapperFiltro.getFecha_hasta()

        }

    }

    private fun showDateDialog(id: Int) {
        val datePicker =
            DatePickerFragment { day, month, year -> onDateSelected(day, month, year, id) }

        if(id == R.id.fechaDesde){
            if(wrapperFiltro.getFecha_hasta().isNotEmpty()){
                datePicker.setMaxDate(wrapperFiltro.getFecha_hasta())
            }
            if(wrapperFiltro.getFecha_desde().isNotEmpty()){
                datePicker.setDate(wrapperFiltro.getFecha_desde())
            }
        }


        if(id == R.id.fechaHasta){
            if(wrapperFiltro.getFecha_desde().isNotEmpty()){
                datePicker.setMinDate(wrapperFiltro.getFecha_desde())
            }
            if(wrapperFiltro.getFecha_hasta().isNotEmpty()){
                datePicker.setDate(wrapperFiltro.getFecha_hasta())
            }

        }
        datePicker.show(supportFragmentManager, "datePicker")


    }



}