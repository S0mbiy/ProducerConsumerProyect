
package producerconsumer;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;

public class Producer extends Thread {
    Buffer buffer;
    int id;
    int wait;
    JTextPane gui;
    int bufferSize;
    
    Producer(JTextPane gui,Buffer buffer, int id, int wait) {
        this.buffer = buffer;
        this.id = id;
        this.wait = wait;
        this.gui = gui;
        this.bufferSize = buffer.getSize();
    }
    
    @Override
    public void run() {
        int num1;
        int num2;
        int oper;
        Random r = new Random(System.currentTimeMillis()*(id+1));
        
        while(true) {
            num1 = r.nextInt(10);
            num2 = r.nextInt(10);
            oper = r.nextInt(4);
            Integer[] product = {oper, num1, num2, id};
            this.buffer.produce(product);
            try {
                Thread.sleep(wait);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
