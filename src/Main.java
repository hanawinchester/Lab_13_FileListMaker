import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import javax.swing.JFileChooser;




public class Main
{
    //static ArrayList<String> list = new ArrayList<>();
    public static void main(String[] args)
    {
        String listForm;
        final String menu = "A - Add  D - Delete  V - View  Q - Quit  O - Open List File  C - Clear List  S - Save List";
        boolean done = false;
        String cmd = "";
       // Scanner newList = new Scanner(System.in);
       // System.out.println("Do you want to choose an existing list or create a new list (E/N) ?: ");
        //listForm = newList.nextLine();
        // Test data the lines of the file we will write
        ArrayList <String>recs = new ArrayList<>();

        // uses a fixed known path:
        //  Path file = Paths.get("c:\\My Documents\\data.txt");

        // use the toolkit to get the current working directory of the IDE
        // will create the file within the Netbeans project src folder
        // (may need to adjust for other IDE)
        // Not sure if the toolkit is thread safe...

        try
        {
                File workingDirectory = new File(System.getProperty("user.dir"));
                Path file = Paths.get(workingDirectory.getPath() + "c:\\Downloads\\newData.txt");

            //else{
             //   JFileChooser chooser = new JFileChooser();
             //   File selectedFile;
             //   File workingDirectory = new File(System.getProperty("user.dir"));
             //   chooser.setCurrentDirectory(workingDirectory);
             //   selectedFile = chooser.getSelectedFile();
             //   Path file = selectedFile.toPath();
            //}

            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream

            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));
            Scanner console = new Scanner(System.in);
            //Boolean newOpen = false;
            Boolean saved = false;
            do {


                String listAddition = "";
                //int listDeletion;
                //display the list
                //display the menu options
                //get a menu choice
                cmd = SafeInput.getRegExString(console, "\n" + menu, "[AaDdVvQqOoCcSs]");
                cmd = cmd.toUpperCase();


                //execute the choice
                switch(cmd)
                {
                    case "A":
                        saved = false;
                        Scanner in1 = new Scanner(System.in);
                        System.out.print("Enter item to add: ");
                        listAddition = in1.nextLine();
                        recs.add(listAddition);
                        //selectedFile.add(listAddition);
                        System.out.println("Please save your list edits!");
                        break;
                    case "D":
                        saved = false;
                        Scanner in2 = new Scanner(System.in);
                        System.out.println("Which item index do you want to delete? ");
                        int deletion = SafeInput.getRangedInt(in2, "Which item index do you want to delete?", 1, recs.size());
                        //listDeletion = in2.nextInt();
                        //String var = list.get(listDeletion);
                        recs.remove(deletion-1);
                        System.out.println("Please save your list edits!");
                        break;
                    case "V":
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        if (recs.size() != 0)
                        {
                            for (int i = 0; i < recs.size(); i++)
                            {
                                System.out.println("");
                                System.out.printf("%3d%35s", i + 1, recs.get(i) );
                            }
                        }
                        else
                        {
                            System.out.println("+++                          List is empty                           +++");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        }
                        //System.out.printf(String.valueOf(recs));
                        break;
                    case "Q":
                        Scanner in3 = new Scanner(System.in);
                        SafeInput.getYNConfirm(in3, "Are you sure you want to quit?");
                        if (!saved) {
                            System.out.println("Please save your list first.");
                            break;
                        }
                        System.exit(0);
                        done = true;
                        break;
                    case "O":
                        //newOpen = true;
                        if (!saved){
                            System.out.println("Please save your current list first.");
                            break;
                        }
                        JFileChooser chooser = new JFileChooser();
                        File selectedFile;
                        String rec = "";
                        recs.clear();

                        try
                        {
                            // uses a fixed known path:
                            //  Path file = Paths.get("c:\\My Documents\\data.txt");

                            // use the toolkit to get the current working directory of the IDE
                            // Not sure if the toolkit is thread safe...
                            File workingDirectory1 = new File(System.getProperty("user.dir"));

                            // Typiacally, we want the user to pick the file so we use a file chooser
                            // kind of ugly code to make the chooser work with NIO.
                            // Because the chooser is part of Swing it should be thread safe.
                            chooser.setCurrentDirectory(workingDirectory1);
                            // Using the chooser adds some complexity to the code.
                            // we have to code the complete program within the conditional return of
                            // the filechooser because the user can close it without picking a file

                            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                            {
                                selectedFile = chooser.getSelectedFile();
                                Path file1 = selectedFile.toPath();

                                // Typical java pattern of inherited classes
                                // we wrap a BufferedWriter around a lower level BufferedOutputStream
                                InputStream in =
                                        new BufferedInputStream(Files.newInputStream(file1, CREATE));
                                BufferedReader reader =
                                        new BufferedReader(new InputStreamReader(in));

                                // Finally we can read the file LOL!
                                int line = 0;
                                int linesIndex = 0;
                                while(reader.ready())
                                {
                                    rec = reader.readLine();
                                    recs.add(rec);
                                    line++;
                                    linesIndex = linesIndex + 1;
                                    // echo to screen
                                    //System.out.printf("\nLine %4d %-60s ", line, rec);
                                }

                                System.out.println("Name of selected file: " + selectedFile);
                                reader.close(); // must close the file to seal it and flush buffer
                                System.out.println("\n\nData file read!");
                            }
                            else  // user closed the file dialog wihtout choosin
                            // g
                            {
                                System.out.println("Failed to choose a file to process");
                                System.out.println("Run the program again!");
                                System.exit(0);
                            }

                        }  // end of TRY
                        catch (FileNotFoundException e)
                        {
                            System.out.println("File not found!!!");
                            e.printStackTrace();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        //JFileChooser chooser = new JFileChooser();
                       // File selectedFile1;
                       // File workingDirectory1 = new File(System.getProperty("user.dir"));
                    //    chooser.setCurrentDirectory(workingDirectory1);

                        //if(chooser.showOpenDialogue(null) == JFileChooser.APPROVE_OPTION) {
                        //Path file = Paths.get(workingDirectory.getPath()
                      //      selectedFile1 = chooser.getSelectedFile();
                      //      Path file1 = Paths.get(workingDirectory1.getPath());
                      //      InputStream in =
                      //              new BufferedInputStream(Files.newInputStream(file1, CREATE));
                     //       BufferedReader reader =
                    //                new BufferedReader(new InputStreamReader(in));
                    //        int line = 0;
                      //      String lines = "";
                      //      recs.clear();
                    //        while (reader.ready()) {
                  //              lines = reader.readLine();
                  //              recs.add(lines);
                  //              line++;
                  //          }
                  //          OutputStream out1 =
                 //                   new BufferedOutputStream(Files.newOutputStream(file1));
                 ///           BufferedWriter writer1 =
                  //                  new BufferedWriter(new OutputStreamWriter(out));
                        //}
                        break;

                    case "S":
                        File workingDirectory3 = new File(System.getProperty("user.dir"));
                        Path file1 = Paths.get(workingDirectory.getPath() + "\\Users\\stevewinchester\\Downloads\\newData.txt");
                        OutputStream out1 =
                                new BufferedOutputStream(Files.newOutputStream(file1));
                        BufferedWriter writer1 =
                                new BufferedWriter(new OutputStreamWriter(out));
                        for(String rec1 : recs)
                        {
                            writer1.write(rec1, 0, rec1.length());  // stupid syntax for write rec
                            // 0 is where to start (1st char) the write
                            // rec. length() is how many chars to write (all)
                            writer1.newLine();  // adds the new line

                        }

                        saved = true;
                        writer.close(); // must close the file to seal it and flush buffer
                        System.out.println("Saved!");
                        break;
                    case "C":
                        saved = false;
                        recs.clear();
                        System.out.println("Please save your list edits.");
                        break;
                }
                //System.out.println("cmd is " + cmd);
            }
            while (!done);
            // Finally can write the file LOL!

            //for(String rec : recs)
            //{
            //    writer.write(rec, 0, rec.length());  // stupid syntax for write rec
            //    // 0 is where to start (1st char) the write
             //   // rec. length() is how many chars to write (all)
             //   writer.newLine();  // adds the new line

            //}
            //writer.close(); // must close the file to seal it and flush buffer
            //System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


}