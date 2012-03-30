package mobilecrawler;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.*;

public class FinishedHistory extends Frame implements WindowListener, ActionListener
{
    public Label label;
    public TextArea history;
    public Button close;
    
    public FinishedHistory()
    {
        label = new Label("History: ");
        history = new TextArea();
        close = new Button("Close");
        close.addActionListener(this);
        addWindowListener(this);
        layoutComponents();
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if("Close".equals(ae.getActionCommand()))
        {
            System.out.println("Close");
            dispose();
        }
    }
    
    private void layoutComponents()
    {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints cns = new GridBagConstraints();

        setLayout(grid);

        cns.weightx = 0.5;
        cns.ipadx = cns.ipady = 5;
        cns.fill = GridBagConstraints.HORIZONTAL;
        cns.insets = new Insets(5, 5, 5, 5);

        cns.weightx = 1.0;
        cns.gridwidth = GridBagConstraints.REMAINDER;
        grid.setConstraints(history, cns);
        add(label);
        add(history);
        
        Panel p = new Panel();

        grid.setConstraints(p, cns);
        add(p);     
        p.setLayout(new FlowLayout());
        p.add(close);        
    }
    
    public void windowActivated(WindowEvent we) {}
    public void windowClosed(WindowEvent we) {}
    public void windowClosing(WindowEvent we) 
    {
    	dispose();
    }
    public void windowDeactivated(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowIconified(WindowEvent we) {}
    public void windowOpened(WindowEvent we) {}
}
