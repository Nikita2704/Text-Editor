import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;
import javax.swing.JScrollBar;


public class SimpleTextEditor implements ActionListener {
    //creating frame
    static JFrame frame;

    JTextArea textarea;//creating area for text
    JMenuBar menubar;//creating menubar
    JMenu File, Edit, Close;//creating menu's
    JMenuItem New, Open, Save, Print;//creating item present in menu
    JMenuItem Cut, Copy, Paste;
    JMenuItem CloseEditor;
    JScrollPane scroll;

    // create a constructor
    SimpleTextEditor() {
        //add name
        frame = new JFrame("Simple Text Editor");
        frame.setLayout(null);

        // set boundaries of the frame
        frame.setBounds(0, 0, 500, 500);


        //add text
        textarea = new JTextArea("Welcome to the editor");
        textarea.setBounds(0, 0, 1500, 720);
        //create object
        menubar = new JMenuBar();
        File = new JMenu("File");
        Edit = new JMenu("Edit");
        Close = new JMenu("Close");

        //add menu to the menubar
        menubar.add(File);
        menubar.add(Edit);
        menubar.add(Close);

        New = new JMenuItem("New");
        New.addActionListener(this);//create object for actionlistener
        Open = new JMenuItem("Open");
        Open.addActionListener(this);
        Save = new JMenuItem("Save");
        Save.addActionListener(this);
        Print = new JMenuItem("Print");
        Print.addActionListener(this);
        File.add(New);//add menuitem to menu
        File.add(Open);
        File.add(Save);
        File.add(Print);
        Cut = new JMenuItem("Cut");
        Cut.addActionListener(this);
        Copy = new JMenuItem("Copy");
        Copy.addActionListener(this);
        Paste = new JMenuItem("Paste");
        Paste.addActionListener(this);
        Edit.add(Cut);
        Edit.add(Copy);
        Edit.add(Paste);
        CloseEditor = new JMenuItem("Close");
        CloseEditor.addActionListener(this);
        Close.add(CloseEditor);
        scroll = new JScrollPane(textarea);
        scroll.setVerticalScrollBar(new JScrollBar());
        //scroll.setBounds(480, 0, 20, 500);
        frame.add(scroll);

        frame.setJMenuBar(menubar);

        //added text to frame
        frame.add(textarea);

        //when we click exit then the program will be stop running
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //make our frame visible
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        //object crated for constructor
        SimpleTextEditor s = new SimpleTextEditor();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //string to add the command given by user
        String s = e.getActionCommand();
        if (s.equals("Copy")) {
            //use copy function to copy the text
            textarea.copy();
        } else if (s.equals("Cut")) {
            // use cut function to cut the text
            textarea.cut();
        } else if (s.equals("Paste")) {
            //use paste function to paste the text
            textarea.paste();
        } else if (s.equals("Print")) {
            try {
                //use print function to print the text
                textarea.print();
            } catch (PrinterException ex) {

                //catch funtion when no external device was connected
                throw new RuntimeException(ex);
            }
        } else if (s.equals("New")) {
            //for new we just set the text as blank
            textarea.setText("");
        } else if (s.equals("Close")) {
            //for close we use system exit function and to make it work we put status 1 if we put status 0 then it will not work;
            System.exit(1);
        } else if (s.equals("Open")) {
            // Jfilechoose to choose the file from our folder we have given
            JFileChooser filechooser = new JFileChooser("C:");

            // to store the file which was choosed,and initially its value is null because we havent choose any file ywt
            int ans = filechooser.showOpenDialog(null);

            //function whe we approve the given option
            if (ans == JFileChooser.APPROVE_OPTION) {

                //for the path of the file we have choose
                File file1 = new File(filechooser.getSelectedFile().getAbsolutePath());

                String s1 = "", s2 = "";
                try {
                    //for reading the text of the file we choose
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file1));

                    //for storing the text present in file line by line
                    s2 = bufferedReader.readLine();

                    // run until the test in file become null
                    while ((s1 = bufferedReader.readLine()) != null) {
                        //for storing the text which was present in the text we choose
                        s2 += s1 + "\n";
                    }
                    textarea.setText(s2);
                } catch (FileNotFoundException ex) {
                    //exception if there is no file found with the given name
                    throw new RuntimeException(ex);

                } catch (IOException ex) {
                    //exception when file is empty
                    throw new RuntimeException(ex);
                }
            }
        } else if (s.equals("Save")) {
            //to choose file
            JFileChooser fileChooser = new JFileChooser("C:");

            int ans = fileChooser.showOpenDialog(null);
            if (ans == fileChooser.APPROVE_OPTION) {

                //for path of choosed file
                File file1 = new File(fileChooser.getSelectedFile().getAbsolutePath());

                //for writing in the choosed file
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(file1, false));

                    //for writing the text present in textarea
                    writer.write(textarea.getText());

                    //for flushing the old data
                    writer.flush();

                    //for closing the writer
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);

                }
            }
        }
    }
}

