package DAOImplement;

import DAO.SellerDAO;
import DB.DBConnection;
import Entities.Department;
import Entities.Seller;

import java.sql.*;
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

        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                var rs = st.getGeneratedKeys();
                if(rs.next()){
                    int ID = rs.getInt(1);
                    obj.setId(ID);
                }
                DBConnection.CloseResult(rs);
            }
            else{
                throw new RuntimeException("Erro inesperado, nenhuma linha foi afetada");
            }

        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        finally {
            DBConnection.CloseStatement(st);

        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("UPDATE seller " +
                    "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                    "WHERE Id = ? ", Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());
            st.setInt(6,obj.getId());

            st.executeUpdate();

        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        finally {
            DBConnection.CloseStatement(st);

        }

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
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
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
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                   "ON seller.DepartmentId = department.Id " +
                    "ORDER BY Name");


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
