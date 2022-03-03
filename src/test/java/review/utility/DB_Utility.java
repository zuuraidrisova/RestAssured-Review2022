package review.utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB_Utility {


    /*
     * a static method to create connection between java and database
     * with valid url and username password
     * */

    private static Connection connection;

    private static Statement statement;

    private static ResultSet resultSet;

    private static ResultSetMetaData resultSetMetaData;


    public static void createConnection(){


        String connectionURL = ConfigurationReader.getProperty("hr.database.url");

        String username = ConfigurationReader.getProperty("hr.database.username");

        String password = ConfigurationReader.getProperty("hr.database.password");


        try {

            connection = DriverManager.getConnection(connectionURL, username, password);

            System.out.println("Connection is successful");


        }catch (SQLException e){

            System.out.println("Connection has failed");

            e.printStackTrace();

        }

    }


    /*

    overload createConnection method by passing three parameters: connectionString, username, password
    so we can provide those info for different database

* @param url ==> connectionString
 * @param username
 * @param password
     */


    public static void createConnection(String url, String username, String password){

        try{

            connection = DriverManager.getConnection(url, username, password);

            System.out.println("Connection is successful");


        }catch (SQLException e){

            e.printStackTrace();

            System.out.println("Connection has failed");

        }

    }


    public static void createConnection(String env){

        String connectionURL = ConfigurationReader.getProperty(env+".database.url");

        String username = ConfigurationReader.getProperty(env+".database.username");

        String password = ConfigurationReader.getProperty(env+".database.password");


        try {

            connection = DriverManager.getConnection(connectionURL, username, password);

            System.out.println("Connection is successful");


        }catch (SQLException e){

            System.out.println("Connection has failed");

            e.printStackTrace();

        }

    }

    /*
     * a static method to get the ResultSet object
     * with valid connection by executing query
     *
     * */

    public static ResultSet runQuery(String query){

        try {

            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            resultSet = statement.executeQuery(query);

        }catch (SQLException e){

            System.out.println("Invalid query");

            e.printStackTrace();

        }

        return resultSet;

    }


    /*
     * a method to get the column count of the current ResultSet
     *
     *   getColumnCNT()
     *
     * */

    public static int getColumnCount(){

        int columnCount = 0;

        try {

            resultSetMetaData = resultSet.getMetaData();

            columnCount = resultSetMetaData.getColumnCount();

        }catch (SQLException e){

            e.printStackTrace();
        }

        return columnCount;
    }


        /*
     * a method to get the row count of the current ResultSet
     *
         getting a row count
     */

    public static int getRowCount(){

        int rowCount = 0;

        try {

            resultSet.last();

            rowCount = resultSet.getRow();

            //moving cursor to the initial position again
            resultSet.beforeFirst();

        }catch (SQLException e){

            e.printStackTrace();

        }

        return rowCount;

    }


    /*
     * a method to display all the data in the result set
     *
     * */

    public static void displayAllData(){

        int columnCount = DB_Utility.getColumnCount();
        //we have to learn column count at first

        try {

            resultSet.beforeFirst();//we have to be at beforeFirst place to be
            // able to start from the very beginning

            while (resultSet.next()) {//row iteration

                for (int i = 1; i <= columnCount; i++) {//column iteration

                    System.out.println(resultSet.getString(i)+ " \t");
                    //printing result
                }


                System.out.println();//giving blank line

            }

            resultSet.beforeFirst();

        }catch (SQLException e){


            e.printStackTrace();

        }


    }

    /**
     *
     * @param columnIndex the column you want to get a list out of
     * @return List of String that contains entire column data from 1st row to last row
     */
    public static List<String> getColumnDataAsList(int columnIndex){

        List<String> columnDataLst = new ArrayList<>();

        try {

            resultSet.beforeFirst();  // moving the cursor to before first location

            while(resultSet.next() ){

                String data =  resultSet.getString(columnIndex) ;
                // getting the data from that column and adding to the the list

                columnDataLst.add( data  );

            }

            resultSet.beforeFirst();  // moving the cursor to before first location after we are done

        } catch (SQLException e) {

            System.out.println("ERROR WHILE getColumnDataAsList ");
            e.printStackTrace();
        }

        return columnDataLst;
    }

    /**
     *
     * @param columnName the column you want to get a list out of
     * @return List of String that contains entire column data from the column name specified
     */
    public static List<String> getColumnDataAsList(String columnName){

        List<String> columnDataLst = new ArrayList<>();

        try {

            resultSet.beforeFirst();  // moving the cursor to before first location

            while(resultSet.next() ){

                String data =  resultSet.getString(columnName) ;
                // getting the data from that column and adding to the the list
                columnDataLst.add( data  );

            }

            resultSet.beforeFirst();  // moving the cursor to before first location after we are done

        } catch (SQLException e) {

            System.out.println("ERROR WHILE getColumnDataAsList ");
            e.printStackTrace();
        }

        return columnDataLst;
    }



    /*
    getting entire row data and store it into a list of strings
    @param rowNum the row number you want the list from
    @return list of string that contains the row data
     */

    public static List<String> getEntireRowDataAsList(int rowNum){

        List<String> entireRowDataAsList = new ArrayList<>();

        try{

            resultSet.absolute(rowNum);

            for (int i = 1; i <= getColumnCount(); i++) {

                entireRowDataAsList.add(resultSet.getString(i));

            }

            //moving cursor back to before first location just in case
            resultSet.beforeFirst();

        }catch (SQLException e){

            e.getMessage();

        }

        return  entireRowDataAsList;
    }

    /*
     @param columnIndex the column u want to get the list of
     @return list of string
      */
    public static List<String> getEntireColumnDataAsList(int columnIndex){

        List<String> columnDataAsList = new ArrayList<>();

        try {

            resultSet.beforeFirst();

            while (resultSet.next()) {

                for (int i = 1; i <= getColumnCount(); i++) {

                    columnDataAsList.add(resultSet.getString(columnIndex));

                }

            }

            resultSet.beforeFirst();

        }catch (SQLException e){

            e.printStackTrace();
        }

        return  columnDataAsList;
    }


    /*
     overload above method with string parameter
      */
    public static List<String> getEntireColumnDataAsList(String columnName){

        List<String> columnDataAsList = new ArrayList<>();

        try {

            resultSet.beforeFirst();

            while (resultSet.next()) {

                for (int i = 1; i <= getColumnCount(); i++) {

                    columnDataAsList.add(resultSet.getString(columnName));

                }

            }

            resultSet.beforeFirst();

        }catch (SQLException e){

            e.printStackTrace();
        }

        return  columnDataAsList;
    }


    /*
     we want to store certain row data as a map
     give me number 3 row ===> Map<String, String> ()
      */

    public static Map<String, String> getRowDataAsMap(int rowNum){

        Map<String, String> rowDataAsMap = new HashMap<>();

        try {

            resultSet.absolute(rowNum);

            resultSetMetaData = resultSet.getMetaData();

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {

                String columnName = resultSetMetaData.getColumnName(i);

                String columnValue = resultSet.getString(i);

                rowDataAsMap.put(columnName, columnValue);

            }


        }catch (SQLException e){

            e.printStackTrace();

        }

        return rowDataAsMap;
    }


    //get entire data result as list of maps

    public static List<Map<String, String> > getDataAsListOfMap (){

        List<Map<String, String>> listOfMap = new ArrayList<>();

        for (int i = 1; i < getRowCount(); i++){

            listOfMap.add(getRowDataAsMap(i));

        }

        return  listOfMap;
    }


    /*
         closing all resources
          */

    public static void destroy(){

        try {

            if (resultSet != null) {

                resultSet.close();
            }

            if (statement != null) {

                statement.close();
            }

            if (connection != null) {

                connection.close();
            }

        }catch (SQLException e){

            e.printStackTrace();

        }
    }




}
