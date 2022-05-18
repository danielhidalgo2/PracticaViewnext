package com.example.danielhidalgopractica1


fun concatenar(vararg words:String) : String{
    val resultado = StringBuilder()
    for(word in words){
        resultado.append(word).append(" ")
    }
    return resultado.toString()
}
fun concatenarSinEspacios(vararg words:String) : String{
    val resultado = StringBuilder()
    for(word in words){
        resultado.append(word)
    }
    return resultado.toString()
}