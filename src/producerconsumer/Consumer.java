
package producerconsumer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;

public class Consumer extends Thread {
    Buffer buffer;
    int id;
    int wait;
    JTextPane gui;
    
    Consumer(JTextPane gui, Buffer buffer, int id, int wait) {
        this.buffer = buffer;
        this.id = id;
        this.wait = wait;
        this.gui = gui;
    }
    
    @Override
    public void run() {
        Integer[] product;
        
        while(true) {
            product = this.buffer.consume();
            switch (product[0]){
                    case 0:
                        gui.setText(gui.getText()+"C ID:"+id+"| (+ "+product[1]+" "+product[2]+") = "+(product[1]+product[2])+"\n");
                        break;
                    case 1:
                        gui.setText(gui.getText()+"C ID:"+id+"| (- "+product[1]+" "+product[2]+") = "+(product[1]-product[2])+"\n");
                        break;
                    case 2:
                        gui.setText(gui.getText()+"C ID:"+id+"| (* "+product[1]+" "+product[2]+") = "+(product[1]*product[2])+"\n");
                        break;
                    case 3:
                        if(product[2]==0)
                            gui.setText(gui.getText()+"C ID:"+id+"| (/ "+product[1]+" "+product[2]+") = ERROR 0 division\n");
                        else    
                            gui.setText(gui.getText()+"C ID:"+id+"| (/ "+product[1]+" "+product[2]+") = "+((float)product[1]/product[2])+"\n");
                        break;
            }

            try {
                Thread.sleep(wait);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
