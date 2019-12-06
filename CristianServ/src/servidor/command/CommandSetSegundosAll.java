package servidor.command;

import servidor.Servidor;
import servidor.ServidorCliente;

/**
 *
 * @author Viviane, Andrew
 */
public class CommandSetSegundosAll extends Command {

    public CommandSetSegundosAll(String sCommand, ServidorCliente oCli) {
        super(sCommand, oCli);
    }

    @Override
    public void execute() throws Exception {
        Servidor s = Servidor.getInstance();
        s.notificaSegundoAtualizado("fAtualizarSegundo-" + s.getSegundos());
    }
    
}
