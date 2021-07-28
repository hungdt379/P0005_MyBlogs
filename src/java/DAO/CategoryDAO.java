package DAO;

import Context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;

public class CategoryDAO extends DBContext{

    public List<Category> getAllCategory() {
        List<Category> ctgList = new ArrayList<>();
        String sql = "select * from Category";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("Category");
                Category ctr = new Category(category);
                ctgList.add(ctr);
            }
        } catch (SQLException ex) { 
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ctgList;
    }
}
