/**
 * @(#)PartitionSizeCalculator.java
 *
 *
 * @author Lim Chunwei
 * @version 1.00 06/01/2016AD
 */

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class PartitionSizeCalculator extends JFrame
{
    JTextField size;
    JLabel sizeFAT32, sizeNTFS;
    JButton calc, about;

    public PartitionSizeCalculator()
    {
        setTitle("Partition Size Calculator");
        setLayout(new GridLayout(3, 0));

        JPanel p1=new JPanel(new FlowLayout());
        p1.setBorder(new TitledBorder("Desired Size"));
        p1.add(about=new JButton("About"));
        p1.add(size=new JTextField(4));
        p1.add(new JLabel("GB"));
        p1.add(calc=new JButton("Calculate"));

        JPanel p2=new JPanel(new FlowLayout());
        p2.setBorder(new TitledBorder("FAT32"));
        p2.add(sizeFAT32=new JLabel("Click Calculate to get the result"));

        JPanel p3=new JPanel(new FlowLayout());
        p3.setBorder(new TitledBorder("NTFS"));
        p3.add(sizeNTFS=new JLabel("Click Calculate to get the result"));

        add(p1);
        add(p2);
        add(p3);

        size.setHorizontalAlignment(SwingConstants.RIGHT);
        calc.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(size.getText().compareTo("")!=0)
                {
                    try
                    {
                        int sizei=Integer.parseInt(size.getText());
                        if(sizei>0)
                        {
                            if(sizei>2088991)
                            {
                                sizeFAT32.setText("Maximum accepted size is 2088991 GB");
                                sizeNTFS.setText("Maximum accepted size is 2088991 GB");
                            }
                            else
                            {
                                sizeFAT32.setText(calcSizeFAT32(sizei)+" MB");
                                sizeNTFS.setText(calcSizeNTFS(sizei)+" MB");
                            }
                        }
                        else
                        {
                            sizeFAT32.setText("Size must be larger than 0");
                            sizeNTFS.setText("Size must be larger than 0");
                        }
                    }
                    catch(NumberFormatException nfe)
                    {
                        sizeFAT32.setText("Error - size invalid!");
                        sizeNTFS.setText("Error - size invalid!");
                    }
                }
                else
                {
                    sizeFAT32.setText("Please fill in the size!");
                    sizeNTFS.setText("Please fill in the size!");
                }
            }
        });
        about.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Partition Size Calculator\nVersion 1.0\n\n(C) 2016 Lim Chunwei", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public int calcSizeFAT32(int sizeGB)
    {
        return(sizeGB*1024+(sizeGB-1)*4);
    }

    public int calcSizeNTFS(int sizeGBi)
    {
        double sizeGB=sizeGBi, result=Math.ceil(Math.ceil(sizeGB*2097152/16065)*16065/2048);
        return((int)result);
    }

    public static void main(String[] args)
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        PartitionSizeCalculator f=new PartitionSizeCalculator();
        f.setResizable(false);
        f.setSize(260, 225);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
