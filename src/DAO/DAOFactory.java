package DAO;

public class DAOFactory {

    public static SellerDAO createSellerDAO() {
        return new SellerDaoJDBC();
    }
}
