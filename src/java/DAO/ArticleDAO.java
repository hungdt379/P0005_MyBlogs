package DAO;

import Context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Article;

public class ArticleDAO extends DBContext{

    public List<Article> getTop3Artcile() {
        List<Article> atcList = new ArrayList();
        String sql = "select top 3 * from Article order by Date DESC";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat SDF = new SimpleDateFormat("MMMM dd, YYYY");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("Title");
                String content = rs.getString("LongContent");
                String image = rs.getString("Image");
                String category = rs.getString("Category");
                String date = SDF.format(rs.getDate("Date"));
                Article artc = new Article(id, title, content, image, category, date);
                atcList.add(artc);
            }
        } catch (SQLException ex) { 
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atcList;
    }

    public Article getArticleByID(int id) {
        Article atc = null;
        String sql = "select * from Article where id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat SDF = new SimpleDateFormat("MMMM dd, YYYY");
            while (rs.next()) {
                String title = rs.getString("Title");
                String content = rs.getString("LongContent");
                String image = rs.getString("Image");
                String category = rs.getString("Category");
                String date = SDF.format(rs.getDate("Date"));
                atc = new Article(id, title, content, image, category, date);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return atc;
    }

    public List<Article> getArticleByCategory(String category, int articlePage, int sizePage) {
        List<Article> atcList = new ArrayList<>();
        String sql = "select * from "
                    + "(SELECT ROW_NUMBER() over(order by Date desc) as row, * FROM Article "
                    + "where Category like ?) as t "
                    + "where row between ? and ?;";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            int to = sizePage * articlePage;
            int from = to - sizePage + 1;
            ps.setString(1, category);
            ps.setInt(2, from);
            ps.setInt(3, to);
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat SDF = new SimpleDateFormat("MMMM dd, YYYY");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("Title");
                String content = rs.getString("LongContent");
                String image = rs.getString("Image");
                String date = SDF.format(rs.getDate("Date"));
                Article artc = new Article(id, title, content, image, category, date);
                atcList.add(artc);
            }
        } catch (SQLException ex) { 
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atcList;
    }

    public int getTotalNumbersArticle(String category) {
        int total = 0;
        String sql = "select COUNT(*) as total from Article where Category like ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException ex) { 
            Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
}
