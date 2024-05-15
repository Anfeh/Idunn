package com.example.idunn.Logica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionUsuario {
    // Patrón para verificar contraseñas: mínimo 6 caracteres, máximo 15, al menos una mayúscula, un número y un carácter especial
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,15}$";

    // Patrón para verificar nombres de usuario: mínimo 6 caracteres, máximo 12
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{6,12}$";

    // Patrón para verificar el formato de correo electrónico
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // Método para validar la contraseña
    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Método para validar el nombre de usuario
    public static boolean isValidUsername(String username) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    // Método para validar el formato del correo electrónico
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
