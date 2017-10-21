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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.*;

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
        JMenu menu,menu1;
        JMenuItem newAction,saveAction,openAction,closeAction,undoAction,redoAction;
        JTabbedPane tabbedPane,tabbedPane1;
         
        


        public text_area()
        {

        // creates new window
          super("SAS");
          setSize(900,900);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setLayout(new BorderLayout());
          tabbedPane = new JTabbedPane();




           tabbedPane.addChangeListener(new ChangeListener() {

           public void stateChanged(ChangeEvent evt) 
           {
              JTabbedPane tabbedPane = (JTabbedPane)evt.getSource();
           }

           });

    
         

       
         // create_File();
          create_File();

        //calls create menu
         create_menu();
        }

        public void create_menu()
        {
                menuBar = new JMenuBar();
                menu = new JMenu("FILE");
                
                menu1 = new JMenu("Edit");


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
                         
                         
                        System.out.println("The selected tab is"+tabbedPane.getSelectedIndex());
                        if(userSelection == JFileChooser.APPROVE_OPTION)
                        {
                                File file = fileChooser.getSelectedFile();
                                
                                BufferedWriter  writer = null;
                                try

                                {
                                        writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()+""));
                                        
                                        String filename = file.getName();
                                        tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),filename);
                                        int index = filename.indexOf(".");
                                        System.out.println(index);
                                        if(index==-1)
                                        {
                                         UIManager.put("OptionPane.minimumSize",new Dimension(300,300)); 
                                         String message = "Please enter a file type\n";
                                         JOptionPane.showMessageDialog(null,message,"title",JOptionPane.WARNING_MESSAGE);
                                        }
                                        writer.write(textAreaNo.get(tabbedPane.getSelectedIndex()).getText());
                                        writer.close();
                                }

                                catch(IOException a)
                                {
                                    System.out.println("Something went Wrong");
                                }
                        }
                        
                }
         });


                closeAction = new JMenuItem("Close");

                closeAction.addActionListener(new ActionListener()
                {
                   public void actionPerformed(ActionEvent e)
                   {
                     tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
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
                        tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),filename);


                       try 
                       {
                          FileReader fr = new FileReader(file);
                          BufferedReader br = new BufferedReader(fr);

                          
                          String s=""; int c=0;
                                while((c=br.read())!=-1)
                                        s+=(char)c; 

                         textAreaNo.get(tabbedPane.getSelectedIndex()).append(s);

                        
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


                undoAction = new JMenuItem("Undo");
                UndoManager manager = new UndoManager();
                textAreaNo.get(tabbedPane.getSelectedIndex()).getDocument().addUndoableEditListener(manager);

                 undoAction.addActionListener(new ActionListener()
                 {

                   public void actionPerformed(ActionEvent ev)
                   {
                    try
                    {
                          manager.undo();
                    }
                    catch(CannotUndoException ex)
                    {
                      ex.printStackTrace();
                    }


                   }
                 });


                redoAction = new JMenuItem("Redo");
                 textAreaNo.get(tabbedPane.getSelectedIndex()).getDocument().addUndoableEditListener(manager);

                redoAction.addActionListener(new ActionListener()
                {

                  public void actionPerformed(ActionEvent ev1)
                  {
                    try
                    {
                      manager.redo();
                    }
                      catch(CannotRedoException rx)
                      {
                        rx.printStackTrace();
                      }
                  }
                });

                
        


                menuBar.add(menu);
                menuBar.add(menu1);
                menu.add(newAction);
                menu.add(saveAction);
                menu.add(openAction);
                menu.add(closeAction);
                menu1.add(undoAction);
                menu1.add(redoAction);
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
                   // System.out.println("counter is "+textAreaCounter+" "+panelCounter);

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