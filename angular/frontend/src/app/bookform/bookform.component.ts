import { Component, OnInit } from '@angular/core';
import { BookService } from '../service/book.service';
import Book, { BookCategory } from '../entity/Book';

@Component({
  selector: 'app-bookform',
  templateUrl: './bookform.component.html',
  styleUrls: ['./bookform.component.scss']
})
export class BookformComponent implements OnInit {
  book:Book= new Book('book1_url', 'Book1', BookCategory.ADVENTURE, 1, '', '', 'ABC Publisher', new Date(), 'This is book1 content', true);
  categoryList: BookCategory[] = [];
  bookCategory = BookCategory;
  books: any = []
  title: String = 'Book1';
  category: BookCategory = BookCategory.ADVENTURE;
  author: String = 'David';
  price: number = 1;
  publisher: String = 'ABC Publisher';
  
  constructor(public bookService: BookService) {
    this.categoryList.push(this.bookCategory.ACTION);
    this.categoryList.push(this.bookCategory.ADVENTURE);
   }

  ngOnInit(): void {
  }

  formatDate(date: Date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return day+ "/" + month + "/" + year;
  }

  save(){
    console.log("clicked");
    const observable = this.bookService.saveBook(this.book);
    observable.subscribe((response)=>{
      console.log(response);
    },
    (error)=>{
      alert('Something went wrong'+ error);
      console.log("error :",error);
    })
  }

  searchBooks(){
    console.log("clicked");
    const observable = this.bookService.searchBooks(this.title, this.category, this.author, this.price, this.publisher);
    observable.subscribe((books)=>{
      console.log(books);
      this.books = books;
    })
  }



  // categoryChange(value: any){
  //   this.book.category = this.bookCategory[value] as BookCategory;
  // }
  // selectedCategory(){
  //   alert(this.book.category);
  // }
}
