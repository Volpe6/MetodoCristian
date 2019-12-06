package util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Viviane, Andrew
 */
public class Relogio extends Thread {

    private int hora;
    private int minutos;
    private int segundos;
    private int maximo = Integer.MAX_VALUE;

    private boolean bAtrasar;
    private float tempoAtraso;
    private int vAtraso; //vezes q executa o atraso

    public Relogio() {
        this(
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE)
        );
    }

    public Relogio(int hora, int min) {
        this.hora = hora;
        this.minutos = min;
        this.segundos = 0;
        this.bAtrasar = false;
        this.vAtraso = 0;
    }

    public void atrasaRelogio(float tempo) {
        tempoAtraso = tempo;
        System.out.println("O relogio ira atrasar em : " + tempoAtraso + " milisegundos");
        vAtraso = 0;
        atrasa();
       // espera();
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public void setSegundos(int seg) {
        this.segundos = seg;
    }

    public int getSegundos() {
        return segundos;
    }

    public void mostraHora() {

        System.out.println("Hora no Relogio: hora: " + this.getHora()
                + " minutos: " + this.getMinutos()
                + " segundos: " + this.getSegundos());
    }

    public void run() {
        for (int i = 0; i < maximo; i++) {
//            atrasar();
            try {
                if (bAtrasar) {
                    System.out.println("relógio passando mais devagar.");
                    Thread.sleep((long) tempoAtraso);
                } else {
                    TimeUnit.SECONDS.sleep(1);
                }

                if (segundos < 60) {
                    segundos++;
                } else {

                    if (minutos < 60) {
                        minutos++;
                        segundos = 0;

                    } else {
                        if (hora == 24) {
                            hora = 0;
                        }
                        hora++;
                        minutos = 0;
                        minutos++;
                        segundos = 0;
                        segundos++;
                    }

                }
                if (vAtraso == 5) {
                    vAtraso = 0;
                    System.out.println("terminou de atrasar");
                    mostraHora();
                    liberar();
                }
                if (bAtrasar) {
                    ++vAtraso;
                }

            } catch (InterruptedException e) {
            }
        }

    }

    public String capturarHora() {

        String s = this.getHora() + ":" + this.getMinutos();
        return s;
    }

    private void atrasa() {
        this.bAtrasar = true;
    }

    private void liberar() {
        this.bAtrasar = false;
    }

//    private void atrasar() {
//        if(!bAtrasar) {
//            return;
//        }
//        
//        try {
////            mostraHora();
//            System.out.println("o relogio ira atrasar em : " + tempoAtraso + " segundos");
//            
//            new Thread(new Contador(tempoAtraso - 1)).start();
//            
//            this.sleep(tempoAtraso*1000);
////            mostraHora();
//            
//            liberar();
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            liberar();
//        }
//    }
    private void espera() {
        try {
            this.sleep((long) (tempoAtraso + 500) * 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        this.hora = 0;
        this.minutos = 0;
        this.segundos = 0;
    }

}
