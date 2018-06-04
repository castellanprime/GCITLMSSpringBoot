package com.gcit.lmsspringboot.service;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gcit.lmsspringboot.entity.Genre;
import com.gcit.lmsspringboot.dao.AuthorDAO;
import com.gcit.lmsspringboot.dao.BookDAO;
import com.gcit.lmsspringboot.dao.BookLoanDAO;
import com.gcit.lmsspringboot.dao.GenreDAO;
import com.gcit.lmsspringboot.dao.LibraryBranchDAO;
import com.gcit.lmsspringboot.dao.PublisherDAO;
import com.gcit.lmsspringboot.entity.Author;
import com.gcit.lmsspringboot.entity.Book;
import com.gcit.lmsspringboot.entity.BookLoan;
import com.gcit.lmsspringboot.entity.LibraryBranch;
import com.gcit.lmsspringboot.entity.Publisher;

@RestController
@RequestMapping("/lmsspringboot/")
public class AdminService {
	
	@Autowired
	AuthorDAO authorDao;
	
	@Autowired
	BookDAO bookDao;
	
	@Autowired
	LibraryBranchDAO lbdao;
	
	@Autowired
	PublisherDAO publisherDao;
	
	@Autowired
	GenreDAO genreDao;
	
	@Autowired
	BookLoanDAO bldao;
	

	private Author getAuthor(List<Author> authors, int authorId) {
		for (Author author: authors) {
			if (author.getAuthorId() == authorId) {
				return author;
			}
		}
		return null;
	}
	
	private Genre getGenre(List<Genre> genres, int genreId) {
		for (Genre genre: genres) {
			if (genre.getGenre_id() == genreId) {
				return genre;
			}
		}
		return null;
	}
	
	private LibraryBranch getBranchByID(List<LibraryBranch> branches, int branchId) {
		for (LibraryBranch libraryBranch: branches) {
			if (libraryBranch.getBranchId() == branchId) {
				return libraryBranch;
			}
		}
		return null;
	}
	
	private Publisher getPublisherByID(List<Publisher> publishers, int publisherId) {
		for (Publisher publisher: publishers) {
			if (publisher.getPublisherId() == publisherId) {
				return publisher;
			}
		}
		return null;
	}
	
	private Book getBookById(List<Book> books, int bookId) {
		for (Book book: books) {
			if (book.getBookId() == bookId) {
				return book;
			}
		}
		return null;
	}
	
	private BookLoan getBookLoadByDateOut(List<BookLoan> bookLoans, int bookId, int cardNo, LocalDateTime dateOut) {
		for (BookLoan bookLoan: bookLoans) {
			if (bookLoan.getBookId() == bookId && bookLoan.getCardNo() == cardNo && bookLoan.getDateOut().isEqual(dateOut)) {
				return bookLoan;
			}
		}
		return null;
	}
	
	@Transactional
	@RequestMapping(value = "/admin/authors", 
		method = RequestMethod.POST, 
		consumes = "application/json",
		produces = "application/json")
	public ResponseEntity<Author> addAuthor(@RequestParam String name, 
			UriComponentsBuilder ucb) throws SQLException {
		Author author = null;
		int authorId = 0;
		try {
			author = new Author();
			author.setAuthorName(name);
			authorId = authorDao.addAuthorWithID(author);
			List<Author> authors = authorDao.getAllAuthors();
			author = this.getAuthor(authors, authorId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		URI locationUri =
				ucb.path("/admin/authors/")
				.path(String.valueOf(authorId))
				.build()
				.toUri();
		headers.setLocation(locationUri);
		return new ResponseEntity<>(author, headers, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/authors/{authorId}", 
			method = RequestMethod.PATCH, 
			consumes = "application/json",
			produces = "application/json")
	public Author editAuthor(@PathVariable int authorId, 
			@RequestParam String authorName) throws SQLException{
		Author author = null;
		try {
			author = new Author();
			author.setAuthorId(authorId);
			author.setAuthorName(authorName);
			authorDao.editAuthor(author);
			List<Author> authors = authorDao.getAllAuthors();
			author = this.getAuthor(authors, authorId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return author;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/authors/{authorId}", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public Author getAuthor(@PathVariable int authorId) throws SQLException{
		Author author=null;
		try {
			List<Author> authors = authorDao.getAllAuthors();
			author = this.getAuthor(authors, authorId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return author;
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/admin/authors/{authorId}", 
			method = RequestMethod.DELETE)
	public void deleteAuthor(@PathVariable int authorId) throws SQLException {
		try {
			Author author = new Author();
			author.setAuthorId(authorId);
			authorDao.deleteAuthor(author);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/authors", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public List<Author> getAllAuthors() throws SQLException{
		List<Author> authors = new ArrayList<>();
		try {
			authors = authorDao.getAllAuthors();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return authors;
	}
	
	
	@Transactional
	@RequestMapping(value = "/admin/branches", 
		method = RequestMethod.POST, 
		consumes = "application/json",
		produces = "application/json")
	public ResponseEntity<LibraryBranch> addBranch(@RequestParam(value="name", required=true) String name, 
			@RequestParam(value="address", required=true) String address,
			UriComponentsBuilder ucb) throws SQLException {
		LibraryBranch branch = null;
		int branchId = 0;
		try {
			branch = new LibraryBranch();
			branch.setBranchAddress(address);
			branch.setBranchName(name);
			branchId = lbdao.addBranchWithID(branch);
			List<LibraryBranch> branches = lbdao.getAllBranches();
			branch = this.getBranchByID(branches, branchId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		URI locationUri =
				ucb.path("/admin/branches/")
				.path(String.valueOf(branchId))
				.build()
				.toUri();
		headers.setLocation(locationUri);
		return new ResponseEntity<>(branch, headers, HttpStatus.CREATED);
	}
	
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/branches/{branchId}", 
		method = RequestMethod.PATCH, 
		consumes = "application/json",
		produces = "application/json")
	public LibraryBranch editBranch(@PathVariable int branchId, 
			@RequestParam(value="name", required=false) String branchName,
			@RequestParam(value="address", required=false) String branchAddress)
			throws SQLException {
		LibraryBranch branch = null;
		try {
			List<LibraryBranch> branches = lbdao.getAllBranches();
			LibraryBranch libraryBranch = this.getBranchByID(branches, branchId);
			if (branchName != null && branchName.trim().length() != 0) {
				lbdao.updateBranchName(libraryBranch, branchName);
			}
			if (branchAddress != null && branchAddress.trim().length() != 0) {
				lbdao.updateBranchAddress(libraryBranch, branchAddress);
			}
			branches = lbdao.getAllBranches();
			branch = this.getBranchByID(branches, branchId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return branch;
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/admin/branches/{branchId}", 
			method = RequestMethod.DELETE)
	public void deleteBranch(@PathVariable int branchId) throws SQLException {
		try {
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setBranchId(branchId);
			lbdao.deleteBranch(libraryBranch);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/branches", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public List<LibraryBranch> getAllBranches() throws SQLException{
		List<LibraryBranch> branches = new ArrayList<>();
		try {
			branches = lbdao.getAllBranches();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return branches;
	}
	
	
	@Transactional
	@RequestMapping(value = "/admin/publishers", 
		method = RequestMethod.POST, 
		produces = "application/json")
	public ResponseEntity<Publisher> addPublisher(@RequestBody Publisher sentPublisher,
			UriComponentsBuilder ucb) throws SQLException {
		Publisher publisher = null;
		int publisherId = 0;
		try {
			publisherId = publisherDao.addPublisherWithID(sentPublisher);
			List<Publisher> publishers = publisherDao.getAllPublishers();
			publisher = this.getPublisherByID(publishers, publisherId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		URI locationUri =
				ucb.path("/admin/publishers/")
				.path(String.valueOf(publisherId))
				.build()
				.toUri();
		headers.setLocation(locationUri);
		return new ResponseEntity<>(publisher, headers, HttpStatus.CREATED);
	}
	
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/publishers/{publisherId}", 
		method = RequestMethod.PATCH, 
		produces = "application/json")
	public Publisher editPublisher(@PathVariable int publisherId, 
			@RequestBody Publisher sentPublisher) throws SQLException {
		Publisher publisher = null;
		try {
			String publisherName = sentPublisher.getPublisherName();
			String publisherAddress = sentPublisher.getPublisherAddress();
			String publisherPhone = sentPublisher.getPublisherPhone();
			List<Publisher> publishers = publisherDao.getAllPublishers();
			publisher = this.getPublisherByID(publishers, publisherId);
			if (publisherName != null && publisherName.trim().length() != 0) {
				publisher.setPublisherName(publisherName);
				publisherDao.updateName(publisher);
			}
			if (publisherAddress != null && publisherAddress.trim().length() != 0) {
				publisher.setPublisherAddress(publisherAddress);
				publisherDao.updateAddress(publisher);
			}
			if (publisherPhone != null && publisherPhone.trim().length() != 0) {
				publisher.setPublisherPhone(publisherPhone);
				publisherDao.updatePhone(publisher);
			}
			publisher = this.getPublisherByID(publishers, publisherId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return publisher;
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/admin/publishers/{publisherId}", 
			method = RequestMethod.DELETE)
	public void deletePublisher(@PathVariable int publisherId) throws SQLException {
		try {
			Publisher publisher = new Publisher();
			publisher.setPublisherId(publisherId);
			publisherDao.deletePublisher(publisher);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/publishers", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public List<Publisher> getAllPublishers() throws SQLException{
		List<Publisher> publishers = new ArrayList<>();
		try {
			publishers = publisherDao.getAllPublishers();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return publishers;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/publishers/{publisherId}", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public Publisher getPublisher(@PathVariable int publisherId) throws SQLException{
		Publisher publisher=null;
		try {
			List<Publisher> publishers = publisherDao.getAllPublishers();
			publisher = this.getPublisherByID(publishers, publisherId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return publisher;
	}
	
	
	@Transactional
	@RequestMapping(value = "/admin/books", 
		method = RequestMethod.POST, 
		consumes = "application/json",
		produces = "application/json")
	public ResponseEntity<Book> addBook(@RequestParam String title, 
			@RequestParam(value="publisherId") int publisherId, 
			UriComponentsBuilder ucb) throws SQLException {
		Book book = null;
		int bookId = 0;
		try {
			book = new Book();
			book.setTitle(title);
			if (publisherId != 0) {
				List<Publisher> publishers = publisherDao.getAllPublishers();
				Publisher publisher = this.getPublisherByID(publishers, publisherId);
				book.setPublisher(publisher);
			}
			bookId = bookDao.addBookWithID(book);
			List<Book> books = bookDao.readAllBooks();
			book = this.getBookById(books, bookId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		URI locationUri =
				ucb.path("/admin/books/")
				.path(String.valueOf(bookId))
				.build()
				.toUri();
		headers.setLocation(locationUri);
		return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
	}
	
	@Transactional
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/books/{bookId}/genre", 
		method = RequestMethod.PATCH, 
		consumes = "application/json",
		produces = "application/json")
	public Book addGenreToBook(@PathVariable int bookId, 
			@RequestParam int genreId) throws SQLException{
		Book book = null;
		try {
			List<Book> books = bookDao.readAllBooks();
			book = this.getBookById(books, bookId);
			bookDao.addBookGenres(book, genreId);
			books = bookDao.readAllBooks();
			book = this.getBookById(books, bookId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return book;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/books/{bookId}/author", 
		method = RequestMethod.PATCH, 
		consumes = "application/json",
		produces = "application/json")
	public Book addAuthorToBook(@PathVariable int bookId, @RequestParam int authorId) throws SQLException {
		Book book = null;
		try {
			List<Book> books = bookDao.readAllBooks();
			book = this.getBookById(books, bookId);
			bookDao.addBookAuthors(book, authorId);
			books = bookDao.readAllBooks();
			book = this.getBookById(books, bookId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return book;
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/admin/books/{bookId}", 
			method = RequestMethod.DELETE)
	public void deleteBook(@PathVariable int bookId) throws SQLException {
		try {
			Book book = new Book();
			book.setBookId(bookId);
			bookDao.deleteBook(book);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/books/{bookId}", 
			method = RequestMethod.GET, 
			produces = "application/json")
	public Book getBook(@PathVariable int bookId) throws SQLException{
		Book book=null;
		try {
			List<Book> books = bookDao.readAllBooks();
			book = this.getBookById(books, bookId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return book;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/books/{bookId}", 
		method = RequestMethod.PATCH, 
		consumes = "application/json",
		produces = "application/json")
	public Book editBook(@PathVariable int bookId, 
			@RequestParam(value="title", required=false) String title, 
			@RequestParam(value="publisherId", required=false) int publisherId) throws SQLException{
		Book book = null;
		try {
			List<Book> books = bookDao.readAllBooks();
			book = this.getBookById(books, bookId);
			if (title != null && title.trim().length() != 0) {
				book.setTitle(title);
				bookDao.updateBook(book);
			}
			if (publisherId != 0) {
				List<Publisher> publishers = publisherDao.getAllPublishers();
				Publisher publisher = this.getPublisherByID(publishers, publisherId);
				book.setPublisher(publisher);
				bookDao.updateBookPublisher(book);
			}
			books = bookDao.readAllBooks();
			book = this.getBookById(books, bookId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return book;
	}

	
	@Transactional
	@RequestMapping(value = "/admin/genres", 
		method = RequestMethod.POST, 
		produces = "application/json")
	public ResponseEntity<Genre> addGenre(@RequestParam(value="name") String genre_name, 
			UriComponentsBuilder ucb) throws SQLException {
		Genre genre = null;
		int genreId = 0;
		try {
			genre = new Genre();
			genre.setGenreName(genre_name);
			genreId = genreDao.addGenreWithID(genre);
			List<Genre> genres = genreDao.getAllGenres();
			genre = this.getGenre(genres, genreId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		URI locationUri =
				ucb.path("/admin/genre/")
				.path(String.valueOf(genreId))
				.build()
				.toUri();
		headers.setLocation(locationUri);
		return new ResponseEntity<>(genre, headers, HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/genres/{genreId}", 
		method = RequestMethod.PATCH, 
		produces = "application/json")
	public void editGenre(@PathVariable int genreId, @RequestParam String name) throws SQLException{
		try {
			List<Genre> genres = genreDao.getAllGenres();
			Genre genre = this.getGenre(genres, genreId);
			genre.setGenreName(name);
			genreDao.editGenre(genre);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/admin/genres/{genreId}", 
			method = RequestMethod.DELETE)
	public void deleteGenre(@PathVariable int genreId) throws SQLException {
		try {
			Genre genre = new Genre();
			genre.setGenre_id(genreId);
			genreDao.deleteGenre(genre);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/genres", 
		method = RequestMethod.GET, 
		produces = "application/json")
	public List<Genre> getAllGenres() throws SQLException{
		List<Genre> genres = new ArrayList<>();
		try {
			genres = genreDao.getAllGenres();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return genres;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/loans/dueDate", 
		method = RequestMethod.PATCH,
		produces = "application/json")
	public BookLoan changeDueDate(@RequestBody BookLoan loan) throws SQLException{
		BookLoan bookLoan = null;
		try {
			bldao.changeDueDate(loan);
			List<BookLoan> bookLoans = bldao.getAllBookLoansForBranch(loan.getBranchId());
			bookLoan = this.getBookLoadByDateOut(bookLoans, loan.getBookId(), loan.getCardNo(), loan.getDateOut());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return bookLoan;
	}

	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/admin/loans/current", 
		method = RequestMethod.GET, 
		produces = "application/json")
	public List<BookLoan> getAllCurrentLoansForBranch(@RequestParam int branchId) throws SQLException{
		List<BookLoan> bookLoans = null;
		try {
			bookLoans = bldao.getCurrentLoansForBranch(branchId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return bookLoans;
	}
}
