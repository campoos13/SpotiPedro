package persistencia;

import pacote.Usuario;

import java.io.*;

//Os atributos estáticos estão vinculados à classe e não ao objeto portanto nao podem ser Serializados
public class Serializador { //Se a ideia estiver correta, escrever os metodos gravarMusicas/lerMusicas
    public static void gravar(String path, Object objeto) throws FileNotFoundException, IOException { //tratar as exceções
        FileOutputStream arqSaida = new FileOutputStream(path);
        ObjectOutputStream writer = new ObjectOutputStream(arqSaida);

        writer.writeObject(objeto);
        arqSaida.flush();
        writer.reset();
        writer.close();
    }

    public static Usuario ler(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream arq = new FileInputStream(path);
        ObjectInputStream reader = new ObjectInputStream(arq);

        Usuario usuario = (Usuario) reader.readObject();
        reader.close();
        arq.close();
        return usuario;
    }

    /*
    *public static ObjectOutputStream abreStream(String path) throws FileNotFoundException, IOException { //tratar as exceções
        ObjectOutputStream writer;
        if(!path.isEmpty()){
            FileOutputStream arqSaida = new FileOutputStream(path, true);
            writer = new AppendingObjectOutputStream(arqSaida);
        } else{
            FileOutputStream arqSaida = new FileOutputStream(path);
            writer = new ObjectOutputStream(arqSaida);
        }
        return writer;
    }
    public static void gravar(String path, Object objeto) throws FileNotFoundException, IOException { //tratar as exceções

        ObjectOutputStream writer = abreStream(path);

        writer.writeObject(objeto);
        writer.reset();
        writer.flush();
        writer.close();

    }

    *
    *
    * fileOutputStream = new FileOutputStream("abc.dat",true);
     outputBuffer = new BufferedOutputStream(fileOutputStream);
     objectStream = new AppendableObjectOutputStream(outputBuffer);
     BucketUpdate b1 = new BucketUpdate("getAllProducts1",null,"1",null);
     BucketUpdate b2 = new BucketUpdate("getAllProducts2",null,"2",null);
     BucketUpdate b3 = new BucketUpdate("getAllProducts3",null,"3",null);
     objectStream.writeObject(b1);
     objectStream.writeObject(b2);
     objectStream.writeObject(b3);
     objectStream.close();
    * */

}
