package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Arquivo;

/**
 *
 * @author Viviane, Andrew
 */
public class ServidorCliente implements Runnable {

    private Socket  oSocket;
    private boolean bFinaliza;
    private String  sNome;
    private Arquivo oArquivo;
    
    public ServidorCliente(Socket oSoc) {
        this.oSocket   = oSoc;
        this.bFinaliza = false;
        this.oArquivo  = new Arquivo(oSocket, this);
    }
    
    public void escreve(String msg) throws Exception {
        oArquivo.enviar(msg);
    }
    
    public void processa() throws Exception {
        while(!bFinaliza) {
            oArquivo.recebe();
        }
    }
    
    public void setNome(String sNome) {
        this.sNome = sNome;
    }
    
    public String getNome() {
        return sNome;
    }
    
    public void finalizar() {
        this.bFinaliza = true;
    }
    
    @Override
    public void run() {
        try {
           processa();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                oSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(ServidorCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
