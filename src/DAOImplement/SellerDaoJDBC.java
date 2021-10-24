package DAOImplement;

import DAO.SellerDAO;
import Entities.Department;
import Entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDAO {

    private Connection conn;

    public SellerDaoJDBC (Connection conn){
        this.conn = conn;
    }
    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName \n" +
                            "FROM seller INNER JOIN department \n" +
                            "ON seller.DepartmentId = department.Id \n" +
                            "WHERE seller.Id = ?");

            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){
                Department dap = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs,dap);
                return obj;
            }
            return null;

        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    private Seller instantiateSeller(ResultSet rs, Department dap) throws SQLException{
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.getBaseSalary(rs.getDouble("BaseSalary"));
        obj.setDepartment(dap);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dap = new Department();
        dap.setId(rs.getInt("DepartmentId"));
        dap.setName(rs.getString("DepName"));
        return dap;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
