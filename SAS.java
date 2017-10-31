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
      public boolean savedStatus = false;
     // public String[] fontOptions = {"Serif", "Agency FB", "Arial", "Calibri", "Cambrian", "Century Gothic", "Comic Sans MS"};

    // JComboBox <String> fontList = new JComboBox<>(fontOptions);



        JTextArea textArea,textArea1;
        JScrollPane scrollpane,scrollpane1;
        JMenuBar menuBar;
        JMenu menu,menu1,menu2;
        JMenuItem newAction,saveAction,openAction,closeAction,undoAction,redoAction,BoldAction,FontFamily,Serif,courier;
        JTabbedPane tabbedPane,tabbedPane1;
        JComboBox box;
        JLabel label;




        public void setsavedStatus(boolean savedStatus)
        {
          this.savedStatus = savedStatus;
        }

        public boolean getsavedStatus()
        {
          return savedStatus;
        }
        


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
         setVisible(true);
        }

        public void create_menu()
        {
                menuBar = new JMenuBar();

                menu = new JMenu("FILE");
                menu1 = new JMenu("Edit");
                menu2 = new JMenu("Font");
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
                         
                        

                        //System.out.println("The selected tab is"+tabbedPane.getSelectedIndex());
                        if(userSelection == JFileChooser.APPROVE_OPTION)
                        {
                                setsavedStatus(true);
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
                                         JOptionPane.showConfirmDialog(null,message,"title",JOptionPane.WARNING_MESSAGE);
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

                     if(savedStatus)
                     tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());

                      else
                      {
                      UIManager.put("OptionPane.minimumSize",new Dimension(300,100)); 
                       String message = "This file is not saved";
                       int ans = JOptionPane.showConfirmDialog(null,"This file is not saved.\nAre you sure you want to close?");

                       if(ans ==JOptionPane.YES_OPTION)
                       {
                        tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
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
                       // System.out.println("Hello");

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


                  Serif = new JMenuItem("Serif");
                  Serif.addActionListener(new ActionListener()
                  {
                    public void actionPerformed(ActionEvent ev2)
                    {
                      textAreaNo.get(tabbedPane.getSelectedIndex()).setFont(new Font("Serif",Font.PLAIN,12));
                    }
                  });


                 JMenuItem Agency = new JMenuItem("Agency FB");
                  Agency.addActionListener(new ActionListener()
                  {
                    public void actionPerformed(ActionEvent ev3)
                    {
                      textAreaNo.get(tabbedPane.getSelectedIndex()).setFont(new Font("Agency FB",Font.PLAIN,12));
                    }
                  });


                  courier = new JMenuItem("Courier");
                  courier.addActionListener(new ActionListener()
                  {
                    public void actionPerformed(ActionEvent ev4)
                    {
                      textAreaNo.get(tabbedPane.getSelectedIndex()).setFont(new Font("Courier",Font.BOLD,12));
                    }
                  });

                   JMenuItem Cambrian = new JMenuItem("Cambrian");
                  Cambrian.addActionListener(new ActionListener()
                  {
                    public void actionPerformed(ActionEvent ev3)
                    {
                      textAreaNo.get(tabbedPane.getSelectedIndex()).setFont(new Font("Cambrian",Font.PLAIN,12));
                    }
                  });

              
                  JMenuItem selectAction = new JMenuItem("Select All");
                  selectAction.addActionListener(new ActionListener()
                  {
                    public void actionPerformed(ActionEvent ev4)
                    {
                     textAreaNo.get(tabbedPane.getSelectedIndex()).requestFocus();
                     textAreaNo.get(tabbedPane.getSelectedIndex()).selectAll();
                     }
                  });

               



                menuBar.add(menu);
                menuBar.add(menu1);
                menuBar.add(menu2);
                menu.add(newAction);
                menu.add(saveAction);
                menu.add(openAction);
                menu.add(closeAction);
                menu1.add(undoAction);
                menu1.add(redoAction);
                menu1.add(selectAction);
                menu2.add(Serif);
                menu2.add(Agency);
                menu2.add(courier);
                menu2.add(Cambrian);
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
                    setsavedStatus(false);
          
                    JPanel panel = new JPanel();
                    panel.setLayout(new BorderLayout());
                    panelNo.add(panel);
                    panelNo.get(panelCounter).add(new JScrollPane(textAreaNo.get(textAreaCounter)),BorderLayout.CENTER);
                    tabbedPane.addTab("File",panelNo.get(panelCounter));
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