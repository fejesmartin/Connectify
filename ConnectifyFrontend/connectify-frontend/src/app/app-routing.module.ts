import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PostfeedComponent } from './postfeed/postfeed.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'postfeed', component: PostfeedComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
