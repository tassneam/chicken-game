package main;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class chicken extends JFrame{
    public chicken(){
        initUI();
    }
    private void initUI(){
        add(new board());
        setResizable(false);
        pack();
        setTitle("Chicks & Chicken");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            JFrame ex = new chicken();
            ex.setVisible(true);
        });
    }
}
