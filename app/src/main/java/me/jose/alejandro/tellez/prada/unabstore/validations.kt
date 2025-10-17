package me.jose.alejandro.tellez.prada.unabstore

import android.util.Patterns

fun validateEmail(email:String): Pair<Boolean,String>{
    return when{
        email.isEmpty()-> Pair(false,"El correo es requerido.")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches()->Pair(false,"El correo es invalido")
        email.endsWith("@test.com")-> Pair(false,"Ese email no es corporativo")
        else-> Pair(true ,"")

    }
}
fun validatePassword(password: String):Pair<Boolean,String>{
    return when{
        password.isEmpty()-> Pair(false,"La contraseña es requerido.")
        password.length<8-> Pair(false,"La contraseña debe tener almenos 8 caracteres")
        !password.any{it.isDigit()}-> Pair(false,"La contraseña debe tener almenos un número")
        else->Pair(true, "")
    }
}
fun validateName(name: String):Pair<Boolean,String>{
    return when{
        name.isEmpty()-> Pair(false,"La contraseña es requerido.")
        name.length<3-> Pair(false,"La contraseña debe tener almenos 3 caracteres")
        else->Pair(true, "")
    }
}
fun validateConfirmPassword(password: String,confirmPassword : String):Pair<Boolean,String>{
    return when{
        confirmPassword.isEmpty()-> Pair(false,"La contraseña es requerido.")
        confirmPassword != password -> Pair(false,"Las contraseñas no coinciden")
        else->Pair(true, "")
    }
}