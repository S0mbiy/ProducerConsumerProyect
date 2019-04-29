package producerconsumer;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Queue;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;

public class Buffer {

    private Queue<Integer[]> buffer;
    final private int size;
    JProgressBar pb;
    JTextPane producerText, consumerText;
    JLabel tasks, doneTasks;

    Buffer(JTextPane producerText, JTextPane consumerText, JProgressBar pb, JLabel tasks, JLabel doneTasks, int size) {
        this.buffer = new LinkedList<>();
        this.size = size;
        this.pb = pb;
        this.producerText = producerText;
        this.consumerText = consumerText;
        this.tasks = tasks;
        this.doneTasks = doneTasks;
    }

    synchronized void consume(int id) {
        Integer[] product;

        if (this.buffer.isEmpty()) {
            try {
                wait(10);

            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            product = this.buffer.poll();
            pb.setValue((int) ((float) buffer.size() / size * 100));
            tasks.setText("" + buffer.size());
            String text = producerText.getText();
            producerText.setText(text.substring(text.indexOf('\n') + 1));
            doneTasks.setText("" + (Integer.parseInt(doneTasks.getText()) + 1));

            if (product != null) {
                switch (product[0]) {
                    case 0:
                        consumerText.setText(consumerText.getText() + "C ID:" + id + "| (+ " + product[1] + " " + product[2] + ") = " + (product[1] + product[2]) + "\n");
                        break;
                    case 1:
                        consumerText.setText(consumerText.getText() + "C ID:" + id + "| (- " + product[1] + " " + product[2] + ") = " + (product[1] - product[2]) + "\n");
                        break;
                    case 2:
                        consumerText.setText(consumerText.getText() + "C ID:" + id + "| (* " + product[1] + " " + product[2] + ") = " + (product[1] * product[2]) + "\n");
                        break;
                    case 3:
                        if (product[2] == 0) {
                            consumerText.setText(consumerText.getText() + "C ID:" + id + "| (/ " + product[1] + " " + product[2] + ") = ERROR 0 division\n");
                        } else {
                            consumerText.setText(consumerText.getText() + "C ID:" + id + "| (/ " + product[1] + " " + product[2] + ") = " + ((float) product[1] / product[2]) + "\n");
                        }
                        break;
                }
            }

            notify();
        }

    }

    synchronized void produce(Integer[] product) {
        if (this.buffer.size() >= size) {
            try {
                wait(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.buffer.add(product);
            pb.setValue((int) ((float) buffer.size() / size * 100));
            tasks.setText("" + buffer.size());
            String text = producerText.getText();
            switch (product[0]) {
                case 0:
                    producerText.setText(text + "P ID:" + product[3] + "| (+ " + product[1] + " " + product[2] + ")\n");
                    break;
                case 1:
                    producerText.setText(text + "P ID:" + product[3] + "| (- " + product[1] + " " + product[2] + ")\n");
                    break;
                case 2:
                    producerText.setText(text + "P ID:" + product[3] + "| (* " + product[1] + " " + product[2] + ")\n");
                    break;
                case 3:
                    producerText.setText(text + "P ID:" + product[3] + "| (/ " + product[1] + " " + product[2] + ")\n");
                    break;
            }
            notify();
        }

    }

    public int getSize() {
        return size;
    }

    synchronized int getNumberOfTasks() {
        return buffer.size();
    }

    static int count = 1;

    synchronized static void print(String string) {
        System.out.print(count++ + " ");
        System.out.println(string);
    }

}
