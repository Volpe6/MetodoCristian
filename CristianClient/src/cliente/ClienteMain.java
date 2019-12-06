
package cliente;

import util.Relogio;
import cliente.controller.Controller;
import cliente.controller.ControllerCliente;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import util.HoraCompleta;

/**
 *
 * @author Viviane, Andrew
 */
public class ClienteMain {
    
    
    private final String ENDERECO = "127.0.0.1";
    private final int    PORTA    = 56000;
    
    private Socket     oSoc;
    private Controller oCon;
    private Scanner    sc;
    private Relogio    oRelogio;
    private String     sNome;
    
    private Thread iniRelogio;
    
    public ClienteMain() throws IOException {
        sc       = new Scanner(System.in);
        
        inicializaSocket();
        
        oCon     = new ControllerCliente(this, oSoc);
        oRelogio = new Relogio();
        
    }

    private void inicializaSocket() throws IOException {
        String ip = "";
        int porta = 56000;
        
        System.out.println("Informe o ip:");
        ip = sc.nextLine();
        System.out.println("Informe a porta");
        porta = sc.nextInt();
        
        oSoc = new Socket(ip, porta);
    }
    
    public void inicializaRelogio() {
        iniRelogio = new Thread(new Runnable() {
            @Override
            public void run() {
                int op = 0;
                boolean bContinuar = true;

                while (bContinuar) {
                    System.out.println("*******Menu********");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1 - Informar hora para o relógio.");
                    System.out.println("2 - Não informar.");
                    op = sc.nextInt();
                    switch(op) {
                        case 1:
                            System.out.println("Digite a hora:");
                            oRelogio.setHora(sc.nextInt());
                            System.out.println("Digite os minutos:");
                            oRelogio.setMinutos(sc.nextInt());

                            oRelogio.start();

                            bContinuar = false;
                            break;
                        case 2:
//                            solicitaHoraCompleta();
                            
                            oRelogio.start();
                            bContinuar = false;
                            break;
                        default:
                            System.out.println("opção invalida.");
                    }
                }
//                mostraHoraRelogio();
            }
        });
        iniRelogio.start();
    }
    
    public void escrever() throws Exception {
        try {
            iniRelogio.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Aguadando entrada");
                    String str = "";

                    while(true) {
                        str = sc.nextLine();
                        oCon.enviarComando(str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
      
    }
    
    public void escutar() {
        try {
            oCon.processa();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //printa no console
    public void atualizaAreaTexto(String sMsg) {
        System.out.println("Server: " + sMsg);
        oCon.libera();
    }
    
    public void atualizaHoraRelogio(int hora) {
        oRelogio.setHora(hora);
    }
    
    public void atualizaMinutoRelogio(int minutos) {
        oRelogio.setMinutos(minutos);
    }
    
    public void atualizaSegundosRelogio(int segundos) {
        oRelogio.setSegundos(segundos);
    }
    
    //faz uma requisição ao servidor de 5 em 5 segundos
    public void solicitarHorario() {
        
        new Thread(new Runnable() {
            @Override
            public void run() {
//                while(true) {
                    try {
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    solicitaHoraCompleta();
                   
                    mostraHoraRelogio();
                    
                    mostraHoraSaida();
                    mostraHoraChegada();
                    mostraHoraServidor();
//                }
            }
        }).start();
    }
    
    public void solicitaHoraCompleta() {
        oCon.setHoraSaida(oRelogio.getHora(), oRelogio.getMinutos(), oRelogio.getSegundos());
        try {
            oCon.enviarComando("getHora");
            oCon.enviarComando("getMinuto");
            oCon.enviarComando("getSegundos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void finalizarSocket() {
        try {
            oCon.enviarComando("quit");
            oSoc.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void mostraHoraSaida(){
        HoraCompleta h = oCon.getHoraSaida();
        
        System.out.println("Hora saida: hora: "     + h.getHora() 
                         + " minutos: "  + h.getMinuto()
                         + " segundos: " + h.getSegundo());
        
    }
    
    public void mostraHoraChegada(){
        HoraCompleta h = oCon.getHoraChegada();
        
        System.out.println("Hora Chegada: hora: "     + h.getHora() 
                         + " minutos: "  + h.getMinuto()
                         + " segundos: " + h.getSegundo());
        
    }
    
    public void mostraHoraServidor(){
        HoraCompleta h = oCon.getHoraServidor();
        
        System.out.println("Hora Servidor: hora: "     + h.getHora() 
                         + " minutos: "  + h.getMinuto()
                         + " segundos: " + h.getSegundo());
        
    }
    
    public void mostraHoraRelogio() {
        System.out.println("Hora Atualizada: hora: "     + oRelogio.getHora() 
                         + " minutos: "  + oRelogio.getMinutos()
                         + " segundos: " + oRelogio.getSegundos());
        
    }
    
    public int getHora() {
        return oRelogio.getHora();
    }
    
    public int getMinutos() {
        return oRelogio.getMinutos();
    }
    
    public int getSegundos() {
        return oRelogio.getSegundos();
    }
    
    public void setNome(String sNome) {
        this.sNome = sNome;
    }
    
    public String getNome() {
        return sNome;
    }
    
    public void getAtrasoRelogio() {
        
    }
    
    public void atrasaRelogio(float atraso) {
        oRelogio.atrasaRelogio(atraso);
    }
    
    public void resetRelogio() {
        oRelogio.reset();
    }
    
    public void mostraTodasHoras() {
        mostraHoraSaida();
        mostraHoraChegada();
        mostraHoraServidor();
        mostraHoraRelogio();
    }
    
    public void executaComando() {
        try {
            iniRelogio.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                int op = 0;
                boolean bContinuar = true;

                while (bContinuar) {
                    oCon.espera();
                    oCon.esperar();

                    System.out.println("*******Menu********");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1 - Requisitar hora servidor.");
                    System.out.println("2 - Finalizar.");
                    op = sc.nextInt();
                    switch(op) {
                        case 1:
                            solicitaHoraCompleta();
                            break;
                        case 2:
                            finalizarSocket();
                            break;
                        default:
                            System.out.println("opção invalida.");
                    }
                    
                }
            }
        }).start();
    }
    
    public static void main(String[] args) {
        
        ClienteMain c = null;
        try {
            c = new ClienteMain();
            c.inicializaRelogio();
            c.executaComando();
//            c.escrever();
//            m.solicitarHorario();
            c.escutar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            c.finalizarSocket();
        }
    }
}
