package transmissor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import javax.swing.JFileChooser;

/**
 * @author Edinei Perazzoli
 */
public class Transmissor {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket transmissor = new ServerSocket(1234); //criando socket e espera conexão na porta 1234
        System.out.println("\nPronto para iniciar conexão.\n");

        Socket receptor = transmissor.accept(); //conexão aceita; socket criado
        System.out.println("Conexão estabelecida com: " + receptor.getInetAddress().getHostAddress() + ".\n");

        try {
            ObjectOutputStream out = new ObjectOutputStream(receptor.getOutputStream());//objeto criado para saída de dados dessa classe
            String arquivoS = "C:\\Users\\Edinei\\Documents\\TasyCC.pdf";

            File arquivo = new File(arquivoS); //função para obter o tamanho do arquivo 
            long t = arquivo.length(); //coloca o tamanho do arquivo na variável "t"

            out.writeObject(Long.toString(t)); //envia o tamanho do arquivo para o Receptor
            out.writeObject(arquivo.getName()); //envia o nome do arquivo para o Receptor

            FileInputStream file = new FileInputStream(arquivoS); //leitura do arquivo
            System.out.println("Enviando...\n");
            //TimeUnit.SECONDS.sleep(1); //espera dois segundos para começar o envio

            byte[] buffer = new byte[(int) t]; //array de bytes no tamanho do arquivo a ser enviado

            while (true) {
            //loop de envio do arquivo               
            //enquanto o valor da variável "tamanho" não for -1 significa que a envio ainda não terminou
                int tamanho = file.read(buffer);
                if (tamanho == -1) {
                    break;
                }
                out.write(buffer, 0, tamanho);
            }

            out.close();

            System.out.println("O arquivo foi enviado com sucesso.\n");
            System.out.println("Tamanho do arquivo enviado " + (int) t + " Bytes.\n");
            System.out.println("Conexão com o Receptor encerrada.\n");
        } catch (IOException e) {
            System.out.println("Ocorreu algum erro na conexão e/ou no envio do arquivo.\n");
        }

        receptor.close();
        transmissor.close();
    }
}
