
package library.assistant.database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DatabaseHandler {
    private static DatabaseHandler handler=null;

    private static final String DB_URL="jdbc:derby:database/library2;create=true";
    private static Connection conn= null;
    private static Statement stmt =null;
    private DatabaseHandler(){
        createConnection();
        setBookTable();
        setMemberTable();
        
    }
    public static DatabaseHandler getInstance(){
    if(handler==null){
        handler=new DatabaseHandler();
    }
    return handler;
    }
void createConnection(){
    try{
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        conn=DriverManager.getConnection(DB_URL);

    }catch(Exception e){
        e.printStackTrace();
    }
    }
    void setBookTable(){
        String TABLE_NAME ="BOOK";
        try{
            stmt= conn.createStatement();
            DatabaseMetaData dbm =conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            if (tables.next()) {
                System.out.println("Table "+TABLE_NAME+"already exist.Ready for go!");
            }else{
                stmt.execute("CREATE TABLE "+TABLE_NAME+"("
                    +"      id varchar(200) primary key,\n"
                    +"      title varchar(200),\n "
                    +"      author varchar(200),\n"
                    +"      publisher varchar(200),\n"
                    +"      isAvail boolean default true"
                    +"   )"

                    );
            }
        }catch (SQLException e) {
            System.err.println(e.getMessage()+"........setupDatabase");

        }finally{

        }
    }
    public ResultSet executeQuery(String query){
        ResultSet result;
        try{
            stmt=conn.createStatement();
            result=stmt.executeQuery(query);

        }catch (SQLException ex) {
            System.out.println("Exception at execQuery"+ex.getLocalizedMessage());
            return null;
        }finally{

        }return result;
    }
    public boolean execAction(String qu){
        try{
            stmt=conn.createStatement();
            stmt.execute(qu);
            return true;
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error:"+ex.getMessage(),"Error occured",JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:databaseHandler"+ex.getLocalizedMessage());
            return false;

        }finally{
            
        }
    }

    private void setMemberTable() {
        String TABLE_NAME ="MEMBER";
        try{
            stmt= conn.createStatement();
            DatabaseMetaData dbm =conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            if (tables.next()) {
                System.out.println("Table "+TABLE_NAME+"already exist.Ready for go!");
            }else{
                stmt.execute("CREATE TABLE "+TABLE_NAME+"("
                    +"      id varchar(200) primary key,\n"
                    +"      name varchar(200),\n "
                    +"      mobile varchar(20),\n"
                    +"      email varchar(200)\n"
                    +"   )"

                    );
            }
        }catch (SQLException e) {
            System.err.println(e.getMessage()+"........setupDatabase");

        }finally{

        }
    }



}
