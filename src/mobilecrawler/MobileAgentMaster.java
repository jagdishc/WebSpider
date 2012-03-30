package mobilecrawler;

import com.ibm.aglet.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MobileAgentMaster  extends Aglet
{
    HashMap<String, Long> odbfm;
    HashMap<String, String> modified_pages;
    File odbfm_file;
    BufferedReader br;
    FileReader fr;
    MobileAgentMasterView mamv;
    URL destination;
    private static long SLEEP = 6000;
    public void onCreation(Object init)
    {         
         odbfm = new HashMap<String, Long>();
         modified_pages = new HashMap<String, String>();         
         MobileAgentController mac = new MobileAgentController(this);
         mac.pack();
         mac.setPreferredSize(mac.getPreferredSize());
         mac.setVisible(true);
    }
    
    public void setFile(File file, URL dest)
    {
        odbfm_file = file;
        destination = dest;
        try
        {
            fr = new FileReader(odbfm_file);
            br = new BufferedReader(fr);
            String line;                
            StringTokenizer tokens;
            while((line = br.readLine()) != null)
            {
                tokens = new StringTokenizer(line, "^");
                while(tokens.hasMoreTokens())
                {
                    String u = tokens.nextToken();
                    long l = Long.parseLong(tokens.nextToken());
                    
                    if(!odbfm.containsKey(u))
                    {
                        odbfm.put(u, l);
                    }
                }
            }
            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void pause() 
    { 
        try 
        { 
            Thread.sleep(SLEEP); 
        } 
        catch (InterruptedException ie) 
        {
            ie.printStackTrace();
        } 
    }
    public void createChild()
    {
        try
        {
            Object args[] = new Object[]{odbfm, modified_pages};
            AgletProxy proxy = getAgletContext().createAglet(getCodeBase(), "mobilecrawler.MobileAgentChild", args);
            proxy.dispatch(destination);
            pause();
            proxy = getAgletContext().retractAglet(destination, proxy.getAgletID());
            //proxy.dispose();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
   
}
