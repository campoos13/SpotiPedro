package persistencia;

import java.io.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogin {

    public static boolean comparaLogin(Map<String, String> loginMap, String loginCompara, String senhaCompara, String path) throws FileNotFoundException {
        DataInputStream reader = null;
        try {
            reader = new DataInputStream(new FileInputStream(path));
            while (reader.available() > 0) {
                String login = reader.readUTF();
                String senha = reader.readUTF();

                loginMap.put(login, senha);
            }
        } catch (IOException ex) {
            Logger.getLogger(FileLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

        String senhaArmazenadaStr = loginMap.get(loginCompara);
        if (senhaArmazenadaStr != null && senhaCompara.equalsIgnoreCase(senhaArmazenadaStr)) {
            return true;
        }

        return false;
    }

    /*
public static boolean comparaLogin(String loginCompara, String senhaCompara) {
    try {
        byte[] loginBytes = loginCompara.getBytes();
        byte[] senhaBytes = senhaCompara.getBytes();

        // Ler os campos gravados em um arquivo binário
        byte[] loginArmazenadoBytes = Files.readAllBytes(Path.of("caminho/do/arquivo/login.bin"));
        byte[] senhaArmazenadaBytes = Files.readAllBytes(Path.of("caminho/do/arquivo/senha.bin"));

        String loginArmazenado = new String(loginArmazenadoBytes);
        String senhaArmazenada = new String(senhaArmazenadaBytes);

        return loginCompara.equals(loginArmazenado) && senhaCompara.equals(senhaArmazenada);
    } catch (IOException e) {
        Logger.getLogger(FileLogin.class.getName()).log(Level.SEVERE, "Ocorreu um erro ao comparar o login", e);
    }
    return false;
}
     */
    public static void salvarLoginSenha(String path, Map<String, String> loginMap) {
        if (path == null || loginMap == null) {
            throw new IllegalArgumentException("Entrada inválida: path, login, e senha não podem ser nulos");
        }
        try ( DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(path))) {
            for (Map.Entry<String, String> entry : loginMap.entrySet()) {
                String login = entry.getKey();
                String senha = entry.getValue();

                outputStream.writeUTF(login);
                outputStream.writeUTF(senha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

//    private static byte[] hashPassword(String senha) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] senhaBytes = senha.getBytes();
//            byte[] hash = md.digest(senhaBytes);
//            return hash;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            // Handle or throw a custom exception, if preferred
//        }
//        return null;
//    }

