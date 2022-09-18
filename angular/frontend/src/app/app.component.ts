import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  static isInitialHome =true;
  referenceName = AppComponent;
  private roles: string[] = [];
  isLoggedIn = false;
  showSearchBooks = false;
  showPurchasedBooks = false;
  showCreateBooks = false;
  showGetAllAuthorBooks = false;
  username?: string;

  constructor(private router: Router, private tokenStorageService: TokenStorageService) { 
    AppComponent.isInitialHome=true;
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showSearchBooks = this.roles.includes('ROLE_READER');
      this.showPurchasedBooks = this.roles.includes('ROLE_READER');
      this.showCreateBooks = this.roles.includes('ROLE_AUTHOR');
      this.showGetAllAuthorBooks = this.roles.includes('ROLE_AUTHOR');

      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

}
