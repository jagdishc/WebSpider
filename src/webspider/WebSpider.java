package webspider;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class WebSpider extends Thread
{
    Vector vectorToSearch;  //List of url's to be crawled.
    static Vector vectorSearched;  //List of url's that have been crawled.
    String seedURL;         //Starting url given by the user.
    public static final String DISALLOW = "Disallow:"; 
    public static int totalBytes = 0;
    int searchLimit = 30, numberOfSearches = 0;//searchLimit: Defines the total number of page to be crawled.   
    CrawlHistory cHistory;
    HashMap<String, Long> odbfm;
    //WebSpiderView wsv;

    WebSpider(String seed,int search, WebSpiderView wsv)
    {
        seedURL = seed;
        searchLimit = search;
        cHistory = new CrawlHistory();
        odbfm = new HashMap<>();
        //this.wsv = wsv;
    }
    public void run()
    {
        initializer();
    }
    public void initializer()     //initializes the lists.
    {        
        vectorToSearch = new Vector();
        vectorSearched = new Vector();               
        vectorToSearch.addElement(seedURL);
        cHistory.setTitle("Crawl history for: " + seedURL);
        cHistory.crawlHistory.setText("Starting the process...\n");
        cHistory.progressStatus.setMaximum(searchLimit);
        cHistory.setVisible(true);
        cHistory.crawlHistory.setEditable(false);        
        pageReader();               
    }
    public void pageReader()    //Connects to the specified url and reads the html content.
    {
        while(vectorToSearch.size() > 0 && numberOfSearches < searchLimit)
        {
            try
            {
                String url_string = (String)vectorToSearch.elementAt(0);
                URL startURL = new URL(url_string);                
                vectorToSearch.removeElementAt(0);                                         
                if(startURL.getProtocol().compareTo("http") != 0 && robotSafe(startURL))
                {                    
                    //Skips to next iteration when the protocol is not http or when the url is not safe to crawl given by the robots.txt of the website.
                    System.out.println("OMITTED - " + startURL.toString()); 
                    cHistory.crawlHistory.append("OMITTED: " + url_string + "\n");
                    continue;
                }
                numberOfSearches += 1; 
                cHistory.crawlHistory.append("Crawled: " + url_string + "\n");
                vectorSearched.addElement(url_string);
                cHistory.progressStatus.setValue(vectorSearched.size());
                //wsv.statusLabel.setText("Processing: " + cHistory.progressStatus.getPercentComplete()*100 + "% completed");
                cHistory.progress.setText("Completed " + cHistory.progressStatus.getPercentComplete()*100 + "%");
                System.out.println("Crawled: " + startURL.toString());                               
                startURL.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(startURL.openStream()));
                String host = startURL.getHost().toString();
                String filename = startURL.getPath().toString();               
                if(filename.compareTo("") == 0 || filename.compareTo("/") == 0)
                {
                    filename = "/index.html";
                }
                String path = host + filename;
                String[] splittedPath = path.split("/");  
                String filePath = "";
                for(int i=0;i<splittedPath.length-1;i++)
                {
                    if(i>0)
                    {
                        File file = new File(filePath + "/" + splittedPath[i]);
                        file.mkdirs();
                    }
                    else
                    {
                        File file = new File(splittedPath[i]);
                        file.mkdir();
                    }
                    filePath += splittedPath[i] + "/";
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                String line, fullHtml = "";                
                while ((line = reader.readLine()) != null) 
                {
                    totalBytes += line.length();
                    writer.write(line);
                    writer.newLine();
                    fullHtml += line;
                }               
                reader.close();
                writer.close();           
                File file = new File(path);
                long value = file.lastModified();
                if(!odbfm.containsKey(file.toString()))
                {
                    odbfm.put(file.toString(), value);
                }
                //Reads the page and stores it in appropriate location in the local harddisk.
                htmlParser(fullHtml);
            }
            catch(MalformedURLException e)                
            {
                e.printStackTrace();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }           
        }
        if(vectorToSearch.size() <= 0 || numberOfSearches >= searchLimit)
        {
            URL temp;
            try
            {
                temp = new URL(seedURL);
            
                File odbfm_file = new File("odbfm/url_odbfm.dat");
                try
                {
                    FileWriter file_writer = new FileWriter(odbfm_file, true);
                    for(Iterator<String> it = odbfm.keySet().iterator();it.hasNext();)
                    {
                        String u = it.next();
                        long l_mod_date = odbfm.get(u);
                        file_writer.write(u + "^" + l_mod_date);
                        file_writer.write("\n");
                    }
                    file_writer.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                System.out.println("Finished the crawling process.");
                //wsv.statusLabel.setText("Finished the crawling process for: " + seedURL);
                cHistory.crawlHistory.append("Process Completed.\n");
                cHistory.crawlHistory.append("Total number of Kilo bytes downloaded: " + ((float)(totalBytes/1024)));
                cHistory.progress.setText("Completed.");
                //System.exit(0);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public boolean robotSafe(URL url)   //Checks whether the page is safe to crawl from the robots.txt
    {
        String host = url.getHost();
        String robotTxt = "http://" + host + "/robots.txt";
        String strCommands;
        try
        {
            URL robot = new URL(robotTxt);
            InputStream urlRobotStream = robot.openStream();
	    // read in entire file
	    byte b[] = new byte[1000];
	    int numRead = urlRobotStream.read(b);
	    strCommands = new String(b, 0, numRead);
	    while (numRead != -1) {
		numRead = urlRobotStream.read(b);
		if (numRead != -1) {
		    String newCommands = new String(b, 0, numRead);
		    strCommands += newCommands;
		}
	    }
	    urlRobotStream.close();
            String strURL = url.getFile();
            int index = 0;
            while ((index = strCommands.indexOf(DISALLOW, index)) != -1) {
                index += DISALLOW.length();
                String strPath = strCommands.substring(index);
                StringTokenizer st = new StringTokenizer(strPath);
                if (!st.hasMoreTokens())
                {
                    break;
                }
                String strBadPath = st.nextToken();
                // if the URL starts with a disallowed path, it is not safe to crawl
                if (strURL.indexOf(strBadPath) == 0)
                    return false;
            }
        }
        catch(MalformedURLException ex)
        {
            return false;
        }
        catch(IOException e)
        {
            return true;
        }        
        return true;
    }
    public void htmlParser(String html)     //Parses the page to get links in the page. Also adds them to the ToSearch list.
    {   
        Document doc;
        Elements anchorTags;
        try
        {
            doc = Jsoup.connect(seedURL).get();
            anchorTags = doc.select("a");
            for(Element link : anchorTags)
            {
                String absUrl = link.absUrl("href");            
                if(vectorToSearch.contains(absUrl) == false && vectorSearched.contains(absUrl) == false && absUrl.compareTo("") != 0 )
                {                 
                    vectorToSearch.addElement(absUrl);                
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }        
        
    }

}