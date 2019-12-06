
package cliente.controller;

import cliente.ClienteMain;
import java.net.Socket;
import util.ArquivoCliente;
import util.HoraCompleta;
import util.Relogio;

/**
 *
 * @author Viviane, Andrew
 */
public class ControllerCliente implements Controller {
    
    private ClienteMain    oCli;
    private Socket         oSocket;
    private ArquivoCliente oArquivo;
    
    private HoraCompleta horaSaida;
    private HoraCompleta horaChegada  = new HoraCompleta();
    private HoraCompleta horaServidor = new HoraCompleta();
    
    private boolean bEspera;
    
    public ControllerCliente(ClienteMain oCliente, Socket oSocket) {
        this.oCli     = oCliente;
        this.oSocket  = oSocket;
        this.oArquivo = new ArquivoCliente(oSocket, this);
    }

    @Override
    public void setSocket(Socket oSoc) {
        this.oSocket  = oSoc;
        this.oArquivo = new ArquivoCliente(oSoc, this);
    }

    @Override
    public Socket getSocket() {
        return oSocket;
    }

    @Override
    public void espera() {
        this.bEspera = true;
    }

    @Override
    public void libera() {
        this.bEspera = false;
    }

    @Override
    public void esperar() {
        while(bEspera) {
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public HoraCompleta getHoraSaida() {
        return horaSaida;
    }
    
    @Override
    public HoraCompleta getHoraChegada() {
        return horaChegada;
    }

    public HoraCompleta getHoraServidor() {
        return horaServidor;
    }
    
    @Override
    public void notificaMensagem(String msg) {
        oCli.atualizaAreaTexto(msg);
    }

    @Override
    public void notificacao(String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void conectar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void enviarMensagem(String sMsg) throws Exception {
        escreve(sMsg);
    }

    @Override
    public void enviarComando(String sCon) throws Exception {
        oArquivo.enviar(sCon + "-");
    }

    @Override
    public void setHoraSaida(int hora, int minuto, int segundo) {
        this.horaSaida = new HoraCompleta(hora, minuto, segundo);
    }
    
    @Override
    public void processa() throws Exception {
          while(true) {
            if(oSocket == null || !oSocket.isConnected()) {
                continue;
            }
            oArquivo.recebe();
        }
    }

    @Override
    public void execute(String[] comando) {
         switch(comando[0]) {
            case "atualizar":
                this.notificaMensagem(comando[1]);
                break;
            case "atualizarHora":
//                this.notificaMensagem("hora: " + comando[1]);
                this.atualizaHora(Integer.parseInt(comando[1]));
                break;
            case "atualizarMinuto":
//                this.notificaMensagem("minuto: " + comando[1]);
                this.atualizaMinutos(Integer.parseInt(comando[1]));
                break;
            case "atualizarSegundo":
//                this.notificaMensagem("segundos: " + comando[1]);
                this.atualizaSegundos(Integer.parseInt(comando[1]));
                break;
            case "fAtualizarHora":
                this.notificaMensagem("hora: " + comando[1]);
                this.forcaAtualizacaoHora(Integer.parseInt(comando[1]));
                break;
            case "fAtualizarMinuto":
                this.notificaMensagem("minuto: " + comando[1]);
                this.forcaAtualizacaoMinuto(Integer.parseInt(comando[1]));
                break;
            case "fAtualizarSegundo":
                this.notificaMensagem("segundos: " + comando[1]);
                this.forcaAtualizacaoSegundo(Integer.parseInt(comando[1]));
                break;
            case "atualizarNome":
                this.atualizaNome(comando[1]);
                this.notificaMensagem("Nome do cliente: " + comando[1]);
                break;
        }
    }
    
    private void atualizaHora(int hora) {
        horaChegada.setHora(oCli.getHora());
        horaServidor.setHora(hora);
//        oCli.atualizaHoraRelogio(hora);
    }
    
    private void atualizaMinutos(int minutos) {
        horaChegada.setMinuto(oCli.getMinutos());
        horaServidor.setMinuto(minutos);
//        oCli.atualizaMinutoRelogio(minutos);
    }
    
    private void atualizaSegundos(int segundos) {
        horaChegada.setSegundo(oCli.getSegundos());
        horaServidor.setSegundo(segundos);
        calculoCristian();
//        oCli.atualizaSegundosRelogio(segundos);
    }
    
    private void forcaAtualizacaoHora(int hora) {
        oCli.atualizaHoraRelogio(hora);
    }
    
    private void forcaAtualizacaoMinuto(int minuto) {
        oCli.atualizaMinutoRelogio(minuto);
    }
    
    private void forcaAtualizacaoSegundo(int segundo) {
        oCli.atualizaSegundosRelogio(segundo);
    }
    
    private void atualizaNome(String nome) {
        oCli.setNome(nome);
    }
    
    private void calculoCristian() {
        
        int horaLocal = new HoraCompleta(oCli.getHora(), oCli.getMinutos(), oCli.getSegundos()).getHoraConvertidaSegundos();
        
        int iT0     = horaSaida.getHoraConvertidaSegundos();
        int iT1     = horaChegada.getHoraConvertidaSegundos();
        int serverH = horaServidor.getHoraConvertidaSegundos();
        
        int resu = ((iT1 - iT0)/2) + serverH;
        
        if((iT1-iT0) > 1) {
            System.out.println("Latencia muito alta, repita operação");
            this.libera();
            return;
        }
        
        if(horaLocal > resu) {
            printarHorasConsole();
            int   dif    = horaLocal - resu;
            float result = (((float)dif + 5)/5)*1000;
            float a=((result+500)*5)/1000;
            oCli.atualizaSegundosRelogio(oCli.getSegundos()-1);
            
            oCli.atrasaRelogio(result);
            libera();
        } else {
            converteSegundoHora(resu);
        }
        
    }
    
    private void  converteSegundoHora(int resu) {
        int hora    = 0;
        int minuto  = 0;
        int segundo = 0; 
        
        
        if(resu >= 3600) {
            hora    = resu/3600;
            segundo = resu%3600;
            
            if(segundo>=60) {
                minuto  = segundo/60; 
                segundo = segundo%60;
            } 
            
        } else if(resu >= 60) {
            minuto  = resu/60;
            segundo = resu%60;
        }
        
//        oCli.resetRelogio();
        atualizaHoraCliente(hora, minuto, segundo);
        printarHorasConsole();
        libera();
    }
    
    private void atualizaHoraCliente(int hora, int minuto, int segundo) {
        oCli.atualizaHoraRelogio(hora);
        oCli.atualizaMinutoRelogio(minuto);
        oCli.atualizaSegundosRelogio(segundo);
    }
    
    public void printarHorasConsole() {
        oCli.mostraTodasHoras();
    }
    
    private void escreve(String sMsg) throws Exception {
        oArquivo.enviar("print-"+sMsg);
    }
}
