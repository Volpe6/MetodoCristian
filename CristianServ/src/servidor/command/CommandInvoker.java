
package servidor.command;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import servidor.ServidorCliente;

/**
 *
 * @author Viviane, Andrew
 */
public class CommandInvoker {
    
    private static CommandInvoker oInstance;
    
    public Map<String, Class<? extends Command>> comandos;
    
    private CommandInvoker() {
        comandos = new HashMap<>();
        
        comandos.put("print"         , CommandPrint.class);
        comandos.put("setNome"       , CommandSetName.class);
        comandos.put("getHora"       , CommandGetHora.class);
        comandos.put("getMinuto"     , CommandGetMinuto.class);
        comandos.put("getSegundos"   , CommandGetSegundos.class);
        comandos.put("setHoraAll"    , CommandSetHoraAll.class);
        comandos.put("setMinutosAll" , CommandSetMinutosAll.class);
        comandos.put("setSegundosAll", CommandSetSegundosAll.class);
        comandos.put("quit"          , CommandQuit.class);
    }
    
    public static CommandInvoker getInstance() {
        if(oInstance == null) {
            oInstance = new CommandInvoker();
        }
        return oInstance;
    }
    
    public void constroiExecuteCommand(String sCom, String sValor, ServidorCliente oCli) throws Exception {
        if(sCom.equals("")) {
            System.out.println("comando nulo");
            return;
        }
        
        Constructor<? extends Command> oCons = commandConstruct(sCom);
        Command oCom = oCons.newInstance(sValor, oCli);
        
        execute(oCom);
    }
    
    public void constroiExecuteCommand(String sCom, String sValor) throws Exception {
        if(sCom.equals("")) {
            System.out.println("comando nulo");
            return;
        }
        
        Constructor<? extends Command> oCons = commandConstruct(sCom);
        Command oCom = oCons.newInstance(sValor, null);
        
        execute(oCom);
    }
    
    public void constroiExecuteCommand(String sCom) throws Exception {
        if(sCom.equals("")) {
            System.out.println("comando nulo");
            return;
        }
        
        Constructor<? extends Command> oCons = commandConstruct(sCom);
        Command oCom = oCons.newInstance(null, null);
        
        execute(oCom);
    }
    
    private Constructor commandConstruct(String sCom) throws Exception {
        Class<? extends Command> oClasse     = comandos.get(sCom);
        Constructor<? extends Command> oCons = oClasse.getConstructor(String.class, ServidorCliente.class);
        
        return oCons;
    }
    
    public void execute(Command oCom) throws Exception {
        oCom.execute();
    }
}
