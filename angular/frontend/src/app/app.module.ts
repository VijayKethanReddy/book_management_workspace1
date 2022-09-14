import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { BookformComponent } from './bookform/bookform.component';
import { SavebookComponent } from './savebook/savebook.component';
import { AuthGuardService } from './services/auth-guard/auth-guard.service';
import { RouterModule, Routes } from '@angular/router';
import { GetallauthorbooksComponent } from './getallauthorbooks/getallauthorbooks.component';

const routes:Routes = [
  {path: 'bookform', component: BookformComponent},
  {path: 'savebook', component: SavebookComponent, canActivate: [AuthGuardService]},
  {path: 'getallauthorbooks', component: GetallauthorbooksComponent}
]
@NgModule({
  declarations: [
    AppComponent,
    BookformComponent,
    SavebookComponent,
    GetallauthorbooksComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, FormsModule, HttpClientModule, RouterModule.forRoot(routes)
  ],
  providers: [AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
