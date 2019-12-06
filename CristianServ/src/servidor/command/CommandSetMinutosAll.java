package servidor.command;

import servidor.Servidor;
import servidor.ServidorCliente;

/**
 *
 * @author Viviane, Andrew
 */
public class CommandSetMinutosAll extends Command {

    public CommandSetMinutosAll(String sCommand, ServidorCliente oCli) {
        super(sCommand, oCli);
    }

    @Override
    public void execute() throws Exception {
        Servidor s = Servidor.getInstance();
        s.notificaMinutoAtualizado("fAtualizarMinuto-" + s.getMinutos());
    }
    
}
