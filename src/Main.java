import DAO.DAOFactory;
import DAO.SellerDAO;
import Entities.Department;
import Entities.Seller;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SellerDAO sellerDAO = DAOFactory.createSellerDAO();

        System.out.println("--- TEST 1 : Seller findById ---");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);

        System.out.println("--- TEST 2 : Seller findByDepartment ---");
        Department department = new Department(2,null);
        List<Seller> list = sellerDAO.findByDepartment(department);
        for (Seller obj: list
             ) {
            System.out.println(obj);

        }
        System.out.println(seller);
    }
}
