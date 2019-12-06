
package util;

import java.util.concurrent.TimeUnit;

/**
 * Um contador que mostra o tempo que falta para o relogio voltar a andar
 * 
 * @author Viviane, Andrew
 */
public class Contador implements Runnable {

    private boolean bContinua;
    private int    segundos;//segundos a esperar
    
    public Contador(int segundos) {
        this.segundos  = segundos;
        this.bContinua = true;
    }
    
    @Override
    public void run() {
        decrementar();
    }
    
    private void decrementar() {
        while(bContinua) {
            try {
                
                TimeUnit.SECONDS.sleep(1);
                segundos--;
                
                if(segundos == 0) {
                    bContinua = false;
                }
                
                mostraTempoRestante(segundos);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void mostraTempoRestante(int segundo) {
        System.out.println("segundos restantes: " + segundo + "...");
    }
    
}
