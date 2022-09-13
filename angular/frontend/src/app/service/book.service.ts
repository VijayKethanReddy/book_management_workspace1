import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Book, { BookCategory } from '.././entity/Book';
const API_URL = 'http://localhost:8082/digitalbooks';
@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(public client: HttpClient) { }

  saveBook(book: Book){
    const authorId = 1;
    return this.client.post(API_URL + "/author/" + 1 + "/books", book);
  }
  
  searchBooks(title: String, category: BookCategory, author: String, price: number, publisher: String){
    return this.client.get(API_URL + "/books/search?title=" + title + "&category=" + category + 
    "&author=" + author + "&price=" + price + "&publisher=" + publisher);
  }
}
