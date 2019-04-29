
package producerconsumer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer extends Thread {
    Buffer buffer;
    int id;
    int wait;
    private boolean exit;
    
    Consumer(Buffer buffer, int id, int wait) {
        this.buffer = buffer;
        this.id = id;
        this.wait = wait;
        this.exit = false;
    }
    
    @Override
    public void run() {        
        while(!exit) {
            this.buffer.consume(id);
            
            try {
                Thread.sleep(wait);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setExit() {
        this.exit = true;
    }
}
