package persistencia;

import pacote.Musica;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileColecaoMusica {
    private static final int TAM_REG_MUSICA = 102;
    private static final int TAM_TITULO_MUSICA = 40;
    private static final int POS_TITULO_MUSICA = 0;
    private static final int POS_ID_MUSICA = 40;
    private static final int POS_AUTORES_MUSICA = 44;
    private static final int POS_DATA_MUSICA = 74;
    private static final int POS_GMUSICAL_MUSICA = 84;
    private static final int POS_MINUTOS_MUSICA = 94;
    private static final int POS_SEGUNDOS_MUSICA = 98;

    public FileColecaoMusica(){};

    public static void salvar_musicas(Collection<Musica> musicas,String nomeArq){
        try {
            FileOutputStream fw = new FileOutputStream(nomeArq);
            DataOutputStream dw = new DataOutputStream(fw);

            // Para escrever umr registro criamos um array de bytes do com tamanho
            // igual a soma dos bytes dos tipos no registro
            byte[] b = new byte[TAM_REG_MUSICA];

            for (Musica m:musicas){
                // Inicializamos o array de bytes com 0
                Arrays.fill(b, (byte)0);
                // Criamos um objeto ByteBuffer que permite métodos para
                // converter tipos em bytes

                ByteBuffer bb = ByteBuffer.wrap(b);

                // Convertemos a String em bytes
       
                //    String content = "baeldung";
                //    ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes());
                //
                //    String newContent = charset.decode(byteBuffer).toString();
//
                //    assertEquals(content, newContent);
//
                // Copiamos os caracteres da string para a posicao 0 do ByteBuffer
              

                dw.write( bb.array());
            }

            fw.close();
            dw.close();


        } catch (IOException ex) {
            Logger.getLogger(FileColecaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Collection<Musica> ler(String nomeArq){
        Collection<Musica> musicas = new ArrayList<Musica>();

        try {
            FileInputStream fw = new FileInputStream(nomeArq);
            DataInputStream dw = new DataInputStream(fw);
            ByteBuffer bb = ByteBuffer.allocate(TAM_REG_MUSICA);
            byte[] b = new byte[TAM_REG_MUSICA];
            boolean eof = false;

            do{
                try {
                    if (dw.read(b, 0, TAM_REG_MUSICA)==-1){
                        eof = true;
                    }
                    else{
                        String titulo = new String(b,0,TAM_REG_MUSICA);
                        int id = ByteBuffer.wrap(b).getInt(POS_ID_MUSICA);
                        String autores = new String(b,POS_AUTORES_MUSICA,30);
                        String data = new String(b,POS_DATA_MUSICA,10);
                        String gmusical=  new String(b,POS_GMUSICAL_MUSICA,10);
                        int minutos = ByteBuffer.wrap(b).getInt(POS_MINUTOS_MUSICA);
                        int segundos = ByteBuffer.wrap(b).getInt(POS_SEGUNDOS_MUSICA);
                        musicas.add(new Musica(id, titulo, minutos, segundos, autores, data, gmusical));
                        //System.out.println(nome+" "+codigo+" "+preco+" "+quantidade);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FileColecaoMusica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while (!eof);

            try {
                fw.close();
                dw.close();
            } catch (IOException ex) {
                Logger.getLogger(FileColecaoMusica.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileColecaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (musicas.isEmpty()){
            return null;
        }
        else{
            return musicas;
        }
    }

    public static Musica lerRegistroPos(String nomeArq, int pos){
        RandomAccessFile ra;

        try {
            //Cria um arquivo de acesso direto
            ra = new RandomAccessFile(nomeArq,"r");
            //Coloca o cursor no terceiro no quarto registro (salta os 3 registros anteriores)
            ra.seek((pos-1)*TAM_REG_MUSICA);
            byte[] b = new byte[TAM_REG_MUSICA];
            //Le o registro
            ra.read(b, 0, TAM_REG_MUSICA);
            //Pega os dados do registro
            String titulo = new String(b,0,TAM_REG_MUSICA);
            int id = ByteBuffer.wrap(b).getInt(POS_ID_MUSICA);
            String autores = new String(b,POS_AUTORES_MUSICA,30);
            String data = new String(b,POS_DATA_MUSICA,10);
            String gmusical=  new String(b,POS_GMUSICAL_MUSICA,10);
            int minutos = ByteBuffer.wrap(b).getInt(POS_MINUTOS_MUSICA);
            int segundos = ByteBuffer.wrap(b).getInt(POS_SEGUNDOS_MUSICA);
            Musica m = new Musica(id, titulo, minutos, segundos, autores, data, gmusical);
            //System.out.println(nome+" "+codigo+" "+preco+" "+quantidade);
            ra.close();
            return m;

        } catch (IOException ex) {
            Logger.getLogger(FileColecaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void escreveRegistroPos(String nomeArq, int pos,
                                          String titulo,int id,String autores, int minutos,int segundos,String gmusical,String data){
        RandomAccessFile ra;

        try {
            //Cria um arquivo de acesso direto
            ra = new RandomAccessFile(nomeArq,"rw");
            //Coloca o cursor no terceiro no quarto registro (salta os 3 registros anteriores)
            ra.seek((pos-1)*TAM_REG_MUSICA);
            byte[] b = new byte[TAM_REG_MUSICA];
            //Le o registro


            Arrays.fill(b, (byte)0);
            // Criamos um objeto ByteBuffer que permite métodos para
            // converter tipos em bytes

            ByteBuffer bb = ByteBuffer.wrap(b);

            // Convertemos a String em bytes

            // Copiamos os caracteres da string para a posicao 0 do ByteBuffer
            bb.put(titulo.getBytes(),POS_TITULO_MUSICA,titulo.getBytes().length);
            // Colocamos os bytes do inteiro id a partir do byte 40
            bb.putInt(POS_ID_MUSICA, id);
            // Colocamos os bytes do string autores a partir do byte 44
            bb.put(autores.getBytes(),POS_AUTORES_MUSICA, autores.getBytes().length);
            // Colocamos os bytes da string data_musica a partir do byte 74
            bb.put(data.getBytes(),POS_DATA_MUSICA, data.getBytes().length);
            // Colocamos os bytes da string genero_musical a partir do byte 84
            bb.put(gmusical.getBytes(),POS_GMUSICAL_MUSICA,gmusical.getBytes().length);
            // Colocamos os bytes da string minutos a partir do byte 94
            bb.putInt(POS_MINUTOS_MUSICA, minutos);
            // Colocamos os bytes da string segundos a partir do byte 98
            bb.putInt(POS_SEGUNDOS_MUSICA, segundos);
            ra.write( bb.array());

            ra.close();

        } catch (IOException ex) {
            Logger.getLogger(FileColecaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}