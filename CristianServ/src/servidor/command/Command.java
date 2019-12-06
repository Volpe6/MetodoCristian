/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.command;

import servidor.ServidorCliente;

/**
 *
 * @author Drew
 */
public abstract class Command {
    
    protected ServidorCliente oCliente;
    
    public Command(String sCommand, ServidorCliente oCli) {
        this.oCliente = oCli;
    }
    
    public abstract void execute() throws Exception;
    
}
