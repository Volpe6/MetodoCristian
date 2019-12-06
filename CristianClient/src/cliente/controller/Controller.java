package cliente.controller;

import java.net.Socket;
import util.HoraCompleta;
import util.Relogio;

/**
 *
 * @author Viviane, Andrew
 */
public interface Controller {
      
    public void setSocket(Socket oSoc);
    
    public Socket getSocket();
    
    public void notificaMensagem(String msg);
    
    public void notificacao(String msg);
    
    public void conectar() throws Exception;
    
    public void enviarMensagem(String sMsg) throws Exception;
    
    public void enviarComando(String sCon) throws Exception;
    
    public void processa() throws Exception;
    
    public void setHoraSaida(int hora, int minuto, int segundo);
    
    public void espera();
    
    public void libera();
    
    public void esperar();
    
    public HoraCompleta getHoraSaida();
    
    public HoraCompleta getHoraChegada();
    
    public HoraCompleta getHoraServidor();
    
    public void execute(String [] comando);
}
