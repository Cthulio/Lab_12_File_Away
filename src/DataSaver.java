import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String firstName;
        String lastName;
        int userID;
        String userIDStr;//a holding place for the userID as we give it additional zeros
        String email;
        int birthYear;
        ArrayList<String> lines = new ArrayList<>();
        String rec;

        do
        {
            System.out.println("Please input data for a data entry.");
            firstName = SafeInput.getNonZeroLenString(scan, "Please input your First Name");
            lastName = SafeInput.getNonZeroLenString(scan, "Please input your Last Name");
            userID = SafeInput.getRangedInt(scan, "Please input your ID# (000000-999999)", 0, 999999);
            userIDStr = String.format("%06d", userID);//by using the String object we can call .format() and add zeros to the front automatically.
            email = SafeInput.getRegExString(scan, "Please input your email", "[a-z0-9._%+\\-]+@[a-z0-9.\\-]+\\.[a-z]{2,}$");
            //I used W3's example email regex as a reference: https://www.w3schools.com/TAGS/att_input_pattern.asp
            birthYear = SafeInput.getRangedInt(scan, "Please input your birthyear", 1894, 2025);//making assumptions of the oldest living people
            rec = firstName+","+lastName+","+userIDStr+","+email+","+birthYear;
            System.out.println(rec);
            lines.add(rec);
        }
        while (SafeInput.getYNConfirm(scan, "Do you want to make another line?"));

        String fileName = SafeInput.getNonZeroLenString(scan,"Please name your file.");
        System.out.println("Saving your file...");
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\"+fileName+".csv");

        try
        {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for(String recs : lines)
            {
                writer.write(recs,0,recs.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("Saved!");
        }
        catch (Exception e)
        {
            System.out.println(e);        }
    }
}