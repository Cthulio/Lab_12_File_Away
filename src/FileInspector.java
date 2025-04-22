import javax.swing.*;// this was auto imported when I wrote the JFileChooser code.
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector {
    public static void main(String[] args) {
        //setting my variables and initial logic up according to the examples provided in the lectures.
        //note: I typed all of this manually and to the best of my understanding. It still follows the basic structure
        // of the example as a file handler would not have much variation in how it could be set up. I used the variable
        // names according to the examples to follow along more easily.
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec="";
        final int FIELDS_LENGTH = 5;

        try{
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);
            // the example also shows that we can do this another way...
            // Path target = new File(System.getProperty("user.dir")).toPath();
            // chooser.setCurrentDirectory(target.toFile());
            // our way just makes more sense to me, with separation of values.

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                //handle opening file here
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                ArrayList<String> lines = new ArrayList<>();

                int line = 0;
                while(reader.ready())
                {
                    //Line handling:
                    rec = reader.readLine();//read line
                    lines.add(rec); //add the read line to the arraylist
                    line++;//iterate line count (contained within the arraylist)
                    //System.out.printf("\nLine %4d %-60s ", line, rec);//print user-friendly line and line count
                    //commenting line print out, it's good to reference, but we don't need it right now.
                }
                reader.close();//let the reader rest

                int wordCount = 0;
                String[] words;
                int charCount = 0;
                String[] characters;

                for(String l:lines) {
                    words = l.split(" ");
                    wordCount += words.length;


                    characters = l.split("");//this does include spaces.
                    charCount += characters.length;
                }


                System.out.println("File Name: "+file.getFileName());
                System.out.println("Line count: "+line);
                System.out.println("Words: "+wordCount);
                System.out.println("Characters (including space): "+charCount);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Something happened when opening the file, please run the program again.");
            e.printStackTrace();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }
}
