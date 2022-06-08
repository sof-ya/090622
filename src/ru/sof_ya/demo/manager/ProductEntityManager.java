package ru.sof_ya.demo.manager;

import ru.sof_ya.demo.App;
import ru.sof_ya.demo.entity.ProductEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductEntityManager {
    public static void insert(ProductEntity product) throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "INSERT INTO Product (Title, ProductType, ArticleNumber, Description, Image, ProductionPersonCount, ProductionWorkshopNumber, MinCostForAgent) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getType());
            ps.setString(3, product.getArticleNum());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getImagePath());
            ps.setInt(6, product.getPrsonCount());
            ps.setInt(7, product.getWorkshopNum());
            ps.setDouble(8, product.getMinCost());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                product.setId(keys.getInt(1));
                return;
            }
            throw new SQLException("Entity not added");
        }
    }

    public static ProductEntity selectById(int id) throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "SELECT * FROM Product WHERE ID ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return new ProductEntity(resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("ProductType"),
                        resultSet.getString("ArticleNumber"),
                        resultSet.getString("Description"),
                        resultSet.getString("Image"),
                        resultSet.getInt("ProductionPersonCount"),
                        resultSet.getInt("ProductionWorkshopNumber"),
                        resultSet.getDouble("MinCostForAgent"));
            }
            return null;
        }
    }

    public static List<ProductEntity> selectAll() throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "SELECT * FROM Product";
            Statement s = c.createStatement();

            ResultSet resultSet = s.executeQuery(sql);

            List<ProductEntity> list = new ArrayList<>();

            while (resultSet.next()) {
                list.add(new ProductEntity(resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("ProductType"),
                        resultSet.getString("ArticleNumber"),
                        resultSet.getString("Description"),
                        resultSet.getString("Image"),
                        resultSet.getInt("ProductionPersonCount"),
                        resultSet.getInt("ProductionWorkshopNumber"),
                        resultSet.getDouble("MinCostForAgent")));
            }
            return list;
        }
    }

    public static void update(ProductEntity product) throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "UPDATE Product SET Title=?, ProductType=?, ArticleNumber=?, Description=?, Image=?, ProductionPersonCount=?, ProductionWorkshopNumber=?, MinCostForAgent=? WHERE ID=?";

            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getType());
            ps.setString(3, product.getArticleNum());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getImagePath());
            ps.setInt(6, product.getPrsonCount());
            ps.setInt(7, product.getWorkshopNum());
            ps.setDouble(8, product.getMinCost());
            ps.setInt(9, product.getId());

            ps.executeUpdate();
        }
    }

    public static void delete (int id) throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "DELETE FROM Product WHERE ID=?";

            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }
    public static void delete(ProductEntity product) throws SQLException
    {
        delete(product.getId());
    }

}
