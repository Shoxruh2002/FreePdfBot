package repositories;

import configs.Propertyconfigs;
import dtos.BookDto;
import entities.Book;
import enums.Criteria;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bekpulatov Shoxruh, Sat 2:49 AM. 12/25/2021
 */
@Getter
@Setter
public class BookRepository extends AbstractRepository {

    private static final BookRepository instance = new BookRepository();
    private Integer OFFSET = 0;

    public static BookRepository getInstance() {
        return instance;
    }

    public void create(String sessionCriteria, Message message) {
        Document document = message.getDocument();
        Book book = new Book();
        query = new StringBuilder("insert into Book (id,createdAt,title,criteria,ownerChatId,file,downloadCount)" +
                " values (?,?,?,?,?,?,?); ");
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())
        ) {
            preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(2, book.getCreatedAt());
            preparedStatement.setString(3, document.getFileName());
            preparedStatement.setString(4, sessionCriteria);
            preparedStatement.setString(5, message.getChatId().toString());
            preparedStatement.setString(6, document.getFileId());
            preparedStatement.setInt(6, 0);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BookDto> search(String text) {
        List<BookDto> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            query = new StringBuilder("select id,title,downloadCount from Book where title like '%");
            query.append(text);
            query.append("%' limit 5 offset ");
            query.append(OFFSET);
            query.append(" ; ");
            ResultSet resultSet = preparedStatement.executeQuery(query.toString());
            while (resultSet.next()) {
                BookDto book = BookDto.builder()
                        .Id(resultSet.getString("id"))
                        .title(resultSet.getString("title"))
                        .downloadCount(resultSet.getInt("downloadCount"))
                        .build();
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<BookDto> searchMyBooks(String chatId) {
        List<BookDto> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            query = new StringBuilder("select id,title,downloadCount from Book where ");
            query.append(" ownerChatId = '");
            query.append(chatId);
            query.append("' limit 5 offset ");
            query.append(OFFSET);
            query.append(" ; ");
            ResultSet resultSet = preparedStatement.executeQuery(query.toString());
            while (resultSet.next()) {
                BookDto book = BookDto.builder()
                        .Id(resultSet.getString("id"))
                        .title(resultSet.getString("title"))
                        .downloadCount(resultSet.getInt("downloadCount"))
                        .build();
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<BookDto> search(Criteria criteria) {
        List<BookDto> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            query = new StringBuilder("select id,title,downloadCount from Book where ");
            query.append(" criteria = '");
            query.append(criteria.toString().toLowerCase());
            query.append("' limit 5 offset ");
            query.append(OFFSET);
            query.append(" ; ");
            ResultSet resultSet = preparedStatement.executeQuery(query.toString());
            while (resultSet.next()) {
                BookDto book = BookDto.builder()
                        .Id(resultSet.getString("id"))
                        .title(resultSet.getString("title"))
                        .downloadCount(resultSet.getInt("downloadCount"))
                        .build();
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public String findBookById(String data) {
        query = new StringBuilder("select file from Book where id = ");
        query.append(data);
        query.append(" ;");
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            return (preparedStatement.executeQuery(query.toString()) == null) ? null : preparedStatement.executeQuery(query.toString()).getString("file");
        } catch (SQLException e) {
            return null;
        }
    }

    public String SessionCriteria(String chatId) {

        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            query = new StringBuilder("select sessionCriteria from User where chatid =  '");
            query.append(chatId);
            query.append("' ; ");
            ResultSet resultSet = preparedStatement.executeQuery(query.toString());
            String reply;
            reply = (resultSet.getString("sessionCriteria").equals("null")) ? null :
                    resultSet.getString("sessionCriteria");
            return reply;
        } catch (SQLException e) {
            return null;
        }
    }

    public void SessionCriteriaChange(String chatId, String data) {
        query = new StringBuilder("update User set sessionCriteria = '" + data + "' where chatId = '");
        query.append(chatId);
        query.append("' ; ");
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            preparedStatement.execute(query.toString());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String searchingTitle(String chatId) {

        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            query = new StringBuilder("select searchingTitle from User where chatid =  '");
            query.append(chatId);
            query.append("' ; ");
            ResultSet resultSet = preparedStatement.executeQuery(query.toString());
            String reply;
            reply = (resultSet.getString("searchingTitle").equals("null")) ? null :
                    resultSet.getString("searchingTitle");
            return reply;
        } catch (SQLException e) {
            return null;
        }
    }

    public String showingCriteria(String chatId) {

        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            query = new StringBuilder("select showingCriteria from User where chatid =  '");
            query.append(chatId);
            query.append("' ; ");
            ResultSet resultSet = preparedStatement.executeQuery(query.toString());
            String reply;
            reply = (resultSet.getString("showingCriteria").equals("null")) ? null :
                    resultSet.getString("showingCriteria");
            return reply;
        } catch (SQLException e) {
            return null;
        }
    }

    public void showingCriteriaChange(String chatId, String data) {
        query = new StringBuilder("update User set showingCriteria = '" + data + "' where chatId = '");
        query.append(chatId);
        query.append("' ; ");
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            preparedStatement.execute(query.toString());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void searchingTitleChange(String chatId, String title) {
        query = new StringBuilder("update User set searchingTitle = '" + title + "' where chatId = '");
        query.append(chatId);
        query.append("' ; ");
        try (Connection connection = DriverManager.getConnection(Propertyconfigs.get("jdbc.driver"));
             Statement preparedStatement = connection.createStatement()) {
            preparedStatement.execute(query.toString());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
