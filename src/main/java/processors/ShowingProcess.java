package processors;

import enums.Criteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import repositories.BookRepository;

/**
 * @author Bekpulatov Shoxruh, Thu 4:31 PM. 12/23/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShowingProcess {
    private static final ShowingProcess instance = new ShowingProcess();
    private static final BookRepository bookRepository = BookRepository.getInstance();
    private static final SearchingProcess searchingProcess = SearchingProcess.getInstance();

    public void religious(String chatId) {
        bookRepository.setOFFSET(0);
        bookRepository.SessionCriteriaChange(chatId, null);
        searchingProcess.searching(chatId, Criteria.RELIGIOUS);
        bookRepository.showingCriteriaChange(chatId, Criteria.RELIGIOUS.name().toLowerCase());
    }

    public void art(String chatId) {
        bookRepository.setOFFSET(0);
        bookRepository.SessionCriteriaChange(chatId, null);
        searchingProcess.searching(chatId, Criteria.ART);
        bookRepository.showingCriteriaChange(chatId, Criteria.ART.name().toLowerCase());

    }

    public void textbooks(String chatId) {
        bookRepository.setOFFSET(0);
        bookRepository.SessionCriteriaChange(chatId, null);
        searchingProcess.searching(chatId, Criteria.TEXTBOOKS);
        bookRepository.showingCriteriaChange(chatId, Criteria.TEXTBOOKS.name().toLowerCase());

    }

    public void programming(String chatId) {
        bookRepository.setOFFSET(0);
        bookRepository.SessionCriteriaChange(chatId, null);
        searchingProcess.searching(chatId, Criteria.PROGRAMMING);
        bookRepository.showingCriteriaChange(chatId, Criteria.PROGRAMMING.name().toLowerCase());
    }

    public void myBook(String chatId) {
        bookRepository.setOFFSET(0);
        bookRepository.SessionCriteriaChange(chatId, null);
        searchingProcess.searchMyBooks(chatId);
    }

    public static ShowingProcess getInstance() {
        return instance;
    }

}
