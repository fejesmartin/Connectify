import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PostfeedComponent } from './postfeed/postfeed.component';
import { FeedComponent } from './feed/feed.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'postfeed', component: PostfeedComponent },
  { path: 'feed', component: FeedComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
