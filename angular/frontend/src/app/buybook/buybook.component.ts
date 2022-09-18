import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { BookService } from '../services/bookservice/book.service';

@Component({
  selector: 'app-buybook',
  templateUrl: './buybook.component.html',
  styleUrls: ['./buybook.component.scss']
})
export class BuybookComponent implements OnInit {
  book1 : any;
  form: any;
  successMessage: any = "";
  errorMessage: any = "";
  isSuccessful = false;
  
  constructor(public bookService: BookService) { 
    AppComponent.isInitialHome=false;
    this.book1 = this.bookService.book1;
    this.form = this.book1;
  }

  ngOnInit(): void {
  }

  buy(): void{
    console.log("clicked");
    this.book1 = this.form;
    const observable = this.bookService.buyBook(this.book1.id);
    observable.subscribe((response)=>{
      console.log(response);
      if(Number.isFinite(Number(response))){
        this.successMessage = "Book "+this.book1.title+" is purchased successfully";
        this.errorMessage = "";
      }
      else{
        this.errorMessage = JSON.stringify(response);
        this.successMessage = "";
      }
    },
    (error)=>{
      console.log("error :",error);
      if(error.status == 409){
        this.errorMessage = "Book "+this.book1.title+" is already purchased";
      }
      else{
        this.errorMessage = "Failed to purchase book. Please try again later";
      }
      this.successMessage = "";
    })
  }

}
