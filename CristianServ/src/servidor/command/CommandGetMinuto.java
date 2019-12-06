
package servidor.command;

import servidor.Servidor;
import servidor.ServidorCliente;

/**
 *
 * @author Viviane, Andrew
 */
public class CommandGetMinuto extends Command {

    public CommandGetMinuto(String sCommand, ServidorCliente oCli) {
        super(sCommand, oCli);
    }

    @Override
    public void execute() throws Exception {
        Servidor s = Servidor.getInstance();
        String sMsg =   "O cliente: " + oCliente.getNome() + ". Solicitou os minutos do servidor."
                      + "\nServidor enviou os minutos: " + s.getMinutos();
        s.printaConsole(sMsg);
        oCliente.escreve("atualizarMinuto-" + s.getMinutos());
    }
    
}
