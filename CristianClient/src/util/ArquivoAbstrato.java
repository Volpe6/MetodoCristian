package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author Viviane, Andrew
 */
public abstract class ArquivoAbstrato {
    
    protected final int TAMANHO_DADOS = 1024;//tamanho dos dados tranferidos e recebidos
    
    protected DataInputStream   oDtaIn;
    protected DataOutputStream  oDtaOut;
    
    protected InputStreamReader  oInSR;
    protected OutputStreamWriter oOutSW;
    protected BufferedReader     oBufR;
    protected BufferedWriter     oBufW;
    
    
    public abstract void recebe() throws Exception;
    
    public void enviar(String msg) throws Exception {
        oBufW.write(msg + "\r\n");
        oBufW.flush();
    }
    
    public void envia(String msg) throws Exception {
        
        byte[] dados = codificaString(msg);
        
        oDtaOut.writeInt(dados.length);
        
        transferirDados(dados);
    }
    
    private int byteToInt(byte[] value) {
        return ((value[0] & 0xff) << 24) | ((value[1] & 0xff) << 16) | ((value[2] & 0xff) << 8) | ((value[3] & 0xff));
    }
    
    protected byte[] getDados() throws Exception {
//        byte[] in = new byte[4];
//
//        if(oDtaIn.read(in) == -1) {
//            return null;
//        }
//
//        int    tamanho = byteToInt(in);
//        byte[] dados   = new byte[tamanho];
//
//
//        int    posicao = 0;
//        byte[] buffer  = new byte[TAMANHO_DADOS];
//        while(oDtaIn.read(buffer) != -1) {
//            for(int i = 0; i < buffer.length; i++) {
//                if(posicao == tamanho) {
//                    break;
//                }
//                dados[posicao] = buffer[i];
//                posicao++;
//            }
//        }
//        return dados;

        byte[] in = new byte[4];
        while(oDtaIn.read(in) != -1) {
            int    tamanho = byteToInt(in);
            byte[] dados   = new byte[tamanho];
            
            int posicao = 0;
            byte[] buffer = new byte[TAMANHO_DADOS];
            
            for(int i = 0; i < tamanho; i++) {
                oDtaIn.read(buffer);
                for(int j = 0; j < buffer.length; j++) {
                    if(posicao == tamanho) {
                        break;
                    }
                    dados[posicao] = buffer[j];
                    posicao++;
                }
                if(posicao == tamanho) {
                    break;
                }
            }
            return dados;
        }
        return null;
    }
    
    protected byte[] codificaString(String msg) {
        ByteArrayOutputStream but = null;
        DataOutputStream      dos = null;
        
        try {
            but = new ByteArrayOutputStream();
            dos = new DataOutputStream(but);
            dos.writeChars(msg);
            
            return but.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    protected String[] decodificarString(byte[] dados) {
        String dado = new String(dados);
        
        char[] caracteres = dado.toCharArray();
        String comando    = "";
        String conteudo   = "";
        
        for(int i = 0; i < caracteres.length; i++) {
            if(caracteres[i] == '-') {
                comando  = dado.substring(0, i);
                conteudo = dado.substring(i + 1);  
                break;
            }
        }
        
        return new String[]{comando, conteudo};
    }
    
    protected void transferirDados(byte[] dados) throws Exception {
        
        int    cont   = 0;
        byte[] buffer = new byte[TAMANHO_DADOS];
        
        while(cont != dados.length) {
            int tamanho = 0;
            for(int i = 0; i < buffer.length; i++) {
                if(cont == dados.length) {
                    break;
                }
                buffer[i] = dados[cont];
                cont++;
                tamanho++;
            }
            oDtaOut.write(buffer, 0, tamanho);
        }
        oDtaOut.flush();
    }
}
