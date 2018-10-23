package com.cx.gal.tickeTooli.logExtractor;

import com.cx.gal.tickeTooli.logExtractor.dto.Ret;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


/**
 * Created by Galn on 10/18/2018.
 */
public class LogExtractor {
    private String lowerStr;
    private String upperStr;
    private String logsPath;
    private List<LogTypes> types;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH); //todo condfid format
    Date lowerRange;
    Date upperRange;  //todo date or string - depends how you get the param

    public LogExtractor() {
    }

    public LogExtractor(String lowerStr, String upperStr, String logsPath, List<LogTypes> types) throws ParseException {
        this.lowerStr = lowerStr;
        this.upperStr = upperStr;
        this.logsPath = logsPath;
        this.types = types;
        this.lowerRange = sdf.parse(lowerStr);
        this.upperRange = sdf.parse(upperStr);
    }

    public void scanFolderForLogs(final File folder, boolean skipTrace) throws ParseException {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {

                if (fileEntry.getName().equals("Trace") && skipTrace) {
                    skipTrace = false;
                    continue;
                }
                if (!toExtract(fileEntry.getAbsolutePath())) {
                    continue;
                }
                scanFolderForLogs(fileEntry, skipTrace);
            } else {
                skipTrace = createLogsByDate(fileEntry, fileEntry.getName());
            }
        }
    }


    //Private Methods
    private boolean toExtract(String dirName) {
        for (LogTypes type : types) {
            if (dirName.contains(type.value())) {
                return true;
            }
        }
        return false;
    }

    private boolean createLogsByDate(File logFile, String typeStr) {
        boolean reti = false;
        try {
            Ret ret = ExtractLogsByDates(logFile, lowerStr, upperStr);
            List<String> relevantLines = ret.getLines();
            if (!relevantLines.isEmpty()) {
                String temp = writeLogToFile(relevantLines, typeStr);
                reti = true;
                System.out.println(temp);
            }
            if (!reti && ret.getFirstDate().before(sdf.parse(lowerStr))) {
                reti = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reti;
    }

    private Ret ExtractLogsByDates(File logFile, String lowerStr, String upperStr) throws FileNotFoundException, ParseException, FileNotFoundException, ParseException {
        String logStr;
        Ret ret = new Ret();
        Scanner logScanner = new Scanner(logFile, "ISO-8859-1");

        while (logScanner.hasNext()) {
            logStr = logScanner.nextLine().trim();
            if (!logStr.isEmpty()) {
                String[] tokens = logStr.split(",");
                String dateStr = tokens[0];
                Date date;
                try {
                    date = sdf.parse(dateStr);
                } catch (ParseException pe) {
                    continue;
                }
                if (ret.getFirstDate() == null) {
                    ret.setFirstDate(date);
                }
                if (date.after(upperRange)) {
                    break;
                }
                if ((date.equals(lowerRange) || date.after(lowerRange)) && (date.equals(upperRange) || date.before(upperRange))) {
                    ret.getLines().add(logStr);
                    while (!logScanner.hasNext("\\d{4}-\\d{2}-\\d{2}")) {
                        logStr = logScanner.nextLine();
                        ret.getLines().add(logStr);
                    }
                }
            }
        }
        return ret;
    }

    private String writeLogToFile(List<String> lines, String fileName) throws IOException, IOException {
        File temp = File.createTempFile(fileName, ".log");
        Path file = Paths.get(temp.getAbsolutePath());//todo yoter Yail
        Files.write(file, lines, Charset.forName("UTF-8"));

        return temp.getAbsolutePath();
    }


    //Setter & Getters
    public String getLowerStr() {
        return lowerStr;
    }

    public void setLowerStr(String lowerStr) {
        this.lowerStr = lowerStr;
    }

    public String getUpperStr() {
        return upperStr;
    }

    public void setUpperStr(String upperStr) {
        this.upperStr = upperStr;
    }

    public String getLogsPath() {
        return logsPath;
    }

    public void setLogsPath(String logsPath) {
        this.logsPath = logsPath;
    }

    public List<LogTypes> getTypes() {
        return types;
    }

    public void setTypes(List<LogTypes> types) {
        this.types = types;
    }
}
