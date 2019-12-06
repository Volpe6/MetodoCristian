
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import servidor.command.CommandInvoker;
import util.Relogio;

/**
 *
 * @author Viviane, Andrew
 */
public class Servidor {
    
    private static Servidor oInstance;
    
    private final int PORTA = 56000;
    
    private ServerSocket          oServer;
    private Relogio               oRelogioServer;
    private List<ServidorCliente> lClientes;
    private Scanner               sc;
    private long                  valorSequencial;
    
    private Servidor() {
        this.valorSequencial = 0;
    }
    
    public static Servidor getInstance() {
        if(oInstance == null) {
            oInstance = new Servidor();
        }
        
        return oInstance;
    }
    
    
    public void iniciar() throws IOException {
        
        oRelogioServer = new Relogio();
        oRelogioServer.start();
        
        lClientes = new ArrayList<>();
        sc        = new Scanner(System.in);
                
    }
    
    public void iniciarServer() throws IOException {
        int porta = 56000;
        System.out.println("Informe a porta do servidor:");
        porta = sc.nextInt();
        oServer = new ServerSocket(porta);
        oServer.setReuseAddress(true);
        
        System.out.println("hora: " + oRelogioServer.getHora() + " minuto: " + oRelogioServer.getMinutos());
        System.out.println("Servidor iniciado...");
    }
    
    public void conectar() throws IOException  {
        while(true) {
            System.out.println("aguardando conexão...");
            ServidorCliente c = new ServidorCliente(oServer.accept());
            Thread tC = new Thread(c);
            tC.start();
            addCliente(c);
            executaComandoCliente("setNome", "Cliente_" + this.getValorSequencial(), c);
            System.out.println("conectado");
        }
    }
    
    public void inicializaRelogio() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int op = 0;
                boolean bContinuar = true;

                while (bContinuar) {
                    System.out.println("*******Menu********");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1 - Informar hora para o relógio.");
                    System.out.println("2 - Não informar.");
                    System.out.println("3 - Mostra hora servidor.");
                    System.out.println("4 - Parar de exibir menu.");
                    op = sc.nextInt();
                    switch(op) {
                        case 1:
                            System.out.println("Digite a hora:");
                            oRelogioServer.setHora(sc.nextInt());
                            System.out.println("Digite os minutos:");
                            oRelogioServer.setMinutos(sc.nextInt());
//                            bContinuar = false;
                            break;
                        case 2:
//                            bContinuar = false;
                            break;
                        case 3:
                            mostraHora();
//                            System.out.println("Não implementado ainda.");
                            break;
                        case 4:
                            bContinuar = false;
//                            System.out.println("Não implementado ainda.");
                            break;
                        default:
                            System.out.println("opção invalida.");
                    }
                }
                
                System.out.println(" hora: "     + oRelogioServer.getHora() 
                                 + " minutos: "  + oRelogioServer.getMinutos()
                                 + " segundos: " + oRelogioServer.getSegundos());
            }
        }).start();
    }
    
    public void addCliente(ServidorCliente oCli) {
        lClientes.add(oCli);
    }
    
    public void removeCliente(ServidorCliente oCli) {
        lClientes.remove(oCli);
    }
    
    public int getQtdClientesConectado() {
        return lClientes.size();
    }
    
    public int getHora() {
        return oRelogioServer.getHora();
    }
    
    public int getMinutos() {
        return oRelogioServer.getMinutos();
    }
    
    public int getSegundos() {
        return oRelogioServer.getSegundos();
    }
    
    public synchronized long getValorSequencial() {
        valorSequencial++;
        return valorSequencial;
    }
    
    public synchronized void diminuiValorSequencial() {
        valorSequencial--;
    }
    
    public void mostraHora() {
        System.out.println("Hora: " + getHora()
                        + " Minuto: " + getMinutos()
                        + " Segundo: " + getSegundos());
    }
    
    /**
     * Printa no console a mensagem enviada
     */
    public void printaConsole(String sMsg) {
        System.out.println("\n" + sMsg);
    }
    
    public void notificaHoraAtualizada(String sMsg) throws Exception {
        for(ServidorCliente cli : lClientes) {
            cli.escreve(sMsg);
        }
    }
    
    public void notificaMinutoAtualizado(String sMsg) throws Exception {
        for(ServidorCliente cli : lClientes) {
            cli.escreve(sMsg);
        }
    }
    
    public void notificaSegundoAtualizado(String sMsg) throws Exception {
        for(ServidorCliente cli : lClientes) {
            cli.escreve(sMsg);
        }
    }
    
    /**
     * Executa o comando passado para o cliente especifico
     */
    private void executaComandoCliente(String comando, String valor, ServidorCliente oCli) {
        try {
            CommandInvoker.getInstance().constroiExecuteCommand(comando, valor, oCli);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void executaComando(String comando) {
        try {
            CommandInvoker.getInstance().constroiExecuteCommand(comando);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void sincronizarCliente() {
        executaComando("setHoraAll");
        executaComando("setMinutosAll");
        executaComando("setSegundosAll");
    }
    
    public static void main(String[] args) {
        try {
            Servidor srv = Servidor.getInstance();
            
            srv.iniciar();
            srv.iniciarServer();
            srv.inicializaRelogio();
            srv.conectar();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
