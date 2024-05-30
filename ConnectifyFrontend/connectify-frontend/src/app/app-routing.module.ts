import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PostfeedComponent } from './postfeed/postfeed.component';
import { FeedComponent } from './feed/feed.component';
import { VisitUserProfileComponent } from './visit-user-profile/visit-user-profile.component';
import { UserProfileComponent } from './user-profile/user-profile.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'postfeed', component: PostfeedComponent },
  { path: 'feed', component: FeedComponent },
  { path: 'visit/:username', component: VisitUserProfileComponent },
  { path: 'profile/:username', component: UserProfileComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
