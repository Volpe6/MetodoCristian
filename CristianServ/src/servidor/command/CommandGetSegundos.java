package servidor.command;

import servidor.Servidor;
import servidor.ServidorCliente;

/**
 *
 * @author Viviane, Andrew
 */
public class CommandGetSegundos extends Command {

    public CommandGetSegundos(String sCommand, ServidorCliente oCli) {
        super(sCommand, oCli);
    }

    @Override
    public void execute() throws Exception {
        Servidor s = Servidor.getInstance();
        String sMsg =   "O cliente: " + oCliente.getNome() + ". Solicitou os segundos do servidor."
                      + "\nServidor enviou os segundos: " + s.getSegundos();
        s.printaConsole(sMsg);
        oCliente.escreve("atualizarSegundo-" + s.getSegundos());
    }
    
}
