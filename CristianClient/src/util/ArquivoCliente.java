
package util;

import cliente.controller.Controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author Viviane, Andrew
 */
public class ArquivoCliente extends ArquivoAbstrato {
    
    private Controller oControl;
    
    public ArquivoCliente(Socket soc, Controller con) {
        this.oControl = con;
        setInstancias(soc);
    }
    
    private void setInstancias(Socket oSoc) {
        try {
            this.oDtaIn  = new DataInputStream(oSoc.getInputStream());
            this.oDtaOut = new DataOutputStream(oSoc.getOutputStream());
            
            this.oInSR   = new InputStreamReader(oSoc.getInputStream());
            this.oBufR   = new BufferedReader(oInSR);
            
            this.oOutSW = new OutputStreamWriter(oSoc.getOutputStream());
            this.oBufW  = new BufferedWriter(oOutSW);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void recebe() throws Exception {
        if(oBufR.ready()) {
            executeCommand(oBufR.readLine());
        }
    }
    
    private void executeCommand(String msg) throws Exception {
        if(msg == null) {
            return;
        }
        String[] comando = trataMensagem(msg);
        oControl.execute(comando);
    }
    
    private String[] trataMensagem(String msg) {
        char[] caracteres = msg.toCharArray();
        String comando    = "";
        String conteudo   = "";
        
        for(int i = 0; i < caracteres.length; i++) {
            if(caracteres[i] == '-') {
                comando  = msg.substring(0, i);
                conteudo = msg.substring(i + 1);  
                break;
            }
        }
        
        return new String[]{comando, conteudo};
    }
    
    private void executeCommand(byte[] dados) {
        if(dados == null) {
            return;
        }
        
        String[] comando = decodificarString(dados);
        
        oControl.execute(comando);
    }
}
