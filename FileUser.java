package persistencia;

import pacote.*;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;
import pacote.Usuario;

public class FileUser {

    public static final String ARQUIVO_USUARIOS = "usuarios.bin";
    private static final int TAMANHO_NOME_USUARIO = 50;
    private static final int TAMANHO_LOGIN = 20;
    private static final int TAMANHO_SENHA = 30;

    //        public static List<Usuario> carregarUsuarios() {
//
//
//            try (DataInputStream stream = new DataInputStream(new FileInputStream(ARQUIVO_USUARIOS))) {
//                while (stream.available() > 0) {
//                        .add() = Serializador.ler(ARQUIVO_USUARIOS);
//                        String login = (stream, TAMANHO_LOGIN, CODIFICACAO).trim();
//                        String senha = (stream, TAMANHO_SENHA, CODIFICACAO).trim();
//                        int tipo = stream.readInt();
//
//                        Usuario usuario = criarUsuario(nomeUsuario, login, senha);//botar o tipo depois, saber como recebe input do radiobutton
//                        usuarios.add(usuario);
//
//                }
//                    } catch (EOFException e){
//                    Logger.getLogger(FileUser.class.getName()).log(Level.SEVERE, null, e);
//                    } catch(IOException e){
//                        Logger.getLogger(FileUser.class.getName()).log(Level.SEVERE, null, e);
//                    } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            return usuarios;
//                }
//    public static void salvarUsuario(String path, Usuario usuario) {
//        try ( FileOutputStream arq = new FileOutputStream(path);  ObjectOutputStream writer = new ObjectOutputStream(arq)) {
//            writer.writeObject(usuario);
//        } catch (FileNotFoundException e) {
//            System.err.println("File not found: " + e.getMessage());
//        } catch (IOException e) {
//            System.err.println("An error occurred while writing to the file: " + e.getMessage());
//        }
//    }
//    public static Usuario lerUsuario(String path) throws IOException, ClassNotFoundException {
//        try ( FileInputStream arq = new FileInputStream(path);  ObjectInputStream reader = new ObjectInputStream(arq)) {
//            Usuario usuario = (Usuario) reader.readObject();
//            return usuario;
//        } catch (FileNotFoundException e) {
//            System.err.println("File not found: " + e.getMessage());
//            return null;
//        }
//    }
    public static void salvarUsuario(String path, String login, String senha, String nome) {
        if (path == null || login == null || senha == null || nome == null) {
            throw new IllegalArgumentException("Entrada inválida: path, login, e senha não podem ser nulos");
        }

        try ( DataOutputStream output = new DataOutputStream(new FileOutputStream(path));  BufferedOutputStream bos = new BufferedOutputStream(output)) {
            byte[] loginBytes = login.getBytes();
            byte[] senhaBytes = senha.getBytes();
            byte[] nomeBytes = nome.getBytes();
            bos.write(loginBytes);
            bos.write(senhaBytes);
            bos.write(nomeBytes);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
