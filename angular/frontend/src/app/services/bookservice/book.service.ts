import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Book, { BookCategory } from 'src/app/entity/Book';
const API_URL = 'http://localhost:8082/digitalbooks';
@Injectable({
  providedIn: 'root'
})
export class BookService {
  book:Book= new Book('book1_url', 'Book1', BookCategory.ADVENTURE, 1, '', '', 'ABC Publisher', new Date(), 'This is book1 content', true);
  constructor(public client: HttpClient) { }

  saveBook(title: string, logo: string, category: BookCategory, price: number, authorUserName: string, authorName: string, publisher: string, publishedDate: Date, content: string, active: Boolean){
    const authorId = 1;
    this.book = new Book(logo, title, category, price, authorUserName, authorName, publisher, publishedDate, content, active);
    return this.client.post(API_URL + "/author/" + authorId + "/books", this.book);
  }
  
  searchBooks(title: String, category: BookCategory, author: String, price: number, publisher: String){
    return this.client.get(API_URL + "/books/search?title=" + title + "&category=" + category + 
    "&author=" + author + "&price=" + price + "&publisher=" + publisher);
  }

  getAllAuthorBooks() {
    const authorId = 1;
    return this.client.get(API_URL + "/author/" + authorId + "/allbooks");
  }

}
