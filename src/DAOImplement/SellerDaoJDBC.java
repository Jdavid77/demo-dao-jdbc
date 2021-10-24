package DAOImplement;

import DAO.SellerDAO;
import DB.DBConnection;
import Entities.Department;
import Entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        finally {
            DBConnection.CloseStatement(st);
            DBConnection.CloseResult(rs);
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

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentId = ? " +
                    "ORDER BY Name");

            st.setInt(1,department.getId());
            rs = st.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer,Department> map = new HashMap<>();


            while(rs.next()){

                Department dap = map.get(rs.getInt("DepartmentId"));

                if(dap == null){
                    dap = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dap);
                }

                Seller obj = instantiateSeller(rs,dap);
                sellers.add(obj);
            }
            return sellers;

        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        finally {
            DBConnection.CloseStatement(st);
            DBConnection.CloseResult(rs);
        }
    }
}
