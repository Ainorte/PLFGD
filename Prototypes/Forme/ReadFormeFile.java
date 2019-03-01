import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.*;

public class ReadFormeFile {

    private static Pattern pattern;
    private static Matcher matcher;


    static public List<String> readFile(String filename){
    
        List<String> records = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null){
                records.add(line);
            }
            reader.close();
            return records;
        }
        catch (Exception e){
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }


    public static List<Point> convertToPts(String forme) {
        List<String> file = readFile(forme);
        String[] file2 = new String[file.size()];
        file2 = file.toArray(file2);
        List<Double> listNb = new ArrayList<>();
        for(String st : file2){
            pattern = Pattern.compile("[0-9]+.[0-9]+");
            matcher = pattern.matcher(st);
            while(matcher.find()) {
                listNb.add(Double.valueOf(matcher.group()));
            }
        }
        List<Point> listPts = new ArrayList<>();
        for(int i = 0; i < listNb.size()-1; i+=2){
            listPts.add(new Point(listNb.get(i), listNb.get(i+1)));
        }
        return listPts;
    }
    public static void main(String[] args) {
        List<Point> carre1 = convertToPts("./testForme/carre.txt");
        List<Point> carre2 = TraitementPoints.filterPointsByDistance(carre1, 10);
        List<Point> carre3 = TraitementPoints.sanitize(carre1, 0.2);
        List<Segment> segsCarre1 = TraitementPoints.pointsToSegments(carre3, 10);
        List<Segment> segsCarre2 = TraitementPoints.segmentsToSegments(segsCarre1);
        System.out.println(TraitementSegments.trouverSommets(segsCarre2));
        //System.out.println(carre3);
        //System.out.println(segsCarre1);
        //for(Segment seg : segsCarre2){
        //    System.out.println(seg.getP1());
        //    System.out.println(seg.getP2());
        //}
    }
}