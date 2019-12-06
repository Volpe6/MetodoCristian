
package servidor.command;

import servidor.Servidor;
import servidor.ServidorCliente;

/**
 *
 * @author Viviane, Andrew
 */
public class CommandSetHoraAll extends Command {

    public CommandSetHoraAll(String sCommand, ServidorCliente oCli) {
        super(sCommand, oCli);
    }

    @Override
    public void execute() throws Exception {
        Servidor s = Servidor.getInstance();
        s.notificaHoraAtualizada("fAtualizarHora-" + s.getHora());
    }
    
}
