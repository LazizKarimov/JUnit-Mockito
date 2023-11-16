package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {

            String selectQuery = "SELECT *" +
                    "        FROM product" +
                    "        ORDER BY id";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        long productId = resultSet.getLong(1);
                        String productName = resultSet.getString(2);
                        int productPrice = resultSet.getInt(3);

                        productList.add(new Product(productId, productName, productPrice));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Product product = null;

        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "select * from product where id = " + id;

            try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        long productId = resultSet.getLong(1);
                        String productName = resultSet.getString(2);
                        int productPrice = resultSet.getInt(3);

                        product = new Product(productId, productName, productPrice);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) {
        try (Connection connection = dataSource.getConnection()) {
            String updateQuery = "UPDATE product SET id = " + product.getId() +
                    ", name = '" + product.getName() +
                    "', price = " + product.getPrice() +
                    " WHERE id = " + product.getId();

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void save(Product product) {
//        try (Connection connection = dataSource.getConnection()) {
//            String insertQueryIntoUsers = "INSERT INTO product ( name, price) " +
//                    "VALUES ('"  + product.getName() + "' , " +
//                    product.getPrice() + ") RETURNING id;";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(insertQueryIntoUsers);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                long id = resultSet.getLong("id");
//
//                product.setId(id);
//            }
//
//            preparedStatement.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try (Connection connection = dataSource.getConnection()) {
            String insertQueryIntoUsers = "INSERT INTO product ( name, price) " +
                    "VALUES ('"  + product.getName() + "' , " +
                    product.getPrice() + ");";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQueryIntoUsers, Statement.RETURN_GENERATED_KEYS);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    product.setId(id);
                }
            }

            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String deleteQuery = "delete from product where id = "
                    + id;

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);

            preparedStatement.execute();

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
