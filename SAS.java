import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.*;
import java.io.*;
import java.io.BufferedReader;
import javax.swing.JOptionPane;
import java.util.ArrayList;
class text_area extends JFrame
{


    // declares an arraylist of textareas
      public ArrayList<JTextArea> textAreaNo = new ArrayList<JTextArea>();
      public ArrayList<JPanel> panelNo = new ArrayList<JPanel>();
      public int textAreaCounter = 0;
      public int panelCounter = 0;




        JTextArea textArea,textArea1;
        JScrollPane scrollpane,scrollpane1;
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem newAction,saveAction,openAction;
        JTabbedPane tabbedPane,tabbedPane1;
         
        


        public text_area()
        {

        // creates new window
          super("SAS");
          setSize(900,900);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setLayout(new BorderLayout());
          tabbedPane = new JTabbedPane();

    
         

       
         // create_File();
          create_File();

        
      
        

        //calls create menu
         create_menu();
        
        }

        public void create_menu()
        {
                menuBar = new JMenuBar();
                menu = new JMenu("FILE");
                newAction = new JMenuItem("New");


             // implements the new function

                newAction.addActionListener(new ActionListener()
                {
                        public void actionPerformed(ActionEvent e)
                        {
                              create_File();
                        }
                });

                saveAction = new JMenuItem("Save");  
            


            //implements the save function 

                saveAction.addActionListener(new ActionListener()
         {
                public void actionPerformed(ActionEvent e)
                {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Specify new File");
                        fileChooser.setSelectedFile(new File("file1"));
                        int userSelection = fileChooser.showSaveDialog(fileChooser);
                         
                         

                        if(userSelection == JFileChooser.APPROVE_OPTION)
                        {
                                File file = fileChooser.getSelectedFile();

                                BufferedWriter  writer = null;
                                try

                                {
                                        writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()+""));
                                        
                                        String filename = file.getName();
                                        int index = filename.indexOf(".");
                                        System.out.println(index);
                                        if(index==-1)
                                        {
                                         UIManager.put("OptionPane.minimumSize",new Dimension(300,300)); 
                                         String message = "File type kaun tera baap dalega kya BC.\nTera Gand me akkal hai kya?";
                                         JOptionPane.showMessageDialog(null,message,"title",JOptionPane.WARNING_MESSAGE);
                                        }
                                        writer.write(textArea.getText());
                                        writer.close();
                                }

                                catch(IOException a)
                                {
                                    System.out.println("Something went Wrong");
                                }
                        }
                        
                }
         });

             // implements the open function

                openAction = new JMenuItem("Open");

                openAction.addActionListener(new ActionListener()
                {

                 public void actionPerformed(ActionEvent e )
                 {
                      JFileChooser fileChooser = new JFileChooser();
                      int ans = fileChooser.showOpenDialog(fileChooser);
                      if(ans == JFileChooser.APPROVE_OPTION)
                      {
                        File file = fileChooser.getSelectedFile();
                        String filename = fileChooser.getSelectedFile().getName();


                       try 
                       {
                          FileReader fr = new FileReader(file);
                          BufferedReader br = new BufferedReader(fr);

                          
                          String s=""; int c=0;
                                while((c=br.read())!=-1)
                                        s+=(char)c; 

                         textArea.append(s);

                        
                                br.close();
                                fr.close();
                       }

                       catch(IOException b)
                       {
                          System.out.println("Something went wrong\n");
                       }

                      }
              }
                });


                
        


                menuBar.add(menu);
                menu.add(newAction);
                menu.add(saveAction);
                menu.add(openAction);
                setJMenuBar(menuBar);


        }




               public void create_File()
                 {
                    textArea = new JTextArea();
                    textArea.setSize(900,900);
                    textArea.setLineWrap(true);
                    textArea.setEditable(true);
                    textArea.setFont(new Font("Serif",Font.ITALIC,15));
                    textAreaNo.add(textArea);

          
                    JPanel panel = new JPanel();
                    panel.setLayout(new BorderLayout());
                    panelNo.add(panel);
                    panelNo.get(panelCounter).add(new JScrollPane(textAreaNo.get(textAreaCounter)),BorderLayout.CENTER);
                    tabbedPane.addTab("sladyn",panelNo.get(panelCounter));
                    add(tabbedPane);
                    setVisible(true);
                    System.out.println("counter is "+textAreaCounter+" "+panelCounter);

                    textAreaCounter++;
                    panelCounter++;

                 }

      
}
 







public class SAS
{
        public static void main(String args[])
        {
               new text_area();
        }
}