
package servidor.command;

import servidor.Servidor;
import servidor.ServidorCliente;

/**
 *
 * @author Drew
 */
public class CommandQuit extends Command {

    public CommandQuit(String sCommand, ServidorCliente oCli) {
        super(sCommand, oCli);
    }

    @Override
    public void execute() throws Exception {
        Servidor s = Servidor.getInstance();
        s.removeCliente(oCliente);
        s.diminuiValorSequencial();
        System.out.println(oCliente.getNome() + " finalizado");
        oCliente.finalizar();
        oCliente = null;
    }
    
}
