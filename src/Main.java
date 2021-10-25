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

        System.out.println("--- TEST 3 : Seller findALL ---");
        List<Seller> listtwo = sellerDAO.findAll();
        for (Seller obj: listtwo
        ) {
            System.out.println(obj);

        }

        System.out.println("--- TEST 4 : Seller Insert ---");
        Seller seller2 = new Seller(null,"Greg","greg@gmail.com",new Date(),4000.0,department);
        sellerDAO.insert(seller2);
        System.out.println("Inserted! New ID = " + seller2.getId());

        System.out.println("--- TEST 5 : Seller Update ---");
        seller = sellerDAO.findById(1);
        seller.setName("Marta Wayne");
        sellerDAO.update(seller);
        System.out.println("Update Completed!!");

    }
}
