package it.unisalento.prototype.prototypesubpubrabbitmq;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

/**
* classe per ricevere un messaggio
*/
@Component
public class Receiver {

  private CountDownLatch latch = new CountDownLatch(1);   // per intercettare i messaggi

  /**
   * metodo per stampare il messaggio intercettato
   * @param message
   */
  public void receiveMessage(String message) {
    System.out.println("Received <" + message + ">");
    latch.countDown();                                  // decrementa il counter dei messaggi
                                                        // in coda e gli "rilascia"
  }

  /**
   * metodo per avere il latch corrente
   * @return del latch corrente
   */
  public CountDownLatch getLatch() {
    return latch;
  }

}