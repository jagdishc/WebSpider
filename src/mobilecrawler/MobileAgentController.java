package mobilecrawler;

import com.ibm.aglet.*;
import com.ibm.aglet.event.*;
import com.ibm.aglet.util.*;

import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Enumeration;
import javax.swing.JFileChooser;

public class MobileAgentController extends Frame implements WindowListener, ActionListener
{
    private Label label;
    private TextField location;
    private AddressChooser dest;
    private Button loc;
    private Button go;
    MobileAgentMaster mam;
    
    public MobileAgentController(MobileAgentMaster temp)
    {
        mam = temp;        
        label = new Label("ODBFM file: ");
        location = new TextField(20);
        dest = new AddressChooser();
        loc = new Button("...");
        go = new Button("Go");
        layoutComponents();
        this.setTitle("Agent Controller");
        addWindowListener(this);
        loc.addActionListener(this);
        go.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if("Go".equals(ae.getActionCommand()))
        {
            try
            {
                File file = new File(location.getText());
                URL dst = new URL(dest.getAddress());
                mam.setFile(file, dst);
                Thread.sleep(8000);
                mam.createChild();
                this.dispose();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }           
        }
        else if("...".equals(ae.getActionCommand()))
        {
            class MyFilter extends javax.swing.filechooser.FileFilter 
        {
            public boolean accept(File file) 
            {
                String filename = file.getName();
                return filename.endsWith(".dat");
            }
            public String getDescription() 
            {
                return "*.dat";
            }
        }
        JFileChooser fc = new JFileChooser();    
        fc.addChoosableFileFilter(new MyFilter());      
        int reval = fc.showOpenDialog(fc);
        if(reval == JFileChooser.APPROVE_OPTION)
        {
            location.setText(fc.getSelectedFile().toString());                  
        }
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
        grid.setConstraints(location, cns);
        add(label);
        add(location);

        cns.gridwidth = GridBagConstraints.REMAINDER;
        cns.fill = GridBagConstraints.BOTH;
        cns.weightx = 1.0;
        cns.weighty = 1.0;
        cns.gridheight = 2;
        grid.setConstraints(dest, cns);
        add(dest);

        cns.weighty = 0.0;
        cns.fill = GridBagConstraints.NONE;
        cns.gridheight = 1;

        Panel p = new Panel();

        grid.setConstraints(p, cns);
        add(p);     
        p.setLayout(new FlowLayout());
        p.add(loc);
        p.add(go);
        
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
