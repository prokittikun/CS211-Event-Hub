package cs211.project.services;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataFileManager {
    private final String directoryName;
    private final String fileName;

    private ArrayList<HashMap<String, String>> data;
    private  ArrayList<String> headers;
    public DataFileManager(String directoryName, String fileName){
        this.directoryName = directoryName;
        this.fileName = fileName;
        findHeader();
    }
    private void findHeader(){
        String filePath = directoryName + File.separator + fileName;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();
            if (firstLine != null) {
                String[] keys = firstLine.split(",");
                this.headers = new ArrayList<>();
                this.headers.addAll(Arrays.asList(keys));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData(ArrayList<HashMap<String, String>> data){
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        // เตรียม object ที่ใช้ในการเขียนไฟล์
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );
        BufferedWriter buffer = new BufferedWriter(outputStreamWriter);

        try {
            for(HashMap<String, String> datum: data){
                ArrayList<String> values = new ArrayList<>();
                for(String header : headers){
                    String value = datum.get(header);
                    if(value != null){
                        value = value.replace(",", "[COMMA]");
                        value = value.replace("\n", "[NEWLINE]");
                        values.add(value);

                    }else{
                        values.add("");
                    }
                }
                buffer.write(String.join(",", values));
                buffer.newLine();
            }
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void readData(){
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        // เตรียม object ที่ใช้ในการอ่านไฟล์
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader buffer = new BufferedReader(inputStreamReader);

        String line = "";

        try {
            this.data = new ArrayList<HashMap<String,String>>();
            buffer.readLine();
            while ( (line = buffer.readLine()) != null ){
                // ถ้าเป็นบรรทัดว่าง ให้ข้าม
                if (line.equals("")) continue;

                // แยกสตริงด้วย ,
                String[] data = line.split(",");
                HashMap<String, String> temp = new HashMap<>();
                int index = 0;
                for(String datum: data){
                    datum = datum.replace("[COMMA]", ",");
                    datum = datum.replace("[NEWLINE]", "\n");
                    temp.put(this.headers.get(index++), datum);
                }
                this.data.add(temp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<HashMap<String, String>> findById(String id) {
        readData();

        ArrayList<HashMap<String, String>> foundItems = new ArrayList<>();

        for (HashMap<String, String> item : data) {
            if (item.get(headers.get(0)).equals(id)) {
                foundItems.add(item);
            }
        }

        return foundItems;
    }

    public ArrayList<HashMap<String, String>> findAllByColumnsAndValue(Map<String, String> conditions) {
        readData();

        ArrayList<HashMap<String, String>> foundItems = new ArrayList<>();

        for (HashMap<String, String> item : data) {
            boolean allConditionsMatch = true;

            for (Map.Entry<String, String> entry : conditions.entrySet()) {
                String column = entry.getKey();
                String value = entry.getValue();

                if (!item.containsKey(column) || !item.get(column).equals(value)) {
                    allConditionsMatch = false;
                    break;
                }
            }

            if (allConditionsMatch) {
                foundItems.add(item);
            }
        }

        return foundItems;
    }

    public ArrayList<HashMap<String, String>> getData(){
        readData();
        return this.data;
    }

    public void updateColumnById(String id, String targetColumn, String newValue) {
        readData();

        for (HashMap<String, String> item : data) {
            if (item.get(headers.get(0)).equals(id)) {
                item.put(targetColumn, newValue);
                break;
            }
        }

        saveData();
    }

    public void updateColumnsById(String id, Map<String, String> newValues) {
        readData();

        for (HashMap<String, String> item : data) {
            if (item.get(headers.get(0)).equals(id)) {
                item.putAll(newValues);
                break;
            }
        }

        saveData();
    }
    public void deleteById(String id) {
        readData();
        data.removeIf(item -> item.get(headers.get(0)).equals(id));
        saveData();
    }

    public void deleteByColumnAndValue(String keyColumn, String value) {
        readData();
        data.removeIf(item -> item.get(keyColumn).equals(value));
        saveData();
    }

    private boolean matchesConditions(HashMap<String, String> item, Map<String, String> conditions) {
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            String column = entry.getKey();
            String value = entry.getValue();

            if (!item.containsKey(column) || !item.get(column).equals(value)) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<HashMap<String, String>> query(String queryString) {
        readData();

        // Parse the query string into conditions
        Predicate<HashMap<String, String>> condition = parseQuery(queryString);

        // Apply the condition to filter the data
        ArrayList<HashMap<String, String>> result = data.stream()
                .filter(condition)
                .collect(Collectors.toCollection(ArrayList::new));

        return result;
    }

    public ArrayList<HashMap<String, String>> queryWithOffsetAndLimit(String queryString, int offset, int limit) {
        readData();

        // Parse the query string into conditions
        Predicate<HashMap<String, String>> condition = parseQuery(queryString);

        // Apply the condition to filter the data with offset and limit
        ArrayList<HashMap<String, String>> result = data.stream()
                .filter(condition)
                .skip(offset) // Skip the first 'offset' rows
                .limit(limit) // Limit the result to 'limit' rows
                .collect(Collectors.toCollection(ArrayList::new));

        return result;
    }


    private Predicate<HashMap<String, String>> parseQuery(String queryString) {
        // Implement a parser to handle more complex queries.
        // For simplicity, we'll handle a basic query with OR and AND operators.
        String[] subQueries = queryString.split("\\s+AND\\s+");

        Predicate<HashMap<String, String>> finalCondition = item -> true;

        for (String subQuery : subQueries) {
            boolean orFlag = false;
            if (subQuery.contains(" OR ")) {
                orFlag = true;
                subQuery = subQuery.replace(" OR ", " ");
            }

            boolean notFlag = false;
            if (subQuery.contains("NOT ")) {
                notFlag = true;
                subQuery = subQuery.replace("NOT ", "");
            }


            String[] tokens = subQuery.split("\\s+");

            String columnName = tokens[0];
            String operator = tokens[1];
            String value = tokens[2];

            Predicate<HashMap<String, String>> subCondition = item -> {
                String itemValue = item.get(columnName);

                switch (operator) {
                    case "=":
                        return itemValue.equals(value);
                    case ">":
                        return Integer.parseInt(itemValue) > Integer.parseInt(value);
                    case "<":
                        return Integer.parseInt(itemValue) < Integer.parseInt(value);
                    // Add more operators as needed.
                    default:
                        return false;
                }
            };

            if (notFlag) {
                subCondition = subCondition.negate();
            }

            if (orFlag) {
                finalCondition = finalCondition.or(subCondition);
            } else {
                finalCondition = finalCondition.and(subCondition);
            }
        }

        return finalCondition;
    }

    public void deleteByConditions(Map<String, String> conditions) {
        readData();

        data.removeIf(item -> matchesConditions(item, conditions));

        saveData();
    }

    private void saveData() {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        // Prepare objects for writing to the file
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file, false); //false เพื่อเป็นการเขียนทับ
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );
        BufferedWriter buffer = new BufferedWriter(outputStreamWriter);

        try {
            buffer.write(String.join(",", headers));
            buffer.newLine();

            for (HashMap<String, String> datum : data) {
                ArrayList<String> values = new ArrayList<>();
                for (String header : headers) {
                    String value = datum.get(header);
                    if(value != null){
                        value = value.replace(",", "[COMMA]");
                        value = value.replace("\n", "[NEWLINE]");
                        values.add(value);

                    }else{
                        values.add("");
                    }
                }
                buffer.write(String.join(",", values));
                buffer.newLine();
            }

            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
