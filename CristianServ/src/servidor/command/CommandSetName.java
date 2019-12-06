
package servidor.command;

import servidor.ServidorCliente;

/**
 *
 * @author Viviane, Andrew
 */
public class CommandSetName extends Command {

    private String sNome;
    
    public CommandSetName(String sCommand, ServidorCliente oCli) {
        super(sCommand, oCli);
        
        this.sNome = sCommand;
    }

    @Override
    public void execute() throws Exception {
        oCliente.setNome(sNome);
        oCliente.escreve("atualizarNome-" + sNome);
    }
    
}
