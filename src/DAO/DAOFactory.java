package DAO;

import DAOImplement.SellerDaoJDBC;
import DB.DBConnection;

public class DAOFactory {

    public static SellerDAO createSellerDAO() {
        return new SellerDaoJDBC(DBConnection.Connection());
    }
}
