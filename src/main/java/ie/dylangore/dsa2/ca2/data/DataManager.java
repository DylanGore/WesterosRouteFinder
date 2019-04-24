package ie.dylangore.dsa2.ca2.data;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import ie.dylangore.dsa2.ca2.types.Marker;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    public static void saveAll(){
        saveListToCSV("regions.csv", ListManager.getRegionList());
        saveListToCSV("affiliations.csv", ListManager.getAffiliationList());
        saveListToCSV("markers.csv", ListManager.getMarkerList());
    }

    public static void loadAll(){
        loadListFromCSV("regions.csv", ListManager.getRegionList());
        loadListFromCSV("affiliations.csv", ListManager.getAffiliationList());
        loadListFromCSV("markers.csv", ListManager.getMarkerList() );
    }

    private static void saveListToCSV(String filePath, ObservableList list) {
        File file = new File(filePath);

        try {
            FileWriter output = new FileWriter(file);
            CSVWriter writer = new CSVWriter(output);
            List<String[]> data = new ArrayList<String[]>();

            // Headers
            if(list == ListManager.getRegionList()) {
                data.add(new String[]{"NAME"});

                for(int i = 0; i < list.size(); i++) {
                    data.add(new String[]{ListManager.getRegionList().get(i)});
                }
            }else if(list == ListManager.getAffiliationList()) {
                data.add(new String[]{"NAME"});

                for (Object o : list) {
                    data.add(new String[]{o.toString()});
                }
            }else if(list == ListManager.getMarkerList()) {
                data.add(new String[]{"NAME", "X", "Y", "REGION", "AFFILIATION", "TEMPERATURE", "TERRAIN"});

                for(int i = 0; i < list.size(); i++) {
                    Marker currMarker = (Marker) list.get(i);
                    data.add(new String[]{currMarker.getName(), String.valueOf(currMarker.getXCoordinate()), String.valueOf(currMarker.getYCoordinate()), currMarker.getRegion(), currMarker.getAffiliation(), String.valueOf(currMarker.getTemperature()), String.valueOf(currMarker.getTerrain())});
                }
            }else {
                throw new IOException("Unsupported List!");
            }

            writer.writeAll(data);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadListFromCSV(String fileName, ObservableList list){
        try {
            FileReader reader = new FileReader(fileName);
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            String[] nextRecord;

            if(list == ListManager.getRegionList()){
                ListManager.getRegionList().clear();
                while ((nextRecord = csvReader.readNext()) != null) {
                    for (String cell : nextRecord) {
                        ListManager.getRegionList().add(cell);
                        System.out.println(cell);
                    }
                }
            }else if(list == ListManager.getAffiliationList()){
                ListManager.getAffiliationList().clear();
                while ((nextRecord = csvReader.readNext()) != null) {
                    for (String cell : nextRecord) {
                        ListManager.getAffiliationList().add(cell);
                        System.out.println(cell);
                    }
                }
            }else if(list == ListManager.getMarkerList()){
                ListManager.getMarkerList().clear();
                while ((nextRecord = csvReader.readNext()) != null) {
                    int cellCount = 1;

                    String name = "", affiliation = "", region = "";
                    int x = 0, y = 0, temperature = 0, terrain = 0;

                    for (String cell : nextRecord) {
                        switch(cellCount){
                            case 1:
                                name = cell;
                                break;
                            case 2:
                                x = Integer.valueOf(cell);
                                break;
                            case 3:
                                y = Integer.valueOf(cell);
                                break;
                            case 4:
                                region = cell;
                                break;
                            case 5:
                                affiliation = cell;
                                break;
                            case 6:
                                temperature = Integer.valueOf(cell);
                                break;
                            case 7:
                                terrain = Integer.valueOf(cell);
                                break;
                        }
                        if(cellCount == 7){
                            GuiManager.addMarkerButton(name, x,y,affiliation,region, temperature, terrain);
                        }
                        cellCount++;
                    }
                }
            }else{
                throw new IOException("Unsupported List!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
