package DB;

import java.sql.*;

public class DBConnection {

    public static Connection Connection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coursejdbc", "root", "admin");
        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

        return conn;
    }

    public static void CloseConnection(Connection conn) {
        if(conn != null){
            try {
                conn.close();
            }
            catch (SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static void CloseStatement(Statement st){
        if(st != null){
            try{
                st.close();
            }
            catch (SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static void CloseResult(ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }
            catch (SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
