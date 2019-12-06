package servidor.command;

import servidor.Servidor;
import servidor.ServidorCliente;

/**
 *
 * @author Viviane, Andrew
 */
public class CommandGetHora extends Command {

    public CommandGetHora(String sCommand, ServidorCliente oCli) {
        super(sCommand, oCli);
    }

    @Override
    public void execute() throws Exception {
        Servidor s = Servidor.getInstance();
        String sMsg =   "O cliente: " + oCliente.getNome() + ". Solicitou a hora do servidor."
                      + "\nServidor enviou a hora: " + s.getHora() ;
        s.printaConsole(sMsg);
        oCliente.escreve("atualizarHora-" + s.getHora());
    }
    
}
