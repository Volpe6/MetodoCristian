
package util;

/**
 *
 * @author Viviane, Andrew
 */
public class HoraCompleta {
 
    private int hora; 
    private int minuto; 
    private int segundo;
    
    public HoraCompleta() {
        this(0, 0, 0);
    }
    
    public HoraCompleta(int hora, int minuto, int segundo) {
        this.hora    = hora;
        this.minuto  = minuto;
        this.segundo = segundo;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }
    
    public int getHora() {
        return hora;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
    
    public int getMinuto() {
        return minuto;
    }

    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }
    
    public int getSegundo() {
        return segundo;
    }
    
    public int getHoraConvertidaSegundos() {
        return (this.hora*3600) + (this.minuto*60) + this.segundo;
    }
}
