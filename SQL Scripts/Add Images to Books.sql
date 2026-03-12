use ssbookshop;
-- show tables;



Update book set image = "images/book-red.jpg" where bookID = 1004;
Update book set image = "images/book-blue.jpg" where bookID = 1005;
Update book set image = "images/book-green.jpg" where bookID in (1002, 1003);
SELECT * FROM book ORDER BY title ASC;

SELECT * FROM book, book_author, author WHERE book.bookID = 1004
 AND book.bookID = book_author.bookID AND author.authorID = book_author.authorID;