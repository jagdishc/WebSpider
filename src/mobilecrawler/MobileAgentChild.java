package mobilecrawler;

import com.ibm.aglet.*;
import com.ibm.aglet.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;

public class MobileAgentChild extends Aglet
{
    //Object[] args;
    HashMap<String, Long> odbfm;
    HashMap<String, String> modified_pages;
    String loc = "C:\\xampp\\htdocs";
    boolean retracted = false;
    public void onCreation(Object init)
    {
       
        odbfm = (HashMap) ((Object[]) init)[0];
        modified_pages = (HashMap) ((Object[]) init)[1];
       addMobilityListener(
         new MobilityAdapter() {
            public void onArrival(MobilityEvent me) 
            {
               if(!retracted)
               {                   
                   for(Iterator it = odbfm.keySet().iterator(); it.hasNext();)
                   {
                       String key = (String) it.next();
                       long val = (long) odbfm.get(key);
                       
                       File file = new File(loc + "\\" + key);
                       if(file != null)
                       {
                           long temp = file.lastModified();
                           if(temp > val)
                           {
                              try
                              {
                                FileReader fr = new FileReader(file);
                                BufferedReader br = new BufferedReader(fr);
                                String line;
                                System.out.println(key);
                                while((line = br.readLine()) != null)
                                {
                                    modified_pages.put(key, line);
                                }
                              }
                              catch(Exception ex)
                              {
                                  ex.printStackTrace();
                              }
                           }
                       }
                    }
               }
               else
               {
                   FinishedHistory fh = new FinishedHistory();
                   fh.pack();
                   fh.setPreferredSize(fh.getPreferredSize());
                   fh.setVisible(true);
                   fh.history.setEditable(false);
                   fh.history.setText("Crawled pages are: \n");
                   int bytes = 0;
                   for(Iterator it = modified_pages.keySet().iterator();it.hasNext();)
                   {
                       String key = (String) it.next();
                       String text = modified_pages.get(key);
                       bytes += text.length();                       
                       System.out.println(key);                       
                       fh.history.append(key + "\n");
                   }                  
                   fh.history.append("Total number of kilo bytes downloaded: " + (float)bytes/1024);
                   System.out.println("Total number of kilo bytes downloaded: " + (float) bytes/1024);
                   System.out.println("Finished");
               }
            }	    
	    public void onReverting(MobilityEvent me) 
            {
               retracted = true;
	    }
	 }
      );
    }
}
