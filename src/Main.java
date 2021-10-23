import DAO.DAOFactory;
import DAO.SellerDAO;
import Entities.Department;
import Entities.Seller;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        SellerDAO sellerDAO = DAOFactory.createSellerDAO();
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);
    }
}
